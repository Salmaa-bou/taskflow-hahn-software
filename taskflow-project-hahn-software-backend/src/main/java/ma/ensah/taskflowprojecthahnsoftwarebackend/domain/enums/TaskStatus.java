package ma.ensah.taskflowprojecthahnsoftwarebackend.domain.enums;
/**
 * Task Status Enum - Defines possible task states
 */
public enum TaskStatus {
    TODO("Not started"),
    IN_PROGRESS("Currently being worked on"),
    IN_REVIEW("Waiting for review"),
    DONE("Completed");

    private final String description;

    TaskStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Get next logical status
     */
    public TaskStatus getNextStatus() {
        return switch (this) {
            case TODO -> IN_PROGRESS;
            case IN_PROGRESS -> IN_REVIEW;
            case IN_REVIEW -> DONE;
            case DONE -> DONE;
        };
    }

    /**
     * Get previous logical status
     */
    public TaskStatus getPreviousStatus() {
        return switch (this) {
            case TODO -> TODO;
            case IN_PROGRESS -> TODO;
            case IN_REVIEW -> IN_PROGRESS;
            case DONE -> IN_REVIEW;
        };
    }
}