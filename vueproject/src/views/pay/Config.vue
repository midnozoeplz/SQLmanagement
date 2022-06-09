<template>
  <div>
    <!-- 面包屑导航 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>商品管理</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 搜索筛选 -->
    <!-- <el-form :inline="true" :model="formInline" class="user-search">
      <el-form-item label="搜索：">
        <el-select size="small" v-model="formInline.isLock" placeholder="请选择">
          <el-option label="全部" value=""></el-option>
          <el-option label="有货" value="N"></el-option>
          <el-option label="无货" value="Y"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="">
        <el-input size="small" v-model="formInline.userName" placeholder="输入商品id"></el-input>
      </el-form-item>
      <el-form-item label="">
        <el-input size="small" v-model="formInline.userMobile" placeholder="输入商品名"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button size="small" type="primary" icon="el-icon-search" @click="search">搜索</el-button>
        <el-button  size="small" type="primary" icon="el-icon-plus" @click="handleEdit()">添加</el-button>
      </el-form-item>
    </el-form> -->
    <!--列表-->
     <br>
    <h5 class="p1"> 我的购物车 </h5>
    <br>
    <el-table size="small" ref = "multipleTable"  @selection-change="selectChange" :data="data" highlight-current-row v-loading="loading" border element-loading-text="拼命加载中" style="width: 100%;">
      <el-table-column align="center" type="selection" width="50">
      </el-table-column>
      <el-table-column align="center" sortable prop="id" label="id" width="120">
      </el-table-column>
      <el-table-column align="center" sortable prop="productId" label="商品id" width="120">
      </el-table-column>
    
      <el-table-column align="center" sortable prop="price" label="商品价格" min-width="50">
      </el-table-column>
       <el-table-column align="center" prop="picture" label="商品描述" min-width="160">
        <template width="160" slot-scope="scope">
         <img style="width:100px;height:100px;border:none;" :src= scope.row.picture> 
        </template>
      </el-table-column>
      <el-table-column align="center" sortable prop="productName" label="商品名称" min-width="120">
      </el-table-column>
       <el-table-column align="center" sortable prop="num" label="商品数量" width="120">
      </el-table-column>
      <el-table-column label="操作" min-width="150">
        <template slot-scope="scope">
          <el-button size="mini" @click="handleEdit(scope.$index, scope.row)">更改数量</el-button>
          <el-button size="mini" type="danger" @click="deleteUser(scope.$index, scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页组件 -->
    <Pagination v-bind:child-msg="pageparm" @callFather="callFather"></Pagination>
    <!-- 编辑界面 -->
    <el-dialog :title="title" :visible.sync="editFormVisible" width="30%" @click='closeDialog("edit")'>
      <el-form label-width="80px" ref="editForm" :model="editForm" >
        <el-form-item style="margin-left:50px" label="商品数量" prop="num">
          <el-input style="width:200px" size="small" v-model="editForm.num" placeholder="请输入商品数量"></el-input>
        </el-form-item>
        
          <!-- <template   slot-scope="scope">            
                            <img :src="scope.row.image"  min-width="70" height="70" />
                        </template> -->
  
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button size="small" @click='closeDialog("edit")'>取消</el-button>
        <el-button size="small" type="primary" :loading="loading" class="title" @click="submitForm(editForm)">保存</el-button>
      </div>
    </el-dialog>
        <div class="p2">
      <div>价格总计:{{total}}元</div><br>
      <el-button type="success" @click="submit(productIdList)">提交订单</el-button>
    </div>
  </div>
</template>

<script>
// 导入请求方法
import {
  userProductList,
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
        userid: '',
        productId: '',
        num: '',
        price: '',
        picture: '',
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

  computed:{
    total(){
      if(this.isMounted){
        var money = 0;
              for(var i = 0;i<this.$refs.multipleTable.selection.length ;i++){
         var x = this.$refs.multipleTable.selection[i].price * this.$refs.multipleTable.selection[i].num;
         money = money + x;
        }
        return money;
      }
    }
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
      userProductList(parm).then(res => {
          this.loading = false
          if(res.result.code = '200'){
            this.data = []
            for(var i = 0;i<res.result.length;i++){
                this.data.push(res.result[i])
                this.productIdList.push(res.result[i].productId)
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
    
    //显示编辑界面
    handleEdit: function(index, row) {
   
      this.editFormVisible = true
      if (row != undefined && row != 'undefined') {
        this.title = '修改商品数量'
        this.editForm.id  = row.id 
        this.editForm.userid  = row.userid 
        this.editForm.productId  = row.productId 
        this.editForm.num  = row.num 
        this.editForm.price  = row.price 
        this.editForm.picture  = row.picture 
        this.editForm.productName  = row.productName 
      } 
    },
    // 编辑、添加提交方法
    submitForm(editData) {
          this.editFormVisible = false
              this.loading = false
         var index=this.data.findIndex(item=>{
                        if(item.name==editData.name){
                            return true;
                            //返回的是index
                        }
                    })
            console.log(index);
          var newData = {
               id: editData.id,
               productId: editData.productId,
               userid: editData.userid,
               price: editData.price,
               picture: editData.picture,
               productName: editData.productName,
               num: editData.num
          }     
           this.data.splice(index,1,newData)
          addOrupdateProduct(newData).then(res => {
                  this.$message({
                  type: 'success',
                  message: '数量修改成功！'
                }) 
              }        
          )
           setTimeout(() => {
              this.getdata(this.formInline)
          }, 1000)
         
         
       
      },
          // 选择复选框事件
    selectChange(val) {
      this.selectdata = val
    },
      deleteUser(index, row) {
      this.$confirm('确定要删除吗?', '信息', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          productDelete(localStorage.getItem("userid"),row.productId)
            .then(res => {
                this.$message({
                  type: 'success',
                  message: '商品已删除！'
                })
                setTimeout(() => {
                    this.getdata(this.formInline)
                },1000)
                
              } 
            )
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
    },
    submit(productIdList){

        addCastOrder(localStorage.getItem("userid"),productIdList.toString()).then(res =>{
          console.log(res)
                 this.$message({
                  type: 'success',
                  message: '生成订单成功'
                })
        })
    }
  
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

