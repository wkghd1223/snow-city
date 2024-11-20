<script setup lang="ts">
import { onBeforeMount } from 'vue';
import SnowFlake from '../components/Snowflake.vue'
import { useRoute, useRouter } from 'vue-router';
import { ref } from 'vue';
import { watch } from 'vue';
import { cities } from '@/constants/city-list';

const route = useRoute()
const router = useRouter()
const city = ref<string>("")
city.value = route.params.city as string

watch(() => route.params, (next) => {
  city.value = next.city as string;
})

onBeforeMount(() => {
  if (!cities.map(c => c.path).includes(city.value)) {
    router.replace({name: 'dashboard'})
  }
})
</script>

<template>
  <main>
    <SnowFlake :city="city" />
  </main>
</template>
