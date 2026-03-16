<template>
  <div class="p-6 lg:p-8 space-y-6">
    <!-- Header -->
    <div class="flex flex-col sm:flex-row sm:items-center sm:justify-between gap-4">
      <div>
        <h1 class="text-2xl font-bold text-foreground">Raw Materials</h1>
        <p class="text-sm text-muted-foreground">Manage your raw material inventory</p>
      </div>
      <button
        @click="openCreate"
        class="inline-flex items-center gap-2 px-4 py-2 rounded-lg bg-primary text-primary-foreground font-medium text-sm hover:opacity-90 transition-opacity"
      >
        <Plus :size="16" />
        Add Material
      </button>
    </div>

    <!-- Low Stock Alert -->
    <div v-if="!loading && lowStockCount > 0" class="flex items-center gap-3 p-3.5 rounded-lg bg-amber-50 border border-amber-200">
      <AlertTriangle :size="16" class="text-amber-600 flex-shrink-0" />
      <p class="text-sm text-amber-800">
        <strong>{{ lowStockCount }}</strong> material{{ lowStockCount !== 1 ? 's are' : ' is' }} running low on stock
      </p>
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
          <p class="text-sm text-muted-foreground">Loading raw materials...</p>
        </div>
      </div>

      <div v-else-if="filtered.length === 0" class="flex flex-col items-center justify-center py-12 px-6 text-center">
        <FlaskConical :size="28" class="text-muted-foreground mb-3" />
        <p class="text-sm font-semibold text-foreground mb-1">{{ search ? 'No materials found' : 'No raw materials yet' }}</p>
        <p class="text-xs text-muted-foreground mb-4">{{ search ? `No materials match "${search}"` : 'Add your first raw material to get started' }}</p>
        <button
          v-if="!search"
          @click="openCreate"
          class="inline-flex items-center gap-2 px-3 py-1.5 text-xs font-medium rounded-md border border-border hover:bg-muted transition-colors"
        >
          <Plus :size="14" />
          Add Material
        </button>
      </div>

      <div v-else class="overflow-x-auto">
        <table class="w-full text-sm">
          <thead>
            <tr class="border-b border-border bg-muted/40">
              <th class="text-left px-4 py-3 text-xs font-semibold uppercase tracking-wider text-muted-foreground">Code</th>
              <th class="text-left px-4 py-3 text-xs font-semibold uppercase tracking-wider text-muted-foreground">Name</th>
              <th class="text-right px-4 py-3 text-xs font-semibold uppercase tracking-wider text-muted-foreground">Stock Qty</th>
              <th class="text-left px-4 py-3 text-xs font-semibold uppercase tracking-wider text-muted-foreground">Status</th>
              <th class="text-right px-4 py-3 text-xs font-semibold uppercase tracking-wider text-muted-foreground w-24">Actions</th>
            </tr>
          </thead>
          <tbody class="divide-y divide-border">
            <tr v-for="material in filtered" :key="material.id" class="hover:bg-muted/30 transition-colors">
              <td class="px-4 py-3">
                <span class="font-mono text-xs bg-muted px-2 py-0.5 rounded text-muted-foreground">
                  {{ material.code }}
                </span>
              </td>
              <td class="px-4 py-3 font-medium text-foreground">{{ material.name }}</td>
              <td class="px-4 py-3 text-right">
                <span class="font-semibold text-foreground font-mono">{{ material.stockQuantity.toLocaleString() }}</span>
              </td>
              <td class="px-4 py-3">
                {{ getStockBadge(material.stockQuantity) }}
              </td>
              <td class="px-4 py-3">
                <div class="flex items-center justify-end gap-1">
                  <button
                    @click="openEdit(material)"
                    class="w-8 h-8 rounded-md text-muted-foreground hover:text-primary hover:bg-muted transition-colors"
                    title="Edit"
                  >
                    <Pencil :size="16" />
                  </button>
                  <button
                    @click="deleteTarget = material"
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
            {{ filtered.length }} material{{ filtered.length !== 1 ? 's' : '' }}
            {{ search && ` matching "${search}"` }}
          </p>
        </div>
      </div>
    </div>

    <!-- Modal -->
    <div v-if="showModal" class="fixed inset-0 z-50 flex items-center justify-center bg-black/50">
      <div class="bg-card rounded-lg shadow-lg max-w-md w-full mx-4 p-6">
        <h2 class="text-lg font-bold text-foreground mb-1">
          {{ editingMaterial ? 'Edit Raw Material' : 'New Raw Material' }}
        </h2>
        <p class="text-sm text-muted-foreground mb-6">
          {{ editingMaterial ? 'Update the raw material information below' : 'Fill in the details to register a new raw material' }}
        </p>

        <div class="space-y-4 mb-6">
          <div>
            <label class="block text-sm font-medium text-foreground mb-1.5">
              Code <span class="text-destructive">*</span>
            </label>
            <input
              v-model="form.code"
              type="text"
              placeholder="e.g. RM001"
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
              placeholder="e.g. Steel Sheet"
              class="w-full px-3 py-2 rounded-lg border border-border bg-background text-foreground placeholder:text-muted-foreground focus:outline-none focus:ring-2 focus:ring-primary/50"
              :class="{ 'border-destructive': formErrors.name }"
            />
            <p v-if="formErrors.name" class="text-xs text-destructive mt-1">{{ formErrors.name }}</p>
          </div>

          <div>
            <label class="block text-sm font-medium text-foreground mb-1.5">
              Stock Quantity <span class="text-destructive">*</span>
            </label>
            <input
              v-model="form.stockQuantity"
              type="number"
              min="0"
              step="1"
              placeholder="0"
              class="w-full px-3 py-2 rounded-lg border border-border bg-background text-foreground placeholder:text-muted-foreground focus:outline-none focus:ring-2 focus:ring-primary/50"
              :class="{ 'border-destructive': formErrors.stockQuantity }"
            />
            <p v-if="formErrors.stockQuantity" class="text-xs text-destructive mt-1">{{ formErrors.stockQuantity }}</p>
            <p class="text-xs text-muted-foreground mt-1">Current units available in inventory</p>
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
            {{ editingMaterial ? 'Save Changes' : 'Create Material' }}
          </button>
        </div>
      </div>
    </div>

    <!-- Delete Confirmation -->
    <div v-if="deleteTarget" class="fixed inset-0 z-50 flex items-center justify-center bg-black/50">
      <div class="bg-card rounded-lg shadow-lg max-w-sm w-full mx-4 p-6">
        <h2 class="text-lg font-bold text-foreground mb-2">Delete Raw Material</h2>
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
import { Plus, Search, X, FlaskConical, Pencil, Trash2, Loader2, AlertTriangle } from 'lucide-vue-next'
import { rawMaterialsService, type RawMaterial } from '../services/api'

