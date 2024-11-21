<script setup lang="ts">
import Footer from '@/components/Footer.vue';

</script>

<template setup>
  <article>
    <h1>Let it snow!</h1>
    <p>Once you click, then everybody in the same page as you gets a snowflake.</p>
  </article>
  <Footer></Footer>
</template>
  
<style scoped>
article {
  text-align: center;
  padding: 1rem;
  padding-top: 150px;
}
h1 {
  font-size: 3rem;
  margin: 20px 0;
}
p {
  margin: 4rem 0;
}
</style>