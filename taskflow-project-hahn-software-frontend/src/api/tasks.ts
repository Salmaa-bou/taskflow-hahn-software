import api from "./axios"
import type { Task, CreateTaskRequest, UpdateTaskRequest } from "@/types"

export const tasksApi = {
  getByProject: (projectId: number) => api.get<Task[]>(`/projects/${projectId}/tasks`),

  getById: (id: number) => api.get<Task>(`/tasks/${id}`),

  create: (projectId: number, data: CreateTaskRequest) => api.post<Task>(`/projects/${projectId}/tasks`, data),

  update: (id: number, data: UpdateTaskRequest) => api.put<Task>(`/tasks/${id}`, data),

  delete: (id: number) => api.delete(`/tasks/${id}`),

  markComplete: (id: number) => api.patch<Task>(`/tasks/${id}/complete`),

  markIncomplete: (id: number) => api.patch<Task>(`/tasks/${id}/incomplete`),

  // Task assignment endpoints
  assignUsersToTask: (taskId: number, data: any) => api.post(`/tasks/${taskId}/assign`, data),

  getTaskAssignees: (taskId: number) => api.get(`/tasks/${taskId}/assignments`),

  unassignUser: (taskId: number, userId: number) => api.delete(`/tasks/${taskId}/unassign/${userId}`),

  updatePermission: (taskId: number, userId: number, permission: string) =>
    api.put(`/tasks/${taskId}/permissions/${userId}`, null, { params: { permission } }),
  
  // Update task status: expects query param `status`
  updateStatus: (id: number, status: string) => api.patch(`/tasks/${id}/status`, null, { params: { status } }),
}
