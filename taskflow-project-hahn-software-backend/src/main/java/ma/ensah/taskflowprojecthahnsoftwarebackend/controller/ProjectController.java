package ma.ensah.taskflowprojecthahnsoftwarebackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.ensah.taskflowprojecthahnsoftwarebackend.domain.entity.Project;
import ma.ensah.taskflowprojecthahnsoftwarebackend.dto.request.ProjectRequest;
import ma.ensah.taskflowprojecthahnsoftwarebackend.dto.response.ApiResponse;
import ma.ensah.taskflowprojecthahnsoftwarebackend.dto.response.ProjectProgressResponse;
import ma.ensah.taskflowprojecthahnsoftwarebackend.dto.response.ProjectResponse;
import ma.ensah.taskflowprojecthahnsoftwarebackend.service.IProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
@SecurityRequirement(name ="bearerAuth")
@Tag(name = "Projects", description = "Projects managements APIs ")
public class ProjectController {
    private final IProjectService projectService;
    @PostMapping
    @Operation(summary = "Create project", description = "Create a new project (user becomes owner)")
    public ResponseEntity<ProjectResponse> createProject(
            @Valid @RequestBody ProjectRequest request,
            Authentication authentication
    ){
        String email = authentication.getName();
        ProjectResponse response  = projectService.createProject(request, email);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @GetMapping
    @Operation(summary = "Get all projects", description = "Get all projects where user is a member")
    public ResponseEntity<List<ProjectResponse>> getAllProjects(Authentication authentication) {
        String email = authentication.getName();
        List<ProjectResponse> projects = projectService.getAllUserProjects(email);
        return ResponseEntity.ok(projects);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get project by ID", description = "Get project details by ID")
    public ResponseEntity<ProjectResponse> getProjectById(
            @PathVariable Long id,
            Authentication authentication
    ) {
        String email = authentication.getName();
        ProjectResponse response = projectService.getProjectById(id, email);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update project", description = "Update project details (Owner/Admin only)")
    public ResponseEntity<ProjectResponse> updateProject(
            @PathVariable Long id,
            @Valid @RequestBody ProjectRequest request,
            Authentication authentication
    ) {
        String email = authentication.getName();
        ProjectResponse response = projectService.updateProject(id, request, email);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete project", description = "Delete project (Owner only)")
    public ResponseEntity<ApiResponse> deleteProject(
            @PathVariable Long id,
            Authentication authentication
    ) {
        String email = authentication.getName();
        projectService.deleteProject(id, email);
        return ResponseEntity.ok(new ApiResponse(true, "Project deleted successfully"));
    }

    @GetMapping("/{id}/progress")
    @Operation(summary = "Get project progress", description = "Get project completion statistics")
    public ResponseEntity<ProjectProgressResponse> getProjectProgress(
            @PathVariable Long id,
            Authentication authentication
    ) {
        String email = authentication.getName();
        ProjectProgressResponse response = projectService.getProjectProgress(id, email);
        return ResponseEntity.ok(response);
    }
}
