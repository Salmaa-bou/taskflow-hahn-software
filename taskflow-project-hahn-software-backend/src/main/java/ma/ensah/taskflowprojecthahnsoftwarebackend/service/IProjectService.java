package ma.ensah.taskflowprojecthahnsoftwarebackend.service;

import ma.ensah.taskflowprojecthahnsoftwarebackend.dto.request.ProjectRequest;
import ma.ensah.taskflowprojecthahnsoftwarebackend.dto.response.ProjectProgressResponse;
import ma.ensah.taskflowprojecthahnsoftwarebackend.dto.response.ProjectResponse;

import java.util.List;

public interface IProjectService {
    ProjectResponse createProject(ProjectRequest request, String userEmail);
    ProjectResponse getProjectById(Long projectId, String userEmail);
    List<ProjectResponse> getAllUserProjects(String userEmail);
    ProjectResponse updateProject(Long projectId, ProjectRequest request, String userEmail);
    void deleteProject(Long projectId, String userEmail);
    ProjectProgressResponse getProjectProgress(Long projectId, String userEmail);
}
