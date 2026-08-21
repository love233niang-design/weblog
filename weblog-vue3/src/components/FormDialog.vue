<template>
    <el-dialog v-model="dialogVisible" :title="title" :width="width" :destroy-on-close="destroyOnClose"
        :draggable="true" :close-on-click-modal="false" :close-on-press-escape="false">
        <!-- 插槽 -->
        <slot></slot>
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="dialogVisible = false">取消</el-button>
                <el-button type="primary" @click="submit" :loading="btnLoading">
                    {{ confirmText }}
                </el-button>
            </span>
        </template>
    </el-dialog>
</template>

<script setup>
import { ref } from 'vue'

const dialogVisible = ref(false)

// 打开弹框
const open = () => dialogVisible.value = true
// 关闭弹框
const close = () => dialogVisible.value = false


// 确认按钮加载 loading
const btnLoading = ref(false)
// 显示 loading
const showBtnLoading = () => btnLoading.value = true
// 隐藏 loading
const closeBtnLoading = () => btnLoading.value = false

// 对外暴露方法
defineExpose({
    open,
    close,
    showBtnLoading,
    closeBtnLoading
})

// 对外暴露属性
const props = defineProps({
    title: String, // 字段类型
    width: {
        type: String,
        default: '40%' // 默认值
    },
    destroyOnClose: {
        type: Boolean,
        default: false
    },
    confirmText: {
        type: String,
        default: '提交'
    }
})

// 对外暴露一个 submit 方法
const emit = defineEmits(['submit'])
const submit = () => emit('submit')


</script>

<style>
@media (max-width: 768px) {
    .el-dialog:not(.is-fullscreen) {
        width: calc(100vw - 24px) !important;
        margin-top: 8vh !important;
    }

    .el-dialog:not(.is-fullscreen) .el-dialog__body {
        max-height: calc(100vh - 180px);
        overflow-y: auto;
    }

    .el-dialog__footer .dialog-footer {
        display: flex;
        gap: 8px;
    }

    .el-dialog__footer .dialog-footer .el-button {
        flex: 1;
        margin-left: 0 !important;
    }
}
</style>
