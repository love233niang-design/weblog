<template>
    <footer class="mt-16 border-t border-ink-100/80 dark:border-ink-800">
        <div class="blog-container py-10 flex flex-col sm:flex-row sm:items-center sm:justify-between gap-3">
            <span class="text-sm text-ink-400">
                © {{ year }}
                <a href="#" class="text-ink-600 hover:text-accent dark:text-ink-300 dark:hover:text-accent-muted transition-colors">hq</a>
                · All Rights Reserved
            </span>
            <span class="text-sm text-ink-400">
                备案号：
                <a href="#" class="hover:text-accent dark:hover:text-accent-muted transition-colors">xxxxxxxxxxxx号</a>
            </span>
        </div>
    </footer>
</template>

<script setup>
const year = new Date().getFullYear()
</script>
