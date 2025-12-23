export interface User {
  id: number
  email: string
  name: string
}

export interface AuthResponse {
  token: string
  user: User
}

export interface LoginRequest {
  email: string
  password: string
}

export interface RegisterRequest {
  email: string
  password: string
  fullName: string
  skills?: string
  role: "USER" | "EMPLOYER" | "ADMIN"
}

export interface Project {
  id: number
  title: string
  description?: string
  createdAt: string
  updatedAt: string
  ownerId: number
}

export interface ProjectProgress {
  totalTasks: number
  completedTasks: number
  progressPercentage: number
}

export interface Task {
  id: number
  title: string
  description?: string
  dueDate?: string
  priority?: string 
  status?: "TODO" | "IN_PROGRESS" | "IN_REVIEW" | "DONE"
  completed: boolean
  projectId: number
  createdAt: string
  updatedAt: string
  createdByUserId: number
  assignments?: TaskAssignment[]
}

export interface TaskAssignment {
  id: number
  userId: number
  taskId: number
  permission: "READ_ONLY" | "CAN_UPDATE_STATUS" | "CAN_EDIT"
  assignedAt: string
  user?: User
}

export interface ProjectMember {
  id: number
  userId: number
  projectId: number
  role: "OWNER" | "ADMIN" | "MEMBER"
  joinedAt: string
  user?: User
}

export interface CreateProjectRequest {
  title: string
  description?: string
}

export interface CreateTaskRequest {
  title: string
  description?: string
  dueDate?: string
  priority?: string
}

export interface UpdateTaskRequest {
  title?: string
  description?: string
  dueDate?: string
  priority?: string
  completed?: boolean
}

export interface AssignTaskRequest {
  userIds: number[]
  permission: "READ_ONLY" | "CAN_UPDATE_STATUS" | "CAN_EDIT"
}
