package core;

import academics.AcademicItem;
import academics.Assignment;
import academics.Exam;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Course implements Serializable {
    private String courseName;
    private String courseCode;

    private Assignment assignment;   // 10 marks
    private Exam quiz1;              // 10 marks
    private Exam quiz2;              // 10 marks
    private Exam midterm;            // 30 marks
    private Exam finalExam;          // 40 marks

    public Course(String courseName, String courseCode) {
        this.courseName = courseName;
        this.courseCode = courseCode;
    }
    
    

    // Setters
    public void setAssignment(Assignment assignment) { this.assignment = assignment; }
    public void setQuiz1(Exam quiz1) { this.quiz1 = quiz1; }
    public void setQuiz2(Exam quiz2) { this.quiz2 = quiz2; }
    public void setMidterm(Exam midterm) { this.midterm = midterm; }
    public void setFinalExam(Exam finalExam) { this.finalExam = finalExam; }

    // Getters
    public Assignment getAssignment() { return assignment; }
    public Exam getQuiz1() { return quiz1; }
    public Exam getQuiz2() { return quiz2; }
    public Exam getMidterm() { return midterm; }
    public Exam getFinalExam() { return finalExam; }

    // Return all academic items
    public List<AcademicItem> getAcademicItems() {
        List<AcademicItem> items = new ArrayList<>();
        if (assignment != null) items.add(assignment);
        if (quiz1 != null) items.add(quiz1);
        if (quiz2 != null) items.add(quiz2);
        if (midterm != null) items.add(midterm);
        if (finalExam != null) items.add(finalExam);
        return items;
    }

    public String getCourseName() { return courseName; }
    public String getCourseCode() { return courseCode; }

    @Override
    public String toString() {
        return "Course: " + courseName + " (" + courseCode + ")";
    }
}