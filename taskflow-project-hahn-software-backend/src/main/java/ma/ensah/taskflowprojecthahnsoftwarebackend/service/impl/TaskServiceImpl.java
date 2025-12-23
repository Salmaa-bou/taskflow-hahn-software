package ma.ensah.taskflowprojecthahnsoftwarebackend.service.impl;


import ma.ensah.taskflowprojecthahnsoftwarebackend.domain.entity.Project;
import ma.ensah.taskflowprojecthahnsoftwarebackend.domain.entity.ProjectMember;
import ma.ensah.taskflowprojecthahnsoftwarebackend.domain.entity.Task;
import ma.ensah.taskflowprojecthahnsoftwarebackend.domain.entity.TaskAssignment;
import ma.ensah.taskflowprojecthahnsoftwarebackend.domain.entity.User;
import ma.ensah.taskflowprojecthahnsoftwarebackend.domain.enums.TaskStatus;
import ma.ensah.taskflowprojecthahnsoftwarebackend.dto.request.TaskRequest;
import ma.ensah.taskflowprojecthahnsoftwarebackend.dto.response.TaskResponse;
import ma.ensah.taskflowprojecthahnsoftwarebackend.dto.response.UserResponse;
import ma.ensah.taskflowprojecthahnsoftwarebackend.exception.ForbiddenException;
import ma.ensah.taskflowprojecthahnsoftwarebackend.exception.ResourceNotFoundException;
import ma.ensah.taskflowprojecthahnsoftwarebackend.repository.ProjectMemberRepository;
import ma.ensah.taskflowprojecthahnsoftwarebackend.repository.ProjectRepository;
import ma.ensah.taskflowprojecthahnsoftwarebackend.repository.TaskAssignmentRepository;
import ma.ensah.taskflowprojecthahnsoftwarebackend.repository.TaskRepository;
import ma.ensah.taskflowprojecthahnsoftwarebackend.repository.UserRepository;
import ma.ensah.taskflowprojecthahnsoftwarebackend.service.ITaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements ITaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ProjectMemberRepository projectMemberRepository;
    private final TaskAssignmentRepository taskAssignmentRepository;

    @Override
    @Transactional
    public TaskResponse createTask(Long projectId, TaskRequest request, String userEmail) {
        Project project = getProjectOrThrow(projectId);
        User user = getUserByEmail(userEmail);

        ProjectMember member = getMemberOrThrow(project, user);
        if (!member.canCreateTasks()) {
            throw new ForbiddenException("You don't have permission to create tasks");
        }

        Task task = Task.builder()
                .project(project)
                .createdBy(user)
                .title(request.getTitle())
                .description(request.getDescription())
                .dueDate(request.getDueDate())
                .status(request.getStatus() != null ? request.getStatus() : TaskStatus.TODO)
                .priority(request.getPriority())
                .build();

        Task saved = taskRepository.save(task);
        return toTaskResponse(saved);
    }

    @Override
    public TaskResponse getTaskById(Long taskId, String userEmail) {
        Task task = getTaskOrThrow(taskId);
        checkUserAccessToProject(task.getProject(), userEmail);
        return toTaskResponse(task);
    }

    @Override
    public List<TaskResponse> getProjectTasks(Long projectId, String userEmail) {
        Project project = getProjectOrThrow(projectId);
        checkUserAccessToProject(project, userEmail);

        List<Task> tasks = taskRepository.findByProjectId(projectId);
        return tasks.stream()
                .map(this::toTaskResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TaskResponse updateTask(Long taskId, TaskRequest request, String userEmail) {
        Task task = getTaskOrThrow(taskId);
        User user = getUserByEmail(userEmail);

        checkCanEditTask(task, user);

        task.setTitle( request.getTitle());
        task.setDescription(request.getDescription());
        task.setDueDate(request.getDueDate());
        if (request.getStatus() != null) {
            task.setStatus(request.getStatus());
        }
        if (request.getPriority() != null) {
            task.setPriority(request.getPriority());
        }

        Task updated = taskRepository.save(task);
        return toTaskResponse(updated);
    }

    @Override
    @Transactional
    public void deleteTask(Long taskId, String userEmail) {
        Task task = getTaskOrThrow(taskId);
        User user = getUserByEmail(userEmail);

        ProjectMember member = getMemberOrThrow(task.getProject(), user);
        boolean isCreator = task.getCreatedBy().getId().equals(user.getId());

        if (!isCreator && !member.canManageTasks()) {
            throw new ForbiddenException("Only task creator or project admins can delete tasks");
        }

        taskRepository.delete(task);
    }

    @Override
    @Transactional
    public TaskResponse updateTaskStatus(Long taskId, TaskStatus status, String userEmail) {
        Task task = getTaskOrThrow(taskId);
        User user = getUserByEmail(userEmail);

        checkCanUpdateStatus(task, user);

        task.setStatus(status);
        if (status == TaskStatus.DONE) {
            task.setCompleted(true);
        } else {
            task.setCompleted(false);
        }

        Task updated = taskRepository.save(task);
        return toTaskResponse(updated);
    }

    @Override
    @Transactional
    public TaskResponse markAsCompleted(Long taskId, String userEmail) {
        Task task = getTaskOrThrow(taskId);
        User user = getUserByEmail(userEmail);

        checkCanUpdateStatus(task, user);
        task.markAsCompleted();

        Task updated = taskRepository.save(task);
        return toTaskResponse(updated);
    }

    @Override
    @Transactional
    public TaskResponse markAsIncomplete(Long taskId, String userEmail) {
        Task task = getTaskOrThrow(taskId);
        User user = getUserByEmail(userEmail);

        checkCanUpdateStatus(task, user);
        task.markAsIncomplete();

        Task updated = taskRepository.save(task);
        return toTaskResponse(updated);
    }

    private Task getTaskOrThrow(Long taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));
    }

    private Project getProjectOrThrow(Long projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));
    }

    private User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    private ProjectMember getMemberOrThrow(Project project, User user) {
        return projectMemberRepository.findByProjectIdAndUserId(project.getId(), user.getId())
                .orElseThrow(() -> new ForbiddenException("You are not a member of this project"));
    }

    private void checkUserAccessToProject(Project project, String userEmail) {
        User user = getUserByEmail(userEmail);
        boolean hasAccess = projectMemberRepository.existsByProjectIdAndUserId(project.getId(), user.getId());
        if (!hasAccess) {
            throw new ForbiddenException("You don't have access to this project");
        }
    }

    private void checkCanEditTask(Task task, User user) {
        boolean isCreator = task.getCreatedBy().getId().equals(user.getId());

        ProjectMember member = getMemberOrThrow(task.getProject(), user);
        if (member.canManageTasks() || isCreator) {
            return;
        }

        TaskAssignment assignment = taskAssignmentRepository
                .findByTaskIdAndAssigneeId(task.getId(), user.getId())
                .orElse(null);

        if (assignment == null || !assignment.canEdit()) {
            throw new ForbiddenException("You don't have permission to edit this task");
        }
    }

    private void checkCanUpdateStatus(Task task, User user) {
        // Fast-path: project owner or task creator can always move status
        if (task.getProject().getOwner().getId().equals(user.getId())
                || task.getCreatedBy().getId().equals(user.getId())) {
            return;
        }

        // Any project member can update status (lighter-touch permissions for Kanban)
        // If you need stricter control, re-enable the assignment/role checks here.
        getMemberOrThrow(task.getProject(), user);
    }

    private TaskResponse toTaskResponse(Task task) {
        UserResponse createdBy = UserResponse.builder()
                .id(task.getCreatedBy().getId())
                .email(task.getCreatedBy().getEmail())
                .fullName(task.getCreatedBy().getFullName())
                .skills(task.getCreatedBy().getSkills())
                .createdAt(task.getCreatedBy().getCreatedAt())
                .build();

        return TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .dueDate(task.getDueDate())
                .status(task.getStatus())
                .priority(task.getPriority())
                .completed(task.getCompleted())
                .createdBy(createdBy)
                .projectId(task.getProject().getId())
                .assignedUserCount(task.getAssignedUserCount())
                .createdAt(task.getCreatedAt())
                .updatedAt(task.getUpdatedAt())
                .build();
    }
}
