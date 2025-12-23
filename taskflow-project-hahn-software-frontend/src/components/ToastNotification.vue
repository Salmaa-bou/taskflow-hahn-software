<template>
  <Teleport to="body">
    <div class="fixed top-4 right-4 z-50 flex flex-col gap-2 max-w-md">
      <TransitionGroup name="toast">
        <div
          v-for="notification in notificationStore.notifications"
          :key="notification.id"
          :class="[
            'flex items-start gap-3 p-4 rounded-lg shadow-lg backdrop-blur-sm border',
            notificationClasses[notification.type]
          ]"
        >
          <component :is="notificationIcons[notification.type]" class="w-5 h-5 flex-shrink-0 mt-0.5" />
          <div class="flex-1 min-w-0">
            <p class="text-sm font-medium">{{ notification.message }}</p>
          </div>
          <button
            @click="notificationStore.removeNotification(notification.id)"
            class="flex-shrink-0 text-current opacity-60 hover:opacity-100 transition-opacity"
          >
            <X class="w-4 h-4" />
          </button>
        </div>
      </TransitionGroup>
    </div>
  </Teleport>
</template>

<script setup lang="ts">
import { useNotificationStore } from '@/stores/notifications'
import { CheckCircle, XCircle, AlertTriangle, Info, X } from 'lucide-vue-next'

const notificationStore = useNotificationStore()

const notificationClasses = {
  success: 'bg-green-50/95 border-green-200 text-green-800',
  error: 'bg-red-50/95 border-red-200 text-red-800',
  warning: 'bg-yellow-50/95 border-yellow-200 text-yellow-800',
  info: 'bg-blue-50/95 border-blue-200 text-blue-800',
}

const notificationIcons = {
  success: CheckCircle,
  error: XCircle,
  warning: AlertTriangle,
  info: Info,
}
</script>

<style scoped>
.toast-enter-active,
.toast-leave-active {
  transition: all 0.3s ease;
}

.toast-enter-from {
  opacity: 0;
  transform: translateX(100%);
}

.toast-leave-to {
  opacity: 0;
  transform: translateX(100%);
}

.toast-move {
  transition: transform 0.3s ease;
}
</style>
