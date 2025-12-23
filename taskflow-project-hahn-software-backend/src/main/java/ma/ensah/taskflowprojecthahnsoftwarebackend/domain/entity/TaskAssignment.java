package ma.ensah.taskflowprojecthahnsoftwarebackend.domain.entity;
import ma.ensah.taskflowprojecthahnsoftwarebackend.domain.enums.TaskPermission;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * TaskAssignment Entity - Represents user assignment to task with permissions
 */
@Entity
@Table(
        name = "task_assignments",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"task_id", "user_id"},
                name = "uk_task_user"
        ),
        indexes = {
                @Index(name = "idx_assignment_task", columnList = "task_id"),
                @Index(name = "idx_assignment_user", columnList = "user_id")
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class TaskAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User assignee;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    @Builder.Default
    private TaskPermission permission = TaskPermission.CAN_UPDATE_STATUS;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_by")
    private User assignedBy;

    @CreatedDate
    @Column(name = "assigned_at", nullable = false, updatable = false)
    private LocalDateTime assignedAt;

    public boolean canRead() {
        return true;
    }

    public boolean canUpdateStatus() {
        return permission == TaskPermission.CAN_UPDATE_STATUS ||
                permission == TaskPermission.CAN_EDIT;
    }

    public boolean canEdit() {
        return permission == TaskPermission.CAN_EDIT;
    }

    public boolean isReadOnly() {
        return permission == TaskPermission.READ_ONLY;
    }

    @PrePersist
    protected void onCreate() {
        assignedAt = LocalDateTime.now();
    }
}