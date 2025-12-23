package ma.ensah.taskflowprojecthahnsoftwarebackend.service.impl;


import ma.ensah.taskflowprojecthahnsoftwarebackend.domain.entity.ProjectMember;
import ma.ensah.taskflowprojecthahnsoftwarebackend.domain.entity.Task;
import ma.ensah.taskflowprojecthahnsoftwarebackend.domain.entity.TaskAssignment;
import ma.ensah.taskflowprojecthahnsoftwarebackend.domain.entity.User;
import ma.ensah.taskflowprojecthahnsoftwarebackend.domain.enums.TaskPermission;
import ma.ensah.taskflowprojecthahnsoftwarebackend.dto.request.AssignTaskRequest;
import ma.ensah.taskflowprojecthahnsoftwarebackend.dto.response.MemberResponse;
import ma.ensah.taskflowprojecthahnsoftwarebackend.dto.response.UserResponse;
import ma.ensah.taskflowprojecthahnsoftwarebackend.exception.ForbiddenException;
import ma.ensah.taskflowprojecthahnsoftwarebackend.exception.ResourceNotFoundException;
import ma.ensah.taskflowprojecthahnsoftwarebackend.exception.ValidationException;
import ma.ensah.taskflowprojecthahnsoftwarebackend.repository.ProjectMemberRepository;
import ma.ensah.taskflowprojecthahnsoftwarebackend.repository.TaskAssignmentRepository;
import ma.ensah.taskflowprojecthahnsoftwarebackend.repository.TaskRepository;
import ma.ensah.taskflowprojecthahnsoftwarebackend.repository.UserRepository;
import ma.ensah.taskflowprojecthahnsoftwarebackend.service.ITaskAssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskAssignmentServiceImpl implements ITaskAssignmentService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskAssignmentRepository taskAssignmentRepository;
    private final ProjectMemberRepository projectMemberRepository;

    @Override
    @Transactional
    public List<MemberResponse> assignUsersToTask(Long taskId, AssignTaskRequest request, String userEmail) {
        Task task = getTaskOrThrow(taskId);
        User currentUser = getUserByEmail(userEmail);

        checkCanAssignTasks(task, currentUser);

        List<TaskAssignment> assignments = new ArrayList<>();

        for (Long userId : request.getUserIds()) {
            User assignee = userRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

            ProjectMember assigneeMember = projectMemberRepository
                    .findByProjectIdAndUserId(task.getProject().getId(), assignee.getId())
                    .orElseThrow(() -> new ValidationException("User " + assignee.getEmail() +
                            " is not a member of this project"));

            if (taskAssignmentRepository.existsByTaskIdAndAssigneeId(taskId, userId)) {
                throw new ValidationException("User " + assignee.getEmail() + " is already assigned to this task");
            }

            TaskAssignment assignment = TaskAssignment.builder()
                    .task(task)
                    .assignee(assignee)
                    .permission(request.getPermission())
                    .assignedBy(currentUser)
                    .build();

            assignments.add(taskAssignmentRepository.save(assignment));
        }

        return assignments.stream()
                .map(this::toMemberResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<MemberResponse> getTaskAssignees(Long taskId, String userEmail) {
        Task task = getTaskOrThrow(taskId);
        checkUserAccessToProject(task, userEmail);

        List<TaskAssignment> assignments = taskAssignmentRepository.findByTaskId(taskId);
        return assignments.stream()
                .map(this::toMemberResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void unassignUser(Long taskId, Long userId, String userEmail) {
        Task task = getTaskOrThrow(taskId);
        User currentUser = getUserByEmail(userEmail);

        checkCanAssignTasks(task, currentUser);

        TaskAssignment assignment = taskAssignmentRepository
                .findByTaskIdAndAssigneeId(taskId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("User is not assigned to this task"));

        taskAssignmentRepository.delete(assignment);
    }

    @Override
    @Transactional
    public MemberResponse updatePermission(Long taskId, Long userId, TaskPermission permission, String userEmail) {
        Task task = getTaskOrThrow(taskId);
        User currentUser = getUserByEmail(userEmail);

        checkCanAssignTasks(task, currentUser);

        TaskAssignment assignment = taskAssignmentRepository
                .findByTaskIdAndAssigneeId(taskId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("User is not assigned to this task"));

        assignment.setPermission(permission);
        TaskAssignment updated = taskAssignmentRepository.save(assignment);

        return toMemberResponse(updated);
    }

    private Task getTaskOrThrow(Long taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));
    }

    private User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    private void checkCanAssignTasks(Task task, User user) {
        ProjectMember member = projectMemberRepository
                .findByProjectIdAndUserId(task.getProject().getId(), user.getId())
                .orElseThrow(() -> new ForbiddenException("You are not a member of this project"));

        if (!member.canManageTasks()) {
            throw new ForbiddenException("Only owners and admins can assign tasks");
        }
    }

    private void checkUserAccessToProject(Task task, String userEmail) {
        User user = getUserByEmail(userEmail);
        boolean hasAccess = projectMemberRepository
                .existsByProjectIdAndUserId(task.getProject().getId(), user.getId());

        if (!hasAccess) {
            throw new ForbiddenException("You don't have access to this project");
        }
    }

    private MemberResponse toMemberResponse(TaskAssignment assignment) {
        UserResponse user = UserResponse.builder()
                .id(assignment.getAssignee().getId())
                .email(assignment.getAssignee().getEmail())
                .fullName(assignment.getAssignee().getFullName())
                .skills(assignment.getAssignee().getSkills())
                .createdAt(assignment.getAssignee().getCreatedAt())
                .build();

        return MemberResponse.builder()
                .id(assignment.getId())
                .userId(assignment.getAssignee().getId())
                .user(user)
                .role(null) // Not applicable for task assignments
                .canAddMembers(null)
                .permission(assignment.getPermission()) // Task permission level
                .joinedAt(assignment.getAssignedAt())
                .build();
    }
}