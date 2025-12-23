<template>
  <div class="space-y-8">
    <div class="flex items-center justify-between gap-4">
      <button @click="router.push('/')" class="inline-flex items-center gap-2 text-gray-300 hover:text-white transition">
        <ChevronLeft class="w-5 h-5" />
        Back to projects
      </button>
      <div class="flex items-center gap-3">
        <button @click="toggleMembersPanel" class="btn-secondary text-sm">
          {{ showMembers ? 'Hide' : 'Show' }} team
        </button>
        <button @click="showTaskModal = true" class="btn-primary text-sm px-4">
          <Plus class="w-4 h-4" /> New task
        </button>
      </div>
    </div>

    <div v-if="project" class="glass-panel p-6 border border-white/10">
      <div class="flex items-start justify-between gap-4 flex-wrap">
        <div class="space-y-2">
          <p class="text-xs uppercase tracking-[0.2em] text-gray-400">Project</p>
          <h1 class="text-3xl font-semibold text-white">{{ project.title }}</h1>
          <p v-if="project.description" class="text-gray-400 max-w-2xl">{{ project.description }}</p>
        </div>
        <div class="grid grid-cols-2 sm:grid-cols-3 gap-3">
          <div class="glass-panel px-4 py-3 border border-primary-500/20">
            <p class="text-xs text-gray-400 uppercase tracking-[0.2em]">Progress</p>
            <p class="text-2xl font-bold text-primary-100">
              {{ progress ? progress.progressPercentage.toFixed(0) : 0 }}%
            </p>
          </div>
          <div class="glass-panel px-4 py-3 border border-white/10">
            <p class="text-xs text-gray-400 uppercase tracking-[0.2em]">Complete</p>
            <p class="text-lg font-semibold text-white">{{ progress?.completedTasks || 0 }}</p>
          </div>
          <div class="glass-panel px-4 py-3 border border-white/10">
            <p class="text-xs text-gray-400 uppercase tracking-[0.2em]">Total</p>
            <p class="text-lg font-semibold text-white">{{ progress?.totalTasks || tasks.length }}</p>
          </div>
        </div>
      </div>
    </div>

    <div v-if="showMembers" class="glass-panel p-6 border border-white/10 space-y-4">
      <div class="flex items-center justify-between">
        <div>
          <p class="text-xs uppercase tracking-[0.2em] text-gray-400">Team</p>
          <h3 class="text-xl font-semibold text-white">Project members</h3>
        </div>
        <div class="flex items-center gap-2">
          <input v-model="newMemberEmail" type="email" placeholder="Add by email" class="input-field flex-[0_0_60%]" />
          <select v-model="newMemberRole" class="input-field flex-[0_0_20%]">
            <option value="MEMBER">Member</option>
            <option value="ADMIN">Admin</option>
            <option value="OWNER">Owner</option>
            <option value="VIEWER">Viewer</option>
          </select>
          <button @click="handleAddMember" :disabled="addingMember" class="btn-primary flex-[0_0_20%]">
            {{ addingMember ? 'Adding...' : 'Invite' }}
          </button>
        </div>
      </div>
      <div class="flex flex-wrap gap-3">
        <div
          v-for="m in membersStore.members"
          :key="m.id || m.userId"
          class="member-chip bg-white/10 border border-white/10"
        >
          <span class="w-7 h-7 rounded-full bg-primary-500/30 flex items-center justify-center font-semibold text-white text-xs">
            {{ getInitials(m.user?.name || m.user?.email || 'U') }}
          </span>
          <div class="flex flex-col">
            <span class="text-sm text-white">{{ m.user?.name || m.user?.email }}</span>
            <span class="text-[10px] text-gray-400 uppercase tracking-wide">{{ m.role }}</span>
          </div>
        </div>
      </div>
    </div>

    <div class="grid grid-cols-1 lg:grid-cols-4 gap-4">
      <div
        v-for="col in columns"
        :key="col.key"
        class="glass-panel p-4 border border-white/10 min-h-[320px]"
        @dragover.prevent
        @dragenter.prevent
        @drop.prevent="handleDrop(col.key, $event)"
      >
        <div class="flex items-center justify-between mb-3">
          <div class="flex items-center gap-2">
            <span class="w-2 h-2 rounded-full" :class="col.dot"></span>
            <h3 class="text-sm font-semibold text-white uppercase tracking-[0.18em]">{{ col.label }}</h3>
          </div>
          <span class="text-xs text-gray-400">{{ columnTasks(col.key).length }}</span>
        </div>

        <div class="space-y-3">
          <div
            v-for="task in columnTasks(col.key)"
            :key="task.id"
            class="bg-white/5 border border-white/10 rounded-2xl p-3 hover:border-primary-500/30 transition cursor-grab active:cursor-grabbing"
            draggable="true"
            @dragstart="handleDragStart(task, $event)"
            @dragend="handleDragEnd"
          >
            <div class="flex items-start justify-between gap-2">
              <div class="space-y-1">
                <div class="flex items-center gap-2">
                  <p class="text-white font-semibold text-sm">{{ task.title }}</p>
                  <span class="pill" :class="priorityPill(task.priority)">
                    {{ task.priority || 'MEDIUM' }}
                  </span>
                </div>
                <p v-if="task.description" class="text-xs text-gray-400 line-clamp-2">{{ task.description }}</p>
              </div>
              <button @click="deleteTaskConfirm(task.id)" class="text-gray-500 hover:text-red-400">
                <Trash2 class="w-4 h-4" />
              </button>
            </div>

            <div class="flex items-center gap-3 text-[11px] text-gray-400 mt-2">
              <span class="inline-flex items-center gap-1">
                <Calendar class="w-3 h-3" />
                {{ task.dueDate ? formatDate(task.dueDate) : 'No due date' }}
              </span>
              <span class="inline-flex items-center gap-1">
                <Clock class="w-3 h-3" />
                {{ formatDate(task.createdAt) }}
              </span>
            </div>

            <div class="mt-3 flex items-center gap-2 flex-wrap">
              <div
                v-for="assignee in taskAssignees[task.id] || []"
                :key="assignee.id || assignee.userId"
                class="flex items-center gap-2 bg-white/10 border border-white/10 px-2.5 py-1 rounded-xl"
              >
                <span class="w-7 h-7 rounded-full bg-primary-500/20 flex items-center justify-center text-xs font-semibold text-primary-100">
                  {{ getInitials(assignee.user?.name || assignee.user?.email || 'U') }}
                </span>
                <div class="flex flex-col">
                  <span class="text-xs text-white">{{ assignee.user?.name || assignee.user?.email }}</span>
                  <span class="text-[10px] text-gray-400">{{ formatPermission(assignee.permission) }}</span>
                </div>
                <button
                  class="text-gray-500 hover:text-red-400"
                  @click="openRemoveAssigneeDialog(task.id, assignee.userId || assignee.id, assignee.user?.name || assignee.user?.email || 'User')"
                >
                  ✕
                </button>
              </div>
              <button
                class="btn-secondary text-xs px-3 py-1"
                @click.prevent="openAssignModal(task)"
              >
                Assign
              </button>
            </div>

            <p class="mt-3 text-[11px] text-gray-500">Drag to another column to change status.</p>
          </div>

          <div
            v-if="columnTasks(col.key).length === 0"
            class="border border-dashed border-white/10 rounded-xl p-4 text-center text-sm text-gray-500"
          >
            Drag tasks here
          </div>
        </div>
      </div>
    </div>

    <!-- Create Task Modal -->
    <div v-if="showTaskModal" class="fixed inset-0 bg-black/60 flex items-center justify-center p-4 z-50">
      <div class="modal-content max-w-md w-full p-6 border border-white/10">
        <div class="flex items-center justify-between mb-4">
          <h2 class="text-xl font-semibold text-white">Add task</h2>
          <button class="text-gray-400 hover:text-white" @click="closeTaskModal">✕</button>
        </div>
        <form @submit.prevent="handleCreateTask" class="space-y-4">
          <div>
            <label class="text-sm text-gray-300">Title</label>
            <input v-model="newTask.title" required class="input-field mt-1" placeholder="Task title" />
          </div>
          <div>
            <label class="text-sm text-gray-300">Description</label>
            <textarea v-model="newTask.description" rows="3" class="input-field mt-1" placeholder="Optional context"></textarea>
          </div>
          <div class="grid grid-cols-2 gap-3">
            <div>
              <label class="text-sm text-gray-300">Priority</label>
              <select v-model="newTask.priority" class="input-field mt-1">
                <option value="LOW">Low</option>
                <option value="MEDIUM">Medium</option>
                <option value="HIGH">High</option>
              </select>
            </div>
            <div>
              <label class="text-sm text-gray-300">Due date</label>
              <input type="date" v-model="newTask.dueDate" class="input-field mt-1" />
            </div>
          </div>
          <div class="flex gap-3 pt-2">
            <button type="submit" :disabled="creating" class="btn-primary flex-1">
              {{ creating ? 'Creating...' : 'Create' }}
            </button>
            <button type="button" class="btn-secondary flex-1" @click="closeTaskModal">Cancel</button>
          </div>
        </form>
      </div>
    </div>

    <!-- Assign Users Modal -->
    <div v-if="showAssignModal" class="fixed inset-0 bg-black/60 flex items-center justify-center p-4 z-50">
      <div class="modal-content max-w-lg w-full p-6 border border-white/10 space-y-4">
        <div class="flex items-center justify-between">
          <div>
            <p class="text-xs uppercase tracking-[0.2em] text-gray-400">Assign</p>
            <h2 class="text-xl font-semibold text-white">Select members</h2>
            <p v-if="taskToAssign" class="text-sm text-gray-400">Task: {{ taskToAssign.title }}</p>
          </div>
          <button class="text-gray-400 hover:text-white" @click="showAssignModal = false">✕</button>
        </div>

        <div class="space-y-3">
          <label class="text-xs uppercase tracking-[0.2em] text-gray-400">Permission</label>
          <select v-model="assignPermission" class="input-field">
            <option value="READ_ONLY">Read only</option>
            <option value="CAN_UPDATE_STATUS">Update status</option>
            <option value="CAN_EDIT">Can edit</option>
          </select>
        </div>

        <div class="space-y-3">
          <label class="text-xs uppercase tracking-[0.2em] text-gray-400">Invite by email</label>
          <div class="flex gap-2">
            <input v-model="inviteEmail" type="email" placeholder="user@company.com" class="input-field flex-[0_0_60%]" />
            <select v-model="inviteRole" class="input-field flex-[0_0_20%]">
              <option value="MEMBER">Member</option>
              <option value="ADMIN">Admin</option>
              <option value="OWNER">Owner</option>
              <option value="VIEWER">Viewer</option>
            </select>
            <button class="btn-secondary flex-[0_0_20%]" :disabled="inviting || !inviteEmail" @click="handleInviteAndSelect">
              {{ inviting ? 'Adding...' : 'Add' }}
            </button>
          </div>
        </div>

        <div class="space-y-2">
          <label class="text-xs uppercase tracking-[0.2em] text-gray-400">Project members</label>
          <input
            v-model="memberSearch"
            type="text"
            placeholder="Search by name, email, or role"
            class="input-field mb-2"
          />
          <div class="max-h-48 overflow-y-auto rounded-xl border border-white/10 divide-y divide-white/5">
            <div v-if="filteredMembers.length === 0" class="p-3 text-sm text-gray-400 text-center">
              No members yet — invite to start assigning.
            </div>
            <label
              v-for="m in filteredMembers"
              :key="m.userId"
              class="flex items-center gap-3 p-3 hover:bg-white/5 cursor-pointer"
            >
              <input
                type="checkbox"
                :value="m.userId"
                v-model="selectedUserIds"
                class="w-4 h-4 rounded border-white/20 text-primary-400 bg-transparent"
              />
              <div class="flex items-center gap-2">
                <span class="w-8 h-8 rounded-full bg-primary-500/20 flex items-center justify-center text-sm font-semibold text-primary-100">
                  {{ getInitials(m.user?.name || m.user?.email || 'U') }}
                </span>
                <div>
                  <p class="text-sm text-white">{{ m.user?.name || m.user?.email }}</p>
                  <p class="text-[11px] text-gray-400 uppercase tracking-wide">{{ m.role }}</p>
                </div>
              </div>
            </label>
          </div>
        </div>

        <div class="flex gap-3 pt-2">
          <button class="btn-secondary flex-1" @click="showAssignModal = false">Cancel</button>
          <button class="btn-primary flex-1" @click="handleAssignUsers">Save</button>
        </div>
      </div>
    </div>

    <!-- Remove Assignee Dialog -->
    <div v-if="showRemoveAssigneeDialog" class="fixed inset-0 bg-black/60 flex items-center justify-center p-4 z-50">
      <div class="modal-content max-w-sm w-full p-6 border border-white/10 space-y-4">
        <h2 class="text-lg font-semibold text-white">Remove {{ assigneeToRemove?.userName }}?</h2>
        <p class="text-sm text-gray-400">Choose how you want to remove this member.</p>
        <label
          class="flex items-center gap-3 p-3 rounded-xl border cursor-pointer"
          :class="removeScope === 'task' ? 'border-primary-500/60 bg-primary-500/5' : 'border-white/10'"
        >
          <input type="radio" v-model="removeScope" value="task" />
          <div>
            <p class="text-white text-sm font-semibold">From this task only</p>
            <p class="text-xs text-gray-400">Keep them in the project, remove from this task.</p>
          </div>
        </label>
        <label
          class="flex items-center gap-3 p-3 rounded-xl border cursor-pointer"
          :class="removeScope === 'project' ? 'border-primary-500/60 bg-primary-500/5' : 'border-white/10'"
        >
          <input type="radio" v-model="removeScope" value="project" />
          <div>
            <p class="text-white text-sm font-semibold">From entire project</p>
            <p class="text-xs text-gray-400">Remove everywhere and all tasks.</p>
          </div>
        </label>
        <div class="flex gap-3">
          <button class="btn-secondary flex-1" @click="showRemoveAssigneeDialog = false">Cancel</button>
          <button class="btn-primary flex-1" @click="handleRemoveAssignee">Remove</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed, onMounted, ref } from "vue"
