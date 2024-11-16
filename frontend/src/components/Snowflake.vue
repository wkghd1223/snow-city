<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue';
import { Client, type IMessage } from '@stomp/stompjs';

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

// Initialize STOMP client for WebSocket connection
const client = new Client({
  brokerURL: 'ws://localhost:8080/ws-message', // WebSocket endpoint for Spring Boot
  reconnectDelay: 5000,
  heartbeatIncoming: 4000,
  heartbeatOutgoing: 4000
});

// Subscribe to WebSocket messages
client.onConnect = () => {
  client.subscribe('/topic/seoul', (message: IMessage) => {
    if (message.body === 'makeitsnow') {
      createSnowflake(); // Trigger snowflake on receiving message
    }
  });
};

// Function to send the "make it snow" message to the server
const sendMessage = () => {
  client.publish({ destination: '/app/send/seoul', body: 'makeitsnow' });
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
  client.activate();
});

onUnmounted(() => {
  client.deactivate();
});
</script>

<template>
  <div @click="sendMessage">
    <div class="snow-container">
      <img class="city-view" src="@/assets/css/city-seoul.png" alt="city" />
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
  background-image: url(@/assets/css/city-seoul.png);
  position: absolute;
  pointer-events: none;
  bottom: 0;
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
