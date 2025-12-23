package ma.ensah.taskflowprojecthahnsoftwarebackend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String email;
    private String fullName;
    private String skills;
    private String role;
    private LocalDateTime createdAt;

    // Alias for frontend compatibility
    public String getName() {
        return fullName;
    }
}