import { useRoute, useRouter } from "vue-router"
import { ChevronLeft, Plus, Calendar, Clock, Trash2 } from "lucide-vue-next"
import { useProjectsStore } from "@/stores/projects"
import { useTasksStore } from "@/stores/tasks"
import { useMembersStore } from "@/stores/members"
import { useNotificationStore } from "@/stores/notifications"
import { storeToRefs } from "pinia"
import type { Task } from "@/types"

const route = useRoute()
const router = useRouter()
const projectsStore = useProjectsStore()
const tasksStore = useTasksStore()
const membersStore = useMembersStore()
const notificationStore = useNotificationStore()

const projectId = computed(() => parseInt(route.params.id as string))
const { currentProject: project, currentProgress: progress } = storeToRefs(projectsStore)
const { tasks } = storeToRefs(tasksStore)

const showTaskModal = ref(false)
const creating = ref(false)
const newTask = ref({ title: "", description: "", dueDate: "", priority: "MEDIUM" })
const showMembers = ref(false)
const newMemberEmail = ref("")
const newMemberRole = ref("MEMBER")
const addingMember = ref(false)

const showAssignModal = ref(false)
const taskToAssign = ref<Task | null>(null)
const selectedUserIds = ref<number[]>([])
const assignPermission = ref<"READ_ONLY" | "CAN_UPDATE_STATUS" | "CAN_EDIT">("CAN_UPDATE_STATUS")
const inviteEmail = ref("")
const inviteRole = ref("MEMBER")
const inviting = ref(false)
const memberSearch = ref("")
const taskAssignees = ref<Record<number, any[]>>({})

