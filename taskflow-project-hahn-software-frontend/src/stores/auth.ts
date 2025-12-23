import { defineStore } from "pinia"
import { ref, computed } from "vue"
import { authApi } from "@/api/auth"
import type { User, LoginRequest, RegisterRequest } from "@/types"
import router from "@/router"

const SESSION_TIMEOUT = 30 * 60 * 1000 // 30 minutes in milliseconds

export const useAuthStore = defineStore("auth", () => {
  const token = ref<string | null>(localStorage.getItem("token"))
  const user = ref<User | null>(localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")!) : null)
  const sessionTimeoutId = ref<number | null>(null)
  const lastActivityTime = ref<number>(Date.now())

  const isAuthenticated = computed(() => !!token.value)

  const clearSessionTimeout = () => {
    if (sessionTimeoutId.value) {
      clearTimeout(sessionTimeoutId.value)
      sessionTimeoutId.value = null
    }
  }

  const startSessionTimeout = () => {
    clearSessionTimeout()

    sessionTimeoutId.value = window.setTimeout(() => {
      logout()
      router.push("/login?timeout=true")
    }, SESSION_TIMEOUT)
  }

  const resetSessionTimeout = () => {
    if (isAuthenticated.value) {
      lastActivityTime.value = Date.now()
      startSessionTimeout()
    }
  }

  const login = async (credentials: LoginRequest) => {
    const response = await authApi.login(credentials)
    token.value = response.data.token
    user.value = response.data.user
    localStorage.setItem("token", response.data.token)
    localStorage.setItem("user", JSON.stringify(response.data.user))
    localStorage.setItem("lastActivity", Date.now().toString())
    startSessionTimeout()
  }

  const register = async (data: RegisterRequest) => {
    // Don't automatically authenticate user on registration.
    // Many backends return a token here, but we want users to login explicitly.
    const response = await authApi.register(data)
    return response
  }

  const logout = () => {
    clearSessionTimeout()
    token.value = null
    user.value = null
    localStorage.removeItem("token")
    localStorage.removeItem("user")
    localStorage.removeItem("lastActivity")
  }

  const checkSession = () => {
    const lastActivity = localStorage.getItem("lastActivity")
    if (lastActivity && isAuthenticated.value) {
      const timeSinceLastActivity = Date.now() - parseInt(lastActivity)
      if (timeSinceLastActivity >= SESSION_TIMEOUT) {
        logout()
        router.push("/login?timeout=true")
      } else {
        startSessionTimeout()
      }
    }
  }

  const updateLocalProfile = (payload: Partial<User>) => {
    if (!user.value) return
    user.value = { ...user.value, ...payload }
    localStorage.setItem("user", JSON.stringify(user.value))
  }

  return {
    token,
    user,
    isAuthenticated,
    login,
    register,
    logout,
    resetSessionTimeout,
    checkSession,
    updateLocalProfile,
  }
})
