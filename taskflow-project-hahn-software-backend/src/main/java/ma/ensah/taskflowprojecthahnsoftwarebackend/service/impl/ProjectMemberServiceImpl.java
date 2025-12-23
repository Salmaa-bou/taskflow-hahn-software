package ma.ensah.taskflowprojecthahnsoftwarebackend.service.impl;

import lombok.RequiredArgsConstructor;
import ma.ensah.taskflowprojecthahnsoftwarebackend.dto.request.ProjectRequest;
import ma.ensah.taskflowprojecthahnsoftwarebackend.service.IProjectMemberService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ma.ensah.taskflowprojecthahnsoftwarebackend.domain.entity.Project;
import ma.ensah.taskflowprojecthahnsoftwarebackend.domain.entity.ProjectMember;
import ma.ensah.taskflowprojecthahnsoftwarebackend.domain.entity.User;
import ma.ensah.taskflowprojecthahnsoftwarebackend.domain.enums.ProjectRole;
import ma.ensah.taskflowprojecthahnsoftwarebackend.dto.request.AddMemberRequest;
import ma.ensah.taskflowprojecthahnsoftwarebackend.dto.response.MemberResponse;
import ma.ensah.taskflowprojecthahnsoftwarebackend.dto.response.UserResponse;
import ma.ensah.taskflowprojecthahnsoftwarebackend.exception.ForbiddenException;
import ma.ensah.taskflowprojecthahnsoftwarebackend.exception.ResourceNotFoundException;
import ma.ensah.taskflowprojecthahnsoftwarebackend.exception.ValidationException;
import ma.ensah.taskflowprojecthahnsoftwarebackend.repository.ProjectMemberRepository;
import ma.ensah.taskflowprojecthahnsoftwarebackend.repository.ProjectRepository;
import ma.ensah.taskflowprojecthahnsoftwarebackend.repository.UserRepository;
import ma.ensah.taskflowprojecthahnsoftwarebackend.service.IProjectMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectMemberServiceImpl implements IProjectMemberService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ProjectMemberRepository projectMemberRepository;

    @Override
    @Transactional
    public MemberResponse addMember(Long projectId, AddMemberRequest request, String userEmail) {
        Project project = getProjectOrThrow(projectId);
        User currentUser = getUserByEmail(userEmail);
        User newUser = getUserByEmail(request.getEmail());

        ProjectMember currentMember = getMemberOrThrow(project, currentUser);
        if (!currentMember.canManageMembers()) {
            throw new ForbiddenException("You don't have permission to add members");
        }

        if (projectMemberRepository.existsByProjectIdAndUserId(projectId, newUser.getId())) {
            throw new ValidationException("User is already a member of this project");
        }

        ProjectMember newMember = ProjectMember.builder()
                .project(project)
                .user(newUser)
                .role(request.getRole())
                .canAddMembers(request.getCanAddMembers())
                .build();

        ProjectMember saved = projectMemberRepository.save(newMember);
        return toMemberResponse(saved);
    }

    @Override
    public List<MemberResponse> getProjectMembers(Long projectId, String userEmail) {
        Project project = getProjectOrThrow(projectId);
        checkUserAccess(project, userEmail);

        List<ProjectMember> members = projectMemberRepository.findByProjectId(projectId);
        return members.stream()
                .map(this::toMemberResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public MemberResponse updateMemberRole(Long projectId, Long userId, ProjectRole role, String userEmail) {
        Project project = getProjectOrThrow(projectId);
        User currentUser = getUserByEmail(userEmail);

        ProjectMember currentMember = getMemberOrThrow(project, currentUser);
        if (!currentMember.canManageMembers()) {
            throw new ForbiddenException("You don't have permission to update member roles");
        }

        User targetUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        ProjectMember targetMember = getMemberOrThrow(project, targetUser);

        if (targetMember.isOwner()) {
            throw new ForbiddenException("Cannot change owner role");
        }

        targetMember.setRole(role);
        ProjectMember updated = projectMemberRepository.save(targetMember);
        return toMemberResponse(updated);
    }

    @Override
    @Transactional
    public void removeMember(Long projectId, Long userId, String userEmail) {
        Project project = getProjectOrThrow(projectId);
        User currentUser = getUserByEmail(userEmail);

        ProjectMember currentMember = getMemberOrThrow(project, currentUser);
        if (!currentMember.canManageMembers()) {
            throw new ForbiddenException("You don't have permission to remove members");
        }

        User targetUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        ProjectMember targetMember = getMemberOrThrow(project, targetUser);

        if (targetMember.isOwner()) {
            throw new ForbiddenException("Cannot remove project owner");
        }

        projectMemberRepository.delete(targetMember);
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
                .orElseThrow(() -> new ForbiddenException("User is not a member of this project"));
    }

    private void checkUserAccess(Project project, String userEmail) {
        User user = getUserByEmail(userEmail);
        boolean hasAccess = projectMemberRepository.existsByProjectIdAndUserId(project.getId(), user.getId());
        if (!hasAccess) {
            throw new ForbiddenException("You don't have access to this project");
        }
    }

    private MemberResponse toMemberResponse(ProjectMember member) {
        UserResponse user = UserResponse.builder()
                .id(member.getUser().getId())
                .email(member.getUser().getEmail())
                .fullName(member.getUser().getFullName())
                .skills(member.getUser().getSkills())
                .createdAt(member.getUser().getCreatedAt())
                .build();

        return MemberResponse.builder()
                .id(member.getId())
                .userId(member.getUser().getId())
                .user(user)
                .role(member.getRole())
                .canAddMembers(member.getCanAddMembers())
                .permission(null) // Not applicable for project members
                .joinedAt(member.getJoinedAt())
                .build();
    }
}