package com.main;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DailyScheduler extends JFrame {
    private JTextField titleField, descriptionField, dateField, timeField;
    private JTable taskTable;
    private DefaultTableModel tableModel;
    private List<Task> taskList;
    private File taskFile;

    public DailyScheduler() {
        setTitle("Daily Scheduler");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null); // Absolute layout

        // Initialize task list
        taskList = new ArrayList<>();
        taskFile = new File("tasks.txt"); // Define the file location

        // Ensure the file exists
        createFileIfNotExists();
        loadTasks();

        // Title Label and Field
        JLabel titleLabel = new JLabel("Title:");
        titleLabel.setBounds(20, 20, 100, 25);
        add(titleLabel);

        titleField = new JTextField();
        titleField.setBounds(120, 20, 200, 25);
        add(titleField);

        // Description Label and Field
        JLabel descriptionLabel = new JLabel("Description:");
        descriptionLabel.setBounds(20, 60, 100, 25);
        add(descriptionLabel);

        descriptionField = new JTextField();
        descriptionField.setBounds(120, 60, 200, 25);
        add(descriptionField);

        // Date Label and Field
        JLabel dateLabel = new JLabel("Date (YYYY-MM-DD):");
        dateLabel.setBounds(20, 100, 150, 25);
        add(dateLabel);

        dateField = new JTextField();
        dateField.setBounds(170, 100, 150, 25);
        add(dateField);

        // Time Label and Field
        JLabel timeLabel = new JLabel("Time (HH:MM):");
        timeLabel.setBounds(20, 140, 150, 25);
        add(timeLabel);

        timeField = new JTextField();
        timeField.setBounds(170, 140, 150, 25);
        add(timeField);

        // Add Task Button
        JButton addButton = new JButton("Add Task");
        addButton.setBounds(20, 180, 100, 30);
        add(addButton);
        addButton.addActionListener(e -> addTask());

        // Delete Task Button
        JButton deleteButton = new JButton("Delete Task");
        deleteButton.setBounds(140, 180, 120, 30);
        add(deleteButton);
        deleteButton.addActionListener(e -> deleteTask());

        // Clear Fields Button
        JButton clearButton = new JButton("Clear Fields");
        clearButton.setBounds(280, 180, 120, 30);
        add(clearButton);
        clearButton.addActionListener(e -> clearFields());

        // Task Table
        tableModel = new DefaultTableModel(new String[]{"Title", "Description", "Date", "Time"}, 0);
        taskTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(taskTable);
        scrollPane.setBounds(20, 230, 540, 200);
        add(scrollPane);

        updateTaskList();
    }

    private void addTask() {
        try {
            String title = titleField.getText().trim();
            String description = descriptionField.getText().trim();
            String date = dateField.getText().trim();
            String time = timeField.getText().trim();

            if (title.isEmpty() || description.isEmpty() || date.isEmpty() || time.isEmpty()) {
                throw new IllegalArgumentException("All fields must be filled.");
            }

            Task task = new Task(title, description, date, time);
            taskList.add(task);
            saveTasks();
            updateTaskList();
            clearFields();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteTask() {
        try {
            int selectedRow = taskTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a task to delete.", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            taskList.remove(selectedRow);
            saveTasks();
            updateTaskList();
            JOptionPane.showMessageDialog(this, "Task deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        titleField.setText("");
        descriptionField.setText("");
        dateField.setText("");
        timeField.setText("");
    }

    private void updateTaskList() {
        tableModel.setRowCount(0); // Clear existing rows
        for (Task task : taskList) {
            tableModel.addRow(new Object[]{task.getTitle(), task.getDescription(), task.getDate(), task.getTime()});
        }
    }

    private void saveTasks() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(taskFile))) {
            for (Task task : taskList) {
                writer.write(task.toFileString());
                writer.newLine();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving tasks: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadTasks() {
        taskList.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(taskFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|", 4);
                if (parts.length == 4) {
                    Task task = new Task(parts[0].trim(), parts[1].trim(), parts[2].trim(), parts[3].trim());
                    taskList.add(task);
                }
            }
        } catch (IOException e) {
            // File not found or empty; initialize empty task list
        }
    }

    private void createFileIfNotExists() {
        try {
            if (!taskFile.exists()) {
                taskFile.createNewFile(); // Create the file if it doesn't exist
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error creating file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DailyScheduler app = new DailyScheduler();
            app.setVisible(true);
        });
    }

    static class Task {
        private String title;
        private String description;
        private String date;
        private String time;

        public Task(String title, String description, String date, String time) {
            this.title = title;
            this.description = description;
            this.date = date;
            this.time = time;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public String getDate() {
            return date;
        }

        public String getTime() {
            return time;
        }

        public String toFileString() {
            return String.join("|", title, description, date, time);
        }

        @Override
        public String toString() {
            return String.format("Title: %s | Description: %s | Date: %s | Time: %s", title, description, date, time);
        }
    }
}