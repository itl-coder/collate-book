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
  </div>
</template>

<script>
import * as echarts from 'echarts'
import request from '@/utils/request'
import HeaderNav from "@/views/componests/HeaderNav.vue";
import Cookies from "js-cookie";

export default {
  name: 'WrongStats',
  components:{
    HeaderNav
  },
  data() {
    return {
      type: 'bar',
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

        this.chartInstance.setOption({
          title: { text: '错题统计' },
          tooltip: {},
          xAxis: { type: 'category', data: xAxisData },
          yAxis: { type: 'value' },
          series: [{
            data: seriesData,
            type: 'bar'
          }]
        })
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
