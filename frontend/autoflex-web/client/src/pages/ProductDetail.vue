<template>
  <div class="p-6 lg:p-8 space-y-6">
    <!-- Back button -->
    <RouterLink to="/products" class="inline-flex items-center gap-2 text-sm text-muted-foreground hover:text-foreground transition-colors">
      <ArrowLeft :size="16" />
      Back to Products
    </RouterLink>

    <div v-if="loading" class="flex items-center justify-center py-20">
      <div class="text-center">
        <Loader2 :size="24" class="animate-spin text-muted-foreground mx-auto mb-2" />
        <p class="text-sm text-muted-foreground">Loading product...</p>
      </div>
    </div>

    <div v-else-if="!product" class="flex flex-col items-center justify-center py-12 px-6 text-center">
      <AlertCircle :size="28" class="text-muted-foreground mb-3" />
      <p class="text-sm font-semibold text-foreground mb-1">Product not found</p>
      <p class="text-xs text-muted-foreground mb-4">The product you're looking for doesn't exist or was removed.</p>
      <RouterLink to="/products" class="inline-flex">
        <button class="inline-flex items-center gap-2 px-3 py-1.5 text-xs font-medium rounded-md border border-border hover:bg-muted transition-colors">
          <ArrowLeft :size="14" />
          Back to Products
        </button>
      </RouterLink>
    </div>

    <div v-else class="space-y-6">
      <!-- Product Info -->
      <div class="bg-card rounded-lg border border-border shadow-sm overflow-hidden">
        <div class="px-5 py-4 border-b border-border flex items-center gap-3">
          <div class="w-10 h-10 rounded-lg bg-blue-50 flex items-center justify-center">
            <Package :size="20" class="text-blue-600" />
          </div>
          <div>
            <p class="text-xs font-semibold uppercase tracking-wider text-muted-foreground">Product Information</p>
            <p class="text-sm font-semibold text-foreground">{{ product.name }}</p>
          </div>
        </div>

        <div class="p-5 grid grid-cols-1 sm:grid-cols-3 gap-6">
          <div>
            <p class="text-xs font-semibold uppercase tracking-wider text-muted-foreground mb-2">Code</p>
            <p class="text-lg font-bold text-foreground font-mono">{{ product.code }}</p>
          </div>
          <div>
            <p class="text-xs font-semibold uppercase tracking-wider text-muted-foreground mb-2">Price</p>
            <p class="text-lg font-bold text-foreground">${{ product.price.toFixed(2) }}</p>
          </div>
          <div class="flex items-end gap-2">
            <button
              @click="showEditModal = true"
              class="flex-1 inline-flex items-center justify-center gap-2 px-3 py-2 rounded-lg border border-border text-foreground font-medium hover:bg-muted transition-colors"
            >
              <Pencil :size="16" />
              Edit
            </button>
          </div>
        </div>
      </div>

      <!-- Raw Materials Composition -->
      <div class="bg-card rounded-lg border border-border shadow-sm overflow-hidden">
        <div class="px-5 py-4 border-b border-border flex items-center justify-between">
          <div>
            <p class="text-xs font-semibold uppercase tracking-wider text-muted-foreground">Raw Materials Composition</p>
            <p class="text-sm font-semibold text-foreground">Required materials for production</p>
          </div>
          <button
            @click="showAddMaterial = true"
            class="inline-flex items-center gap-2 px-3 py-1.5 text-xs font-medium rounded-md border border-border hover:bg-muted transition-colors"
          >
            <Plus :size="14" />
            Add Material
          </button>
        </div>

        <div v-if="!product.rawMaterials || product.rawMaterials.length === 0" class="flex flex-col items-center justify-center py-8 px-6 text-center">
          <FlaskConical :size="24" class="text-muted-foreground mb-2" />
          <p class="text-sm text-muted-foreground">No raw materials associated yet</p>
        </div>

        <div v-else class="overflow-x-auto">
          <table class="w-full text-sm">
            <thead>
              <tr class="border-b border-border bg-muted/40">
                <th class="text-left px-4 py-3 text-xs font-semibold uppercase tracking-wider text-muted-foreground">Code</th>
                <th class="text-left px-4 py-3 text-xs font-semibold uppercase tracking-wider text-muted-foreground">Name</th>
                <th class="text-right px-4 py-3 text-xs font-semibold uppercase tracking-wider text-muted-foreground">Required Qty</th>
                <th class="text-right px-4 py-3 text-xs font-semibold uppercase tracking-wider text-muted-foreground w-16">Actions</th>
              </tr>
            </thead>
            <tbody class="divide-y divide-border">
              <tr v-for="rm in product.rawMaterials" :key="rm.id" class="hover:bg-muted/30 transition-colors">
                <td class="px-4 py-3">
                  <span class="font-mono text-xs bg-muted px-2 py-0.5 rounded text-muted-foreground">
                    {{ rm.rawMaterialCode }}
                  </span>
                </td>
                <td class="px-4 py-3 font-medium text-foreground">{{ rm.rawMaterialName }}</td>
                <td class="px-4 py-3 text-right font-semibold text-foreground">{{ rm.requiredQuantity }}</td>
                <td class="px-4 py-3">
                  <div class="flex items-center justify-end">
                    <button
                      @click="removeMaterial(rm.id)"
                      class="w-8 h-8 rounded-md text-muted-foreground hover:text-destructive hover:bg-muted transition-colors"
                      title="Remove"
                    >
                      <Trash2 :size="16" />
                    </button>
                  </div>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>

      <!-- Add Material Modal -->
      <div v-if="showAddMaterial" class="fixed inset-0 z-50 flex items-center justify-center bg-black/50">
        <div class="bg-card rounded-lg shadow-lg max-w-md w-full mx-4 p-6">
          <h2 class="text-lg font-bold text-foreground mb-1">Add Raw Material</h2>
          <p class="text-sm text-muted-foreground mb-6">Select a raw material and specify the required quantity</p>

          <div class="space-y-4 mb-6">
            <div>
              <label class="block text-sm font-medium text-foreground mb-1.5">Raw Material <span class="text-destructive">*</span></label>
              <select
                v-model="newMaterial.rawMaterialId"
                class="w-full px-3 py-2 rounded-lg border border-border bg-background text-foreground focus:outline-none focus:ring-2 focus:ring-primary/50"
              >
                <option value="">Select a material...</option>
                <option v-for="rm in availableMaterials" :key="rm.id" :value="rm.id">
                  {{ rm.code }} - {{ rm.name }}
                </option>
              </select>
            </div>

            <div>
              <label class="block text-sm font-medium text-foreground mb-1.5">Required Quantity <span class="text-destructive">*</span></label>
              <input
                v-model="newMaterial.requiredQuantity"
                type="number"
                min="0"
                step="1"
                placeholder="0"
                class="w-full px-3 py-2 rounded-lg border border-border bg-background text-foreground placeholder:text-muted-foreground focus:outline-none focus:ring-2 focus:ring-primary/50"
              />
            </div>
          </div>

          <div class="flex gap-2">
            <button
              @click="showAddMaterial = false"
              class="flex-1 px-4 py-2 rounded-lg border border-border text-foreground font-medium hover:bg-muted transition-colors"
              :disabled="addingMaterial"
            >
              Cancel
            </button>
            <button
              @click="handleAddMaterial"
              class="flex-1 px-4 py-2 rounded-lg bg-primary text-primary-foreground font-medium hover:opacity-90 transition-opacity flex items-center justify-center gap-2"
              :disabled="addingMaterial"
            >
              <Loader2 v-if="addingMaterial" :size="16" class="animate-spin" />
              Add Material
            </button>
          </div>
        </div>
      </div>

      <!-- Edit Product Modal -->
      <div v-if="showEditModal" class="fixed inset-0 z-50 flex items-center justify-center bg-black/50">
        <div class="bg-card rounded-lg shadow-lg max-w-md w-full mx-4 p-6">
          <h2 class="text-lg font-bold text-foreground mb-1">Edit Product</h2>
          <p class="text-sm text-muted-foreground mb-6">Update the product information</p>

          <div class="space-y-4 mb-6">
            <div>
              <label class="block text-sm font-medium text-foreground mb-1.5">Code</label>
              <input
                v-model="editForm.code"
                type="text"
                class="w-full px-3 py-2 rounded-lg border border-border bg-background text-foreground focus:outline-none focus:ring-2 focus:ring-primary/50"
              />
            </div>

            <div>
              <label class="block text-sm font-medium text-foreground mb-1.5">Name</label>
              <input
                v-model="editForm.name"
                type="text"
                class="w-full px-3 py-2 rounded-lg border border-border bg-background text-foreground focus:outline-none focus:ring-2 focus:ring-primary/50"
              />
            </div>

            <div>
              <label class="block text-sm font-medium text-foreground mb-1.5">Price</label>
              <input
                v-model="editForm.price"
                type="number"
                min="0"
                step="0.01"
                class="w-full px-3 py-2 rounded-lg border border-border bg-background text-foreground focus:outline-none focus:ring-2 focus:ring-primary/50"
              />
            </div>
          </div>

          <div class="flex gap-2">
            <button
              @click="showEditModal = false"
              class="flex-1 px-4 py-2 rounded-lg border border-border text-foreground font-medium hover:bg-muted transition-colors"
              :disabled="editingSaving"
            >
              Cancel
            </button>
            <button
              @click="handleEditProduct"
              class="flex-1 px-4 py-2 rounded-lg bg-primary text-primary-foreground font-medium hover:opacity-90 transition-opacity flex items-center justify-center gap-2"
              :disabled="editingSaving"
            >
              <Loader2 v-if="editingSaving" :size="16" class="animate-spin" />
              Save Changes
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { RouterLink } from 'vue-router'
import { Package, Pencil, Plus, Trash2, Loader2, AlertCircle, ArrowLeft, FlaskConical } from 'lucide-vue-next'
import { productsService, rawMaterialsService, type Product, type RawMaterial } from '../services/api'

