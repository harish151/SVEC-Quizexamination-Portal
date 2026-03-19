package com.exam.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.exam.model.CurrentExam;
import com.exam.model.ExamAnswers;
import com.exam.model.Student;
import com.exam.service.SubmitAnswers;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class ExamReviewPanel extends JPanel {

    private JTable table;
    private DefaultTableModel model;

    private ExamAnswers examanswers;

    private String host;
    private String token;
    private Student student;
    private CurrentExam currentexam;

    public ExamReviewPanel(ActionListener backAction,
                           ActionListener forwardAction,
                           ExamAnswers examanswers,
                           String host,
                           String token,
                           Student student,
                           CurrentExam currentexam){

        this.examanswers = examanswers;
        this.host = host;
        this.token = token;
        this.student = student;
        this.currentexam = currentexam;

        setLayout(new BorderLayout());

        /* -------- TABLE -------- */

        model = new DefaultTableModel(
                new Object[]{"Type", "Count"}, 0
        );

        table = new JTable(model);
        table.setRowHeight(40);
        table.setEnabled(false);

        JScrollPane scrollPane = new JScrollPane(table);

        scrollPane.setPreferredSize(new Dimension(350,120));

        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        /* -------- BUTTONS -------- */

        JButton cancel = new JButton("Cancel");
        JButton submit = new JButton("Submit");

        cancel.addActionListener(backAction);

        submit.addActionListener(e -> {

            List<String> originalans = examanswers.getOriginalans();
            List<String> answers = examanswers.getAnswers();

            new SubmitAnswers(
                    host,
                    token,
                    student,
                    currentexam,
                    originalans,
                    answers
            );

            forwardAction.actionPerformed(e);
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(cancel);
        buttonPanel.add(submit);

        /* -------- CENTER PANEL -------- */

        JPanel centerPanel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        // TABLE
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0,0,15,0); // space between table and buttons
        centerPanel.add(scrollPane, gbc);

        // BUTTONS (slightly below table)
        gbc.gridy = 1;
        gbc.insets = new Insets(0,0,0,0);
        centerPanel.add(buttonPanel, gbc);

        add(centerPanel, BorderLayout.CENTER);

        refreshTable();
    }

    public void refreshTable() {

        List<String> answers = examanswers.getAnswers();

        if(answers == null) return;

        int answered = 0;
        int unanswered = 0;

        for(String ans : answers){
            if(ans == null || ans.isEmpty()){
                unanswered++;
            } else{
                answered++;
            }
        }

        model.setRowCount(0);

        model.addRow(new Object[]{"Answered", answered});
        model.addRow(new Object[]{"Unanswered", unanswered});

        revalidate();
        repaint();
    }
}