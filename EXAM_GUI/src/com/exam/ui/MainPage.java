package src.com.exam.ui;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import javax.swing.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import src.com.exam.model.CurrentExam;
import src.com.exam.model.ExamAnswers;
import src.com.exam.model.Student;
import src.com.exam.service.CurrentExamService;
import src.com.exam.service.StudentService;

public class MainPage extends JFrame {

    private JPanel mainPanel;
    public CardLayout cardLayout;

    private String host = "http://localhost:8080/";
    private String frontendhost = "http://localhost:3000/";

    //parameters (Student s, CurrentExam currentExam)
    public MainPage() {

        /* ================= FRAME SETTINGS ================= */

        setTitle("SVES QUIZ APP");
        setUndecorated(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setAlwaysOnTop(true);

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        setMinimumSize(new Dimension(1200, 700));
        setLocationRelativeTo(null);

        //System.out.println(s.getUsername());
        //System.out.println(currentExam.getSubject());

        /* ================= SERVICES ================= */

        StudentService service = new StudentService();
        Student s = service.getStudentFromWeb();

        CurrentExamService currentExamService = new CurrentExamService();
        CurrentExam currentExam = currentExamService.GetCurrentExam();

        ExamAnswers examanswers = new ExamAnswers();

        /* ================= MAIN PANEL ================= */

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        /* ================= REVIEW PANEL (CREATE ONLY ONCE) ================= */

        ExamReviewPanel review = new ExamReviewPanel(
                e -> cardLayout.show(mainPanel, "EXAM"),
                e -> cardLayout.show(mainPanel, "SUBMITTED"),
                examanswers,
                host,
                s.getToken(),
                s,
                currentExam
        );

        /* ================= PANELS ================= */

        InfoPanel infoPanel =
                new InfoPanel(
                        e -> cardLayout.show(mainPanel, "INSTRUCTION"),
                        s,
                        currentExam,
                        host
                );

        InstructionPanel instructionPanel =
                new InstructionPanel(
                        e -> cardLayout.show(mainPanel, "INFO"),
                        e -> cardLayout.show(mainPanel, "EXAM")
                );

        ExamPanel examPanel =
        new ExamPanel(
                e -> {
                    review.refreshTable();
                    cardLayout.show(mainPanel, "REVIEW");
                },
                s,
                currentExam,
                examanswers,
                host
        );

        ExamSubmitted PanelSubmitted = new ExamSubmitted();

        /* ================= ADD PANELS ================= */

        mainPanel.add(infoPanel, "INFO");
        mainPanel.add(instructionPanel, "INSTRUCTION");
        mainPanel.add(examPanel, "EXAM");
        mainPanel.add(review, "REVIEW");
        mainPanel.add(PanelSubmitted, "SUBMITTED");

        add(mainPanel);

        /* ================= AUTH CHECK ================= */

        if (s == null || s.getUsername() == null || s.getUsername().isEmpty()) {

            JLabel message = new JLabel(
                    "<html><i>Not Authenticated. Please access via "
                            + "<a href=''>" + frontendhost + "</a>.</i></html>"
            );

            message.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            message.addMouseListener(new java.awt.event.MouseAdapter() {

                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {

                    try {

                        Desktop.getDesktop().browse(new java.net.URI(frontendhost));

                        dispose();
                        System.exit(0);

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });

            JOptionPane.showMessageDialog(
                    this,
                    message,
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            dispose();
            System.exit(0);
        }

        else {
            cardLayout.show(mainPanel, "INFO");
        }

        /* ================= BLOCK ESC ================= */

        getRootPane()
                .getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke("ESCAPE"), "none");

        /* ================= BLOCK ALT + F4 ================= */

        getRootPane()
                .getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_DOWN_MASK), "none");

        /* ================= BLOCK ALT + TAB ================= */

        KeyboardFocusManager
                .getCurrentKeyboardFocusManager()
                .addKeyEventDispatcher(e -> {

                    if (e.getKeyCode() == KeyEvent.VK_TAB && e.isAltDown())
                        return true;

                    if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
                        return true;

                    return false;
                });

        /* ================= WINDOW SWITCH DETECTION ================= */

        addWindowFocusListener(new WindowAdapter() {

            @Override
            public void windowLostFocus(WindowEvent e) {

                Window active =
                        KeyboardFocusManager
                                .getCurrentKeyboardFocusManager()
                                .getActiveWindow();

                if (active instanceof JDialog) {
                    return;
                }

                JOptionPane.showMessageDialog(
                        MainPage.this,
                        "Switching applications is not allowed during exam!"
                );

                System.exit(0);
            }
        });

        setVisible(true);
    }

    /* ================= MAIN ================= */

    public static void main(String[] args) {
        new MainPage();

    //  try {

    //      String studentJson =
    //              URLDecoder.decode(args[0], StandardCharsets.UTF_8);

    //      String examJson =
    //              URLDecoder.decode(args[1], StandardCharsets.UTF_8);

    //      ObjectMapper mapper = new ObjectMapper();

    //      Student student =
    //              mapper.readValue(studentJson, Student.class);

    //      CurrentExam exam =
    //              mapper.readValue(examJson, CurrentExam.class);

    //      SwingUtilities.invokeLater(() -> {
    //          new MainPage(student, exam);
    //      });

    //  } catch (Exception e) {
    //      e.printStackTrace();
    //  }
}
}