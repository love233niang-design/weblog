<template>
    <Header></Header>

    <main class="blog-container py-16 flex items-center justify-center flex-col min-h-[60vh]">
        <p class="font-display text-8xl font-semibold text-ink-200 dark:text-ink-800 tracking-tighter select-none">404</p>
        <p class="mt-4 mb-10 text-ink-400">您要找的页面飞到月球去啦~</p>
        <button @click="router.push('/')" type="button" class="blog-btn-primary px-6">
            返回首页
        </button>
    </main>

    <Footer></Footer>
</template>

<script setup>
import Header from '@/layouts/frontend/components/Header.vue'
import Footer from '@/layouts/frontend/components/Footer.vue'
import { useRouter } from 'vue-router'

const router = useRouter()
</script>
