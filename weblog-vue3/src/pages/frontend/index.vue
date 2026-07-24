<template>
    <Header></Header>

    <main class="blog-container py-8 md:py-12">
        <div class="grid grid-cols-12 gap-8 lg:gap-10">
            <!-- 文章列表 -->
            <div class="col-span-12 lg:col-span-8 space-y-5">
                <article v-for="(article, index) in articles" :key="index"
                    class="blog-card-hover group overflow-hidden animate-fade-up"
                    :style="{ animationDelay: `${Math.min(index, 6) * 60}ms` }">
                    <div class="flex flex-col sm:flex-row">
                        <!-- 封面 -->
                        <a @click="goArticleDetailPage(article.id)"
                            class="relative sm:w-52 md:w-60 shrink-0 cursor-pointer overflow-hidden">
                            <img class="h-44 sm:h-full w-full object-cover transition duration-500 group-hover:scale-105"
                                :src="article.cover" :alt="article.title" />
                            <span v-if="article.isTop"
                                class="absolute top-3 left-3 text-[11px] font-semibold tracking-wide text-white bg-ink-900/80 backdrop-blur px-2.5 py-1 rounded-md">
                                置顶
                            </span>
                        </a>

                        <!-- 内容 -->
                        <div class="flex flex-col flex-1 p-5 md:p-6 min-w-0">
                            <div class="flex flex-wrap gap-2 mb-3" v-if="article.tags && article.tags.length">
                                <span v-for="(tag, tagIndex) in article.tags" :key="tagIndex"
                                    @click="goTagArticleListPage(tag.id, tag.name)" class="blog-tag">
                                    {{ tag.name }}
                                </span>
                            </div>

                            <a @click="goArticleDetailPage(article.id)" class="cursor-pointer">
                                <h2
                                    class="font-display text-xl md:text-[1.35rem] font-semibold tracking-tight text-ink-900 dark:text-white leading-snug group-hover:text-accent dark:group-hover:text-accent-muted transition-colors">
                                    {{ article.title }}
                                </h2>
                            </a>

                            <p v-if="article.summary"
                                class="mt-2.5 text-sm text-ink-500 dark:text-ink-400 line-clamp-2 leading-relaxed">
                                {{ article.summary }}
                            </p>

                            <div class="blog-meta mt-auto pt-4">
                                <span>{{ article.createDate }}</span>
                                <a @click="goCategoryArticleListPage(article.category.id, article.category.name)"
                                    class="cursor-pointer hover:text-accent dark:hover:text-accent-muted transition-colors">
                                    {{ article.category.name }}
                                </a>
                            </div>
                        </div>
                    </div>
                </article>

                <!-- 分页 -->
                <nav aria-label="Page navigation" class="pt-6 flex justify-center" v-if="pages > 0">
                    <ul class="flex items-center overflow-hidden rounded-xl shadow-soft">
                        <li>
                            <a @click="getArticles(current - 1)"
                                class="blog-page-btn rounded-l-xl"
                                :class="[current > 1 ? '' : 'cursor-not-allowed opacity-40']">
                                <span class="sr-only">上一页</span>
                                <svg class="w-3 h-3" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none"
                                    viewBox="0 0 6 10">
                                    <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round"
                                        stroke-width="2" d="M5 1 1 5l4 4" />
                                </svg>
                            </a>
                        </li>
                        <li v-for="(pageNo, index) in pages" :key="index">
                            <a @click="getArticles(pageNo)" class="blog-page-btn border-l-0"
                                :class="[pageNo == current ? 'blog-page-btn-active' : '']">
                                {{ index + 1 }}
                            </a>
                        </li>
                        <li>
                            <a @click="getArticles(current + 1)"
                                class="blog-page-btn border-l-0 rounded-r-xl"
                                :class="[current < pages ? '' : 'cursor-not-allowed opacity-40']">
                                <span class="sr-only">下一页</span>
                                <svg class="w-3 h-3" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none"
                                    viewBox="0 0 6 10">
                                    <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round"
                                        stroke-width="2" d="m1 9 4-4-4-4" />
                                </svg>
                            </a>
                        </li>
                    </ul>
                </nav>
            </div>

            <!-- 侧边栏 -->
            <aside class="col-span-12 lg:col-span-4">
                <div class="lg:sticky lg:top-24 space-y-4 animate-fade-up" style="animation-delay: 100ms">
                    <UserInfoCard></UserInfoCard>
                    <CategoryListCard></CategoryListCard>
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
import CategoryListCard from '@/layouts/frontend/components/CategoryListCard.vue'
import TagListCard from '@/layouts/frontend/components/TagListCard.vue'
import ScrollToTopButton from '@/layouts/frontend/components/ScrollToTopButton.vue'
import { initTooltips } from 'flowbite'
import { onMounted, ref } from 'vue'
import { getArticlePageList } from '@/api/frontend/article'
import { useRouter } from 'vue-router'

const router = useRouter()

const goCategoryArticleListPage = (id, name) => {
    router.push({ path: '/category/article/list', query: { id, name } })
}

onMounted(() => {
    initTooltips();
})

const articles = ref([])
const current = ref(1)
const size = ref(10)
const total = ref(0)
const pages = ref(0)

function getArticles(currentNo) {
    if (currentNo < 1 || (pages.value > 0 && currentNo > pages.value)) return
    getArticlePageList({ current: currentNo, size: size.value }).then((res) => {
        if (res.success) {
            articles.value = res.data
            current.value = res.current
            size.value = res.size
            total.value = res.total
            pages.value = res.pages
        }
    })
}
getArticles(current.value)

const goArticleDetailPage = (articleId) => {
    router.push('/article/' + articleId)
}

const goTagArticleListPage = (id, name) => {
    router.push({ path: '/tag/article/list', query: { id, name } })
}
</script>
