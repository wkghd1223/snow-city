import { createRouter, createWebHistory } from 'vue-router'
import CityView from '../views/CityView.vue';
import DashboardView from '../views/DashboardView.vue';

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    { path: '/', name: 'dashboard', component: DashboardView },
    { path: '/:city', name: 'city view', component: CityView, 
      beforeEnter: async (to, from, next) => {
      const city = to.params?.city
      if (!city) {
        next({name: 'dashboard'})    
      }
      next()
    } },
  ],
})

export default router
