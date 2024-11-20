<script setup lang="ts">
import { RouterLink } from 'vue-router';
import { cities } from '@/constants/city-list';
</script>

<template>
  <header class="cloudy-header">
    <div class="wrapper">
      <nav class="cloudy-nav">
        <RouterLink to="/" class="cloud-link">Home</RouterLink>
        <RouterLink v-for="city in cities" :to="`${city.path}`" class="cloud-link">{{ city.name }}</RouterLink>
      </nav>
    </div>
  </header>
</template>

<style scoped>
.cloudy-header {
  position: absolute;
  top: 0;
  z-index: 1;
  width: 100%;
  max-width: 100vw;
    width: 100%;
}

.wrapper {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 20px 30px;
  flex-wrap: wrap;
  width: 100%;
  overflow: scroll;
}

.cloudy-nav {
  margin-top: 10px;
  display: flex;
  gap: 15px;
  text-shadow: 0px -2px 8px white;
  min-width: max-content;
}

/* Cloudy link with cloud shape using border-radius and pseudo-elements */
.cloud-link {
  position: relative;
  color: #333;

}
</style>
