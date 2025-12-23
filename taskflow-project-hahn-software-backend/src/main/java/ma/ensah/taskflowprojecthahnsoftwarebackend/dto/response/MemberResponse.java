package ma.ensah.taskflowprojecthahnsoftwarebackend.dto.response;


import ma.ensah.taskflowprojecthahnsoftwarebackend.domain.enums.ProjectRole;
import ma.ensah.taskflowprojecthahnsoftwarebackend.domain.enums.TaskPermission;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class MemberResponse {
    private Long id;
    private Long userId; // User ID for easy reference
    private UserResponse user;
    private ProjectRole role;
    private Boolean canAddMembers;
    private TaskPermission permission; // For task assignments
    private LocalDateTime joinedAt;
}