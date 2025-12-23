package ma.ensah.taskflowprojecthahnsoftwarebackend.domain.enums;

/**
 * Task Priority Enum - Defines task priority levels
 */
public enum TaskPriority {
    LOW(1, "Low priority"),
    MEDIUM(2, "Medium priority"),
    HIGH(3, "High priority"),
    URGENT(4, "Urgent - needs immediate attention");

    private final int level;
    private final String description;

    TaskPriority(int level, String description) {
        this.level = level;
        this.description = description;
    }

    public int getLevel() {
        return level;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Compare priorities
     */
    public boolean isHigherThan(TaskPriority other) {
        return this.level > other.level;
    }

    public boolean isLowerThan(TaskPriority other) {
        return this.level < other.level;
    }
}
