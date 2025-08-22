package academics;

import java.util.Date;

/**
 * Subclass representing an exam.
 * It extends the AcademicItem superclass and overrides the getScore() method.
 */
public class Exam extends AcademicItem {
    private String examType;
    private double marks;

    public Exam(String title, Date date, String examType, double marks) {
        super(title, date);
        this.examType = examType;
        this.marks = marks;
    }

    @Override
    public double getScore() {
        // Method overriding with a mathematical calculation.
        // Let's assume a bonus based on exam type (e.g., final exams get a small bump).
        double bonus = examType.equalsIgnoreCase("Final") ? 2.0 : 0.0;
        return marks + bonus;
    }

    public String getExamType() {
        return examType;
    }

    public double getMarks() {
        return marks;
    }
}