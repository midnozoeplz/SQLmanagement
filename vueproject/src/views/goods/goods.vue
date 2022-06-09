<template>
  <div>
    <!-- 面包屑导航 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>商品管理</el-breadcrumb-item>
    </el-breadcrumb>
    <!--列表-->
     <br>
    <h5 class="p1"> 订单详情 </h5>
    <br>
    <el-table  size="small" ref = "multipleTable"  @selection-change="selectChange" :data="data" highlight-current-row v-loading="loading" border element-loading-text="拼命加载中" style="width: 90%;">
      <el-table-column align="center" type="selection" width="50">
      </el-table-column>
      <el-table-column align="center" sortable prop="id" label="订单id" width="120">
      </el-table-column>
      <el-table-column align="center" sortable prop="orderTime" label="下单时间" width="308">
      </el-table-column>
      <el-table-column align="center" sortable prop="productId" label="商品id" width="120">
      </el-table-column>
      <el-table-column align="center" sortable prop="productName" label="商品名称" min-width="150">
      </el-table-column>
      <el-table-column align="center" sortable prop="price" label="商品价格" min-width="80">
      </el-table-column>
       <el-table-column align="center" sortable prop="num" label="商品数量" width="100">
      </el-table-column>
    </el-table>
    <!-- 分页组件 -->
    <Pagination v-bind:child-msg="pageparm" @callFather="callFather"></Pagination>
  
  </div>
</template>

<script>
// 导入请求方法
import {
  getOrderList,
  addOrupdateProduct,
  productDelete,
  addCastOrder
} from '../../api/userMG'
import Pagination from '../../components/Pagination'
export default {
  data() {
    return {
      isMounted: false,
      flag: true,
      nshow: true, //switch开启
      fshow: false, //switch关闭
      loading: false, //是显示加载
      title: '添加商品',
      editFormVisible: false, //控制编辑页面显示与隐藏
      dataAccessshow: false, //控制数据权限显示与隐藏
      unitAccessshow: false, //控制所属单位隐藏与显示
      // 编辑与添加
      editForm: {
        id: '',
        orderTime: '',
        productId: '',
        num: '',
        price: '',
        productName: '',
      },
     

      // 请求数据参数
      formInline: {
        page: 1,
        limit: 10,
        deptId: '',
        userName: '',
        userMobile: '',
        isLock: ''
      },
      // 选中
      checkmenu: [],
      //参数role
      saveroleId: '',
      // 分页参数
      pageparm: {
        currentPage: 1,
        pageSize: 10,
        total: 10
      },
      data: [
        ],
      productIdList:[]
    }
  },
  // 注册组件
  components: {
    Pagination
  },

  mounted(){
    this.isMounted = true;
  },
  /**
   * 数据发生改变
   */
  watch: {},

  /**
   * 创建完毕
   */
  created() {
    this.getdata(this.formInline)
  },

  /**
   * 里面的方法只有被调用才会执行
   */
  methods: {
    // 获取数据方法
    getdata(parameter) {
      this.loading = true
      var parm = localStorage.getItem("userid")
      getOrderList(parm).then(res => {
          this.loading = false
          console.log(res)
          if(res.result.code = '200'){
            this.data = []
            for(var i = 0;i<res.result.length;i++){
                this.data.push(res.result[i])
            }

          }else {
            this.$message({
              type: 'error',
              message: "无法获取数据"
            })
          }
      })
         
      this.loading = false
  
      // 分页赋值
      this.pageparm.currentPage = this.formInline.page
      this.pageparm.pageSize = this.formInline.limit
      
    },
    // 分页插件事件
    callFather(parm) {
      this.formInline.page = parm.currentPage
      this.formInline.limit = parm.pageSize
      this.getdata(this.formInline)
    },
    
    
          // 选择复选框事件
    selectChange(val) {
      this.selectdata = val
    },
  
  }
}
</script>

<style scoped>
.user-search {
  margin-top: 20px;
}
.userRole {
  width: 100%;
}
.p2{
  margin-top: 80px;
  margin-left: 1300px;
}
.p1{
  font-size: 160%;
  margin-left: 43% ;
}
</style>

