package src.com.exam.ui;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

import src.com.exam.model.CurrentExam;
import src.com.exam.model.Student;

public class InfoPanel extends JPanel {

    public InfoPanel(ActionListener startExamAction, Student s, CurrentExam currentExam, String host) {

        setLayout(new BorderLayout());

        // ---------------- TOP SECTION ----------------
        JPanel topsection = new JPanel(new BorderLayout(20, 0));
        topsection.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        topsection.setBackground(Color.WHITE);

        // LEFT COLUMN - IMAGE
        JPanel imageLogoPanel = new JPanel(new BorderLayout());
        imageLogoPanel.setBackground(Color.WHITE);
        ImageIcon icon = new ImageIcon("resources/logo.png");
        Image originalImage = icon.getImage();
        int newWidth = 250;
        int newHeight = (originalImage.getHeight(null) * newWidth) / originalImage.getWidth(null);
        Image scaledImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
        imageLogoPanel.add(imageLabel, BorderLayout.NORTH);
        imageLogoPanel.setPreferredSize(new Dimension(newWidth, newHeight));

        // RIGHT COLUMN
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBackground(Color.WHITE);

        // Row 1 - Title
        JPanel textPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        textPanel.setBackground(Color.WHITE);

        JLabel centerText = new JLabel("SVES QUIZ EXAMINATION");
        centerText.setFont(new Font("Arial", Font.BOLD, 20));

        textPanel.add(centerText,BorderLayout.WEST);

        // move text ~40% from left
        textPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int leftGap = (int) (textPanel.getWidth() * 0.31);
                centerText.setBorder(
                    BorderFactory.createEmptyBorder(0, leftGap, 0, 0)
                );
            }
        });

        rightPanel.add(textPanel);

        // Row 2 - Student details

        JPanel valuesPanel = new JPanel(new GridBagLayout());
        valuesPanel.setBackground(Color.WHITE);
        //valuesPanel.setBorder(new LineBorder(Color.BLUE, 2)); // debug
        valuesPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 100, 20, 300); // spacing between columns
        gbc.anchor = GridBagConstraints.WEST;

        // Row 1
        gbc.gridx = 0; gbc.gridy = 0;
        valuesPanel.add(new JLabel("<html><i><h3>Name: " + s.getName()+ "</h3></i></html>"), gbc);

        gbc.gridx = 1;
        valuesPanel.add(new JLabel("<html><i><h3>Username: " + s.getUsername()+ "</h3></i></html>"), gbc);

        // Row 2
        gbc.gridx = 0; gbc.gridy = 1;
        valuesPanel.add(new JLabel("<html><i><h3>Branch: " + s.getBranch()+ "</h3></i></html>"), gbc);

        gbc.gridx = 1;
        valuesPanel.add(new JLabel("<html><i><h3>Semester: " + s.getSemester()+ "</h3></i></html>"), gbc);


        rightPanel.add(valuesPanel);

        topsection.add(imageLogoPanel, BorderLayout.WEST);
        topsection.add(rightPanel, BorderLayout.CENTER);
        topsection.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));

        add(topsection, BorderLayout.NORTH);

        // ---------------- CENTER SECTION - EXAM TABLE ----------------

        String[] columns = {"COURSE CODE", "COURSE NAME", "TIMING"};
        Object[][] data = {
            {currentExam.getCoursecode(), currentExam.getSubject(), currentExam.getStartTime() + " - " + currentExam.getEndTime()}
        };

        JTable table = new JTable(data, columns);
        table.setRowHeight(32);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        headerRenderer.setBackground(new Color(175, 175, 175));
        headerRenderer.setForeground(Color.BLACK);
        headerRenderer.setFont(new Font("Arial", Font.BOLD, 17));
        headerRenderer.setOpaque(true);
        table.getTableHeader().setDefaultRenderer(headerRenderer);
        table.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        table.setBackground(Color.white);

        JScrollPane scrollPane = new JScrollPane(table);
        int tableWidth = 900;
        int tableHeight = 21 + table.getRowHeight() * table.getRowCount();
        scrollPane.setPreferredSize(new Dimension(tableWidth, tableHeight));
        scrollPane.setBackground(Color.white);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc1 = new GridBagConstraints();
        gbc1.gridx = 0;
        gbc1.gridy = 0;
        gbc1.anchor = GridBagConstraints.CENTER;
        gbc1.insets = new Insets(0, 0, 12, 0);
        centerPanel.add(scrollPane, gbc1);

        // ---------------- START BUTTON ----------------
        JButton startExamButton = new JButton("NEXT");
        if(currentExam.getCoursecode() == null || currentExam.getCoursecode().isEmpty()){
            startExamButton.setEnabled(false);
            startExamButton.setBackground(Color.lightGray);
        }
        else{
            startExamButton.setFont(new Font("Arial", Font.BOLD, 14));
            startExamButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            startExamButton.setBackground(new Color(0, 123, 255));
            startExamButton.setForeground(Color.WHITE);
            startExamButton.setOpaque(true);
            startExamButton.setFocusPainted(false);
            startExamButton.setMargin(new Insets(8, 22, 8, 22));
            startExamButton.addActionListener(startExamAction);
        }

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(startExamButton);

        gbc1.gridy = 1;
        gbc1.insets = new Insets(0, 0, 0, 0);
        centerPanel.add(buttonPanel, gbc1);
        centerPanel.setBackground(Color.white);

        add(centerPanel, BorderLayout.CENTER);
    }
}