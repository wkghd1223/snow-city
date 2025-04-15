<script setup lang="ts">
import Footer from '@/components/Footer.vue'
import { RouterLink, RouterView } from 'vue-router'
</script>

<template>
  <main>
    <RouterView />
    <Footer></Footer>
  </main>
</template>
<style scoped>
main {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}
</style>
