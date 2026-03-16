<template>
  <div class="flex flex-col h-full">
    <!-- Logo -->
    <div class="px-5 py-4 border-b border-sidebar-border flex items-center gap-2">
      <div class="w-8 h-8 rounded-lg bg-sidebar-primary flex items-center justify-center flex-shrink-0">
        <Factory :size="18" class="text-sidebar-primary-foreground" />
      </div>
      <div>
        <p class="text-xs font-bold text-sidebar-foreground">Industrial PM</p>
        <p class="text-xs text-sidebar-foreground/50">Production Manager</p>
      </div>
    </div>

    <!-- Navigation -->
    <nav class="flex-1 px-3 py-4 space-y-1 overflow-y-auto">
      <p class="text-xs font-semibold uppercase tracking-wider text-sidebar-foreground/40 px-2 mb-3">
        Navigation
      </p>
      <RouterLink
        v-for="item in navItems"
        :key="item.path"
        :to="item.path"
        @click="$emit('close')"
        :class="[
          'flex items-center gap-3 px-3 py-2.5 rounded-md text-sm font-medium transition-all duration-150 group',
          isActive(item.path)
            ? 'bg-sidebar-primary text-sidebar-primary-foreground shadow-sm'
            : 'text-sidebar-foreground/70 hover:bg-sidebar-accent hover:text-sidebar-accent-foreground'
        ]"
      >
        <component
          :is="item.icon"
          :size="18"
          :class="[
            'flex-shrink-0',
            isActive(item.path)
              ? 'text-sidebar-primary-foreground'
              : 'text-sidebar-foreground/50 group-hover:text-sidebar-accent-foreground'
          ]"
        />
        <span class="flex-1">{{ item.label }}</span>
        <ChevronRight
          v-if="isActive(item.path)"
          :size="14"
          class="opacity-70"
        />
      </RouterLink>
    </nav>

    <!-- Footer -->
    <div class="px-5 py-4 border-t border-sidebar-border">
      <p class="text-xs text-sidebar-foreground/50">Industrial PM v1.0</p>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { useRoute } from 'vue-router'
import {
  LayoutDashboard,
  Package,
  FlaskConical,
  Lightbulb,
  ChevronRight,
  Factory
} from 'lucide-vue-next'

const route = useRoute()

const navItems = [
  { label: 'Dashboard', path: '/', icon: LayoutDashboard },
  { label: 'Products', path: '/products', icon: Package },
  { label: 'Raw Materials', path: '/raw-materials', icon: FlaskConical },
  { label: 'Production Suggestion', path: '/production/suggestion', icon: Lightbulb }
]

defineEmits<{
  close: []
}>()

const isActive = (path: string) => {
  if (path === '/') return route.path === '/'
  return route.path.startsWith(path)
}
</script>
