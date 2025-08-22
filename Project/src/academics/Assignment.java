package academics;

import java.util.Date;

/**
 * Subclass representing an assignment.
 * It extends the AcademicItem superclass and overrides the getScore() method.
 */
public class Assignment extends AcademicItem {
    private double marks;
    private boolean isSubmitted;

    public Assignment(String title, Date date, double marks) {
        super(title, date);
        this.marks = marks;
        this.isSubmitted = false;
    }

    public void setSubmitted(boolean submitted) {
        isSubmitted = submitted;
    }

    @Override
    public double getScore() {
        // Method overriding with a mathematical calculation.
        // Let's assume a bonus for submitting on time, simulated here.
        double bonus = isSubmitted ? 5.0 : 0.0;
        return marks + bonus;
    }

    // Custom method overloading:
    public String getStatus() {
        return "Assignment: " + this.getTitle() + " - " + (isSubmitted ? "Submitted" : "Not Submitted");
    }

    public String getStatus(String studentName) {
        return "Assignment for " + studentName + ": " + this.getTitle() + " - " + (isSubmitted ? "Submitted" : "Not Submitted");
    }

    public double getMarks() {
        return marks;
    }
}