<template>
  <div class="min-h-[70vh] flex items-center justify-center">
    <div class="text-center space-y-6">
      <div class="relative">
        <div class="absolute inset-0 blur-3xl opacity-30">
          <div class="w-64 h-64 mx-auto rounded-full bg-gradient-to-br from-red-500 to-purple-600"></div>
        </div>
        <div class="relative">
          <h1 class="text-9xl font-bold text-white mb-2">404</h1>
          <div class="flex items-center justify-center gap-2 mb-4">
            <span class="w-3 h-3 rounded-full bg-red-400 animate-pulse"></span>
            <p class="text-xs uppercase tracking-[0.2em] text-gray-400">Error</p>
          </div>
        </div>
      </div>

      <div class="glass-panel max-w-md mx-auto p-6 border border-white/10">
        <h2 class="text-2xl font-semibold text-white mb-2">Page Not Found</h2>
        <p class="text-gray-400 mb-6">
          The page you're looking for doesn't exist or has been moved.
        </p>

        <div class="space-y-3">
          <button @click="goBack" class="btn-secondary w-full">
            <svg xmlns="http://www.w3.org/2000/svg" class="w-4 h-4" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10 19l-7-7m0 0l7-7m-7 7h18" />
            </svg>
            Go Back
          </button>

          <router-link to="/" class="btn-primary w-full block">
            <svg xmlns="http://www.w3.org/2000/svg" class="w-4 h-4" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 12l2-2m0 0l7-7 7 7M5 10v10a1 1 0 001 1h3m10-11l2 2m-2-2v10a1 1 0 01-1 1h-3m-6 0a1 1 0 001-1v-4a1 1 0 011-1h2a1 1 0 011 1v4a1 1 0 001 1m-6 0h6" />
            </svg>
            Go to Home
          </router-link>
        </div>
      </div>

      <div class="text-sm text-gray-500">
        <p>If you believe this is an error, please contact support.</p>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'

const router = useRouter()

const goBack = () => {
  if (window.history.length > 1) {
    router.back()
  } else {
    router.push('/')
  }
}
</script>
