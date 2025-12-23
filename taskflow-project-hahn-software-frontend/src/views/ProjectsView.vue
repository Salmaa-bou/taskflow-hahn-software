<template>
  <div class="max-w-6xl mx-auto px-4 sm:px-6 py-8">
    <div class="flex items-center justify-between mb-8">
      <div>
        <p class="text-xs uppercase tracking-[0.2em] text-primary-200">Dashboard</p>
        <h1 class="text-4xl font-black text-[var(--text)] drop-shadow-sm">My Projects</h1>
        <p class="text-[var(--muted)] mt-1">Manage all your projects in one place</p>
      </div>
      <button @click="showCreateModal = true" class="btn-primary flex items-center gap-2 shadow-lg shadow-primary-500/30">
        <Plus class="w-5 h-5" />
        New Project
      </button>
    </div>
    
    <div v-if="loading" class="text-center py-12">
      <div class="inline-block animate-spin rounded-full h-8 w-8 border-4 border-primary-600 border-t-transparent"></div>
    </div>
    
    <div v-else-if="projects.length === 0" class="text-center py-12 glass-panel border border-white/10">
      <FolderOpen class="w-16 h-16 text-gray-400 mx-auto mb-4" />
      <h3 class="text-lg font-medium text-white mb-2">No projects yet</h3>
      <p class="text-gray-400 mb-4">Create your first project to get started</p>
      <button @click="showCreateModal = true" class="btn-primary">
        Create Project
      </button>
    </div>
    
    <div v-else class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
      <router-link
        v-for="project in projects"
        :key="project.id"
        :to="`/projects/${project.id}`"
        class="card hover:border-primary-500/40 transition cursor-pointer"
      >
        <div class="flex items-start justify-between mb-4">
          <div class="flex items-center gap-3">
            <div class="p-2 bg-primary-500/15 rounded-lg border border-primary-500/30">
              <FolderKanban class="w-6 h-6 text-primary-200" />
            </div>
            <h3 class="text-lg font-semibold text-[var(--text)] drop-shadow-sm">{{ project.title }}</h3>
          </div>
        </div>
        
        <p v-if="project.description" class="text-[var(--muted)] text-sm mb-4 line-clamp-2">
          {{ project.description }}
        </p>
        
        <div class="text-xs text-gray-400">
          Created {{ formatDate(project.createdAt) }}
        </div>
      </router-link>
    </div>
    
    <!-- Create Project Modal -->
    <div v-if="showCreateModal" class="fixed inset-0 bg-black/70 flex items-center justify-center p-4 z-50">
      <div class="modal-content max-w-md w-full p-6 border border-white/10 shadow-2xl shadow-black/50">
        <div class="flex items-center justify-between mb-2">
          <div>
            <h2 class="text-xl font-bold text-[var(--text)]">Create New Project</h2>
            <p class="text-sm text-[var(--muted)]">Name it, add a short description, and start shipping.</p>
          </div>
          <button class="text-gray-400 hover:text-white" @click="closeModal">✕</button>
        </div>
        
        <form @submit.prevent="handleCreateProject" class="space-y-4">
          <div>
            <label class="block text-sm font-medium text-gray-300 mb-1">Title*</label>
            <input
              v-model="newProject.title"
              type="text"
              required
              class="input-field"
              placeholder="My Project"
            />
          </div>
          
          <div>
            <label class="block text-sm font-medium text-gray-300 mb-1">Description</label>
            <textarea
              v-model="newProject.description"
              class="input-field"
              rows="3"
              placeholder="Project description..."
            />
          </div>
          
          <div class="flex gap-3 pt-4">
            <button type="submit" :disabled="creating" class="btn-primary flex-1">
              {{ creating ? 'Creating...' : 'Create' }}
            </button>
            <button type="button" @click="closeModal" class="btn-secondary flex-1">
              Cancel
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { Plus, FolderOpen, FolderKanban } from 'lucide-vue-next'
import { useProjectsStore } from '@/stores/projects'
import { useNotificationStore } from '@/stores/notifications'
import { storeToRefs } from 'pinia'

const projectsStore = useProjectsStore()
const notificationStore = useNotificationStore()
const { projects, loading } = storeToRefs(projectsStore)

const showCreateModal = ref(false)
const creating = ref(false)
const newProject = ref({ title: '', description: '' })

onMounted(() => {
  projectsStore.fetchProjects()
})

const handleCreateProject = async () => {
  creating.value = true
  try {
    await projectsStore.createProject(newProject.value)
    notificationStore.success('Project created successfully!')
    closeModal()
  } catch (error: any) {
    console.error('Failed to create project:', error)
    notificationStore.error(error.response?.data?.message || 'Failed to create project')
  } finally {
    creating.value = false
  }
}

const closeModal = () => {
  showCreateModal.value = false
  newProject.value = { title: '', description: '' }
}

const formatDate = (dateString: string) => {
  const date = new Date(dateString)
  return date.toLocaleDateString('en-US', { month: 'short', day: 'numeric', year: 'numeric' })
}
</script>
