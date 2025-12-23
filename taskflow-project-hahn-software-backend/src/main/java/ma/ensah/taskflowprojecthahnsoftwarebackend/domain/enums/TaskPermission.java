package ma.ensah.taskflowprojecthahnsoftwarebackend.domain.enums;

/**
 * Task Permission Enum - Defines permission levels for task assignments
 */
public enum TaskPermission {
    READ_ONLY("Can only view task details"),
    CAN_UPDATE_STATUS("Can change task status"),
    CAN_EDIT("Full edit rights for task");

    private final String description;

    TaskPermission(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}