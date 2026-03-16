<template>
  <div class="min-h-full">
    <!-- Hero Banner -->
    <div
      class="relative h-52 sm:h-64 overflow-hidden"
      :style="{
        backgroundImage: `url('https://d2xsxph8kpxj0f.cloudfront.net/310519663438504720/8697BvErGHHpC7ziH2bcPq/production-suggestion-bg-eeCyvoTmN6b3PydBDJfnyY.webp')`,
        backgroundSize: 'cover',
        backgroundPosition: 'center'
      }"
    >
      <div class="absolute inset-0 bg-gradient-to-r from-blue-900/80 via-blue-800/60 to-transparent" />
      <div class="relative z-10 flex flex-col justify-center h-full px-6 lg:px-8">
        <div class="flex items-center gap-2 mb-2">
          <Lightbulb :size="20" class="text-amber-400" />
          <p class="text-xs font-semibold uppercase tracking-widest text-amber-400">AI-Powered Optimization</p>
        </div>
        <h1 class="text-2xl sm:text-3xl font-bold text-white leading-tight mb-2">Production Suggestion</h1>
        <p class="text-sm text-white/80 max-w-md">
          Maximize your revenue by producing the right products at the right quantities based on available stock.
        </p>
      </div>

      <!-- Refresh button in hero -->
      <div class="absolute bottom-4 right-6">
        <button
          @click="refreshSuggestion"
          :disabled="refreshing || loading"
          class="inline-flex items-center gap-2 px-3 py-1.5 text-xs font-medium rounded-md bg-white/10 border border-white/30 text-white hover:bg-white/20 backdrop-blur-sm transition-colors disabled:opacity-50"
        >
          <RefreshCw :size="14" :class="{ 'animate-spin': refreshing }" />
          Refresh Suggestion
        </button>
      </div>
    </div>

    <div class="p-6 lg:p-8 space-y-6">
      <!-- Algorithm explanation -->
      <div class="flex items-start gap-3 p-4 rounded-lg bg-blue-50 border border-blue-100">
        <Info :size="16" class="text-blue-500 flex-shrink-0 mt-0.5" />
        <p class="text-sm text-blue-800">
          <strong>How it works:</strong> The system prioritizes products with higher sale value and respects available stock constraints. Products are ranked by total potential revenue to maximize your production output.
        </p>
      </div>

      <div v-if="loading" class="flex items-center justify-center py-20">
        <div class="text-center">
          <Loader2 :size="24" class="animate-spin text-muted-foreground mx-auto mb-2" />
          <p class="text-sm text-muted-foreground">Calculating production suggestion...</p>
        </div>
      </div>

      <div v-else-if="items.length === 0" class="flex flex-col items-center justify-center py-16 px-6 text-center bg-card rounded-lg border border-border">
        <AlertCircle :size="28" class="text-muted-foreground mb-3" />
        <h3 class="text-base font-semibold text-foreground mb-2">No production suggestion available</h3>
        <p class="text-sm text-muted-foreground max-w-sm mb-6">
          To generate a suggestion, make sure you have products with prices, raw materials with stock, and associations between them.
        </p>
        <button
          @click="refreshSuggestion"
          :disabled="refreshing"
          class="inline-flex items-center gap-2 px-3 py-1.5 text-xs font-medium rounded-md border border-border hover:bg-muted transition-colors disabled:opacity-50"
        >
          <RefreshCw :size="14" :class="{ 'animate-spin': refreshing }" />
          Try Again
        </button>
      </div>

      <template v-else>
        <!-- Summary Stats -->
        <div class="grid grid-cols-1 sm:grid-cols-3 gap-4">
          <div class="bg-card rounded-lg border border-border shadow-sm p-5 relative overflow-hidden">
            <div class="absolute left-0 top-0 bottom-0 w-1 rounded-l-lg" style="background: oklch(0.45 0.2 260)" />
            <div class="flex items-start justify-between pl-2">
              <div>
                <p class="text-xs font-semibold uppercase tracking-wider text-muted-foreground mb-1">Products to Produce</p>
                <p class="text-2xl font-bold text-foreground">{{ items.length }}</p>
              </div>
              <div class="w-10 h-10 rounded-lg flex items-center justify-center" style="background: oklch(0.45 0.2 260 / 0.12)">
                <Package :size="20" style="color: oklch(0.45 0.2 260)" />
              </div>
            </div>
          </div>

          <div class="bg-card rounded-lg border border-border shadow-sm p-5 relative overflow-hidden">
            <div class="absolute left-0 top-0 bottom-0 w-1 rounded-l-lg" style="background: oklch(0.55 0.18 160)" />
            <div class="flex items-start justify-between pl-2">
              <div>
                <p class="text-xs font-semibold uppercase tracking-wider text-muted-foreground mb-1">Total Units</p>
                <p class="text-2xl font-bold text-foreground">{{ totalUnits.toLocaleString() }}</p>
              </div>
              <div class="w-10 h-10 rounded-lg flex items-center justify-center" style="background: oklch(0.55 0.18 160 / 0.12)">
                <TrendingUp :size="20" style="color: oklch(0.55 0.18 160)" />
              </div>
            </div>
          </div>

          <div class="rounded-lg border shadow-sm p-5 relative overflow-hidden" style="background: oklch(0.45 0.18 160); border-color: oklch(0.4 0.18 160)">
            <div class="absolute left-0 top-0 bottom-0 w-1 rounded-l-lg bg-white/30" />
            <div class="flex items-start justify-between pl-2">
              <div>
                <p class="text-xs font-semibold uppercase tracking-wider text-white/70 mb-1">Total Production Value</p>
                <p class="text-2xl font-bold text-white">{{ formatCurrency(totalValue) }}</p>
              </div>
              <div class="w-10 h-10 rounded-lg flex items-center justify-center bg-white/20">
                <DollarSign :size="20" class="text-white" />
              </div>
            </div>
          </div>
        </div>

        <!-- Top Cards -->
        <div>
          <h2 class="text-sm font-semibold uppercase tracking-wider text-muted-foreground mb-3">Top Suggestions</h2>
          <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4">
            <div
              v-for="(item, idx) in items.slice(0, 6)"
              :key="item.productId"
              class="bg-card rounded-lg border border-border shadow-sm p-5 relative overflow-hidden hover:shadow-md transition-shadow duration-200"
            >
              <!-- Rank accent -->
              <div
                class="absolute top-0 right-0 w-12 h-12 flex items-center justify-center text-white font-bold text-sm rounded-bl-xl"
                :style="{ background: getRankColor(idx) }"
              >
                #{{ idx + 1 }}
              </div>

              <!-- Left border -->
              <div
                class="absolute left-0 top-0 bottom-0 w-1 rounded-l-lg"
                :style="{ background: getRankColor(idx) }"
              />

              <div class="pl-2 pr-10">
                <p class="text-xs font-semibold uppercase tracking-wider mb-1" :style="{ color: getRankColor(idx) }">
                  {{ item.productCode }}
                </p>
                <h3 class="text-base font-bold text-foreground leading-tight mb-3">{{ item.productName }}</h3>

                <div class="grid grid-cols-3 gap-3">
                  <div>
                    <p class="text-xs text-muted-foreground mb-0.5">Unit Price</p>
                    <p class="text-sm font-semibold text-foreground">${{ item.unitPrice.toFixed(2) }}</p>
                  </div>
                  <div>
                    <p class="text-xs text-muted-foreground mb-0.5">Qty to Produce</p>
                    <p class="text-sm font-bold text-foreground">{{ item.quantityToProduce.toLocaleString() }}</p>
                  </div>
                  <div>
                    <p class="text-xs text-muted-foreground mb-0.5">Total Value</p>
                    <p class="text-sm font-bold" style="color: oklch(0.45 0.18 160)">
                      {{ formatCurrency(item.totalValue) }}
                    </p>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- Full Table -->
        <div class="bg-card rounded-lg border border-border shadow-sm overflow-hidden">
          <div class="px-5 py-4 border-b border-border">
            <h2 class="text-sm font-semibold text-foreground">Complete Production Plan</h2>
            <p class="text-xs text-muted-foreground">All {{ items.length }} products ordered by total value</p>
          </div>
          <div class="overflow-x-auto">
            <table class="w-full text-sm">
              <thead>
                <tr class="border-b border-border bg-muted/40">
                  <th class="text-left px-4 py-3 text-xs font-semibold uppercase tracking-wider text-muted-foreground w-10">#</th>
                  <th class="text-left px-4 py-3 text-xs font-semibold uppercase tracking-wider text-muted-foreground">Code</th>
                  <th class="text-left px-4 py-3 text-xs font-semibold uppercase tracking-wider text-muted-foreground">Product</th>
                  <th class="text-right px-4 py-3 text-xs font-semibold uppercase tracking-wider text-muted-foreground">Unit Price</th>
                  <th class="text-right px-4 py-3 text-xs font-semibold uppercase tracking-wider text-muted-foreground">Qty to Produce</th>
                  <th class="text-right px-4 py-3 text-xs font-semibold uppercase tracking-wider text-muted-foreground">Total Value</th>
                </tr>
              </thead>
              <tbody class="divide-y divide-border">
                <tr v-for="(item, idx) in items" :key="item.productId" class="hover:bg-muted/30 transition-colors">
                  <td class="px-4 py-3">
                    <span
                      class="w-6 h-6 rounded-full flex items-center justify-center text-xs font-bold text-white"
                      :style="{ background: getRankColor(idx), display: 'inline-flex' }"
                    >
                      {{ idx + 1 }}
                    </span>
                  </td>
                  <td class="px-4 py-3">
                    <span class="font-mono text-xs bg-muted px-2 py-0.5 rounded text-muted-foreground">
                      {{ item.productCode }}
                    </span>
                  </td>
                  <td class="px-4 py-3 font-medium text-foreground">{{ item.productName }}</td>
                  <td class="px-4 py-3 text-right text-muted-foreground">${{ item.unitPrice.toFixed(2) }}</td>
                  <td class="px-4 py-3 text-right font-semibold text-foreground">{{ item.quantityToProduce.toLocaleString() }}</td>
                  <td class="px-4 py-3 text-right">
                    <span class="font-bold" style="color: oklch(0.45 0.18 160)">
                      {{ formatCurrency(item.totalValue) }}
                    </span>
                  </td>
                </tr>
              </tbody>
              <!-- Total row -->
              <tfoot>
                <tr class="border-t-2 border-border bg-muted/30">
                  <td colSpan="5" class="px-4 py-3 text-sm font-bold text-foreground text-right">Total Production Value</td>
                  <td class="px-4 py-3 text-right">
                    <span class="text-base font-bold" style="color: oklch(0.45 0.18 160)">
                      {{ formatCurrency(totalValue) }}
                    </span>
                  </td>
                </tr>
              </tfoot>
            </table>
          </div>
        </div>
      </template>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { RefreshCw, Lightbulb, Info, Loader2, AlertCircle, Package, TrendingUp, DollarSign } from 'lucide-vue-next'
