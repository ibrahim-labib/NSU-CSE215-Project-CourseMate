package core;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CourseMate implements Serializable {
    private List<Course> courses;

    private static final String FILE_PATH = "coursemate.dat";

    public CourseMate() {
        courses = new ArrayList<>();
    }

    public void addCourse(Course c) {
        courses.add(c);
        saveToFile();  // save automatically when adding
    }

    public void removeCourse(String code) throws CourseNotFoundException {
        Course toRemove = null;
        for (Course c : courses) {
            if (c.getCourseCode().equalsIgnoreCase(code)) {
                toRemove = c;
                break;
            }
        }
        if (toRemove == null) throw new CourseNotFoundException("Course not found: " + code);
        courses.remove(toRemove);
        saveToFile();  // save automatically when removing
    }
 // Method overloading example: getStatus() without parameters
    public String getStatus() {
        return "You are enrolled in " + courses.size() + " course(s).";
    }

    // Overloaded version: getStatus() with student name
    public String getStatus(String studentName) {
        return "Student " + studentName + " is enrolled in " + courses.size() + " course(s).";
    }


    public List<Course> getCourses() {
        return courses;
    }

    // GPA calculation
    public double calculateGPA() {
        GPACalculator calc = new GPACalculator();
        return calc.calculateGPA(courses);
    }

    /** Save CourseMate to file */
    public void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Load CourseMate from file */
    public static CourseMate loadFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return new CourseMate(); // if no file, return new instance

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (CourseMate) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new CourseMate();
        }
    }
}
