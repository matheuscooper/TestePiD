<template>
  <div class="p-6 lg:p-8 space-y-6">
    <!-- Header -->
    <div class="flex flex-col sm:flex-row sm:items-center sm:justify-between gap-4">
      <div>
        <h1 class="text-2xl font-bold text-foreground">Products</h1>
        <p class="text-sm text-muted-foreground">Manage your product catalog</p>
      </div>
      <button
        @click="openCreate"
        class="inline-flex items-center gap-2 px-4 py-2 rounded-lg bg-primary text-primary-foreground font-medium text-sm hover:opacity-90 transition-opacity"
      >
        <Plus :size="16" />
        Add Product
      </button>
    </div>

    <!-- Search -->
    <div class="relative max-w-sm">
      <Search :size="16" class="absolute left-3 top-1/2 -translate-y-1/2 text-muted-foreground" />
      <input
        v-model="search"
        type="text"
        placeholder="Search by name or code..."
        class="w-full pl-9 pr-8 py-2 rounded-lg border border-border bg-background text-foreground placeholder:text-muted-foreground focus:outline-none focus:ring-2 focus:ring-primary/50"
      />
      <button
        v-if="search"
        @click="search = ''"
        class="absolute right-3 top-1/2 -translate-y-1/2 text-muted-foreground hover:text-foreground"
      >
        <X :size="14" />
      </button>
    </div>

    <!-- Table -->
    <div class="bg-card rounded-lg border border-border shadow-sm overflow-hidden">
      <div v-if="loading" class="flex items-center justify-center py-16">
        <div class="text-center">
          <Loader2 :size="24" class="animate-spin text-muted-foreground mx-auto mb-2" />
          <p class="text-sm text-muted-foreground">Loading products...</p>
        </div>
      </div>

      <div v-else-if="filtered.length === 0" class="flex flex-col items-center justify-center py-12 px-6 text-center">
        <Package :size="28" class="text-muted-foreground mb-3" />
        <p class="text-sm font-semibold text-foreground mb-1">{{ search ? 'No products found' : 'No products yet' }}</p>
        <p class="text-xs text-muted-foreground mb-4">{{ search ? `No products match "${search}"` : 'Add your first product to get started' }}</p>
        <button
          v-if="!search"
          @click="openCreate"
          class="inline-flex items-center gap-2 px-3 py-1.5 text-xs font-medium rounded-md border border-border hover:bg-muted transition-colors"
        >
          <Plus :size="14" />
          Add Product
        </button>
      </div>

      <div v-else class="overflow-x-auto">
        <table class="w-full text-sm">
          <thead>
            <tr class="border-b border-border bg-muted/40">
              <th class="text-left px-4 py-3 text-xs font-semibold uppercase tracking-wider text-muted-foreground">Code</th>
              <th class="text-left px-4 py-3 text-xs font-semibold uppercase tracking-wider text-muted-foreground">Name</th>
              <th class="text-right px-4 py-3 text-xs font-semibold uppercase tracking-wider text-muted-foreground">Price</th>
              <th class="text-right px-4 py-3 text-xs font-semibold uppercase tracking-wider text-muted-foreground w-24">Actions</th>
            </tr>
          </thead>
          <tbody class="divide-y divide-border">
            <tr v-for="product in filtered" :key="product.id" class="hover:bg-muted/30 transition-colors">
              <td class="px-4 py-3">
                <span class="font-mono text-xs bg-muted px-2 py-0.5 rounded text-muted-foreground">
                  {{ product.code }}
                </span>
              </td>
              <td class="px-4 py-3 font-medium text-foreground">{{ product.name }}</td>
              <td class="px-4 py-3 text-right text-foreground font-semibold">${{ product.price.toFixed(2) }}</td>
              <td class="px-4 py-3">
                <div class="flex items-center justify-end gap-1">
                  <RouterLink :to="`/products/${product.id}`" class="inline-flex">
                    <button class="w-8 h-8 rounded-md text-muted-foreground hover:text-primary hover:bg-muted transition-colors" title="View details">
                      <Eye :size="16" />
                    </button>
                  </RouterLink>
                  <button
                    @click="openEdit(product)"
                    class="w-8 h-8 rounded-md text-muted-foreground hover:text-primary hover:bg-muted transition-colors"
                    title="Edit"
                  >
                    <Pencil :size="16" />
                  </button>
                  <button
                    @click="deleteTarget = product"
                    class="w-8 h-8 rounded-md text-muted-foreground hover:text-destructive hover:bg-muted transition-colors"
                    title="Delete"
                  >
                    <Trash2 :size="16" />
                  </button>
                </div>
              </td>
            </tr>
          </tbody>
        </table>
        <div class="px-4 py-2.5 border-t border-border bg-muted/20">
          <p class="text-xs text-muted-foreground">
            {{ filtered.length }} product{{ filtered.length !== 1 ? 's' : '' }}
            {{ search && ` matching "${search}"` }}
          </p>
        </div>
      </div>
    </div>

    <!-- Modal -->
    <div v-if="showModal" class="fixed inset-0 z-50 flex items-center justify-center bg-black/50">
      <div class="bg-card rounded-lg shadow-lg max-w-md w-full mx-4 p-6">
        <h2 class="text-lg font-bold text-foreground mb-1">
          {{ editingProduct ? 'Edit Product' : 'New Product' }}
        </h2>
        <p class="text-sm text-muted-foreground mb-6">
          {{ editingProduct ? 'Update the product information below' : 'Fill in the details to register a new product' }}
        </p>

        <div class="space-y-4 mb-6">
          <div>
            <label class="block text-sm font-medium text-foreground mb-1.5">
              Code <span class="text-destructive">*</span>
            </label>
            <input
              v-model="form.code"
              type="text"
              placeholder="e.g. PROD001"
              class="w-full px-3 py-2 rounded-lg border border-border bg-background text-foreground placeholder:text-muted-foreground focus:outline-none focus:ring-2 focus:ring-primary/50"
              :class="{ 'border-destructive': formErrors.code }"
            />
            <p v-if="formErrors.code" class="text-xs text-destructive mt-1">{{ formErrors.code }}</p>
          </div>

          <div>
            <label class="block text-sm font-medium text-foreground mb-1.5">
              Name <span class="text-destructive">*</span>
            </label>
            <input
              v-model="form.name"
              type="text"
              placeholder="e.g. Widget A"
              class="w-full px-3 py-2 rounded-lg border border-border bg-background text-foreground placeholder:text-muted-foreground focus:outline-none focus:ring-2 focus:ring-primary/50"
              :class="{ 'border-destructive': formErrors.name }"
            />
            <p v-if="formErrors.name" class="text-xs text-destructive mt-1">{{ formErrors.name }}</p>
          </div>

          <div>
            <label class="block text-sm font-medium text-foreground mb-1.5">
              Price <span class="text-destructive">*</span>
            </label>
            <input
              v-model="form.price"
              type="number"
              min="0"
              step="0.01"
              placeholder="0.00"
              class="w-full px-3 py-2 rounded-lg border border-border bg-background text-foreground placeholder:text-muted-foreground focus:outline-none focus:ring-2 focus:ring-primary/50"
              :class="{ 'border-destructive': formErrors.price }"
            />
            <p v-if="formErrors.price" class="text-xs text-destructive mt-1">{{ formErrors.price }}</p>
          </div>
        </div>

        <div class="flex gap-2">
          <button
            @click="showModal = false"
            class="flex-1 px-4 py-2 rounded-lg border border-border text-foreground font-medium hover:bg-muted transition-colors"
            :disabled="saving"
          >
            Cancel
          </button>
          <button
            @click="handleSave"
            class="flex-1 px-4 py-2 rounded-lg bg-primary text-primary-foreground font-medium hover:opacity-90 transition-opacity flex items-center justify-center gap-2"
            :disabled="saving"
          >
            <Loader2 v-if="saving" :size="16" class="animate-spin" />
            {{ editingProduct ? 'Save Changes' : 'Create Product' }}
          </button>
        </div>
      </div>
    </div>

    <!-- Delete Confirmation -->
    <div v-if="deleteTarget" class="fixed inset-0 z-50 flex items-center justify-center bg-black/50">
      <div class="bg-card rounded-lg shadow-lg max-w-sm w-full mx-4 p-6">
        <h2 class="text-lg font-bold text-foreground mb-2">Delete Product</h2>
        <p class="text-sm text-muted-foreground mb-6">
          Are you sure you want to delete "{{ deleteTarget.name }}"? This action cannot be undone.
        </p>
        <div class="flex gap-2">
          <button
            @click="deleteTarget = null"
            class="flex-1 px-4 py-2 rounded-lg border border-border text-foreground font-medium hover:bg-muted transition-colors"
            :disabled="deleting"
          >
            Cancel
          </button>
          <button
            @click="handleDelete"
            class="flex-1 px-4 py-2 rounded-lg bg-destructive text-destructive-foreground font-medium hover:opacity-90 transition-opacity flex items-center justify-center gap-2"
            :disabled="deleting"
          >
            <Loader2 v-if="deleting" :size="16" class="animate-spin" />
            Delete
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { RouterLink } from 'vue-router'
import { Plus, Search, X, Package, Eye, Pencil, Trash2, Loader2 } from 'lucide-vue-next'
import { productsService, type Product } from '../services/api'

