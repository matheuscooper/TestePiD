import { createRouter, createWebHistory } from 'vue-router'
import Dashboard from '../pages/Dashboard.vue'
import Products from '../pages/Products.vue'
import ProductDetail from '../pages/ProductDetail.vue'
import RawMaterials from '../pages/RawMaterials.vue'
import ProductionSuggestion from '../pages/ProductionSuggestion.vue'
import NotFound from '../pages/NotFound.vue'

const routes = [
  { path: '/', component: Dashboard },
  { path: '/products', component: Products },
  { path: '/products/:id', component: ProductDetail },
  { path: '/raw-materials', component: RawMaterials },
  { path: '/production/suggestion', component: ProductionSuggestion },
  { path: '/:pathMatch(.*)*', component: NotFound }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
