package app;

import academics.Assignment;
import academics.Exam;
import core.Course;
import core.CourseMate;
import core.CourseNotFoundException;
import core.GPACalculator;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ManageCoursesDialog extends JDialog {

    private final CourseMate courseMate;
    private final JTextField codeField = new JTextField();
    private final JTextField nameField = new JTextField();

    public ManageCoursesDialog(JFrame owner, CourseMate courseMate) {
        super(owner, "Manage Courses", true);
        this.courseMate = courseMate;

        setSize(800, 480);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(245, 245, 245));
        ((JComponent)getContentPane()).setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        // Input panel
        JPanel input = new JPanel(new GridLayout(2, 2, 10, 10));
        input.setBackground(Color.WHITE);
        input.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(100, 149, 237), 2, true),
                BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));
        input.add(new JLabel("Course Code:"));
        input.add(codeField);
        input.add(new JLabel("Course Name:"));
        input.add(nameField);

        add(input, BorderLayout.NORTH);

        // Buttons
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        JButton addBtn = new JButton("Add Course");
        JButton removeBtn = new JButton("Remove Course");
        JButton viewBtn = new JButton("View All Courses");
        JButton closeBtn = new JButton("Close");

        actions.add(addBtn);
        actions.add(removeBtn);
        actions.add(viewBtn);
        actions.add(closeBtn);
        add(actions, BorderLayout.SOUTH);

        // List (just for visual context)
        JTextArea info = new JTextArea();
        info.setEditable(false);
        info.setBackground(Color.WHITE);
        info.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        add(new JScrollPane(info), BorderLayout.CENTER);
        refreshInfo(info);

        // Listeners
        addBtn.addActionListener(e -> {
            addCourse();
            refreshInfo(info);
        });
        removeBtn.addActionListener(e -> {
            removeCourse();
            refreshInfo(info);
        });
        viewBtn.addActionListener(e -> viewCoursesTable());
        closeBtn.addActionListener(e -> dispose());
    }

    private void refreshInfo(JTextArea info) {
        StringBuilder sb = new StringBuilder("Current Courses:\n");
        if (courseMate.getCourses().isEmpty()) {
            sb.append("  (none)\n");
        } else {
            for (Course c : courseMate.getCourses()) {
                sb.append("  • ").append(c.getCourseName()).append(" (").append(c.getCourseCode()).append(")\n");
            }
        }
        info.setText(sb.toString());
    }

    // Helpers from your previous app:
    private double askMarks(String msg, double max) {
        while (true) {
            String input = JOptionPane.showInputDialog(this, msg);
            if (input == null) return -1; // cancel
            try {
                double m = Double.parseDouble(input);
                if (m < 0 || m > max) {
                    JOptionPane.showMessageDialog(this, "Enter a number between 0 and " + max);
                    continue;
                }
                return m;
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid number.");
            }
        }
    }

    private void addCourse() {
        String code = codeField.getText().trim();
        String name = nameField.getText().trim();
        if (code.isEmpty() || name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Course Code and Name are required.");
            return;
        }

        Course c = new Course(name, code);

        double a = askMarks("Assignment (out of 10):", 10);
        if (a < 0) return;
        c.setAssignment(new Assignment("Assignment", new Date(), a));

        double q1 = askMarks("Quiz 1 (out of 20):", 20);
        if (q1 < 0) return;
        c.setQuiz1(new Exam("Quiz 1", new Date(), "Quiz", q1));

        double q2 = askMarks("Quiz 2 (out of 20):", 20);
        if (q2 < 0) return;
        c.setQuiz2(new Exam("Quiz 2", new Date(), "Quiz", q2));

        double mid = askMarks("Midterm (out of 30):", 30);
        if (mid < 0) return;
        c.setMidterm(new Exam("Midterm", new Date(), "Midterm", mid));

        double fin = askMarks("Final (out of 40):", 40);
        if (fin < 0) return;
        c.setFinalExam(new Exam("Final", new Date(), "Final", fin));

        courseMate.addCourse(c);
        JOptionPane.showMessageDialog(this, "Added: " + c.getCourseName());
        codeField.setText("");
        nameField.setText("");
    }

    private void removeCourse() {
        String code = codeField.getText().trim();
        if (code.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter course code to remove.");
            return;
        }
        try {
            courseMate.removeCourse(code);
            JOptionPane.showMessageDialog(this, "Removed: " + code);
            codeField.setText("");
        } catch (CourseNotFoundException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Not Found", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void viewCoursesTable() {
        String[] cols = {"Course Code", "Course Name", "Course GPA"};
        List<Object[]> rows = new ArrayList<>();
        GPACalculator calc = new GPACalculator();
        for (Course c : courseMate.getCourses()) {
            double g = calc.calculateGPA(List.of(c));
            rows.add(new Object[]{c.getCourseCode(), c.getCourseName(), String.format("%.2f", g)});
        }
        JTable table = new JTable(rows.toArray(new Object[0][]), cols);
        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(center);
        }
        table.setRowHeight(24);

        JScrollPane sp = new JScrollPane(table);
        sp.setPreferredSize(new Dimension(700, 260));
        JOptionPane.showMessageDialog(this, sp, "Courses & GPA", JOptionPane.PLAIN_MESSAGE);
    }
}