const products = ref<Product[]>([])
const search = ref('')
const loading = ref(true)
const showModal = ref(false)
const saving = ref(false)
const deleting = ref(false)
const editingProduct = ref<Product | null>(null)
const deleteTarget = ref<Product | null>(null)

const form = ref({ code: '', name: '', price: '' })
const formErrors = ref({ code: '', name: '', price: '' })

const filtered = computed(() => {
  const q = search.value.toLowerCase()
  return products.value.filter(p => p.name.toLowerCase().includes(q) || p.code.toLowerCase().includes(q))
})

const validateForm = () => {
  formErrors.value = { code: '', name: '', price: '' }
  if (!form.value.code.trim()) formErrors.value.code = 'Code is required'
  if (!form.value.name.trim()) formErrors.value.name = 'Name is required'
  if (form.value.price === '' || isNaN(Number(form.value.price)) || Number(form.value.price) < 0) {
    formErrors.value.price = 'Price must be zero or greater'
  }
  return Object.values(formErrors.value).every(e => !e)
}

const openCreate = () => {
  editingProduct.value = null
  form.value = { code: '', name: '', price: '' }
  formErrors.value = { code: '', name: '', price: '' }
  showModal.value = true
}

const openEdit = (product: Product) => {
  editingProduct.value = product
  form.value = { code: product.code, name: product.name, price: String(product.price) }
  formErrors.value = { code: '', name: '', price: '' }
  showModal.value = true
}

const handleSave = async () => {
  if (!validateForm()) return
  saving.value = true
  try {
    const payload = { code: form.value.code.trim(), name: form.value.name.trim(), price: Number(form.value.price) }
    if (editingProduct.value) {
      await productsService.update(editingProduct.value.id, payload)
    } else {
      await productsService.create(payload)
    }
    showModal.value = false
    fetchProducts()
  } catch (err) {
    console.error('Failed to save product:', err)
  } finally {
    saving.value = false
  }
}

const handleDelete = async () => {
  if (!deleteTarget.value) return
  deleting.value = true
  try {
    await productsService.delete(deleteTarget.value.id)
    deleteTarget.value = null
    fetchProducts()
  } catch (err) {
    console.error('Failed to delete product:', err)
  } finally {
    deleting.value = false
  }
}

const fetchProducts = async () => {
  loading.value = true
  try {
    products.value = await productsService.getAll()
  } catch (err) {
    console.error('Failed to load products:', err)
  } finally {
    loading.value = false
  }
}

onMounted(() => fetchProducts())
</script>
