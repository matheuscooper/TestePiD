<template>
  <div class="flex flex-col items-center justify-center min-h-[60vh] px-6 text-center">
    <div class="w-16 h-16 rounded-2xl flex items-center justify-center mb-6" style="background: oklch(0.45 0.2 260 / 0.1)">
      <Factory :size="32" style="color: oklch(0.45 0.2 260)" />
    </div>
    <h1 class="text-5xl font-bold text-foreground mb-2">404</h1>
    <h2 class="text-xl font-semibold text-foreground mb-2">Page Not Found</h2>
    <p class="text-sm text-muted-foreground max-w-xs mb-8">
      The page you're looking for doesn't exist or has been moved.
    </p>
    <RouterLink to="/">
      <button class="inline-flex items-center gap-2 px-4 py-2 rounded-lg bg-primary text-primary-foreground font-medium hover:opacity-90 transition-opacity">
        <ArrowLeft :size="16" />
        Back to Dashboard
      </button>
    </RouterLink>
  </div>
</template>

<script setup lang="ts">
import { Factory, ArrowLeft } from 'lucide-vue-next'
import { RouterLink } from 'vue-router'
</script>
