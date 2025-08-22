package academics;

import java.io.Serializable;
import java.util.Date;

/**
 * Superclass for all academic items like assignments and exams.
 * This class provides common attributes and methods, and also implements the AcademicEntity interface.
 */
public abstract class AcademicItem implements AcademicEntity, Serializable {
    private String title;
    private Date date;


    public AcademicItem(String title, Date date) {
        this.title = title;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Abstract method to be implemented by subclasses to calculate their score.
     * This method fulfills the Abstraction requirement.
     */
    @Override
    public abstract double getScore();
}