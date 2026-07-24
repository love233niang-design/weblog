<template>
    <Header></Header>

    <main class="blog-container py-8 md:py-12">
        <div class="grid grid-cols-12 gap-8 lg:gap-10">
            <div class="col-span-12 lg:col-span-8">
                <div class="blog-card p-6 md:p-8">
                    <h2 class="blog-section-title mb-6">
                        分类
                        <span v-if="categories && categories.length > 0"
                            class="ml-1 text-ink-400 font-normal normal-case tracking-normal">({{ categories.length }})</span>
                    </h2>

                    <div class="flex flex-wrap gap-2.5">
                        <a @click="goCategoryArticleListPage(category.id, category.name)"
                            v-for="(category, index) in categories" :key="index" class="blog-chip">
                            {{ category.name }}
                            <span
                                class="inline-flex items-center justify-center min-w-[1.1rem] h-4 px-1 text-[10px] font-semibold text-accent bg-accent-soft rounded-md dark:bg-accent-dark dark:text-accent-soft">
                                {{ category.articlesTotal }}
                            </span>
                        </a>
                    </div>
                </div>
            </div>

            <aside class="col-span-12 lg:col-span-4">
                <div class="lg:sticky lg:top-24 space-y-4">
                    <UserInfoCard></UserInfoCard>
                    <TagListCard></TagListCard>
                </div>
            </aside>
        </div>
    </main>

    <ScrollToTopButton></ScrollToTopButton>
    <Footer></Footer>
</template>

<script setup>
import Header from '@/layouts/frontend/components/Header.vue'
import Footer from '@/layouts/frontend/components/Footer.vue'
import UserInfoCard from '@/layouts/frontend/components/UserInfoCard.vue'
import TagListCard from '@/layouts/frontend/components/TagListCard.vue'
import ScrollToTopButton from '@/layouts/frontend/components/ScrollToTopButton.vue'
import { getCategoryList } from '@/api/frontend/category'
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

const goCategoryArticleListPage = (id, name) => {
    router.push({ path: '/category/article/list', query: { id, name } })
}

const categories = ref([])
getCategoryList({}).then((res) => {
    if (res.success) {
        categories.value = res.data
    }
})
</script>