const LOW_STOCK_THRESHOLD = 10

const materials = ref<RawMaterial[]>([])
const search = ref('')
const loading = ref(true)
const showModal = ref(false)
const saving = ref(false)
const deleting = ref(false)
const editingMaterial = ref<RawMaterial | null>(null)
const deleteTarget = ref<RawMaterial | null>(null)

const form = ref({ code: '', name: '', stockQuantity: '' })
const formErrors = ref({ code: '', name: '', stockQuantity: '' })

const filtered = computed(() => {
  const q = search.value.toLowerCase()
  return materials.value.filter(m => m.name.toLowerCase().includes(q) || m.code.toLowerCase().includes(q))
})

const lowStockCount = computed(() => {
  return materials.value.filter(m => m.stockQuantity <= LOW_STOCK_THRESHOLD).length
})

const getStockBadge = (qty: number) => {
  if (qty === 0) {
    return 'Out of stock'
  }
  if (qty <= LOW_STOCK_THRESHOLD) {
    return 'Low stock'
  }
  return 'In stock'
}

const validateForm = () => {
  formErrors.value = { code: '', name: '', stockQuantity: '' }
  if (!form.value.code.trim()) formErrors.value.code = 'Code is required'
  if (!form.value.name.trim()) formErrors.value.name = 'Name is required'
  if (form.value.stockQuantity === '' || isNaN(Number(form.value.stockQuantity)) || Number(form.value.stockQuantity) < 0) {
    formErrors.value.stockQuantity = 'Stock quantity must be zero or greater'
  }
  return Object.values(formErrors.value).every(e => !e)
}

const openCreate = () => {
  editingMaterial.value = null
  form.value = { code: '', name: '', stockQuantity: '' }
  formErrors.value = { code: '', name: '', stockQuantity: '' }
  showModal.value = true
}

const openEdit = (material: RawMaterial) => {
  editingMaterial.value = material
  form.value = { code: material.code, name: material.name, stockQuantity: String(material.stockQuantity) }
  formErrors.value = { code: '', name: '', stockQuantity: '' }
  showModal.value = true
}

const handleSave = async () => {
  if (!validateForm()) return
  saving.value = true
  try {
    const payload = { code: form.value.code.trim(), name: form.value.name.trim(), stockQuantity: Number(form.value.stockQuantity) }
    if (editingMaterial.value) {
      await rawMaterialsService.update(editingMaterial.value.id, payload)
    } else {
      await rawMaterialsService.create(payload)
    }
    showModal.value = false
    fetchMaterials()
  } catch (err) {
    console.error('Failed to save material:', err)
  } finally {
    saving.value = false
  }
}

const handleDelete = async () => {
  if (!deleteTarget.value) return
  deleting.value = true
  try {
    await rawMaterialsService.delete(deleteTarget.value.id)
    deleteTarget.value = null
    fetchMaterials()
  } catch (err) {
    console.error('Failed to delete material:', err)
  } finally {
    deleting.value = false
  }
}

const fetchMaterials = async () => {
  loading.value = true
  try {
    materials.value = await rawMaterialsService.getAll()
  } catch (err) {
    console.error('Failed to load materials:', err)
  } finally {
    loading.value = false
  }
}

onMounted(() => fetchMaterials())
</script>
