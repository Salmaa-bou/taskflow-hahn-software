package ma.ensah.taskflowprojecthahnsoftwarebackend.dto.response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ProjectProgressResponse {
    private Long totalTasks;
    private Long completedTasks;
    private Double progressPercentage;
}
