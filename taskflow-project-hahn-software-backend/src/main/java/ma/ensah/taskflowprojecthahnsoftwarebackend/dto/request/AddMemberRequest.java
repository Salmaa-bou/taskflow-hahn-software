package ma.ensah.taskflowprojecthahnsoftwarebackend.dto.request;


import ma.ensah.taskflowprojecthahnsoftwarebackend.domain.enums.ProjectRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddMemberRequest {

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;

    @NotNull(message = "Role is required")
    private ProjectRole role;

    private Boolean canAddMembers = false;
}