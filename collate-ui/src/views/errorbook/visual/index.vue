<template>
  <div class="app-container">
    <header-nav></header-nav>
    <el-card shadow="never">
      <el-form inline>
        <el-form-item label="统计类型">
          <el-select v-model="type" @change="loadData" placeholder="请选择统计类型">
            <el-option label="按标签" value="tag" />
            <el-option label="按月份" value="month" />
            <el-option label="按学科" value="subject" />
            <el-option label="整体准确率" value="accuracy" />
          </el-select>
        </el-form-item>
      </el-form>
      <div ref="chart" style="width: 100%; height: 400px; margin-top: 20px;"></div>
    </el-card>
    <common-footer/>
  </div>
</template>

<script>
import * as echarts from 'echarts'
import request from '@/utils/request'
import HeaderNav from "@/views/componests/HeaderNav.vue";
import Cookies from "js-cookie";
import CommonFooter from "@/views/componests/CommonFooter.vue";

export default {
  name: 'WrongStats',
  components:{
    CommonFooter,
    HeaderNav
  },
  data() {
    return {
      type: 'tag',
      chartInstance: null
    }
  },
  mounted() {
    this.chartInstance = echarts.init(this.$refs.chart)
    this.loadData()
  },
  methods: {
    loadData() {
     let userId = Cookies.get("userId")
      if(userId==undefined || userId ==""){
        this.$message.error("登录已失效,请重新登录!")
        return;
      }
      request({
        url: `/stats/${userId}`,
        method: 'get'
      }).then(response => {
        const data = response.data
        console.log("response aa: ",response)
        const xAxisData = data.map(d => d.tag_name)
        const seriesData = data.map(d => d.wrong_count)

        if (this.type === 'accuracy') {
          this.renderAccuracyChart(data)
        } else{
          this.renderBarOrPieChart(data)
        }
      })
    },
    renderBarOrPieChart(data) {
      const xData = data.map(item => item.tag_name)
      const yData = data.map(item => item.wrong_count)

      const isPie = this.type === 'subject'
      const option = isPie
        ? {
          title: { text: '错题分布（按学科）', left: 'center' },
          tooltip: { trigger: 'item' },
          series: [{
            type: 'pie',
            radius: '50%',
            data: data.map(item => ({ name: item.tag_name, value: item.wrong_count })),
            emphasis: {
              itemStyle: { shadowBlur: 10, shadowOffsetX: 0, shadowColor: 'rgba(0, 0, 0, 0.5)' }
            }
          }]
        }
        : {
          title: { text: '错题统计' },
          xAxis: { type: 'category', data: xData },
          yAxis: { type: 'value' },
          series: [{ data: yData, type: this.type === 'month' ? 'line' : 'bar' }]
        }

      this.chartInstance.setOption(option)
    },

    renderAccuracyChart(data) {
      const value = parseFloat(data[0]?.accuracy || 0).toFixed(2)
      this.chartInstance.setOption({
        title: { text: '正确率', left: 'center' },
        series: [{
          type: 'gauge',
          detail: { formatter: '{value}%' },
          data: [{ value, name: '准确率' }]
        }]
      })
    }
  }
}
</script>

<style scoped>
.el-card {
  margin: 20px;
}
</style>
