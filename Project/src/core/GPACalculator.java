package core;

import java.util.List;

public class GPACalculator {

    /**
     * Calculate GPA from a list of courses.
     */
    public double calculateGPA(List<Course> courses) {
        if (courses.isEmpty()) return 0.0;
        double totalGPA = 0;
        for (Course c : courses) {
            totalGPA += courseGPA(c);
        }
        return totalGPA / courses.size();
    }

    /** Convert a single course score to GPA (0-4 scale) */
    private double courseGPA(Course c) {
        double total = c.getAssignment().getScore() + c.getQuiz1().getScore() +
                       c.getQuiz2().getScore() + c.getMidterm().getScore() + c.getFinalExam().getScore();
        double percent = total / 120.0; // max points = 10+20+20+30+40=120
        if (percent >= 0.9) return 4.0;
        else if (percent >= 0.85) return 3.7;
        else if (percent >= 0.8) return 3.3;
        else if (percent >= 0.75) return 3.0;
        else if (percent >= 0.7) return 2.7;
        else if (percent >= 0.65) return 2.3;
        else if (percent >= 0.6) return 2.0;
        else if (percent >= 0.5) return 1.0;
        else return 0.0;
    }
}
