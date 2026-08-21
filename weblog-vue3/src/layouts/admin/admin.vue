<template>
    <!-- 外层容器 -->
    <el-container class="admin-layout min-h-screen bg-gray-50">

        <div v-if="isMobileMenuOpen" class="admin-menu-mask" @click="closeMobileMenu"></div>

        <!-- 左边侧边栏 -->
        <el-aside :width='menuStore.menuWidth' class="admin-aside transition-all"
            :class="{ 'is-mobile-open': isMobileMenuOpen }">
            <AdminMenu></AdminMenu>
        </el-aside>

        <!-- 主容器 -->
        <el-container class="admin-content">
            <!-- 顶栏容器 -->
            <el-header>
                <AdminHeader></AdminHeader>
            </el-header>

            <el-main class="admin-main">
                <!-- 标签导航栏 -->
                <AdminTagList></AdminTagList>

                <!-- 主内容（根据路由动态展示不同页面） -->
                <router-view v-slot="{ Component }">
                    <Transition name="fade">
                        <!-- max 指定最多缓存 10 个组件 -->
                        <KeepAlive :max="10">
                            <component :is="Component"></component>
                        </KeepAlive>
                    </Transition>

                </router-view>
            </el-main>

            <!-- 底栏容器 -->
            <el-footer>
                <AdminFooter></AdminFooter>
            </el-footer>
        </el-container>
    </el-container>
</template>

<script setup>
// 引入组件
import AdminFooter from './components/AdminFooter.vue';
import AdminHeader from './components/AdminHeader.vue';
import AdminMenu from './components/AdminMenu.vue';
import AdminTagList from './components/AdminTagList.vue';
import { useMenuStore } from '@/stores/menu'
import { computed, onMounted } from 'vue';
const menuStore = useMenuStore()
const isMobileMenuOpen = computed(() => menuStore.menuWidth == '250px')

const closeMobileMenu = () => {
    menuStore.menuWidth = '64px'
}

onMounted(() => {
    // 移除 html 标签中的 class="dark"
    document.documentElement.classList.remove('dark');

    if (window.innerWidth <= 768) {
        menuStore.menuWidth = '64px'
    }
})
</script>

<style scoped>
.el-header {
    padding: 0 !important;
}

.el-footer {
    padding: 0 !important;
}

.admin-layout {
    min-width: 0;
}

.admin-aside {
    flex-shrink: 0;
    transition: width 0.25s ease;
}

.admin-content {
    min-width: 0;
}

.admin-main {
    padding: 16px !important;
    background: #f8fafc;
    overflow-x: hidden;
}

.admin-menu-mask {
    display: none;
}


/* 内容区域过渡动画：淡入淡出效果 */
/* 刚开始进入时 */
.fade-enter-from {
    /* 透明度 */
    opacity: 0;
}

/* 刚开始结束 */
.fade-enter-to {
    opacity: 1;
}

/* 刚开始离开 */
.fade-leave-from {
  opacity: 1;
}

/* 离开已结束 */
.fade-leave-to {
  opacity: 0;
}

/* 离开进行中 */
.fade-leave-active {
    transition: all 0.3s;
}

/* 进入进行中 */
.fade-enter-active {
    transition: all 0.3s;
    transition-delay: 0.3s;
}

:deep(.admin-table-wrap) {
    width: 100%;
    overflow-x: auto;
}

@media (max-width: 768px) {
    .admin-layout {
        display: block;
    }

    .admin-aside {
        position: fixed !important;
        top: 0;
        bottom: 0;
        left: 0;
        z-index: 80;
        width: 250px !important;
        transform: translateX(-100%);
        transition: transform 0.25s ease;
    }

    .admin-aside.is-mobile-open {
        transform: translateX(0);
    }

    .admin-content {
        width: 100%;
        min-height: 100vh;
    }

    .admin-main {
        padding: 12px !important;
    }

    .admin-menu-mask {
        position: fixed;
        inset: 0;
        z-index: 70;
        display: block;
        background: rgba(15, 23, 42, 0.45);
    }

    :deep(.el-card) {
        border-radius: 6px;
    }

    :deep(.el-card__body) {
        padding: 14px !important;
    }

    :deep(.el-card .flex.items-center) {
        flex-wrap: wrap;
        gap: 10px;
        align-items: stretch;
    }

    :deep(.el-card .flex.items-center > .el-text) {
        width: 100%;
    }

    :deep(.el-card .flex.items-center > div[class*="w-"]) {
        width: 100% !important;
        margin-right: 0 !important;
        margin-left: 0 !important;
    }

    :deep(.el-card .flex.items-center > .el-button) {
        flex: 1 1 0;
        min-width: 0;
        margin-left: 0 !important;
    }

    :deep(.el-date-editor.el-input__wrapper),
    :deep(.el-select),
    :deep(.el-input) {
        width: 100% !important;
    }

    :deep(.admin-table-wrap .el-table) {
        min-width: 720px;
    }

    :deep(.admin-table-wrap-lg .el-table) {
        min-width: 1050px;
    }

    :deep(.admin-table-wrap-xl .el-table) {
        min-width: 980px;
    }

    :deep(.el-pagination) {
        flex-wrap: wrap;
        justify-content: center;
        gap: 8px;
    }

    :deep(.el-pagination .el-pagination__jump),
    :deep(.el-pagination .el-pagination__sizes) {
        margin-left: 0;
    }

    :deep(.el-form-item) {
        display: block;
    }

    :deep(.el-form-item__label) {
        justify-content: flex-start;
        width: auto !important;
        padding: 0 0 6px !important;
    }

    :deep(.el-form-item__content) {
        display: block;
        margin-left: 0 !important;
    }
}
</style>
