package ma.ensah.taskflowprojecthahnsoftwarebackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.ensah.taskflowprojecthahnsoftwarebackend.domain.enums.TaskStatus;
import ma.ensah.taskflowprojecthahnsoftwarebackend.dto.request.TaskRequest;
import ma.ensah.taskflowprojecthahnsoftwarebackend.dto.response.ApiResponse;
import ma.ensah.taskflowprojecthahnsoftwarebackend.dto.response.TaskResponse;
import ma.ensah.taskflowprojecthahnsoftwarebackend.service.ITaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Tasks", description = "Task management APIs")
public class TaskController {

    private final ITaskService taskService;

    @PostMapping("/projects/{projectId}/tasks")
    @Operation(summary = "Create task", description = "Create a new task in project")
    public ResponseEntity<TaskResponse> createTask(
            @PathVariable Long projectId,
            @Valid @RequestBody TaskRequest request,
            Authentication authentication
    ) {
        String email = authentication.getName();
        TaskResponse response = taskService.createTask(projectId, request, email);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/projects/{projectId}/tasks")
    @Operation(summary = "Get project tasks", description = "Get all tasks in a project")
    public ResponseEntity<List<TaskResponse>> getProjectTasks(
            @PathVariable Long projectId,
            Authentication authentication
    ) {
        String email = authentication.getName();
        List<TaskResponse> tasks = taskService.getProjectTasks(projectId, email);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/tasks/{id}")
    @Operation(summary = "Get task by ID", description = "Get task details by ID")
    public ResponseEntity<TaskResponse> getTaskById(
            @PathVariable Long id,
            Authentication authentication
    ) {
        String email = authentication.getName();
        TaskResponse response = taskService.getTaskById(id, email);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/tasks/{id}")
    @Operation(summary = "Update task", description = "Update task details (requires edit permission)")
    public ResponseEntity<TaskResponse> updateTask(
            @PathVariable Long id,
            @Valid @RequestBody TaskRequest request,
            Authentication authentication
    ) {
        String email = authentication.getName();
        TaskResponse response = taskService.updateTask(id, request, email);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/tasks/{id}")
    @Operation(summary = "Delete task", description = "Delete task (creator or admin only)")
    public ResponseEntity<ApiResponse> deleteTask(
            @PathVariable Long id,
            Authentication authentication
    ) {
        String email = authentication.getName();
        taskService.deleteTask(id, email);
        return ResponseEntity.ok(new ApiResponse(true, "Task deleted successfully"));
    }

    @PatchMapping("/tasks/{id}/status")
    @Operation(summary = "Update task status", description = "Update task status (requires status update permission)")
    public ResponseEntity<TaskResponse> updateTaskStatus(
            @PathVariable Long id,
            @RequestParam TaskStatus status,
            Authentication authentication
    ) {
        String email = authentication.getName();
        TaskResponse response = taskService.updateTaskStatus(id, status, email);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/tasks/{id}/complete")
    @Operation(summary = "Mark task as completed", description = "Mark task as completed")
    public ResponseEntity<TaskResponse> markAsCompleted(
            @PathVariable Long id,
            Authentication authentication
    ) {
        String email = authentication.getName();
        TaskResponse response = taskService.markAsCompleted(id, email);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/tasks/{id}/uncomplete")
    @Operation(summary = "Mark task as incomplete", description = "Mark task as incomplete")
    public ResponseEntity<TaskResponse> markAsIncomplete(
            @PathVariable Long id,
            Authentication authentication
    ) {
        String email = authentication.getName();
        TaskResponse response = taskService.markAsIncomplete(id, email);
        return ResponseEntity.ok(response);
    }
}