package ma.ensah.taskflowprojecthahnsoftwarebackend.domain.enums;
/**
 * Project Role Enum - Defines roles within a project
 */
public enum ProjectRole {
    OWNER("Full project control including deletion"),
    ADMIN("Can manage members and tasks"),
    MEMBER("Can create and work on tasks"),
    VIEWER("Read-only access");

    private final String description;

    ProjectRole(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

