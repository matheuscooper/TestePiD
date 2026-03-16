<template>
  <div class="p-6 lg:p-8 space-y-6">
    <!-- Hero Banner -->
    <div
      class="relative h-40 lg:h-48 rounded-lg overflow-hidden bg-gradient-to-r from-blue-600 to-blue-800 shadow-lg"
      :style="{
        backgroundImage: `url('https://d2xsxph8kpxj0f.cloudfront.net/310519663438504720/8697BvErGHHpC7ziH2bcPq/hero-dashboard-eeCyvoTmN6b3PydBDJfnyY.webp')`,
        backgroundSize: 'cover',
        backgroundPosition: 'center',
      }"
    >
      <div
        class="absolute inset-0 bg-gradient-to-r from-blue-900/80 via-blue-800/60 to-transparent"
      />
      <div
        class="relative z-10 flex flex-col justify-center h-full px-6 lg:px-8"
      >
        <h1 class="text-2xl lg:text-3xl font-bold text-white mb-2">
          Production Overview
        </h1>
        <p class="text-sm text-white/80 max-w-md">
          Monitor products, materials, and optimize your production line
        </p>
      </div>
    </div>

    <!-- Stats Cards -->
    <div class="grid grid-cols-1 sm:grid-cols-3 gap-4">
      <div
        class="bg-card rounded-lg border border-border shadow-sm p-5 relative overflow-hidden"
      >
        <div
          class="absolute left-0 top-0 bottom-0 w-1 rounded-l-lg"
          style="background: oklch(0.45 0.2 260)"
        />
        <div class="flex items-start justify-between pl-2">
          <div>
            <p
              class="text-xs font-semibold uppercase tracking-wider text-muted-foreground mb-1"
            >
              Total Products
            </p>
            <p class="text-2xl font-bold text-foreground">
              {{ stats.totalProducts }}
            </p>
          </div>
          <div
            class="w-10 h-10 rounded-lg flex items-center justify-center"
            style="background: oklch(0.45 0.2 260 / 0.12)"
          >
            <Package :size="20" style="color: oklch(0.45 0.2 260)" />
          </div>
        </div>
      </div>

      <div
        class="bg-card rounded-lg border border-border shadow-sm p-5 relative overflow-hidden"
      >
        <div
          class="absolute left-0 top-0 bottom-0 w-1 rounded-l-lg"
          style="background: oklch(0.55 0.18 160)"
        />
        <div class="flex items-start justify-between pl-2">
          <div>
            <p
              class="text-xs font-semibold uppercase tracking-wider text-muted-foreground mb-1"
            >
              Raw Materials
            </p>
            <p class="text-2xl font-bold text-foreground">
              {{ stats.totalMaterials }}
            </p>
          </div>
          <div
            class="w-10 h-10 rounded-lg flex items-center justify-center"
            style="background: oklch(0.55 0.18 160 / 0.12)"
          >
            <FlaskConical :size="20" style="color: oklch(0.55 0.18 160)" />
          </div>
        </div>
      </div>

      <div
        class="rounded-lg border shadow-sm p-5 relative overflow-hidden"
        style="
          background: oklch(0.45 0.18 160);
          border-color: oklch(0.4 0.18 160);
        "
      >
        <div
          class="absolute left-0 top-0 bottom-0 w-1 rounded-l-lg bg-white/30"
        />
        <div class="flex items-start justify-between pl-2">
          <div>
            <p
              class="text-xs font-semibold uppercase tracking-wider text-white/70 mb-1"
            >
              Production Value
            </p>
            <p class="text-2xl font-bold text-white">
              {{ stats.productionValue }}
            </p>
          </div>
          <div
            class="w-10 h-10 rounded-lg flex items-center justify-center bg-white/20"
          >
            <TrendingUp :size="20" class="text-white" />
          </div>
        </div>
      </div>
    </div>

    <!-- Quick Actions -->
    <div className="bg-card rounded-lg border border-border shadow-sm p-6">
      <h2
        class="text-sm font-semibold uppercase tracking-wider text-muted-foreground mb-4"
      >
        Quick Actions
      </h2>
      <div class="grid grid-cols-1 sm:grid-cols-3 gap-3">
        <RouterLink
          to="/products"
          class="flex items-center gap-3 p-4 rounded-lg border border-border hover:border-primary/40 hover:bg-accent transition-all duration-150 group"
        >
          <div
            class="w-9 h-9 rounded-lg bg-blue-50 flex items-center justify-center group-hover:bg-blue-100 transition-colors"
          >
            <Plus :size="16" class="text-blue-600" />
          </div>
          <div>
            <p class="text-sm font-semibold text-foreground">Add Product</p>
            <p class="text-xs text-muted-foreground">Register a new product</p>
          </div>
          <ArrowRight
            :size="16"
            class="text-muted-foreground ml-auto opacity-0 group-hover:opacity-100 transition-opacity"
          />
        </RouterLink>

        <RouterLink
          to="/raw-materials"
          class="flex items-center gap-3 p-4 rounded-lg border border-border hover:border-emerald-400/40 hover:bg-accent transition-all duration-150 group"
        >
          <div
            class="w-9 h-9 rounded-lg bg-emerald-50 flex items-center justify-center group-hover:bg-emerald-100 transition-colors"
          >
            <Plus :size="16" class="text-emerald-600" />
          </div>
          <div>
            <p class="text-sm font-semibold text-foreground">
              Add Raw Material
            </p>
            <p class="text-xs text-muted-foreground">Register new material</p>
          </div>
          <ArrowRight
            :size="16"
            class="text-muted-foreground ml-auto opacity-0 group-hover:opacity-100 transition-opacity"
          />
        </RouterLink>

        <RouterLink
          to="/production/suggestion"
          class="flex items-center gap-3 p-4 rounded-lg border border-border hover:border-amber-400/40 hover:bg-accent transition-all duration-150 group"
        >
          <div
            class="w-9 h-9 rounded-lg bg-amber-50 flex items-center justify-center group-hover:bg-amber-100 transition-colors"
          >
            <Lightbulb :size="16" class="text-amber-600" />
          </div>
          <div>
            <p class="text-sm font-semibold text-foreground">View Suggestion</p>
            <p class="text-xs text-muted-foreground">Optimize production</p>
          </div>
          <ArrowRight
            :size="16"
            class="text-muted-foreground ml-auto opacity-0 group-hover:opacity-100 transition-opacity"
          />
        </RouterLink>
      </div>
    </div>

    <!-- Production Suggestion Preview -->
    <div
      class="bg-card rounded-lg border border-border shadow-sm overflow-hidden"
    >
      <div
        class="px-5 py-4 border-b border-border flex items-center justify-between"
      >
        <div>
          <h2 class="text-sm font-semibold text-foreground">
            Production Suggestion
          </h2>
          <p class="text-xs text-muted-foreground">
            Top products to manufacture
          </p>
        </div>
        <RouterLink to="/production/suggestion" class="inline-flex">
          <button
            class="inline-flex items-center gap-1.5 px-3 py-1.5 text-xs font-medium rounded-md border border-border hover:bg-muted transition-colors"
          >
            View All
            <ArrowRight :size="14" />
          </button>
        </RouterLink>
      </div>

      <div v-if="loading" class="flex items-center justify-center py-8">
        <div class="text-center">
          <Loader2
            :size="24"
            class="animate-spin text-muted-foreground mx-auto mb-2"
          />
          <p class="text-sm text-muted-foreground">Loading dashboard...</p>
        </div>
      </div>

      <div
        v-else-if="suggestion.items.length === 0"
        class="flex flex-col items-center justify-center py-12 px-6 text-center"
      >
        <AlertCircle :size="28" class="text-muted-foreground mb-3" />
        <p class="text-sm font-semibold text-foreground mb-1">
          No suggestion available
        </p>
        <p class="text-xs text-muted-foreground">
          Add products and materials to generate suggestions
        </p>
      </div>

      <div v-else class="overflow-x-auto">
        <table class="w-full text-sm">
          <thead>
            <tr class="border-b border-border bg-muted/40">
              <th
                class="text-left px-4 py-3 text-xs font-semibold uppercase tracking-wider text-muted-foreground"
              >
                Product
              </th>
              <th
                class="text-right px-4 py-3 text-xs font-semibold uppercase tracking-wider text-muted-foreground"
              >
                Qty
              </th>
              <th
                class="text-right px-4 py-3 text-xs font-semibold uppercase tracking-wider text-muted-foreground"
              >
                Value
              </th>
            </tr>
          </thead>
          <tbody class="divide-y divide-border">
            <tr
              v-for="item in suggestion.items.slice(0, 5)"
              :key="item.productId"
              class="hover:bg-muted/30 transition-colors"
            >
              <td class="px-4 py-3 font-medium text-foreground">
                {{ item.productName }}
              </td>
              <td class="px-4 py-3 text-right">{{ item.quantityToProduce }}</td>
              <td
                class="px-4 py-3 text-right font-semibold"
                style="color: oklch(0.45 0.18 160)"
              >
                ${{ formatCurrency(item.totalValue) }}
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from "vue";
import {
  Package,
  FlaskConical,
  TrendingUp,
  Plus,
  ArrowRight,
  Lightbulb,
  Loader2,
  AlertCircle,
} from "lucide-vue-next";
import { RouterLink } from "vue-router";
import {
  productsService,
  rawMaterialsService,
  productionService,
} from "../services/api";

const loading = ref(true);
const stats = ref({ totalProducts: 0, totalMaterials: 0, productionValue: 0 });
const suggestion = ref<{ items: any[]; totalValue: number | string }>({
  items: [],
  totalValue: 0,
});

const formatCurrency = (value: number | string) => {
  return new Intl.NumberFormat("en-US", {
    style: "currency",
    currency: "USD",
    maximumFractionDigits: 0,
  })
    .format(Number(value))
    .replace("$", "");
};

onMounted(async () => {
  try {
    const [products, materials, prod] = await Promise.all([
      productsService.getAll(),
      rawMaterialsService.getAll(),
      productionService.getSuggestion(),
    ]);

    console.log("products:", products);
    console.log("materials:", materials);
    console.log("production:", prod);

    stats.value = {
      totalProducts: products.length,
      totalMaterials: materials.length,
      productionValue: Number(prod.totalValue) || 0,
    };

    suggestion.value = prod;
  } catch (err) {
    console.error("Failed to load dashboard:", err);
  } finally {
    loading.value = false;
  }
});
</script>