const route = useRoute()
const product = ref<Product | null>(null)
const loading = ref(true)
const showAddMaterial = ref(false)
const showEditModal = ref(false)
const addingMaterial = ref(false)
const editingSaving = ref(false)
const allMaterials = ref<RawMaterial[]>([])

const newMaterial = ref({ rawMaterialId: '', requiredQuantity: '' })
const editForm = ref({ code: '', name: '', price: '' })

const availableMaterials = computed(() => {
  if (!product.value?.rawMaterials) return allMaterials.value
  const usedIds = product.value.rawMaterials.map(rm => rm.rawMaterialId)
  return allMaterials.value.filter(rm => !usedIds.includes(rm.id))
})

const fetchProduct = async () => {
  loading.value = true
  try {
    const id = Number(route.params.id)
    product.value = await productsService.getById(id)
    editForm.value = { code: product.value.code, name: product.value.name, price: String(product.value.price) }
  } catch (err) {
    console.error('Failed to load product:', err)
  } finally {
    loading.value = false
  }
}

const handleAddMaterial = async () => {
  if (!product.value || !newMaterial.value.rawMaterialId || !newMaterial.value.requiredQuantity) return
  addingMaterial.value = true
  try {
    await productsService.addRawMaterial(product.value.id, {
      rawMaterialId: Number(newMaterial.value.rawMaterialId),
      requiredQuantity: Number(newMaterial.value.requiredQuantity)
    })
    showAddMaterial.value = false
    newMaterial.value = { rawMaterialId: '', requiredQuantity: '' }
    fetchProduct()
  } catch (err) {
    console.error('Failed to add material:', err)
  } finally {
    addingMaterial.value = false
  }
}

const removeMaterial = async (rmId: number | undefined) => {
  if (!product.value || !rmId) return
  try {
    product.value.rawMaterials = product.value.rawMaterials?.filter(rm => rm.id !== rmId)
  } catch (err) {
    console.error('Failed to remove material:', err)
  }
}

const handleEditProduct = async () => {
  if (!product.value) return
  editingSaving.value = true
  try {
    await productsService.update(product.value.id, {
      code: editForm.value.code,
      name: editForm.value.name,
      price: Number(editForm.value.price)
    })
    showEditModal.value = false
    fetchProduct()
  } catch (err) {
    console.error('Failed to edit product:', err)
  } finally {
    editingSaving.value = false
  }
}

onMounted(async () => {
  await fetchProduct()
  try {
    allMaterials.value = await rawMaterialsService.getAll()
  } catch (err) {
    console.error('Failed to load materials:', err)
  }
})
</script>
