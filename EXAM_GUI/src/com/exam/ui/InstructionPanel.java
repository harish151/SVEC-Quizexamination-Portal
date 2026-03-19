package src.com.exam.ui;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class InstructionPanel extends JPanel {

    public InstructionPanel(ActionListener backAction, ActionListener nextAction) {
         setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(20, 40, 20, 40));

        // ---------------- TITLE ----------------
        JLabel title = new JLabel("EXAM INSTRUCTIONS");
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBorder(new EmptyBorder(10, 0, 20, 0));

        add(title, BorderLayout.NORTH);

        // ---------------- INSTRUCTIONS TEXT ----------------
        JTextArea instructions = new JTextArea();
        instructions.setEditable(false);
        instructions.setLineWrap(true);
        instructions.setWrapStyleWord(true);
        instructions.setFont(new Font("Arial", Font.PLAIN, 17));
        instructions.setBackground(Color.WHITE);

        instructions.setText(
            "\n1. This Examination consists of a total of 20 MCQs. Each Question has Four Options, out of which only one option is Correct.\n\n" +
            "2. The Total duration of the Examination is 20 minutes. The Timer will start immediately after you click the 'Start' button.\n\n" +
            "3. Each Question carries 0.5 marks. The Maximum score for the Examination is 10 marks.\n\n" +
            "4. There is No negative marking for Incorrect answers. You are encouraged to attempt all questions.\n\n" +
            "5. If you do not answer a question, it will be considered unanswered and no marks will be awarded for that question.\n\n" +
            "6. The Examination will be automatically submitted once the 20 minute time limit is completed. No extra time will be provided under any circumstances.\n\n" +
            "7. You can navigate between questions using the navigation buttons provided. You may review and change your answers before final submission.\n\n" +
            "8. Once you click the 'Submit' button, your responses will be permanently recorded and cannot be modified.\n\n" +
            "9. Ensure that you have a stable internet connection throughout the examination to avoid unexpected disruptions.\n\n" +
            "10. Do not refresh, close, minimize, or switch away from the examination window during the test.\n\n" +
            "11. Any attempt to open new tabs, switch applications, take screenshots, or perform suspicious activities may be treated as malpractice.\n\n" +
            "12. If any malpractice or unfair means is detected during the examination, your test will be immediately cancelled and reported to the authorities.\n\n" +
            "13. You must complete the examination independently. Seeking help from other individuals or external resources is strictly prohibited.\n\n" +
            "14. Make sure your system is fully charged (if using a laptop) and properly connected to power before starting the exam.\n\n" +
            "15. In case of system Failure or Internet Disconnection, immediately inform your exam invigilator.\n\n" +
            "16. If you face any technical issues during the examination, contact your exam invigilator without delay.\n\n" +
            "17. Keep your exam environment quiet and free from distractions throughout the examination duration.\n\n" +
            "18. By clicking the 'I Agree' checkbox and proceeding to the exam, you confirm that you have read, understood, and agree to follow all the above rules and regulations."
            );

        JScrollPane scrollPane = new JScrollPane(instructions);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        scrollPane.setPreferredSize(new Dimension(800, 300));
        // Disable both scrollbars
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        add(scrollPane, BorderLayout.CENTER);

        // ---------------- CHECKBOX ----------------
        JCheckBox agreeCheckBox = new JCheckBox(
                "I have read and agree to the exam instructions and policy terms"
        );
        agreeCheckBox.setBackground(Color.WHITE);
        agreeCheckBox.setFont(new Font("Arial", Font.PLAIN, 14));

        // ---------------- BUTTONS ----------------
        JButton backButton = new JButton("BACK");
        JButton nextButton = new JButton("START");

        //backbutton styling
        backButton.setFont(new Font("Arial", Font.BOLD, 13));
        backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backButton.setBackground(new Color(0, 123, 255));
        backButton.setForeground(Color.WHITE);
        backButton.setOpaque(true);
        backButton.setFocusPainted(false);
        backButton.setMargin(new Insets(8, 22, 8, 22));
        //nextbutton styling
        nextButton.setFont(new Font("Arial", Font.BOLD, 13));
        nextButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        nextButton.setBackground(new Color(0, 123, 255));
        nextButton.setForeground(Color.WHITE);
        nextButton.setOpaque(true);
        nextButton.setFocusPainted(false);
        nextButton.setMargin(new Insets(8, 22, 8, 22));

        nextButton.setEnabled(false); // disabled by default

        // Enable NEXT only when checkbox is checked
        agreeCheckBox.addActionListener(e ->
                nextButton.setEnabled(agreeCheckBox.isSelected())
        );

        backButton.addActionListener(backAction);
        nextButton.addActionListener(nextAction);

        // ---------------- BOTTOM PANEL ----------------
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBorder(new EmptyBorder(15, 0, 0, 0));

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.setBackground(Color.WHITE);
        leftPanel.add(agreeCheckBox);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setBackground(Color.WHITE);
        rightPanel.add(backButton);
        rightPanel.add(nextButton);

        bottomPanel.add(leftPanel, BorderLayout.WEST);
        bottomPanel.add(rightPanel, BorderLayout.EAST);

        add(bottomPanel, BorderLayout.SOUTH);
    }
}
