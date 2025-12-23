package ma.ensah.taskflowprojecthahnsoftwarebackend.domain.enums;

public enum UserRole {
    USER("Standard application user"),
    ADMIN("Application administrator");

    private final String description;

    UserRole(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
