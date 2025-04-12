<script setup lang="ts">
import Footer from '@/components/Footer.vue';
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
  height: 100vh;
}
</style>