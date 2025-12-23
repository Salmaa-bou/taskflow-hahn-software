<template>
  <div class="min-h-[80vh] grid lg:grid-cols-[0.9fr,1.1fr] gap-8 items-center">
    <div class="card order-last lg:order-first lg:mt-6">
      <div class="flex items-center justify-between mb-6">
        <div>
          <p class="text-xs uppercase tracking-[0.2em] text-gray-400">Create</p>
          <h2 class="text-3xl font-semibold text-white">Join TaskFlow</h2>
        </div>
        <img src="/logo2.png" class="w-12 h-12 opacity-90 rounded-full object-cover" alt="TaskFlow" />
      </div>

      <form @submit.prevent="handleRegister" class="space-y-4">
        <div class="grid sm:grid-cols-2 gap-3">
          <div class="sm:col-span-2">
            <label class="block text-sm text-gray-300 mb-1">Full Name</label>
            <input
              v-model="name"
              type="text"
              required
              class="input-field"
              placeholder="John Doe"
            />
          </div>
          
          <div class="sm:col-span-2">
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
            <label class="block text-sm text-gray-300 mb-1">Role</label>
            <select
              v-model="role"
              required
              class="input-field"
            >
              <option value="" disabled>Select a role</option>
              <option value="USER">Team Member</option>
              <option value="EMPLOYER">Project Owner</option>
            </select>
          </div>

          <div>
            <label class="block text-sm text-gray-300 mb-1">Password</label>
            <div class="relative">
              <input
                v-model="password"
                :type="showPassword ? 'text' : 'password'"
                required
                minlength="6"
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
        </div>

        <div>
          <label class="block text-sm text-gray-300 mb-1">Skills (comma-separated)</label>
          <textarea
            v-model="skills"
            class="input-field resize-none"
            placeholder="e.g. JavaScript, Vue.js, REST APIs"
            rows="3"
          ></textarea>
        </div>
        
        <div v-if="error" class="text-sm text-red-200 bg-red-500/10 border border-red-500/30 p-3 rounded-lg">
          {{ error }}
        </div>
        
        <button type="submit" :disabled="loading" class="btn-primary w-full py-3 text-base">
          {{ loading ? 'Creating account...' : 'Create account' }}
        </button>
      </form>
      
      <div class="mt-6 text-center text-sm text-gray-400">
        Already have an account?
        <router-link to="/login" class="text-primary-200 hover:text-primary-100 font-medium">
          Sign in
        </router-link>
      </div>
    </div>

    <div class="hidden lg:flex flex-col gap-4">
      <div class="inline-flex items-center gap-3 px-3 py-1 rounded-full bg-white/5 border border-white/10 w-max">
        <span class="w-2 h-2 rounded-full bg-primary-400 animate-pulse"></span>
        <span class="text-xs text-gray-300 uppercase tracking-[0.25em]">Onboard</span>
      </div>
      <h1 class="text-5xl font-bold text-white leading-tight">Build together in a sleek, X-inspired canvas.</h1>
      <p class="text-gray-400 text-lg max-w-2xl">
        Kanban, focus mode, and ambient dark styling make your team’s workload feel organized and modern.
      </p>
      <div class="grid grid-cols-2 gap-3">
        <div class="glass-panel p-4 border border-white/10">
          <p class="text-sm text-gray-300">Drag & drop tasks</p>
          <p class="text-xs text-gray-500">Move work across swimlanes with real-time status updates.</p>
        </div>
        <div class="glass-panel p-4 border border-white/10">
          <p class="text-sm text-gray-300">Assign smarter</p>
          <p class="text-xs text-gray-500">Pick teammates from your roster or invite via email.</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/auth'

const router = useRouter()
const authStore = useAuthStore()

const name = ref('')
const email = ref('')
const password = ref('')
const showPassword = ref(false)
const skills = ref('')
const role = ref<'USER' | 'EMPLOYER' | ''>('')
const loading = ref(false)
const error = ref('')

const handleRegister = async () => {
  loading.value = true
  error.value = ''
  
  try {
    await authStore.register({ 
      fullName: name.value,
      email: email.value, 
      password: password.value,
      skills: skills.value,
      role: role.value as 'USER' | 'EMPLOYER' | 'ADMIN'
    })
    // Redirect to login after successful registration
    router.push('/login')
  } catch (err: any) {
    error.value = err.response?.data?.message || 'Failed to create account'
  } finally {
    loading.value = false
  }
}
</script>
