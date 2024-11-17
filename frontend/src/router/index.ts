import { createRouter, createWebHistory } from 'vue-router'
import SeoulView from '../views/SeoulView.vue';
import TokyoView from '../views/TokyoView.vue';
import DashboardView from '../views/DashboardView.vue';

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    { path: '/', name: 'dashboard', component: DashboardView },
    { path: '/seoul', name: 'seoul', component: SeoulView },
    { path: '/tokyo', name: 'tokyo', component: TokyoView },
  ],
})

export default router
