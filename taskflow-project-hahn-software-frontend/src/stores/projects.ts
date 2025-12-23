import { defineStore } from "pinia"
import { ref } from "vue"
import { projectsApi } from "@/api/projects"
import type { Project, CreateProjectRequest, ProjectProgress } from "@/types"

export const useProjectsStore = defineStore("projects", () => {
  const projects = ref<Project[]>([])
  const currentProject = ref<Project | null>(null)
  const currentProgress = ref<ProjectProgress | null>(null)
  const loading = ref(false)
  const error = ref<string | null>(null)

  const fetchProjects = async () => {
    loading.value = true
    error.value = null
    try {
      const response = await projectsApi.getAll()
      projects.value = response.data
    } catch (err: any) {
      error.value = err.response?.data?.message || "Failed to fetch projects"
      throw err
    } finally {
      loading.value = false
    }
  }

  const fetchProject = async (id: number) => {
    loading.value = true
    error.value = null
    try {
      const response = await projectsApi.getById(id)
      currentProject.value = response.data
    } catch (err: any) {
      error.value = err.response?.data?.message || "Failed to fetch project"
      throw err
    } finally {
      loading.value = false
    }
  }

  const fetchProgress = async (id: number) => {
    try {
      const response = await projectsApi.getProgress(id)
      currentProgress.value = response.data
    } catch (err: any) {
      console.error("Failed to fetch progress:", err)
    }
  }

  const createProject = async (data: CreateProjectRequest) => {
    loading.value = true
    error.value = null
    try {
      const response = await projectsApi.create(data)
      projects.value.push(response.data)
      return response.data
    } catch (err: any) {
      error.value = err.response?.data?.message || "Failed to create project"
      throw err
    } finally {
      loading.value = false
    }
  }

  const deleteProject = async (id: number) => {
    try {
      await projectsApi.delete(id)
      projects.value = projects.value.filter((p) => p.id !== id)
    } catch (err: any) {
      error.value = err.response?.data?.message || "Failed to delete project"
      throw err
    }
  }

  return {
    projects,
    currentProject,
    currentProgress,
    loading,
    error,
    fetchProjects,
    fetchProject,
    fetchProgress,
    createProject,
    deleteProject,
  }
})
