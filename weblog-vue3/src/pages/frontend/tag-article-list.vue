<template>
    <Header></Header>

    <main class="blog-container py-8 md:py-12">
        <div class="grid grid-cols-12 gap-8 lg:gap-10">
            <div class="col-span-12 lg:col-span-8 space-y-5">
                <div v-if="tags && tags.length > 0" class="blog-card p-6">
                    <h2 class="blog-section-title mb-5">
                        标签
                        <span class="ml-1 text-ink-400 font-normal normal-case tracking-normal">({{ tags.length }})</span>
                    </h2>
                    <div class="flex flex-wrap gap-2">
                        <a v-for="(tag, index) in tags" :key="index" @click="goTagArticleListPage(tag.id, tag.name)"
                            class="blog-chip"
                            :class="[route.query.name == tag.name ? '!border-accent/50 !bg-accent-soft/50 !text-accent dark:!bg-accent-dark/40 dark:!text-accent-soft' : '']">
                            {{ tag.name }}
                            <span
                                class="inline-flex items-center justify-center min-w-[1.1rem] h-4 px-1 text-[10px] font-semibold text-accent bg-accent-soft rounded-md dark:bg-accent-dark dark:text-accent-soft">
                                {{ tag.articlesTotal }}
                            </span>
                        </a>
                    </div>
                </div>

                <div class="blog-card p-2 sm:p-4">
                    <div class="px-3 pt-2 pb-3 mb-1">
                        <h3 class="font-display text-lg font-semibold text-ink-900 dark:text-white">
                            {{ tagName }}
                        </h3>
                    </div>
                    <ol v-if="articles && articles.length > 0" class="divide-y divide-ink-100 dark:divide-ink-800">
                        <li v-for="(article, index) in articles" :key="index">
                            <a @click="goArticleDetailPage(article.id)"
                                class="items-center gap-4 block p-3 sm:flex rounded-xl hover:bg-ink-50 dark:hover:bg-ink-800/60 cursor-pointer transition-colors">
                                <img class="w-full sm:w-28 h-16 object-cover mb-3 sm:mb-0 rounded-lg shrink-0"
                                    :src="article.cover" :alt="article.title" />
                                <div class="min-w-0">
                                    <h2 class="text-base font-medium text-ink-900 dark:text-white truncate">
                                        {{ article.title }}
                                    </h2>
                                    <span class="text-xs text-ink-400 mt-1 inline-block">{{ article.createDate }}</span>
                                </div>
                            </a>
                        </li>
                    </ol>
                    <div v-else class="flex items-center justify-center flex-col py-16">
                        <p class="text-ink-400 text-sm">此标签下还未发布文章</p>
                    </div>
                </div>

                <nav aria-label="Page navigation" class="pt-2 flex justify-center" v-if="pages > 0">
                    <ul class="flex items-center overflow-hidden rounded-xl shadow-soft">
                        <li>
                            <a @click="getTagArticles(current - 1)" class="blog-page-btn rounded-l-xl"
                                :class="[current > 1 ? '' : 'cursor-not-allowed opacity-40']">
                                <svg class="w-3 h-3" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none"
                                    viewBox="0 0 6 10">
                                    <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round"
                                        stroke-width="2" d="M5 1 1 5l4 4" />
                                </svg>
                            </a>
                        </li>
                        <li v-for="(pageNo, index) in pages" :key="index">
                            <a @click="getTagArticles(pageNo)" class="blog-page-btn border-l-0"
                                :class="[pageNo == current ? 'blog-page-btn-active' : '']">
                                {{ index + 1 }}
                            </a>
                        </li>
                        <li>
                            <a @click="getTagArticles(current + 1)" class="blog-page-btn border-l-0 rounded-r-xl"
                                :class="[current < pages ? '' : 'cursor-not-allowed opacity-40']">
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

            <aside class="col-span-12 lg:col-span-4">
                <div class="lg:sticky lg:top-24 space-y-4">
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
import TagListCard from '@/layouts/frontend/components/TagListCard.vue'
import CategoryListCard from '@/layouts/frontend/components/CategoryListCard.vue'
import ScrollToTopButton from '@/layouts/frontend/components/ScrollToTopButton.vue'
import { ref, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { getTagArticlePageList, getTagList } from '@/api/frontend/tag'

const route = useRoute()
const router = useRouter()

const articles = ref([])
const tagName = ref(route.query.name)
const tagId = ref(route.query.id)

watch(route, (newRoute) => {
    tagName.value = newRoute.query.name
    tagId.value = newRoute.query.id
    getTagArticles(current.value)
})

const current = ref(1)
const size = ref(10)
const total = ref(0)
const pages = ref(0)

function getTagArticles(currentNo) {
    if (currentNo < 1 || (pages.value > 0 && currentNo > pages.value)) return
    getTagArticlePageList({ current: currentNo, size: size.value, id: tagId.value }).then((res) => {
        if (res.success) {
            articles.value = res.data
            current.value = res.current
            size.value = res.size
            total.value = res.total
            pages.value = res.pages
        }
    })
}
getTagArticles(current.value)

const goArticleDetailPage = (articleId) => {
    router.push('/article/' + articleId)
}

const tags = ref([])
getTagList({}).then((res) => {
    if (res.success) {
        tags.value = res.data
    }
})

const goTagArticleListPage = (id, name) => {
    router.push({ path: '/tag/article/list', query: { id, name } })
}
</script>
