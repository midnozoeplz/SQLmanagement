
<template>
<div>
  <div id="myChart" :style="{width: '300px', height: '300px'}"></div>
  <div id="myChart2" :style="{width: '600px', height: '600px'}"></div>
</div>
</template>


<script>
export default {
  name: 'FuncFormsBase',
  data () {
    return {
      msg: 'Welcome to Your Vue.js App'
    }
  },
  mounted () {
    this.drawLine();
    this.run(_rawData);
  },
  methods: {
    drawLine () {
      var echarts = require('echarts');
      var myChart = echarts.init(document.getElementById('myChart'));
      myChart.setOption({
        title: {
          text: 'ECharts 入门示例'
        },
        tooltip: {},
        xAxis: {
          data: ['衬衫', '羊毛衫', '雪纺衫', '裤子', '高跟鞋', '袜子']
        },
        yAxis: {},
        series: [{
          name: '销量',
          type: 'bar',
          data: [5, 20, 36, 10, 10, 20]
        }]
      });
    },
  }
}
</script>
 
<style scoped>
</style>
