<template>
    <!-- PV 折线图容器 -->
    <div class="chart-scroll w-full overflow-x-auto">
        <div ref="lineChatRef" class="line-chart"></div>
    </div>
</template>

<script setup>
import * as echarts from 'echarts'
import { nextTick, onBeforeUnmount, onMounted, ref, watch } from 'vue'

// 对外暴露的属性值
const props = defineProps({
    value: { // 属性值名称
        type: Object, // 类型为对象
        default: null // 默认为 null
    }
})

const lineChatRef = ref(null)
let myChart = null

// 初始化折线图
function initLineChat() {
    nextTick(() => {
        if (!lineChatRef.value) {
            return
        }

        myChart = echarts.getInstanceByDom(lineChatRef.value) || echarts.init(lineChatRef.value)
        // 从 props.value 中获取日期集合和 pv 访问量集合
        const pvDates = props.value?.pvDates || []
        const pvCounts = props.value?.pvCounts || []

        const option = {
            xAxis: {
                type: 'category',
                data: pvDates // x 轴数据
            },
            yAxis: {
                type: 'value'
            },
            series: [
                {
                    data: pvCounts, // 设置 pv 访问量
                    type: 'line'
                }
            ],
            tooltip: { trigger: 'axis' }
        }

        myChart.setOption(option)
        myChart.resize()
    })
}

const resizeChart = () => myChart && myChart.resize()

// 侦听属性, 监听 props.value 的变化，一旦 props.value 发生变化，就调用 initLineChat 初始化折线图
watch(() => props.value, () => initLineChat(), { deep: true, immediate: true })

onMounted(() => window.addEventListener('resize', resizeChart))
onBeforeUnmount(() => {
    window.removeEventListener('resize', resizeChart)
    myChart && myChart.dispose()
})
</script>

<style scoped>
.line-chart {
    width: 100%;
    min-width: 520px;
    height: 240px;
}
</style>
