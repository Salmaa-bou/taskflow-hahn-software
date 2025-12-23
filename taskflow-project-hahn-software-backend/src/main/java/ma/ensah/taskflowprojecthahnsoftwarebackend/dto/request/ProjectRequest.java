package ma.ensah.taskflowprojecthahnsoftwarebackend.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProjectRequest {

    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 255)
    private String title;

    private String description;
}