import { productionService, type ProductionSuggestionItem } from '../services/api'

const loading = ref(true)
const refreshing = ref(false)
const items = ref<ProductionSuggestionItem[]>([])
const totalValue = ref(0)

const totalUnits = computed(() => items.value.reduce((s, i) => s + i.quantityToProduce, 0))

const formatCurrency = (value: number) => {
  return new Intl.NumberFormat('en-US', { style: 'currency', currency: 'USD', maximumFractionDigits: 0 }).format(value).replace('$', '')
}

const getRankColor = (idx: number) => {
  if (idx === 0) return 'oklch(0.75 0.18 80)' // gold
  if (idx === 1) return 'oklch(0.65 0.06 240)' // silver
  if (idx === 2) return 'oklch(0.6 0.12 40)' // bronze
  return 'oklch(0.45 0.2 260)' // blue
}

const fetchSuggestion = async (isRefresh = false) => {
  if (isRefresh) {
    refreshing.value = true
  } else {
    loading.value = true
  }
  try {
    const result = await productionService.getSuggestion()
    items.value = result.items
    totalValue.value = result.totalValue
  } catch (err) {
    console.error('Failed to load production suggestion:', err)
  } finally {
    loading.value = false
    refreshing.value = false
  }
}

const refreshSuggestion = () => fetchSuggestion(true)

onMounted(() => fetchSuggestion())
</script>
