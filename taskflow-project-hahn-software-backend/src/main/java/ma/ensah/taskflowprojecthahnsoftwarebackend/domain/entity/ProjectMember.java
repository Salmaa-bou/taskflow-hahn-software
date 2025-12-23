package ma.ensah.taskflowprojecthahnsoftwarebackend.domain.entity;



import ma.ensah.taskflowprojecthahnsoftwarebackend.domain.enums.ProjectRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * ProjectMember Entity - Represents team membership with roles
 */
@Entity
@Table(
        name = "project_members",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"project_id", "user_id"},
                name = "uk_project_user"
        )
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class ProjectMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    @Builder.Default
    private ProjectRole role = ProjectRole.MEMBER;

    @Column(name = "can_add_members")
    @Builder.Default
    private Boolean canAddMembers = false;

    @CreatedDate
    @Column(name = "joined_at", nullable = false, updatable = false)
    private LocalDateTime joinedAt;

    public boolean isOwner() {
        return role == ProjectRole.OWNER;
    }

    public boolean isAdmin() {
        return role == ProjectRole.ADMIN;
    }

    public boolean isMember() {
        return role == ProjectRole.MEMBER;
    }

    public boolean isViewer() {
        return role == ProjectRole.VIEWER;
    }

    public boolean canManageMembers() {
        return isOwner() || isAdmin() || canAddMembers;
    }

    public boolean canManageTasks() {
        return isOwner() || isAdmin();
    }

    public boolean canCreateTasks() {
        return !isViewer();
    }

    @PrePersist
    protected void onCreate() {
        joinedAt = LocalDateTime.now();
    }
}