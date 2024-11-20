<script setup lang="ts">
import { ref, onMounted } from 'vue';
import Footer from '@/components/Footer.vue';

// Define the phrases to rotate
const quotes = [
  "Once you click, then everybody in the same page as you gets a snowflake.",
  "When you click, snow connects us all.",
  "Together, we make it snow."
];

const currentQuote = ref(quotes[0]); // Start with the first quote

// Rotate quotes every 5 seconds
const rotateQuotes = () => {
  let index = 0;
  setInterval(() => {
    index = (index + 1) % quotes.length;
    currentQuote.value = quotes[index];
  }, 5000); // Change every 5 seconds
};

onMounted(() => {
  rotateQuotes();
});
</script>

<template setup>
  <article>
    <h1>Let it snow!</h1>
    <div class="rotating-quotes">
    <p class="quote">{{ currentQuote }}</p>
  </div>
  </article>
  <Footer></Footer>
</template>
  
<style scoped>
article {
  text-align: center;
  padding: 1rem;
  padding-top: 150px;
}
& h1 {
  font-size: 3rem;
  margin: 20px 0;
}
.rotating-quotes {
  text-align: center;
  font-size: 1.5rem;
  font-weight: bold;
  margin: 20px auto;
  position: relative;
  height: 50px; /* To avoid height changes during animations */
  display: flex;
  align-items: center;
  justify-content: center;
  overflow: hidden;
}

.quote {
  animation: fadeInOut 6.5s infinite;
  display: inline-block;
  position: absolute;
}

@keyframes fadeInOut {
  0% {
    opacity: 0;
  }
  20% {
    opacity: 1;
  }
  80% {
    opacity: 1;
  }
  100% {
    opacity: 0;
  }
}
</style>