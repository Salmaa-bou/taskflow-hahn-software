import api from "./axios"
import type { ProjectMember } from "@/types"

export const membersApi = {
  getByProject: (projectId: number) => api.get<ProjectMember[]>(`/projects/${projectId}/members`),

  // Backend expects AddMemberRequest body: { email, role, canAddMembers }
  add: (projectId: number, body: { email: string; role: string; canAddMembers?: boolean }) =>
    api.post<ProjectMember>(`/projects/${projectId}/members`, body),

  updateRole: (projectId: number, userId: number, role: string) =>
    api.put<ProjectMember>(`/projects/${projectId}/members/${userId}`, null, {
      params: { role },
    }),

  remove: (projectId: number, userId: number) => api.delete(`/projects/${projectId}/members/${userId}`),
}
