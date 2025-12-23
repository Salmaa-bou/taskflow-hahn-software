package ma.ensah.taskflowprojecthahnsoftwarebackend.service.impl;


import ma.ensah.taskflowprojecthahnsoftwarebackend.domain.entity.Project;
import ma.ensah.taskflowprojecthahnsoftwarebackend.domain.entity.ProjectMember;
import ma.ensah.taskflowprojecthahnsoftwarebackend.domain.entity.User;
import ma.ensah.taskflowprojecthahnsoftwarebackend.domain.enums.ProjectRole;
import ma.ensah.taskflowprojecthahnsoftwarebackend.dto.request.ProjectRequest;
import ma.ensah.taskflowprojecthahnsoftwarebackend.dto.response.ProjectProgressResponse;
import ma.ensah.taskflowprojecthahnsoftwarebackend.dto.response.ProjectResponse;
import ma.ensah.taskflowprojecthahnsoftwarebackend.dto.response.UserResponse;
import ma.ensah.taskflowprojecthahnsoftwarebackend.exception.ForbiddenException;
import ma.ensah.taskflowprojecthahnsoftwarebackend.exception.ResourceNotFoundException;
import ma.ensah.taskflowprojecthahnsoftwarebackend.repository.ProjectMemberRepository;
import ma.ensah.taskflowprojecthahnsoftwarebackend.repository.ProjectRepository;
import ma.ensah.taskflowprojecthahnsoftwarebackend.repository.UserRepository;
import ma.ensah.taskflowprojecthahnsoftwarebackend.service.IProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements IProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ProjectMemberRepository projectMemberRepository;

    @Override
    @Transactional
    public ProjectResponse createProject(ProjectRequest request, String userEmail) {
        User user = getUserByEmail(userEmail);

        Project project = Project.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .owner(user)
                .build();

        Project savedProject = projectRepository.save(project);

        // Add creator as OWNER member
        ProjectMember ownerMember = ProjectMember.builder()
                .project(savedProject)
                .user(user)
                .role(ProjectRole.OWNER)
                .canAddMembers(true)
                .build();

        projectMemberRepository.save(ownerMember);

        return toProjectResponse(savedProject);
    }

    @Override
    public ProjectResponse getProjectById(Long projectId, String userEmail) {
        Project project = getProjectOrThrow(projectId);
        checkUserAccess(project, userEmail);
        return toProjectResponse(project);
    }

    @Override
    public List<ProjectResponse> getAllUserProjects(String userEmail) {
        User user = getUserByEmail(userEmail);
        List<Project> projects = projectRepository.findAllByUserId(user.getId());
        return projects.stream()
                .map(this::toProjectResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ProjectResponse updateProject(Long projectId, ProjectRequest request, String userEmail) {
        Project project = getProjectOrThrow(projectId);
        checkCanManageProject(project, userEmail);

        project.setTitle(request.getTitle())  ;
        project.setDescription(request.getDescription());

        Project updated = projectRepository.save(project);
        return toProjectResponse(updated);
    }

    @Override
    @Transactional
    public void deleteProject(Long projectId, String userEmail) {
        Project project = getProjectOrThrow(projectId);
        User user = getUserByEmail(userEmail);

        if (!project.getOwner().getId().equals(user.getId())) {
            throw new ForbiddenException("Only project owner can delete the project");
        }

        projectRepository.delete(project);
    }

    @Override
    public ProjectProgressResponse getProjectProgress(Long projectId, String userEmail) {
        Project project = getProjectOrThrow(projectId);
        checkUserAccess(project, userEmail);

        return ProjectProgressResponse.builder()
                .totalTasks(project.getTotalTasks())
                .completedTasks(project.getCompletedTasks())
                .progressPercentage(project.getProgressPercentage())
                .build();
    }

    private Project getProjectOrThrow(Long projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));
    }

    private User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    private void checkUserAccess(Project project, String userEmail) {
        User user = getUserByEmail(userEmail);
        boolean hasAccess = projectMemberRepository
                .existsByProjectIdAndUserId(project.getId(), user.getId());

        if (!hasAccess) {
            throw new ForbiddenException("You don't have access to this project");
        }
    }

    private void checkCanManageProject(Project project, String userEmail) {
        User user = getUserByEmail(userEmail);
        ProjectMember member = projectMemberRepository
                .findByProjectIdAndUserId(project.getId(), user.getId())
                .orElseThrow(() -> new ForbiddenException("You don't have access to this project"));

        if (!member.isOwner() && !member.isAdmin()) {
            throw new ForbiddenException("Only owners and admins can manage the project");
        }
    }

    private ProjectResponse toProjectResponse(Project project) {
        UserResponse owner = UserResponse.builder()
                .id(project.getOwner().getId())
                .email(project.getOwner().getEmail())
                .fullName(project.getOwner().getFullName())
                .skills(project.getOwner().getSkills())
                .createdAt(project.getOwner().getCreatedAt())
                .build();

        return ProjectResponse.builder()
                .id(project.getId())
                .title(project.getTitle())
                .description(project.getDescription())
                .owner(owner)
                .totalTasks(project.getTotalTasks())
                .completedTasks(project.getCompletedTasks())
                .progressPercentage(project.getProgressPercentage())
                .createdAt(project.getCreatedAt())
                .updatedAt(project.getUpdatedAt())
                .build();
    }
}