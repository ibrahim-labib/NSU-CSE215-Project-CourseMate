package academics;

import java.io.Serializable;

/**
 * An interface representing an academic entity, demonstrating Abstraction.
 * This interface is Serializable so that classes implementing it can be written to a file.
 */
public interface AcademicEntity extends Serializable {
    double getScore();
}