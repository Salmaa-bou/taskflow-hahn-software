package ma.ensah.taskflowprojecthahnsoftwarebackend.dto.response;


import ma.ensah.taskflowprojecthahnsoftwarebackend.domain.enums.TaskPriority;
import ma.ensah.taskflowprojecthahnsoftwarebackend.domain.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class TaskResponse {
    private Long id;
    private String title;
    private String description;
    private LocalDate dueDate;
    private TaskStatus status;
    private TaskPriority priority;
    private Boolean completed;
    private UserResponse createdBy;
    private Long projectId;
    private Integer assignedUserCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}