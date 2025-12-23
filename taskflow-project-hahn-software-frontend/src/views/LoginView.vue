<template>
  <div class="min-h-[80vh] grid lg:grid-cols-[1.1fr,0.9fr] gap-8 items-center">
    <div class="hidden lg:flex flex-col justify-center gap-6">
      <div class="inline-flex items-center gap-3 px-3 py-1 rounded-full bg-white/5 border border-white/10 w-max">
        <span class="w-2 h-2 rounded-full bg-emerald-400 animate-pulse"></span>
        <span class="text-xs text-gray-300 uppercase tracking-[0.25em]">Flow</span>
      </div>
      <h1 class="text-5xl font-bold text-white leading-tight">Ship fast with a calm, focused workspace.</h1>
      <p class="text-gray-400 text-lg max-w-2xl">
        A Twitter-inspired, minimal cockpit for tasks, teams, and delivery. Stay in flow, keep context, and move work forward.
      </p>
      <div class="flex items-center gap-3 text-sm text-gray-400">
        <span class="pill bg-primary-500/10 text-primary-100">Secure SSO-ready</span>
        <span class="pill bg-white/5 text-gray-200">Kanban first</span>
        <span class="pill bg-white/5 text-gray-200">Real-time updates</span>
      </div>
    </div>

    <div class="card lg:mt-6">
      <div class="flex items-center justify-between mb-6">
        <div>
          <p class="text-xs uppercase tracking-[0.2em] text-gray-400">Sign in</p>
          <h2 class="text-3xl font-semibold text-white">Welcome back</h2>
        </div>
        <img src="/logo2.png" class="w-12 h-12 opacity-90 rounded-full object-cover" alt="TaskFlow" />
      </div>

      <form @submit.prevent="handleLogin" class="space-y-4">
        <div>
          <label class="block text-sm text-gray-300 mb-1">Email</label>
          <input
            v-model="email"
            type="email"
            required
            class="input-field"
            placeholder="you@example.com"
          />
        </div>
        
        <div>
          <label class="block text-sm text-gray-300 mb-1">Password</label>
          <div class="relative">
            <input
              v-model="password"
              :type="showPassword ? 'text' : 'password'"
              required
              class="input-field pr-10"
              placeholder="••••••••"
            />
            <button
              type="button"
              @click="showPassword = !showPassword"
              class="absolute right-3 top-1/2 -translate-y-1/2 text-gray-400 hover:text-gray-300 transition"
            >
              <svg v-if="!showPassword" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-5 h-5">
                <path stroke-linecap="round" stroke-linejoin="round" d="M2.036 12.322a1.012 1.012 0 010-.639C3.423 7.51 7.36 4.5 12 4.5c4.638 0 8.573 3.007 9.963 7.178.07.207.07.431 0 .639C20.577 16.49 16.64 19.5 12 19.5c-4.638 0-8.573-3.007-9.963-7.178z" />
                <path stroke-linecap="round" stroke-linejoin="round" d="M15 12a3 3 0 11-6 0 3 3 0 016 0z" />
              </svg>
              <svg v-else xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-5 h-5">
                <path stroke-linecap="round" stroke-linejoin="round" d="M3.98 8.223A10.477 10.477 0 001.934 12C3.226 16.338 7.244 19.5 12 19.5c.993 0 1.953-.138 2.863-.395M6.228 6.228A10.45 10.45 0 0112 4.5c4.756 0 8.773 3.162 10.065 7.498a10.523 10.523 0 01-4.293 5.774M6.228 6.228L3 3m3.228 3.228l3.65 3.65m7.894 7.894L21 21m-3.228-3.228l-3.65-3.65m0 0a3 3 0 10-4.243-4.243m4.242 4.242L9.88 9.88" />
              </svg>
            </button>
          </div>
        </div>
        
        <div v-if="error" class="text-sm text-red-200 bg-red-500/10 border border-red-500/30 p-3 rounded-lg">
          {{ error }}
        </div>
        
        <button type="submit" :disabled="loading" class="btn-primary w-full py-3 text-base">
          {{ loading ? 'Signing in...' : 'Enter workspace' }}
        </button>
      </form>
      
      <div class="mt-6 text-center text-sm text-gray-400">
        Don't have an account?
        <router-link to="/register" class="text-primary-200 hover:text-primary-100 font-medium">
          Create one
        </router-link>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { useAuthStore } from '@/stores/auth'
import { useNotificationStore } from '@/stores/notifications'

const router = useRouter()
const route = useRoute()
const authStore = useAuthStore()
const notificationStore = useNotificationStore()

const email = ref('')
const password = ref('')
const showPassword = ref(false)
const loading = ref(false)
const error = ref('')

onMounted(() => {
  if (route.query.sessionExpired === 'true') {
    notificationStore.warning('Your session has expired. Please login again.')
  } else if (route.query.timeout === 'true') {
    notificationStore.info('You have been logged out due to inactivity.')
  }
})

const handleLogin = async () => {
  loading.value = true
  error.value = ''

  try {
    await authStore.login({ email: email.value, password: password.value })
    notificationStore.success('Welcome back!')
    router.push('/')
  } catch (err: any) {
    error.value = err.response?.data?.message || 'Invalid email or password'
    notificationStore.error(error.value)
  } finally {
    loading.value = false
  }
}
</script>
