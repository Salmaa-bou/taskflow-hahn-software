package ma.ensah.taskflowprojecthahnsoftwarebackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.ensah.taskflowprojecthahnsoftwarebackend.domain.enums.TaskPermission;
import ma.ensah.taskflowprojecthahnsoftwarebackend.dto.request.AssignTaskRequest;
import ma.ensah.taskflowprojecthahnsoftwarebackend.dto.response.ApiResponse;
import ma.ensah.taskflowprojecthahnsoftwarebackend.dto.response.MemberResponse;
import ma.ensah.taskflowprojecthahnsoftwarebackend.service.ITaskAssignmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks/{taskId}")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Task Assignments", description = "Task assignment management APIs")
public class TaskAssignmentController {

    private final ITaskAssignmentService taskAssignmentService;

    @PostMapping("/assign")
    @Operation(summary = "Assign users to task", description = "Assign users to task with specific permissions (Owner/Admin only)")
    public ResponseEntity<List<MemberResponse>> assignUsersToTask(
            @PathVariable Long taskId,
            @Valid @RequestBody AssignTaskRequest request,
            Authentication authentication
    ) {
        String email = authentication.getName();
        List<MemberResponse> response = taskAssignmentService.assignUsersToTask(taskId, request, email);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/assignments")
    @Operation(summary = "Get task assignees", description = "Get all users assigned to a task")
    public ResponseEntity<List<MemberResponse>> getTaskAssignees(
            @PathVariable Long taskId,
            Authentication authentication
    ) {
        String email = authentication.getName();
        List<MemberResponse> assignees = taskAssignmentService.getTaskAssignees(taskId, email);
        return ResponseEntity.ok(assignees);
    }

    @DeleteMapping("/unassign/{userId}")
    @Operation(summary = "Unassign user from task", description = "Remove user assignment from task (Owner/Admin only)")
    public ResponseEntity<ApiResponse> unassignUser(
            @PathVariable Long taskId,
            @PathVariable Long userId,
            Authentication authentication
    ) {
        String email = authentication.getName();
        taskAssignmentService.unassignUser(taskId, userId, email);
        return ResponseEntity.ok(new ApiResponse(true, "User unassigned successfully"));
    }

    @PutMapping("/permissions/{userId}")
    @Operation(summary = "Update user permission", description = "Update user's permission level on task (Owner/Admin only)")
    public ResponseEntity<MemberResponse> updatePermission(
            @PathVariable Long taskId,
            @PathVariable Long userId,
            @RequestParam TaskPermission permission,
            Authentication authentication
    ) {
        String email = authentication.getName();
        MemberResponse response = taskAssignmentService.updatePermission(taskId, userId, permission, email);
        return ResponseEntity.ok(response);
    }
}