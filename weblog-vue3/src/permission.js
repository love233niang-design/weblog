import router from '@/router/index'
import { getToken } from '@/composables/cookie'
import { showMessage } from '@/composables/util'
import { showPageLoading, hidePageLoading } from '@/composables/util'
import { useBlogSettingsStore } from '@/stores/blogsettings'
import { useUserStore } from '@/stores/user'
// 全局路由前置守卫
router.beforeEach((to, from, next) => {
    console.log('==> 全局路由前置守卫')
    // 展示页面加载 Loading
    showPageLoading()
    // 若用户想访问后台（以 /admin 为前缀的路由）
    // 未登录，则强制跳转登录页
    let token = getToken()
    if (!token && to.path.startsWith('/admin')) {
        showMessage('请先登录', 'warning')
        next({ path: '/login' })
    } else if (token && to.path == '/login') {
        // 若用户已经登录，且重复访问登录页
        showMessage('请勿重复登录', 'warning')
        // 跳转后台首页
        next({ path: '/admin/index' })
    } else if (!to.path.startsWith('/admin')) {
        // 如果访问的非 /admin 前缀路由
        // 引入博客设置 store
        let blogSettingsStore = useBlogSettingsStore()
        // 获取博客设置信息并保存到全局状态中
        if (!blogSettingsStore.blogSettings.avatar) {
            blogSettingsStore.getBlogSettings()
        }
        next()
    } else {
        // 访问后台页面时，刷新浏览器后 Pinia 内存状态会丢失，需要重新补齐头部展示信息
        let blogSettingsStore = useBlogSettingsStore()
        if (!blogSettingsStore.blogSettings.avatar) {
            blogSettingsStore.getBlogSettings()
        }
        let userStore = useUserStore()
        if (!userStore.userInfo.username) {
            userStore.setUserInfo()
        }
        next()
    }

})

// 全局路由后置守卫
router.afterEach((to, from) => {
    // 隐藏页面加载 Loading
    hidePageLoading()
    // 动态设置页面 Title
    let title = (to.meta.title ? to.meta.title : '') + ' - Weblog'
    document.title = title
})
