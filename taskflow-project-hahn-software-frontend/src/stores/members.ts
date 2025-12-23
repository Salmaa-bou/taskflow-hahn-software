import { defineStore } from "pinia"
import { ref } from "vue"
import { membersApi } from "@/api/members"
import { tasksApi } from "@/api/tasks"
import type { ProjectMember, AssignTaskRequest, TaskAssignment } from "@/types"

export const useMembersStore = defineStore("members", () => {
  const members = ref<ProjectMember[]>([])
  const assignees = ref<TaskAssignment[]>([])
  const loading = ref(false)
  const error = ref<string | null>(null)

  const fetchMembers = async (projectId: number) => {
    loading.value = true
    error.value = null
    try {
      const res = await membersApi.getByProject(projectId)
      members.value = res.data
    } catch (err: any) {
      error.value = err.response?.data?.message || "Failed to fetch members"
      throw err
    } finally {
      loading.value = false
    }
  }

  // Add by email (backend expects email + role)
  const addMember = async (projectId: number, email: string, role: string, canAddMembers = false) => {
    try {
      const res = await membersApi.add(projectId, { email, role, canAddMembers })
      members.value.push(res.data)
      return res.data
    } catch (err: any) {
      error.value = err.response?.data?.message || "Failed to add member"
      throw err
    }
  }

  const updateRole = async (projectId: number, userId: number, role: string) => {
    try {
      const res = await membersApi.updateRole(projectId, userId, role)
      const idx = members.value.findIndex((m) => m.userId === userId)
      if (idx !== -1) members.value[idx] = res.data
      return res.data
    } catch (err: any) {
      error.value = err.response?.data?.message || "Failed to update role"
      throw err
    }
  }

  const removeMember = async (projectId: number, userId: number) => {
    try {
      await membersApi.remove(projectId, userId)
      members.value = members.value.filter((m) => m.userId !== userId)
    } catch (err: any) {
      error.value = err.response?.data?.message || "Failed to remove member"
      throw err
    }
  }

  // Task assignments
  const assignUsersToTask = async (taskId: number, data: AssignTaskRequest) => {
    try {
      console.log('Assign payload:', { taskId, ...data })
      const res = await tasksApi.assignUsersToTask(taskId, data)
      // backend returns MemberResponse[] or similar; refresh assignees
      const assignments = await tasksApi.getTaskAssignees(taskId)
      assignees.value = assignments.data
      return res.data
    } catch (err: any) {
      // capture and expose backend validation details
      const resp = err.response
      console.error('Assign error response:', resp?.status, resp?.data)
      const details = resp?.data || resp?.data?.errors || resp?.data?.message || err.message
      error.value = typeof details === 'string' ? details : JSON.stringify(details)
      // rethrow with enriched message
      throw new Error(error.value)
    }
  }

  const fetchTaskAssignees = async (taskId: number) => {
    try {
      const res = await tasksApi.getTaskAssignees(taskId)
      assignees.value = res.data
      return res.data
    } catch (err: any) {
      error.value = err.response?.data?.message || "Failed to fetch assignees"
      throw err
    }
  }

  const unassignUser = async (taskId: number, userId: number) => {
    try {
      await tasksApi.unassignUser(taskId, userId)
      assignees.value = assignees.value.filter((a) => a.userId !== userId)
    } catch (err: any) {
      error.value = err.response?.data?.message || "Failed to unassign user"
      throw err
    }
  }

  const updatePermission = async (taskId: number, userId: number, permission: string) => {
    try {
      const res = await tasksApi.updatePermission(taskId, userId, permission)
      // refresh assignees
      await fetchTaskAssignees(taskId)
      return res.data
    } catch (err: any) {
      error.value = err.response?.data?.message || "Failed to update permission"
      throw err
    }
  }

  return {
    members,
    assignees,
    loading,
    error,
    fetchMembers,
    addMember,
    updateRole,
    removeMember,
    assignUsersToTask,
    fetchTaskAssignees,
    unassignUser,
    updatePermission,
  }
})
