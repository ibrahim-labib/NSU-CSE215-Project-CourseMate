package core;

/**
 * A custom exception for when a requested course is not found.
 */
public class CourseNotFoundException extends Exception {
    public CourseNotFoundException(String message) {
        super(message);
    }
}