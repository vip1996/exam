package chack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ToDoFrame extends JFrame {
    private JButton createBtn, updateBtn, deleteBtn, completeBtn, closeDBBtn;
    private JTextField descrField, titleField, dateField, compField, idField;
    private JTextArea dataArea;

    public ToDoFrame() {
        super("ToDo");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Set Nimbus Look & Feel
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    SwingUtilities.updateComponentTreeUI(this);
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Nimbus Look & Feel not available, using default.");
        }

        // Main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Input fields panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(5, 2, 5, 5));

        idField = new JTextField("ID");
        titleField = new JTextField("titleTextField");
        descrField = new JTextField("descrTextField");
        dateField = new JTextField("date");
        compField = new JTextField("status");

        inputPanel.add(new JLabel("ID:"));
        inputPanel.add(idField);
        inputPanel.add(new JLabel("Title:"));
        inputPanel.add(titleField);
        inputPanel.add(new JLabel("Description:"));
        inputPanel.add(descrField);
        inputPanel.add(new JLabel("Date:"));
        inputPanel.add(dateField);
        inputPanel.add(new JLabel("Status:"));
        inputPanel.add(compField);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new GridLayout(2, 3, 10, 10));
        createBtn = new JButton("Create");
        updateBtn = new JButton("Update");
        deleteBtn = new JButton("Delete");
        completeBtn = new JButton("Complete");
        closeDBBtn = new JButton("CloseDB");

        buttonPanel.add(createBtn);
        buttonPanel.add(updateBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(completeBtn);
        buttonPanel.add(closeDBBtn);

        // Text area for displaying data
        dataArea = new JTextArea(10, 30);
        dataArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(dataArea);

        // Compose main panel
        JPanel leftPanel = new JPanel(new BorderLayout(10, 10));
        leftPanel.add(inputPanel, BorderLayout.NORTH);
        leftPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        setContentPane(mainPanel);
        setSize(800, 400);
        setResizable(false);
        setLocationRelativeTo(null);

        // Action listeners using lambdas
        createBtn.addActionListener((ActionEvent e) -> {
            try {
                SqlManager sq = new SqlManager();
                sq.createTable();
                dataArea.setText(sq.listAllTasks());
            } catch (Exception ex) {
                dataArea.setText("Failed to create table: " + ex.getMessage());
            }
        });

        deleteBtn.addActionListener((ActionEvent e) -> {
            try {
                SqlManager sq = new SqlManager();
                int id = Integer.parseInt(idField.getText().trim());
                sq.delete(id);
                dataArea.setText(sq.listAllTasks());
            } catch (Exception ex) {
                dataArea.setText("Failed to delete: " + ex.getMessage());
            }
        });

        updateBtn.addActionListener((ActionEvent e) -> {
            try {
                SqlManager sq = new SqlManager();
                int id = Integer.parseInt(idField.getText().trim());
                String title = titleField.getText().trim();
                String description = descrField.getText().trim();
                String date = dateField.getText().trim();
                boolean completed = compField.getText().trim().equalsIgnoreCase("true") || compField.getText().trim().equals("1");
                sq.update(id, description, title, date, completed);
                dataArea.setText(sq.listAllTasks());
            } catch (Exception ex) {
                dataArea.setText("Failed to update: " + ex.getMessage());
            }
        });

        completeBtn.addActionListener((ActionEvent e) -> {
            try {
                SqlManager sq = new SqlManager();
                dataArea.setText(sq.listAllTasks());
            } catch (Exception ex) {
                dataArea.setText("Failed to load tasks: " + ex.getMessage());
            }
        });

        closeDBBtn.addActionListener((ActionEvent e) -> {
            try {
                SqlManager sq = new SqlManager();
                sq.closeDb();
                dataArea.setText("DB connection closed successfully.");
            } catch (Exception ex) {
                dataArea.setText("Failed to close DB connection: " + ex.getMessage());
            }
        });
    }
}