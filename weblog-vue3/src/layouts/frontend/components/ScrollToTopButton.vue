<template>
    <button v-show="showScrollToTopBtn" @click="scrollToTop" type="button" aria-label="返回顶部"
        class="fixed bottom-5 right-5 md:bottom-10 md:right-10 z-20
            w-11 h-11 inline-flex items-center justify-center
            rounded-full bg-ink-900 text-white shadow-lift
            hover:bg-accent hover:-translate-y-0.5
            dark:bg-ink-100 dark:text-ink-900 dark:hover:bg-accent-muted dark:hover:text-white
            transition-all duration-300">
        <svg class="w-4 h-4" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 10 14">
            <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                d="M5 13V1m0 0L1 5m4-4 4 4"></path>
        </svg>
    </button>
</template>

<script setup>
import { ref, onMounted, onBeforeUnmount } from 'vue'

const showScrollToTopBtn = ref(false)

onMounted(() => window.addEventListener('scroll', handleScroll))
onBeforeUnmount(() => window.removeEventListener('scroll', handleScroll))

const handleScroll = () => {
    showScrollToTopBtn.value = window.scrollY > 300
}

const scrollToTop = () => {
    window.scrollTo({
        top: 0,
        behavior: 'smooth'
    });
}
</script>