const showRemoveAssigneeDialog = ref(false)
const assigneeToRemove = ref<{ taskId: number; userId: number; userName: string } | null>(null)
const removeScope = ref<"task" | "project">("task")

const draggingTask = ref<Task | null>(null)
const draggingId = ref<number | null>(null)

const columns = [
  { key: "TODO", label: "To Do", dot: "bg-primary-400" },
  { key: "IN_PROGRESS", label: "In Progress", dot: "bg-amber-300" },
  { key: "IN_REVIEW", label: "In Review", dot: "bg-purple-300" },
  { key: "DONE", label: "Done", dot: "bg-emerald-300" },
]

const columnTasks = (status: string) =>
  tasks.value.filter((t) => (t.status || "TODO") === status)

const filteredMembers = computed(() => {
  if (!memberSearch.value.trim()) return membersStore.members
  const term = memberSearch.value.toLowerCase()
  return membersStore.members.filter((m) => {
    const name = m.user?.name?.toLowerCase() || ""
    const email = m.user?.email?.toLowerCase() || ""
    return name.includes(term) || email.includes(term) || (m.role || "").toLowerCase().includes(term)
  })
})

const handleDragStart = (task: Task, event: DragEvent) => {
  draggingTask.value = task
  draggingId.value = task.id
  if (event.dataTransfer) {
    event.dataTransfer.effectAllowed = "move"
    event.dataTransfer.setData("text/plain", String(task.id))
  }
}

