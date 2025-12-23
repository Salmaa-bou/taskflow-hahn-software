package ma.ensah.taskflowprojecthahnsoftwarebackend.dto.request;


import ma.ensah.taskflowprojecthahnsoftwarebackend.domain.enums.TaskPermission;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class AssignTaskRequest {

    @NotEmpty(message = "User IDs are required")
    private List<Long> userIds;

    @NotNull(message = "Permission is required")
    private TaskPermission permission;
}