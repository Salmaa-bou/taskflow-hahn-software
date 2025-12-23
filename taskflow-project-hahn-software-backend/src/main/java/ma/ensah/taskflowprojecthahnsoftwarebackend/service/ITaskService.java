package ma.ensah.taskflowprojecthahnsoftwarebackend.service;


import ma.ensah.taskflowprojecthahnsoftwarebackend.domain.enums.TaskStatus;
import ma.ensah.taskflowprojecthahnsoftwarebackend.dto.request.TaskRequest;
import ma.ensah.taskflowprojecthahnsoftwarebackend.dto.response.TaskResponse;

import java.util.List;

public interface ITaskService {
    TaskResponse createTask(Long projectId, TaskRequest request, String userEmail);
    TaskResponse getTaskById(Long taskId, String userEmail);
    List<TaskResponse> getProjectTasks(Long projectId, String userEmail);
    TaskResponse updateTask(Long taskId, TaskRequest request, String userEmail);
    void deleteTask(Long taskId, String userEmail);
    TaskResponse updateTaskStatus(Long taskId, TaskStatus status, String userEmail);
    TaskResponse markAsCompleted(Long taskId, String userEmail);
    TaskResponse markAsIncomplete(Long taskId, String userEmail);
}