const handleDragEnd = () => {
  draggingTask.value = null
  draggingId.value = null
}

const handleDrop = async (status: string, event: DragEvent) => {
  event.preventDefault()
  const droppedId = event.dataTransfer?.getData("text/plain")
  const task =
    draggingTask.value ||
    tasks.value.find((t) => String(t.id) === droppedId) ||
    tasks.value.find((t) => t.id === draggingId.value!)
  if (!task) return
  await changeTaskStatus(task, status)
  draggingTask.value = null
  draggingId.value = null
}

const fetchTaskAssignees = async (taskId: number) => {
  try {
    const assignees = await membersStore.fetchTaskAssignees(taskId)
    taskAssignees.value[taskId] = assignees || []
  } catch (err) {
    console.error("Failed to fetch assignees:", err)
    taskAssignees.value[taskId] = []
  }
}

onMounted(async () => {
  await projectsStore.fetchProject(projectId.value)
  await tasksStore.fetchTasks(projectId.value)
  await projectsStore.fetchProgress(projectId.value)
  await membersStore.fetchMembers(projectId.value)
  await Promise.all(tasks.value.map((task) => fetchTaskAssignees(task.id)))
})

const toggleMembersPanel = () => {
  showMembers.value = !showMembers.value
}

const handleAddMember = async () => {
  if (!newMemberEmail.value || !newMemberRole.value) return
  addingMember.value = true
  try {
    await membersStore.addMember(projectId.value, newMemberEmail.value, newMemberRole.value)
    newMemberEmail.value = ""
    newMemberRole.value = "MEMBER"
    await membersStore.fetchMembers(projectId.value)
    notificationStore.success("Member added to project!")
  } catch (err) {
    console.error("Failed to add member:", err)
    notificationStore.error((err as any)?.response?.data?.message || "Failed to add member")
  } finally {
    addingMember.value = false
  }
}

