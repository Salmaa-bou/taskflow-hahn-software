<template>
  <div class="space-y-6">
    <div class="flex items-center gap-4">
      <div class="w-14 h-14 rounded-2xl bg-primary-500/20 border border-primary-500/30 flex items-center justify-center text-lg font-bold text-primary-100">
        {{ initials }}
      </div>
      <div>
        <p class="text-sm text-gray-400 uppercase tracking-[0.2em]">Profile</p>
        <h1 class="text-3xl font-semibold text-white">Your space</h1>
        <p class="text-sm text-gray-400">Manage how you appear across your projects.</p>
      </div>
    </div>

    <div class="grid md:grid-cols-2 gap-6">
      <div class="card">
        <div class="flex items-center justify-between mb-4">
          <h2 class="text-lg font-semibold text-white">Identity</h2>
          <span class="pill bg-primary-500/10 text-primary-100">Live session</span>
        </div>

        <form class="space-y-4" @submit.prevent="saveProfile">
          <div>
            <label class="block text-sm text-gray-300 mb-1">Full name</label>
            <input v-model="name" type="text" class="input-field" />
          </div>

          <div>
            <label class="block text-sm text-gray-300 mb-1">Email</label>
            <input v-model="email" type="email" class="input-field" />
            <p class="text-xs text-gray-500 mt-1">Email changes are local only for now.</p>
          </div>

          <div class="flex items-center justify-between">
            <div class="text-xs text-gray-400">
              Updates are stored for this session. Backend persistence coming next.
            </div>
            <button type="submit" class="btn-primary px-4">Save</button>
          </div>
        </form>
      </div>

      <div class="card space-y-4">
        <div class="flex items-center gap-3">
          <div class="w-10 h-10 rounded-xl bg-white/5 flex items-center justify-center">
            <svg class="w-5 h-5 text-primary-200" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7" />
            </svg>
          </div>
          <div>
            <h3 class="text-white font-semibold">Session security</h3>
            <p class="text-sm text-gray-400">30-minute inactivity logout keeps your workspace safe.</p>
          </div>
        </div>
        <div class="bg-white/5 border border-white/10 rounded-2xl p-4">
          <div class="flex items-center justify-between">
            <div>
              <p class="text-gray-300 text-sm">Need a reset?</p>
              <p class="text-xs text-gray-500">Log out everywhere and sign back in.</p>
            </div>
            <button class="btn-secondary text-sm" @click="logoutAll">Log out</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed, ref } from "vue"
import { useAuthStore } from "@/stores/auth"
import { useRouter } from "vue-router"

const authStore = useAuthStore()
const router = useRouter()

const name = ref(authStore.user?.name || "")
const email = ref(authStore.user?.email || "")

const initials = computed(() => {
  const base = name.value || authStore.user?.name || "TF"
  const parts = base.split(" ").filter(Boolean)
  return parts.slice(0, 2).map(p => p[0]?.toUpperCase()).join("") || "TF"
})

const saveProfile = () => {
  authStore.updateLocalProfile({ name: name.value, email: email.value })
}

const logoutAll = () => {
  authStore.logout()
  router.push("/login")
}
</script>
