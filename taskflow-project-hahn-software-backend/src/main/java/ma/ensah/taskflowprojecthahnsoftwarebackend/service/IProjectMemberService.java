package ma.ensah.taskflowprojecthahnsoftwarebackend.service;

import ma.ensah.taskflowprojecthahnsoftwarebackend.domain.enums.ProjectRole;
import ma.ensah.taskflowprojecthahnsoftwarebackend.dto.request.AddMemberRequest;
import ma.ensah.taskflowprojecthahnsoftwarebackend.dto.response.MemberResponse;

import java.util.List;

public interface IProjectMemberService {
    MemberResponse addMember(Long projectId, AddMemberRequest request, String userEmail);
    List<MemberResponse> getProjectMembers(Long projectId, String userEmail);
    MemberResponse updateMemberRole(Long projectId, Long userId, ProjectRole role, String userEmail);
    void removeMember(Long projectId, Long userId, String userEmail);
}