const openAssignModal = async (task: Task) => {
  taskToAssign.value = task
  selectedUserIds.value = []
  assignPermission.value = "CAN_UPDATE_STATUS"
  await membersStore.fetchTaskAssignees(task.id)
  showAssignModal.value = true
}

const handleAssignUsers = async () => {
  if (!taskToAssign.value) return
  const validUserIds = selectedUserIds.value.filter((id): id is number => id != null && !isNaN(id))
  if (validUserIds.length === 0) {
    notificationStore.warning("Please select at least one user to assign")
    return
  }

  try {
    await membersStore.assignUsersToTask(taskToAssign.value.id, {
      userIds: validUserIds,
      permission: assignPermission.value,
    })
    showAssignModal.value = false
    await tasksStore.fetchTasks(projectId.value)
    await fetchTaskAssignees(taskToAssign.value.id)
    notificationStore.success(`Assigned ${validUserIds.length} user(s)`)
  } catch (err) {
    console.error("Failed to assign users:", err)
    const errorMessage = (err as any)?.message || (err as any)?.response?.data?.message || "Failed to assign users"
    notificationStore.error(errorMessage)
  }
}

const handleInviteAndSelect = async () => {
  if (!inviteEmail.value) return
  inviting.value = true
  try {
    const newMember = await membersStore.addMember(projectId.value, inviteEmail.value, inviteRole.value)
    if (newMember && newMember.userId && !selectedUserIds.value.includes(newMember.userId)) {
      selectedUserIds.value.push(newMember.userId)
    }
    await membersStore.fetchMembers(projectId.value)
    inviteEmail.value = ""
    inviteRole.value = "MEMBER"
    notificationStore.success("Member added successfully!")
  } catch (err) {
    console.error("Failed to invite member:", err)
    notificationStore.error((err as any)?.response?.data?.message || "Failed to add member")
  } finally {
    inviting.value = false
  }
}

