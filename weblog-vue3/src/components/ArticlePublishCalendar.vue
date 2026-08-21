<template>
    <!-- 日历热点图容器 -->
    <div class="chart-scroll w-full overflow-x-auto">
        <div ref="calendarRef" class="calendar-chart"></div>
    </div>
</template>

<script setup>
import * as echarts from 'echarts'
import { nextTick, onBeforeUnmount, onMounted, ref, watch } from 'vue'
import { format, subMonths } from 'date-fns'

// 对外暴露的属性值
const props = defineProps({
    value: { // 属性值名称
        type: Object, // 类型为对象
        default: null // 默认为 null
    }
})

// 当前日期
const currentDate = new Date();
// 半年前
const sixMonthsAgo = subMonths(currentDate, 6)

// 格式化后的开始、结束日期
const startDate = format(sixMonthsAgo, 'yyyy-MM-dd')
const endDate = format(currentDate, 'yyyy-MM-dd')
const calendarRef = ref(null)
let myChart = null

// 初始化日历热点图
function initCalendar() {
    nextTick(() => {
        if (!calendarRef.value) {
            return
        }

        const myData = Object.entries(props.value || {}).map(([key, value]) => [key, value])
        myChart = echarts.getInstanceByDom(calendarRef.value) || echarts.init(calendarRef.value)
        const option = {
            visualMap: {
                show: false,
                min: 0,
                max: 10
            },
            calendar: { // 日历显示的范围：开始日期 - 结束日期
                range: [startDate, endDate],
            },
            series: {
                type: 'heatmap',
                coordinateSystem: 'calendar',
                data: myData
            },
            gradientColor: [ // 自定义热点颜色，参考了 GitHub 代码提交的颜色
                '#fff',
                '#40c463',
                '#30a14e',
                '#216e39',
            ]
        }

        myChart.setOption(option)
        myChart.resize()
    })
}

const resizeChart = () => myChart && myChart.resize()

// 侦听属性, 监听 props.value 的变化，一旦 props.value 发生变化，就调用 initCalendar 初始化日历热点图
watch(() => props.value, () => initCalendar(), { deep: true, immediate: true })

onMounted(() => window.addEventListener('resize', resizeChart))
onBeforeUnmount(() => {
    window.removeEventListener('resize', resizeChart)
    myChart && myChart.dispose()
})

</script>

<style scoped>
.calendar-chart {
    width: 100%;
    min-width: 600px;
    height: 240px;
}

@media (max-width: 768px) {
    .calendar-chart {
        min-width: 520px;
    }
}
</style>
