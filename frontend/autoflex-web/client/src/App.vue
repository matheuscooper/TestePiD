<template>
  <div class="flex h-screen overflow-hidden bg-background">
    <!-- Sidebar -->
    <aside class="hidden lg:flex flex-col w-64 flex-shrink-0 h-full sidebar-bg">
      <Sidebar :current-route="currentRoute" @navigate="navigateTo" />
    </aside>

    <!-- Mobile Drawer -->
    <div
      v-if="mobileMenuOpen"
      class="fixed inset-0 z-40 bg-black/60 lg:hidden"
      @click="mobileMenuOpen = false"
    />
    <aside
      v-if="mobileMenuOpen"
      class="fixed inset-y-0 left-0 z-50 w-72 flex flex-col sidebar-bg transition-transform duration-300"
    >
      <Sidebar
        :current-route="currentRoute"
        @navigate="navigateTo"
        @close="mobileMenuOpen = false"
      />
    </aside>

    <!-- Main Content -->
    <div class="flex-1 flex flex-col min-w-0 overflow-hidden">
      <!-- Header -->
      <header class="flex items-center gap-4 px-4 lg:px-6 h-14 bg-card border-b border-border flex-shrink-0 shadow-sm">
        <button
          v-if="!isLargeScreen"
          @click="mobileMenuOpen = true"
          class="lg:hidden text-foreground/70 hover:text-foreground"
        >
          <Menu :size="20" />
        </button>

        <div class="flex items-center gap-2 text-sm">
          <span class="text-muted-foreground hidden sm:block">Industrial PM</span>
          <span class="text-muted-foreground hidden sm:block">/</span>
          <span class="font-semibold text-foreground">{{ pageName }}</span>
        </div>

        <div class="flex-1" />

        <div class="flex items-center gap-2 text-xs text-muted-foreground">
          <span class="w-2 h-2 rounded-full bg-emerald-500 animate-pulse" />
          <span class="hidden sm:block">System Online</span>
        </div>
      </header>

      <!-- Page Content -->
      <main class="flex-1 overflow-y-auto page-enter">
        <RouterView />
      </main>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted, onUnmounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { Menu } from 'lucide-vue-next'
import Sidebar from './components/Sidebar.vue'

const router = useRouter()
const route = useRoute()
const mobileMenuOpen = ref(false)
const isLargeScreen = ref(window.innerWidth >= 1024)

const pageName = computed(() => {
  const path = route.path
  if (path === '/') return 'Dashboard'
  if (path === '/products') return 'Products'
  if (path.startsWith('/products/')) return 'Product Details'
  if (path === '/raw-materials') return 'Raw Materials'
  if (path === '/production/suggestion') return 'Production Suggestion'
  return 'Page'
})

const currentRoute = computed(() => route.path)

const navigateTo = (path: string) => {
  router.push(path)
  mobileMenuOpen.value = false
}

const handleResize = () => {
  isLargeScreen.value = window.innerWidth >= 1024
}

onMounted(() => {
  window.addEventListener('resize', handleResize)
})

onUnmounted(() => {
  window.removeEventListener('resize', handleResize)
})
</script>

<style scoped>
.sidebar-bg {
  background: oklch(0.2 0.04 240);
}
</style>