const changeTaskStatus = async (task: Task, newStatus: string) => {
  try {
    // optimistic update for snappier DnD
    task.status = newStatus as any
    await tasksStore.updateStatus(task.id, newStatus)
    // ensure local store reflects new status
    const idx = tasks.value.findIndex((t) => t.id === task.id)
    if (idx !== -1) tasks.value[idx].status = newStatus as any
    await projectsStore.fetchProgress(projectId.value)
    await fetchTaskAssignees(task.id)
    notificationStore.success("Task status updated!")
  } catch (err) {
    console.error("Failed to change status:", err)
    notificationStore.error((err as any)?.response?.data?.message || "Failed to change task status")
  }
}

const openRemoveAssigneeDialog = (taskId: number, userId: number, userName: string) => {
  assigneeToRemove.value = { taskId, userId, userName }
  removeScope.value = "task"
  showRemoveAssigneeDialog.value = true
}

const handleRemoveAssignee = async () => {
  if (!assigneeToRemove.value) return
  const { taskId, userId } = assigneeToRemove.value
  try {
    if (removeScope.value === "task") {
      await membersStore.unassignUser(taskId, userId)
      await fetchTaskAssignees(taskId)
    } else {
      await membersStore.removeMember(projectId.value, userId)
      await membersStore.fetchMembers(projectId.value)
      await Promise.all(tasks.value.map((task) => fetchTaskAssignees(task.id)))
    }
    showRemoveAssigneeDialog.value = false
    assigneeToRemove.value = null
    notificationStore.success(removeScope.value === "task" ? "User removed from task!" : "User removed from project!")
  } catch (err) {
    console.error("Failed to remove assignee:", err)
    notificationStore.error((err as any)?.response?.data?.message || "Failed to remove user")
  }
}

const handleCreateTask = async () => {
  creating.value = true
  try {
    const taskData = {
      title: newTask.value.title,
      description: newTask.value.description || undefined,
      dueDate: newTask.value.dueDate || undefined,
      priority: newTask.value.priority,
    }
    await tasksStore.createTask(projectId.value, taskData)
    await projectsStore.fetchProgress(projectId.value)
    closeTaskModal()
    notificationStore.success("Task created successfully!")
  } catch (error) {
    console.error("Failed to create task:", error)
    const resp = (error as any)?.response
    const message = resp?.data?.message || (error as any).message || "Failed to create task"
    notificationStore.error(message)
  } finally {
    creating.value = false
  }
}

const deleteTaskConfirm = async (taskId: number) => {
  if (confirm("Delete this task?")) {
    try {
      await tasksStore.deleteTask(taskId)
      await projectsStore.fetchProgress(projectId.value)
      notificationStore.success("Task deleted successfully!")
    } catch (error) {
      console.error("Failed to delete task:", error)
      notificationStore.error((error as any)?.response?.data?.message || "Failed to delete task")
    }
  }
}

const closeTaskModal = () => {
  showTaskModal.value = false
  newTask.value = { title: "", description: "", dueDate: "", priority: "MEDIUM" }
}

const formatDate = (dateString: string) => {
  if (!dateString) return "No date"
  const date = new Date(dateString)
  return date.toLocaleDateString("en-US", { month: "short", day: "numeric" })
}

const getInitials = (name: string): string => {
  if (!name) return "U"
  const parts = name.trim().split(/\s+/)
  if (parts.length === 1) return parts[0].charAt(0).toUpperCase()
  return (parts[0].charAt(0) + parts[parts.length - 1].charAt(0)).toUpperCase()
}

const formatPermission = (permission: string): string => {
  const permissionMap: Record<string, string> = {
    READ_ONLY: "View",
    CAN_UPDATE_STATUS: "Update",
    CAN_EDIT: "Edit",
  }
  return permissionMap[permission] || permission
}

const priorityPill = (priority?: string) => {
  switch (priority) {
    case "HIGH":
      return "bg-red-500/15 text-red-200 border border-red-400/40"
    case "LOW":
      return "bg-emerald-500/10 text-emerald-200 border border-emerald-400/40"
    default:
      return "bg-amber-500/10 text-amber-200 border border-amber-400/40"
  }
}
</script>
