import { defineStore } from "pinia"
import { ref } from "vue"
import { tasksApi } from "@/api/tasks"
import type { Task, CreateTaskRequest, UpdateTaskRequest } from "@/types"

export const useTasksStore = defineStore("tasks", () => {
  const tasks = ref<Task[]>([])
  const loading = ref(false)
  const error = ref<string | null>(null)

  const fetchTasks = async (projectId: number) => {
    loading.value = true
    error.value = null
    try {
      const response = await tasksApi.getByProject(projectId)
      tasks.value = response.data
    } catch (err: any) {
      error.value = err.response?.data?.message || "Failed to fetch tasks"
      throw err
    } finally {
      loading.value = false
    }
  }

  const createTask = async (projectId: number, data: CreateTaskRequest) => {
    loading.value = true
    error.value = null
    try {
      const response = await tasksApi.create(projectId, data)
      tasks.value.push(response.data)
      return response.data
    } catch (err: any) {
      error.value = err.response?.data?.message || "Failed to create task"
      throw err
    } finally {
      loading.value = false
    }
  }

  const updateTask = async (id: number, data: UpdateTaskRequest) => {
    try {
      const response = await tasksApi.update(id, data)
      const index = tasks.value.findIndex((t) => t.id === id)
      if (index !== -1) {
        tasks.value[index] = response.data
      }
      return response.data
    } catch (err: any) {
      error.value = err.response?.data?.message || "Failed to update task"
      throw err
    }
  }

  const toggleComplete = async (task: Task) => {
    try {
      const response = task.completed ? await tasksApi.markIncomplete(task.id) : await tasksApi.markComplete(task.id)
      const index = tasks.value.findIndex((t) => t.id === task.id)
      if (index !== -1) {
        tasks.value[index] = response.data
      }
    } catch (err: any) {
      error.value = err.response?.data?.message || "Failed to toggle task"
      throw err
    }
  }

  const deleteTask = async (id: number) => {
    try {
      await tasksApi.delete(id)
      tasks.value = tasks.value.filter((t) => t.id !== id)
    } catch (err: any) {
      error.value = err.response?.data?.message || "Failed to delete task"
      throw err
    }
  }

  const updateStatus = async (id: number, status: string) => {
    try {
      const response = await tasksApi.updateStatus(id, status)
      const index = tasks.value.findIndex((t) => t.id === id)
      if (index !== -1) tasks.value[index] = response.data
      return response.data
    } catch (err: any) {
      error.value = err.response?.data?.message || "Failed to update status"
      throw err
    }
  }

  return {
    tasks,
    loading,
    error,
    fetchTasks,
    createTask,
    updateTask,
    toggleComplete,
    deleteTask,
    updateStatus,
  }
})
