package ma.ensah.taskflowprojecthahnsoftwarebackend.service;

import ma.ensah.taskflowprojecthahnsoftwarebackend.domain.enums.TaskPermission;
import ma.ensah.taskflowprojecthahnsoftwarebackend.dto.request.AssignTaskRequest;
import ma.ensah.taskflowprojecthahnsoftwarebackend.dto.response.MemberResponse;

import java.util.List;

public interface ITaskAssignmentService {
    List<MemberResponse> assignUsersToTask(Long taskId, AssignTaskRequest request, String userEmail);
    List<MemberResponse> getTaskAssignees(Long taskId, String userEmail);
    void unassignUser(Long taskId, Long userId, String userEmail);
    MemberResponse updatePermission(Long taskId, Long userId, TaskPermission permission, String userEmail);
}