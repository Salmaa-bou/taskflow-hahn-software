package ma.ensah.taskflowprojecthahnsoftwarebackend.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.ensah.taskflowprojecthahnsoftwarebackend.domain.enums.ProjectRole;
import ma.ensah.taskflowprojecthahnsoftwarebackend.dto.request.AddMemberRequest;
import ma.ensah.taskflowprojecthahnsoftwarebackend.dto.response.ApiResponse;
import ma.ensah.taskflowprojecthahnsoftwarebackend.dto.response.MemberResponse;
import ma.ensah.taskflowprojecthahnsoftwarebackend.service.IProjectMemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects/{projectId}/members")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Project Members", description = "Project team management APIs")
public class ProjectMemberController {

    private final IProjectMemberService projectMemberService;

    @PostMapping
    @Operation(summary = "Add member to project", description = "Add a user to project team (Owner/Admin only)")
    public ResponseEntity<MemberResponse> addMember(
            @PathVariable Long projectId,
            @Valid @RequestBody AddMemberRequest request,
            Authentication authentication
    ) {
        String email = authentication.getName();
        MemberResponse response = projectMemberService.addMember(projectId, request, email);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Get project members", description = "Get all members of a project")
    public ResponseEntity<List<MemberResponse>> getProjectMembers(
            @PathVariable Long projectId,
            Authentication authentication
    ) {
        String email = authentication.getName();
        List<MemberResponse> members = projectMemberService.getProjectMembers(projectId, email);
        return ResponseEntity.ok(members);
    }

    @PutMapping("/{userId}")
    @Operation(summary = "Update member role", description = "Update member's role in project (Owner/Admin only)")
    public ResponseEntity<MemberResponse> updateMemberRole(
            @PathVariable Long projectId,
            @PathVariable Long userId,
            @RequestParam ProjectRole role,
            Authentication authentication
    ) {
        String email = authentication.getName();
        MemberResponse response = projectMemberService.updateMemberRole(projectId, userId, role, email);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{userId}")
    @Operation(summary = "Remove member", description = "Remove member from project (Owner/Admin only)")
    public ResponseEntity<ApiResponse> removeMember(
            @PathVariable Long projectId,
            @PathVariable Long userId,
            Authentication authentication
    ) {
        String email = authentication.getName();
        projectMemberService.removeMember(projectId, userId, email);
        return ResponseEntity.ok(new ApiResponse(true, "Member removed successfully"));
    }
}