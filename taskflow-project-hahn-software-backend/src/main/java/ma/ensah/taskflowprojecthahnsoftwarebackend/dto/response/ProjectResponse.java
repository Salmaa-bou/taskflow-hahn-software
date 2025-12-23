package ma.ensah.taskflowprojecthahnsoftwarebackend.dto.response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class ProjectResponse {
    private Long id;
    private String title;
    private String description;
    private UserResponse owner;
    private Long totalTasks;
    private Long completedTasks;
    private Double progressPercentage;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}