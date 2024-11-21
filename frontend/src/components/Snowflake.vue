<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue';
import { socket } from '@/services/socketio.service';
import { defineProps } from 'vue';
import { v4 as uuid } from 'uuid';
import { watch } from 'vue';
import { computed } from 'vue';

const props = defineProps<{
  city: string;
}>();

// Snowflake interface for typed properties
interface Snowflake {
  id: number;
  left: string;
  fontSize: string;
  animationDuration: string;
  bottom: string;
  opacity: number;
  // fadeOutAndRemove: (callback: () => void) => void;
}

// Reactive references
const snowflakes = ref<Snowflake[]>([]);
let snowflakeId = 0;
// let queue: string[] = [];
const queue = ref<string[]>([]);
// Listen for "makeItSnow" event from server
// socket.on(props.city, (data: any) => {
//   const snowid = data as string
//   const idx = queue.findIndex(item => item === snowid)
//   if (idx === -1) {
//     createSnowflake(); // Trigger snowflake animation when "makeItSnow" is received
//   } else {
//     queue.splice(idx, 1);
//   }
// });
watch(
  () => props.city,
  (newCity, oldCity) => {
    if (oldCity) {
      // Unsubscribe from the old city's event
      socket.off(oldCity);
    }

    // Subscribe to the new city's event
    socket.on(newCity, (data: any) => {
      const snowid = data as string;
      const idx = queue.value.findIndex((item) => item === snowid);

      if (idx === -1) {
        createSnowflake(); // Trigger snowflake animation
      } else {
        queue.value.splice(idx, 1);
      }
    });
  },
  { immediate: true } // Trigger immediately for the initial `city` value
);
// Function to send the "make it snow" event to the server
const sendMessage = () => {
  if (socket.connected) {
    const snowid = uuid();
    queue.value.push(snowid)
    const body = {
      city: props.city, 
      snowid
    }
    socket.emit("snow", JSON.stringify(body));
  }
  createSnowflake();
};

// Snowflake class definition
class Snowflake {
  public id: number;
  public left: string;
  public fontSize: string;
  public animationDuration: string;
  public bottom: string;
  public opacity: number;
  private static groundLevel = 0;

  constructor(id: number) {
    this.id = id;
    this.left = `${Math.random() * 100}vw`;
    this.fontSize = `${Math.random() * 10 + 10}px`;
    this.animationDuration = `${Math.random() * 3 + 2}s`;
    this.bottom = `${Snowflake.groundLevel}px`;
    this.opacity = 1;
    Snowflake.groundLevel += 2;
  }

  // Returns the CSS style for this snowflake
  getStyle(): Record<string, string | number> {
    return {
      left: this.left,
      fontSize: this.fontSize,
      animationDuration: this.animationDuration,
      bottom: this.bottom,
      opacity: this.opacity
    };
  }

  // Fade out and remove this snowflake after hitting the ground
  fadeOutAndRemove(callback: () => void): void {
    setTimeout(() => {
      this.opacity = 0;
      setTimeout(() => callback(), 1000);
    }, 2000);
  }
}

// Function to create a new snowflake and add it to the list
const createSnowflake = (): void => {
  const newSnowflake = new Snowflake(snowflakeId++);
  snowflakes.value.push(newSnowflake);

  // Remove the snowflake after it has "hit the ground" and faded out
  setTimeout(() => {
    newSnowflake.fadeOutAndRemove(() => {
      snowflakes.value = snowflakes.value.filter(s => s.id !== newSnowflake.id);
    });
  }, parseFloat(newSnowflake.animationDuration) * 1000);
};

// Lifecycle hooks for WebSocket connection management
onMounted(() => {
  socket.connect();
});

onUnmounted(() => {
  socket.disconnect();
});

const getBackgroundImage = () => `/city-${props.city}.png`
</script>

<template>
  <div @click="sendMessage">
    <div class="snow-container">
      <img :class="`city-view`" :src="getBackgroundImage()" alt="" />
      <div v-for="snowflake in snowflakes" :key="snowflake.id" :style="snowflake.getStyle()" class="snowflake">❄️</div>
    </div>

    <!-- Snowflakes display -->
  </div>
</template>

<style scoped>
.snow-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background: linear-gradient(to top,
      #d3d9e3,
      /* Light gray-blue at the horizon */
      #c0c9d6 30%,
      /* Soft bluish-gray transition */
      #a9b7c7 60%,
      /* Muted gray-blue in the middle */
      #8fa3b5 80%,
      /* Darker gray-blue towards the top */
      #778ca3
      /* Dark gray at the top, for a cloudy effect */
    );
  /* background: transparent; */
  overflow: hidden;
  position: relative;
  padding-top: 50px;
}

.city-view {
  width: 100%;
  height: auto;
  position: absolute;
  bottom: 0;
  @media (max-width: 575px) {
    height: 100%;
    width: auto; 
  }
  user-drag: none;
  -webkit-user-drag: none;
  user-select: none;
  -moz-user-select: none;
  -webkit-user-select: none;
  -ms-user-select: none;
}

button {
  position: relative;
  top: 20px;
  padding: 10px 20px;
  font-size: 16px;
  cursor: pointer;
}

.snowflake {
  position: absolute;
  top: -50px;
  color: white;
  user-select: none;
  pointer-events: none;
  animation: fall linear;
  transition: opacity 1s ease;
  filter: blur(3px);
  -webkit-filter: blur(3px);
  /* Fade-out transition */
}

@keyframes fall {
  to {
    transform: translateY(100vh);
    opacity: 1;
  }
}
</style>
