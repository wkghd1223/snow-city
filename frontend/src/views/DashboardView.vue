<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue'
import { socket } from '@/services/socketio.service'
import { v4 as uuid } from 'uuid'

interface Snowflake {
  id: number
  left: string
  fontSize: string
  animationDuration: string
  bottom: string
  opacity: number
  word: string
}

const snowflakes = ref<Snowflake[]>([])
let snowflakeId = 0
const queue = ref<string[]>([])
const inputValue = ref<string>('')

socket.on('snow', (data: any) => {
  const parsed = JSON.parse(data as string)
  const snowid = parsed.snowid
  const word = parsed.word
  const idx = queue.value.findIndex((item) => item === snowid)
  if (idx === -1) {
    createSnowflake(word) // use word from server
  } else {
    queue.value.splice(idx, 1)
  }
})

// Send snow event when Enter is pressed
const handleInput = (e: KeyboardEvent) => {
  if (e.key === 'Enter' && inputValue.value.trim() !== '') {
    const snowid = uuid()
    const word = inputValue.value.trim()
    queue.value.push(snowid)
    const body = {
      snowid,
      word,
    }
    socket.emit('snow', JSON.stringify(body))
    createSnowflake(word)
    inputValue.value = ''
  }
}

class Snowflake {
  public id: number
  public left: string
  public fontSize: string
  public animationDuration: string
  public bottom: string
  public opacity: number
  public word: string
  private static groundLevel = 0

  constructor(id: number, word: string) {
    this.id = id
    this.left = `${Math.random() * 100}vw`
    this.fontSize = `${Math.random() * 10 + 10}px`
    this.animationDuration = `${Math.random() * 5 + 2}s`
    this.bottom = `${Snowflake.groundLevel}px`
    this.opacity = 1
    this.word = word
    Snowflake.groundLevel += 2
  }

  getStyle(): Record<string, string | number> {
    return {
      left: this.left,
      fontSize: this.fontSize,
      animationDuration: this.animationDuration,
      bottom: this.bottom,
      opacity: this.opacity,
    }
  }

  fadeOutAndRemove(callback: () => void): void {
    setTimeout(() => {
      this.opacity = 0
      setTimeout(() => callback(), 1000)
    }, 2000)
  }
}

// Create snowflake with passed word
const createSnowflake = (word: string): void => {
  const newSnowflake = new Snowflake(snowflakeId++, word)
  snowflakes.value.push(newSnowflake)
  setTimeout(
    () => {
      newSnowflake.fadeOutAndRemove(() => {
        snowflakes.value = snowflakes.value.filter((s) => s.id !== newSnowflake.id)
      })
    },
    parseFloat(newSnowflake.animationDuration) * 1000,
  )
}

onMounted(() => socket.connect())
onUnmounted(() => socket.disconnect())
</script>

<template setup>
  <article>
    <h1>Let it snow!</h1>
    <p>
      Type a word and press Enter — it will fall like snow for everyone viewing this page in near
      real time.
    </p>

    <input
      type="text"
      v-model="inputValue"
      @keydown="handleInput"
      placeholder="Type a word"
      class="snow-input"
    />
    <div
      v-for="snowflake in snowflakes"
      :key="snowflake.id"
      :style="snowflake.getStyle()"
      class="snowflake"
    >
      {{ snowflake.word }}
    </div>
  </article>
</template>

<style scoped>
article {
  position: relative;
  text-align: center;
  height: calc(100% - 230px);
  padding: 1rem;
  padding-top: 7rem;
}
h1 {
  font-size: 3rem;
  margin: 20px 0;
}
p {
  margin: 4rem 0;
}

.snowflake {
  position: absolute;
  top: 0px;
  color: black;
  height: fit-content;
  user-select: none;
  pointer-events: none;
  animation: fall linear;
  animation-fill-mode: forwards; /* ← retain final state */
  transition: opacity 1s ease;
  /* filter: blur(3px); */
  /* -webkit-filter: blur(3px); */
  /* Fade-out transition */
}

@keyframes fall {
  to {
    transform: translateY(calc(100vh - 250px));
    opacity: 1;
  }
}
</style>
