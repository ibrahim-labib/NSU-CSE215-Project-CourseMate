package app;

import core.Course;
import core.CourseMate;
import core.GPACalculator;
import academics.AcademicItem;
import academics.Exam;
import academics.Assignment;


import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class HomeScreen extends JFrame {

    private final CourseMate courseMate;
    private final JPanel cardsGrid;

    public HomeScreen(CourseMate courseMate) {
        super("NSU CourseMate – Home");
        this.courseMate = courseMate;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 720);
        setLocationRelativeTo(null);

        // Root
        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        root.setBackground(new Color(30, 30, 30));
        setContentPane(root);

        // Header
        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 0));
        header.setBackground(new Color(30, 30, 30));

        // Load and scale logo properly
        ImageIcon icon = new ImageIcon("images/logo.png"); // Replace with your logo path
        Image img = icon.getImage();
        int newWidth = 120, newHeight = 120; // Bigger size

        BufferedImage buffered = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = buffered.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2d.drawImage(img, 0, 0, newWidth, newHeight, null);
        g2d.dispose();

        JLabel logoLabel = new JLabel(new ImageIcon(buffered));
        header.add(logoLabel);

        JLabel title = new JLabel("NSU CourseMate");
        title.setFont(new Font("Calibri", Font.BOLD, 32));
        title.setForeground(Color.WHITE);
        header.add(title);

        root.add(header, BorderLayout.NORTH);

        // Center: custom GridBag for 3 per row
        cardsGrid = new JPanel(new GridBagLayout());
        cardsGrid.setBackground(new Color(40, 40, 40));
        JScrollPane scroll = new JScrollPane(cardsGrid);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(new Color(40, 40, 40));
        root.add(scroll, BorderLayout.CENTER);

        // Right buttons
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBackground(new Color(30, 30, 30));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(30, 10, 30, 10));

        JButton manageBtn = new JButton("Manage Courses");
        JButton showOverallBtn = new JButton("Show Overall CGPA");
        JButton statusBtn = new JButton("Show Status"); // NEW BUTTON

        styleBigButton(manageBtn);
        styleBigButton(showOverallBtn);
        styleBigButton(statusBtn);

        // Button actions
        manageBtn.addActionListener(e -> {
            ManageCoursesDialog dlg = new ManageCoursesDialog(this, courseMate);
            dlg.setVisible(true);
            refreshCards();
        });

        showOverallBtn.addActionListener(e -> {
            double gpa = courseMate.calculateGPA();
            JOptionPane.showMessageDialog(this, "Overall CGPA: " + String.format("%.2f", gpa),
                    "Overall CGPA", JOptionPane.INFORMATION_MESSAGE);
        });

        statusBtn.addActionListener(e -> {
            String general = courseMate.getStatus();
            String studentStatus = courseMate.getStatus("Labib"); // hardcoded student name
            JOptionPane.showMessageDialog(this, general + "\n" + studentStatus,
                    "Course Status", JOptionPane.INFORMATION_MESSAGE);
        });

        rightPanel.add(Box.createVerticalGlue());
        rightPanel.add(manageBtn);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        rightPanel.add(showOverallBtn);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        rightPanel.add(statusBtn); // add new button
        rightPanel.add(Box.createVerticalGlue());

        root.add(rightPanel, BorderLayout.EAST);

        refreshCards();
    }

    private void styleBigButton(JButton btn) {
        btn.setBackground(new Color(70, 70, 70));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        btn.setFont(new Font("Arial", Font.BOLD, 18));
        btn.setPreferredSize(new Dimension(220, 60));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    /** rebuild course cards */
    public final void refreshCards() {
        cardsGrid.removeAll();

        if (courseMate.getCourses().isEmpty()) {
            JLabel empty = new JLabel("No courses yet. Click “Manage Courses” to add.");
            empty.setFont(new Font("Arial", Font.ITALIC, 16));
            empty.setForeground(Color.LIGHT_GRAY);
            cardsGrid.add(empty);
        } else {
            Color[] palette = {
                    new Color(255, 99, 132), new Color(54, 162, 235),
                    new Color(255, 206, 86), new Color(75, 192, 192),
                    new Color(153, 102, 255), new Color(255, 159, 64)
            };

            GPACalculator calc = new GPACalculator();
            List<Course> single = new ArrayList<>(1);
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(16, 16, 16, 16);
            gbc.anchor = GridBagConstraints.NORTHWEST;

            int i = 0, row = 0, col = 0;
            for (Course c : courseMate.getCourses()) {
                single.clear();
                single.add(c);
                double gpa = calc.calculateGPA(single);
                String grade = letterFromGPA(gpa);
                int credit = 3;

                JPanel card = makeCourseCard(
                        c.getCourseName(),
                        c.getCourseCode(),
                        String.format("%.2f", gpa),
                        grade,
                        credit,
                        palette[i % palette.length],
                        c
                );

                gbc.gridx = col;
                gbc.gridy = row;
                cardsGrid.add(card, gbc);

                col++;
                if (col == 3) { col = 0; row++; }
                i++;
            }
        }

        cardsGrid.revalidate();
        cardsGrid.repaint();
    }

    private String letterFromGPA(double gpa) {
        if (gpa >= 4.0) return "A";
        else if (gpa >= 3.7) return "A-";
        else if (gpa >= 3.3) return "B+";
        else if (gpa >= 3.0) return "B";
        else if (gpa >= 2.7) return "B-";
        else if (gpa >= 2.3) return "C+";
        else if (gpa >= 2.0) return "C";
        else if (gpa >= 1.0) return "D";
        else return "F";
    }

    private void showCourseDetails(Course course) {
        StringBuilder details = new StringBuilder();
        details.append("<html><body style='width: 350px;'>");
        details.append("<h3>").append(course.getCourseName())
               .append(" (").append(course.getCourseCode()).append(")</h3>");

        // Loop through academic items
        for (AcademicItem item : course.getAcademicItems()) {
            String title = item.getTitle();
            double score = item.getScore();

            if (item instanceof Exam exam) {
                details.append(title)
                       .append(" (Type: ").append(exam.getExamType()).append(")")
                       .append(" → Marks: ").append(String.format("%.2f", exam.getMarks()))
                       .append(", Score with bonus: ").append(String.format("%.2f", score))
                       .append("<br>");
            } else if (item instanceof Assignment assign) {
                details.append(title)
                       .append(" → Marks: ").append(String.format("%.2f", assign.getMarks()))
                       .append(", Score with bonus: ").append(String.format("%.2f", score))
                       .append("<br>");
            }
        }

        details.append("</body></html>");

        JOptionPane.showMessageDialog(this, details.toString(),
                "Course Details", JOptionPane.INFORMATION_MESSAGE);
    }


    private JPanel makeCourseCard(String name, String code, String gpa, String grade, int credit, Color bg, Course course) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setPreferredSize(new Dimension(280, 160));
        card.setBackground(bg);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 0, 0, 80), 2, true),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel title = new JLabel("<html><b>" + name + "</b><br>(" + code + ")</html>");
        title.setFont(new Font("Arial", Font.BOLD, 16));
        title.setForeground(Color.WHITE);

        JLabel info = new JLabel("<html>📊 GPA: " + gpa + "<br>🎓 Grade: " + grade + "<br>📘 Credit: " + credit + "</html>");
        info.setFont(new Font("Arial", Font.PLAIN, 14));
        info.setForeground(Color.WHITE);

        card.add(title);
        card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(info);

        // Hover effect
        card.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                card.setBackground(bg.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                card.setBackground(bg);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                showCourseDetails(course);
            }
        });

        return card;
    }
}
