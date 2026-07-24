<template>
    <Header></Header>

    <main class="blog-container py-8 md:py-12">
        <div class="grid grid-cols-12 gap-8 lg:gap-10">
            <div class="col-span-12 lg:col-span-8 space-y-5">
                <div v-for="(archive, index) in archives" :key="index" class="blog-card p-5 md:p-6">
                    <time class="font-display text-lg font-semibold text-ink-900 dark:text-white">{{ archive.month }}</time>
                    <ol class="mt-4 divide-y divide-ink-100 dark:divide-ink-800">
                        <li v-for="(article, index2) in archive.articles" :key="index2">
                            <a @click="goArticleDetailPage(article.id)"
                                class="items-center gap-4 block p-3 sm:flex rounded-xl hover:bg-ink-50 dark:hover:bg-ink-800/60 cursor-pointer transition-colors">
                                <img class="w-full sm:w-28 h-16 object-cover mb-3 sm:mb-0 rounded-lg shrink-0"
                                    :src="article.cover" :alt="article.title" />
                                <div class="min-w-0">
                                    <h2 class="text-base font-medium text-ink-900 dark:text-white">
                                        {{ article.title }}
                                    </h2>
                                    <span class="text-xs text-ink-400 mt-1 inline-block">{{ article.createDate }}</span>
                                </div>
                            </a>
                        </li>
                    </ol>
                </div>

                <nav aria-label="Page navigation" class="pt-2 flex justify-center" v-if="pages > 1">
                    <ul class="flex items-center overflow-hidden rounded-xl shadow-soft">
                        <li>
                            <a @click="getArchives(current - 1)" class="blog-page-btn rounded-l-xl"
                                :class="[current > 1 ? '' : 'cursor-not-allowed opacity-40']">
                                <svg class="w-3 h-3" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none"
                                    viewBox="0 0 6 10">
                                    <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round"
                                        stroke-width="2" d="M5 1 1 5l4 4" />
                                </svg>
                            </a>
                        </li>
                        <li v-for="(pageNo, index) in pages" :key="index">
                            <a @click="getArchives(pageNo)" class="blog-page-btn border-l-0"
                                :class="[pageNo == current ? 'blog-page-btn-active' : '']">
                                {{ index + 1 }}
                            </a>
                        </li>
                        <li>
                            <a @click="getArchives(current + 1)" class="blog-page-btn border-l-0 rounded-r-xl"
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
import { getArchivePageList } from '@/api/frontend/archive'
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

const archives = ref([])
const current = ref(1)
const size = ref(10)
const total = ref(0)
const pages = ref(0)

function getArchives(currentNo) {
    if (currentNo < 1 || (pages.value > 0 && currentNo > pages.value)) return
    getArchivePageList({ current: currentNo, size: size.value }).then((res) => {
        if (res.success) {
            archives.value = res.data
            current.value = res.current
            size.value = res.size
            total.value = res.total
            pages.value = res.pages
        }
    })
}
getArchives(current.value)

const goArticleDetailPage = (articleId) => {
    router.push('/article/' + articleId)
}
</script>
