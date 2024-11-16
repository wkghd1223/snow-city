<script setup lang="ts">
import { RouterLink } from 'vue-router';
</script>

<template>
  <header class="cloudy-header">
    <div class="wrapper">
      <nav class="cloudy-nav">
        <RouterLink to="/" class="cloud-link">Home</RouterLink>
        <RouterLink to="/seoul" class="cloud-link">About</RouterLink>
      </nav>
    </div>
  </header>
</template>

<style scoped>
.cloudy-header {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 0;
  position: absolute;
  top: 0;
  z-index: 1;
  width: 100%;

}

.wrapper {
  /* background: rgba(255, 255, 255, 0.9); */
  /* border-radius: 50% 50% 40% 40%; */
  padding: 20px 30px;
  /* box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1); */
  backdrop-filter: blur(3px);

}

.cloudy-nav {
  margin-top: 10px;
  display: flex;
  gap: 15px;
}

/* Cloudy link with cloud shape using border-radius and pseudo-elements */
.cloud-link {
  position: relative;
  color: #333;

}
</style>
