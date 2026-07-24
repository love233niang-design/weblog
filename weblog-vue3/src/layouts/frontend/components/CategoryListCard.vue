<template>
    <div v-if="categories && categories.length > 0" class="blog-card p-5">
        <div class="flex items-center mb-4">
            <h2 class="blog-section-title">分类</h2>
            <span class="grow"></span>
            <a @click="router.push('/category/list')"
                class="text-xs text-ink-400 hover:text-accent dark:hover:text-accent-muted transition-colors cursor-pointer">
                全部 →
            </a>
        </div>

        <div class="flex flex-wrap gap-2">
            <a @click="goCategoryArticleListPage(category.id, category.name)" v-for="(category, index) in categories"
                :key="index" class="blog-chip">
                {{ category.name }}
                <span
                    class="inline-flex items-center justify-center min-w-[1.1rem] h-4 px-1 text-[10px] font-semibold text-accent bg-accent-soft rounded-md dark:bg-accent-dark dark:text-accent-soft">
                    {{ category.articlesTotal }}
                </span>
            </a>
        </div>
    </div>
</template>

<script setup>
import { getCategoryList } from '@/api/frontend/category'
import { ref } from 'vue'
import { useRouter } from 'vue-router'
const router = useRouter()

const goCategoryArticleListPage = (id, name) => {
    router.push({ path: '/category/article/list', query: { id, name } })
}

const categories = ref([])
const size = ref(10)

getCategoryList({ size: size.value }).then((res) => {
    if (res.success) {
        categories.value = res.data
    }
})
</script>
