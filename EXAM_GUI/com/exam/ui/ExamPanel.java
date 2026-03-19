package src.com.exam.ui;

import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import src.com.exam.model.CurrentExam;
import src.com.exam.model.ExamAnswers;
import src.com.exam.model.Student;
import src.com.exam.service.GetExamQuestionFromWeb;
import src.com.exam.service.SubmitAnswers;
import src.com.exam.service.UpdateQueProgress;

public class ExamPanel extends JPanel {

    private ArrayList<Map<String, Object>> questions;
    private ArrayList<String> originalans = new ArrayList<>();
    private ArrayList<String> answers;
    private JButton[] questionButtons;
    private Set<Integer> reviewSet = new HashSet<>();
    private int currentIndex = 0;
    private int remainingSeconds;
    private JLabel timerLabel;
    private JLabel questionText;
    private JLabel questionImage;
    private JPanel optionPanel;
    private ButtonGroup optionGroup;
    private Timer timer;
    private boolean examSubmitted = false;
    private String host;
    private ExamAnswers examData;

    // Color constants
    private final Color COLOR_ANSWERED = Color.GREEN;
    private final Color COLOR_UNANSWERED = Color.LIGHT_GRAY;
    private final Color COLOR_REVIEW = new Color(128, 0, 128);
    private final Color COLOR_UNSAVED = Color.RED; // for unanswered questions after Save

    public ExamPanel(ActionListener forwardAction,
                     Student student,
                     CurrentExam exam,
                     ExamAnswers examData,
                     String host) {

        this.host = host;
        this.examData = examData;

        setLayout(new BorderLayout());

        // ------------------ Load Questions ------------------
        GetExamQuestionFromWeb service = new GetExamQuestionFromWeb();
        questions = new ArrayList<>(service.getExamQuestions(
                host,
                student.getToken(),
                student.getBranch(),
                student.getUsername(),
                exam.getCoursecode(),
                exam.getExamtype(),
                student.getBatch()
        ));

        int totalQuestions = questions.size();
        answers = new ArrayList<>(Collections.nCopies(totalQuestions, null));

        for (int i = 0; i < totalQuestions; i++) {
            String ans = (String) questions.get(i).get("answer");
            originalans.add(ans);
            answers.set(i, (String) questions.get(i).get("selectedopt"));
        }

        examData.setOriginalans(originalans);
        examData.setAnswers(answers);

        remainingSeconds = totalQuestions * 60; // 1 min per question

        // ------------------ HEADER ------------------
        JPanel header = new JPanel(new BorderLayout());
        timerLabel = new JLabel("", SwingConstants.RIGHT);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        header.add(timerLabel, BorderLayout.EAST);
        header.setBorder(new EmptyBorder(5, 10, 5, 10));
        add(header, BorderLayout.NORTH);

        startTimer(student.getToken(), student, exam, forwardAction);

        // ------------------ MAIN PANEL ------------------
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Question area
        JPanel questionPanel = new JPanel();
        questionPanel.setLayout(new BoxLayout(questionPanel, BoxLayout.Y_AXIS));
        questionPanel.setBorder(new EmptyBorder(10, 15, 10, 15));
        questionText = new JLabel();
        questionText.setFont(new Font("Arial", Font.BOLD, 16));
        questionText.setAlignmentX(Component.LEFT_ALIGNMENT);
        questionImage = new JLabel();
        questionImage.setAlignmentX(Component.LEFT_ALIGNMENT);
        questionPanel.add(questionText);
        questionPanel.add(Box.createVerticalStrut(5));
        questionPanel.add(questionImage);
        questionPanel.add(Box.createVerticalStrut(10));
        optionPanel = new JPanel();
        optionPanel.setLayout(new BoxLayout(optionPanel, BoxLayout.Y_AXIS));
        optionPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        questionPanel.add(optionPanel);

        JScrollPane scroll = new JScrollPane(questionPanel);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        // Navigation buttons panel
        JPanel navPanel = new JPanel(new GridLayout(0, 5, 3, 3));
        navPanel.setPreferredSize(new Dimension(180, 0));
        navPanel.setBorder(BorderFactory.createTitledBorder("Questions"));
        questionButtons = new JButton[totalQuestions];
        for (int i = 0; i < totalQuestions; i++) {
            int index = i;
            JButton btn = new JButton(String.valueOf(i + 1));
            btn.setFont(new Font("Arial", Font.BOLD, 9));
            btn.setMargin(new Insets(1, 1, 1, 1));
            btn.setPreferredSize(new Dimension(30, 30));
            btn.setBackground(answers.get(i) != null ? COLOR_ANSWERED : COLOR_UNANSWERED);
            btn.addActionListener(e -> loadQuestion(index, student.getToken(), student));
            questionButtons[i] = btn;
            navPanel.add(btn);
        }

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scroll, navPanel);
        split.setResizeWeight(0.8);
        mainPanel.add(split, BorderLayout.CENTER);
        add(mainPanel, BorderLayout.CENTER);

