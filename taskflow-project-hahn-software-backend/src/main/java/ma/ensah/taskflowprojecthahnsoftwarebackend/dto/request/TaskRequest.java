package ma.ensah.taskflowprojecthahnsoftwarebackend.dto.request;


import ma.ensah.taskflowprojecthahnsoftwarebackend.domain.enums.TaskPriority;
import ma.ensah.taskflowprojecthahnsoftwarebackend.domain.enums.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskRequest {

    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 255)
    private String title;

    private String description;

    private LocalDate dueDate;

    private TaskStatus status;

    private TaskPriority priority;
}