package com.exam.ui;

import javax.swing.*;
import java.awt.*;

public class ExamSubmitted extends JPanel {

    public ExamSubmitted() {

        setLayout(new BorderLayout());

        JLabel text = new JLabel("Exam Submitted", SwingConstants.CENTER);
        text.setFont(new Font("Arial", Font.BOLD, 30));

        JButton finishBtn = new JButton("Finish");
        finishBtn.setPreferredSize(new Dimension(150,50));

        finishBtn.addActionListener(e -> {
            System.exit(0);
        });

        // Center panel containing both text and button
        JPanel center = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0,0,20,0); // space between text and button
        center.add(text, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(0,0,0,0);
        center.add(finishBtn, gbc);

        add(center, BorderLayout.CENTER);
    }
}