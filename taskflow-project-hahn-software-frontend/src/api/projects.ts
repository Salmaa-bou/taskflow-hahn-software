import api from "./axios"
import type { Project, CreateProjectRequest, ProjectProgress } from "@/types"

export const projectsApi = {
  getAll: () => api.get<Project[]>("/projects"),

  getById: (id: number) => api.get<Project>(`/projects/${id}`),

  create: (data: CreateProjectRequest) => api.post<Project>("/projects", data),

  update: (id: number, data: Partial<CreateProjectRequest>) => api.put<Project>(`/projects/${id}`, data),

  delete: (id: number) => api.delete(`/projects/${id}`),

  getProgress: (id: number) => api.get<ProjectProgress>(`/projects/${id}/progress`),
}
