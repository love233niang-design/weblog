<template>
    <div v-if="tags && tags.length > 0" class="blog-card p-5">
        <div class="flex items-center mb-4">
            <h2 class="blog-section-title">标签</h2>
            <span class="grow"></span>
            <a @click="router.push('/tag/list')"
                class="text-xs text-ink-400 hover:text-accent dark:hover:text-accent-muted transition-colors cursor-pointer">
                全部 →
            </a>
        </div>

        <div class="flex flex-wrap gap-2">
            <span v-for="(tag, index) in tags" :key="index" @click="goTagArticleListPage(tag.id, tag.name)"
                class="blog-tag">
                {{ tag.name }}
            </span>
        </div>
    </div>
</template>

<script setup>
import { getTagList } from '@/api/frontend/tag'
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

const tags = ref([])
const size = ref(20)
getTagList({ size: size.value }).then((res) => {
    if (res.success) {
        tags.value = res.data
    }
})

const goTagArticleListPage = (id, name) => {
    router.push({ path: '/tag/article/list', query: { id, name } })
}
</script>