        // ------------------ FOOTER ------------------
        JPanel footer = new JPanel(new BorderLayout());
        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        JButton clearBtn = new JButton("Clear Response");
        JButton reviewBtn = new JButton("Mark For Review");
        clearBtn.addActionListener(e -> clearAnswer());
        reviewBtn.addActionListener(e -> {
            reviewSet.add(currentIndex);
            questionButtons[currentIndex].setBackground(COLOR_REVIEW);
        });
        left.add(clearBtn);
        left.add(reviewBtn);

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        JButton saveNext = new JButton("Save & Next");
        JButton submit = new JButton("Submit");
        saveNext.addActionListener(e -> saveNext(student.getToken(), student));
        submit.addActionListener(e -> {
            examData.setOriginalans(originalans);
            examData.setAnswers(answers);
            forwardAction.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "submit"));
        });
        right.add(saveNext);
        right.add(submit);

        footer.add(left, BorderLayout.WEST);
        footer.add(right, BorderLayout.EAST);
        footer.setBorder(new EmptyBorder(5, 10, 5, 10));
        add(footer, BorderLayout.SOUTH);

        loadQuestion(0, student.getToken(), student);
    }

    // ------------------ IMAGE LOADER ------------------
    private ImageIcon loadImage(String url, int w, int h) {
        try {
            ImageIcon icon = new ImageIcon(new URL(url));
            Image img = icon.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        } catch (Exception e) {
            return null;
        }
    }

    // ------------------ TIMER ------------------
    private void startTimer(String token, Student student, CurrentExam exam, ActionListener forwardAction) {
        timer = new Timer(1000, e -> {
            remainingSeconds--;
            int min = remainingSeconds / 60;
            int sec = remainingSeconds % 60;
            timerLabel.setText(String.format("Time Left : %02d:%02d", min, sec));
            if (remainingSeconds <= 0) {
                timer.stop();
                JOptionPane.showMessageDialog(this, "Time is up! Exam auto submitted.");

                // Update examData
                examData.setOriginalans(originalans);
                examData.setAnswers(answers);

                // Forward action
                forwardAction.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "autoSubmit"));
            }
        });
        timer.start();
    }

    // ------------------ LOAD QUESTION ------------------
    @SuppressWarnings("unchecked")
    private void loadQuestion(int index, String token, Student student) {
        currentIndex = index;
        Map<String, Object> q = questions.get(index);

        questionText.setText("<html><div style='text-align:left;'>Q" + (index + 1) + ". " + q.get("question") + "</div></html>");
        String qimg = (String) q.get("questionurl");
        if (qimg != null && !qimg.isEmpty()) {
            questionImage.setIcon(loadImage(qimg, 450, 220));
            questionImage.setText("");
        } else {
            questionImage.setIcon(null);
            questionImage.setText("No Image");
        }

        optionPanel.removeAll();
        optionGroup = new ButtonGroup();

        List<String> textOpts = (List<String>) q.get("options");
        List<String> imgOpts = q.containsKey("optimgurl") ? (List<String>) q.get("optimgurl") : null;

        String selectedOpt = answers.get(index);

        for (int i = 0; i < 4; i++) {
            String optText = (textOpts != null && textOpts.size() > i) ? textOpts.get(i) : "";
            JRadioButton radio = new JRadioButton(optText);
            radio.setAlignmentX(Component.LEFT_ALIGNMENT);

            if (selectedOpt != null && selectedOpt.equals(optText)) {
                radio.setSelected(true);
            }

            int current = index;
            radio.addActionListener(e -> {
                answers.set(current, optText);
                examData.setAnswers(answers);
                new UpdateQueProgress(questions.get(current), optText, host, student.getToken());
                // Do NOT change button color here
            });

            JPanel opt = new JPanel();
            opt.setLayout(new BoxLayout(opt, BoxLayout.Y_AXIS));
            opt.setAlignmentX(Component.LEFT_ALIGNMENT);
            opt.add(radio);

            if (imgOpts != null && imgOpts.size() > i && imgOpts.get(i) != null && !imgOpts.get(i).isEmpty()) {
                JLabel img = new JLabel(loadImage(imgOpts.get(i), 300, 120));
                img.setAlignmentX(Component.LEFT_ALIGNMENT);
                opt.add(img);
            }

            optionGroup.add(radio);
            optionPanel.add(opt);
            optionPanel.add(Box.createVerticalStrut(20));
        }

        // Update navigation buttons (preserve review)
        for (int i = 0; i < questionButtons.length; i++) {
            JButton btn = questionButtons[i];
            if (reviewSet.contains(i)) {
                btn.setBackground(COLOR_REVIEW);
            } else if (answers.get(i) != null) {
                btn.setBackground(COLOR_ANSWERED);
            } else {
                btn.setBackground(COLOR_UNANSWERED);
            }
        }

        optionPanel.revalidate();
        optionPanel.repaint();
    }

    private void clearAnswer() {
        optionGroup.clearSelection();
        answers.set(currentIndex, null);
        reviewSet.remove(currentIndex);
        questionButtons[currentIndex].setBackground(COLOR_UNANSWERED);
        examData.setAnswers(answers);
    }

    private void saveNext(String token, Student student) {
        JButton btn = questionButtons[currentIndex];

        // Set button color based on answer
        if (reviewSet.contains(currentIndex)) {
            btn.setBackground(COLOR_REVIEW);
        } else if (answers.get(currentIndex) != null && !answers.get(currentIndex).isEmpty()) {
            btn.setBackground(COLOR_ANSWERED); // GREEN
        } else {
            btn.setBackground(COLOR_UNSAVED); // RED
        }

        // Move to next question
        if (currentIndex < questions.size() - 1) {
            loadQuestion(currentIndex + 1, token, student);
        }
    }
}