<template>
  <div :class="[theme, 'min-h-screen text-gray-50']">
    <header v-if="authStore.isAuthenticated" class="sticky top-0 z-40 backdrop-blur-xl bg-white/5 border-b border-white/5">
      <div class="max-w-6xl mx-auto px-6 py-4 flex items-center justify-between">
        <router-link to="/" class="flex items-center gap-3 group">
          <div class="w-10 h-10 rounded-full bg-white/10 border border-primary-500/30 flex items-center justify-center overflow-hidden">
            <img src="/logo2.png" alt="Hahn Software Logo" class="w-full h-full object-cover opacity-90 group-hover:opacity-100" />
          </div>
          <div>
            <p class="text-xs uppercase tracking-[0.2em] text-gray-400">TaskFlow</p>
            <p class="text-lg font-semibold text-white">Workspace</p>
          </div>
        </router-link>

        <div class="flex items-center gap-4">
          <button
            class="btn-secondary text-xs px-3 py-1.5"
            @click="toggleTheme"
            :aria-label="'Toggle ' + (theme === 'theme-dark' ? 'light' : 'dark')"
          >
            <span v-if="theme === 'theme-dark'">☀️ Light</span>
            <span v-else>🌙 Dark</span>
          </button>
          <div class="hidden sm:flex items-center gap-2 px-3 py-1.5 rounded-full bg-white/5 border border-white/10">
            <span class="w-2 h-2 rounded-full bg-emerald-400 animate-pulse"></span>
            <span class="text-xs text-gray-300">Online</span>
          </div>
          <button
            v-if="authStore.user"
            @click="showProfile = !showProfile"
            class="flex items-center gap-3 px-3 py-2 rounded-full bg-white/5 border border-white/10 hover:bg-white/10 transition"
          >
            <div class="w-9 h-9 rounded-full bg-primary-500/30 border border-primary-500/40 flex items-center justify-center text-sm font-semibold">
              {{ initials }}
            </div>
            <div class="text-left hidden sm:block">
              <p class="text-sm font-semibold text-white leading-none">{{ authStore.user?.name }}</p>
              <p class="text-xs text-gray-400">{{ authStore.user?.email }}</p>
            </div>
            <svg class="w-4 h-4 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 9l-7 7-7-7" />
            </svg>
          </button>
        </div>
      </div>

      <div v-if="showProfile" class="max-w-6xl mx-auto px-6 pb-4">
        <div class="glass-panel p-4 flex items-center justify-between">
          <div>
            <p class="text-sm text-gray-400">Signed in as</p>
            <p class="text-lg font-semibold text-white">{{ authStore.user?.name }}</p>
            <p class="text-sm text-gray-400">{{ authStore.user?.email }}</p>
          </div>
          <div class="flex items-center gap-3">
            <router-link
              to="/profile"
              class="btn-secondary text-sm"
              @click="showProfile = false"
            >
              Profile
            </router-link>
            <button @click="handleLogout" class="btn-primary text-sm px-4">
              Logout
            </button>
          </div>
        </div>
      </div>
    </header>

    <main class="max-w-6xl mx-auto px-4 sm:px-6 py-10">
      <router-view />
    </main>

    <ToastNotification />
  </div>
</template>

<script setup lang="ts">
import { computed, onMounted, onUnmounted, ref } from "vue"
import { useAuthStore } from "@/stores/auth"
import { useRouter } from "vue-router"
import ToastNotification from "@/components/ToastNotification.vue"

const authStore = useAuthStore()
const router = useRouter()
const showProfile = ref(false)
const theme = ref<"theme-dark" | "theme-light">(
  (localStorage.getItem("theme") as "theme-dark" | "theme-light") || "theme-dark"
)

const applyTheme = () => {
  document.documentElement.classList.remove("theme-dark", "theme-light")
  document.documentElement.classList.add(theme.value)
}

const toggleTheme = () => {
  theme.value = theme.value === "theme-dark" ? "theme-light" : "theme-dark"
  localStorage.setItem("theme", theme.value)
  applyTheme()
}

const handleLogout = () => {
  authStore.logout()
  router.push("/login")
}

const initials = computed(() => {
  const name = authStore.user?.name || ""
  if (!name) return "TF"
  const parts = name.split(" ").filter(Boolean)
  return parts
    .slice(0, 2)
    .map((p) => p[0]?.toUpperCase())
    .join("")
})

const handleUserActivity = () => {
  authStore.resetSessionTimeout()
}

onMounted(() => {
  applyTheme()
  authStore.checkSession()

  const events = ["mousedown", "keydown", "scroll", "touchstart", "click"]
  events.forEach((event) => {
    window.addEventListener(event, handleUserActivity)
  })
})

onUnmounted(() => {
  const events = ["mousedown", "keydown", "scroll", "touchstart", "click"]
  events.forEach((event) => {
    window.removeEventListener(event, handleUserActivity)
  })
})
</script>
