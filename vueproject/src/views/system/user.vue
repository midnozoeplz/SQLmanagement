/**
 * 系统管理 用户管理
 */
<template>
  <div>
    <!-- 面包屑导航 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>商品管理</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 搜索筛选 -->
    <el-form :inline="true" :model="formInline" class="user-search">
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
    </el-form>
    <h5 class="p1"> 购物平台 </h5>
    <!--列表-->
    <el-table size="small" ref = "multipleTable"  @selection-change="selectChange" :data="userData" highlight-current-row v-loading="loading" border element-loading-text="拼命加载中" style="width: 100%;">
      <el-table-column align="center" type="selection" width="50">
      </el-table-column>
      <el-table-column align="center" sortable prop="productId" label="商品id" width="120">
      </el-table-column>
      <el-table-column align="center" sortable prop="num" label="商品数量" width="120">
      </el-table-column>
      <el-table-column align="center" sortable prop="unitPrice" label="商品价格" min-width="50">
      </el-table-column>
      <el-table-column align="center" prop="picture" label="商品描述" min-width="160">
        <template width="160" slot-scope="scope">
         <img style="width:100px;height:100px;border:none;" :src= scope.row.picture> 
        </template>
      </el-table-column>
      <el-table-column align="center" sortable prop="productName" label="商品名称" min-width="120">
      </el-table-column>
      <el-table-column label="操作" min-width="150">
        <template slot-scope="scope">
          <el-button size="mini" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
          <el-button v-if="select()" size="mini" type="danger" @click="deleteUser(scope.$index, scope.row)">删除</el-button>
          <el-button size="mini" type="success" @click="addUser(scope.$index, scope.row)">加入购物车</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页组件 -->
    <Pagination v-bind:child-msg="pageparm" @callFather="callFather"></Pagination>
    <!-- 编辑界面 -->
    <el-dialog :title="title" :visible.sync="editFormVisible" width="30%" @click='closeDialog("edit")'>
      <el-form label-width="80px" ref="editForm" :model="editForm" >
               <el-form-item label="商品名称" prop="productName">
        <el-input size="small" v-model="editForm.productName" placeholder="请输入商品名称"> </el-input>
        </el-form-item>
        <el-form-item label="商品数量" prop="num">
          <el-input size="small" v-model="editForm.num" placeholder="请输入商品数量"></el-input>
        </el-form-item>
        <el-form-item label="商品价格" prop="unitPrice">
          <el-input size="small" v-model="editForm.unitPrice" placeholder="请输入商品价格"></el-input>
        </el-form-item>
        <el-form-item label="商品描述" prop="picture">
        <!-- <el-input size="small" v-model="editForm.picture"> </el-input> -->
           <template>            
                 <!--    上传-->
               <el-button type="primary" @click="fileClick()" size="small">图片上传</el-button>
          <input type="file" id="fileExport" @change="handleFileChange" ref="inputer" style="display:none">
           </template> 
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button size="small" @click='closeDialog("edit")'>取消</el-button>
        <el-button size="small" type="primary" :loading="loading" class="title" @click="submitForm(editForm)">保存</el-button>
      </div>
    </el-dialog>
        <!-- <div class="p2">
      <div>价格总计:{{total}}元</div><br>
      <el-button type="success">提交订单</el-button>
    </div> -->
  </div>
</template>

<script>
// 导入请求方法
import {
  productList,
  UserDeptdeptTree,
  UserChangeDept,
  addOrUpdate,
  deleteProduct,
  addUserProduct
} from '../../api/userMG'
import {task_list_upload} from '../../api/payMG'
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
        productId: '',
        num: '',
        unitPrice: '',
        picture: '',
        productName: '',
      },
      // 部门参数
      unitparm: {
        userIds: '',
        deptId: '',
        deptName: ''
      },
      // 选择数据
      selectdata: [],
      

      // 请求数据参数
      formInline: {
        page: 1,
        limit: 9
      },
      //用户数据
      userData: [],
      // 数据权限
      UserDept: [],
      defaultProps: {
        children: 'children',
        label: 'name'
      },
      // 选中
      checkmenu: [],
      //参数role
      saveroleId: '',
      // 分页参数
      pageparm: {
        currentPage: 1,
        pageSize: 6,
        total: 10
      },
    
    }
  },
  // 注册组件
  components: {
    Pagination
  },

  computed:{
    // total(){
    //   if(this.isMounted){
    //     var money = 0;
    //           for(var i = 0;i<this.$refs.multipleTable.selection.length ;i++){
    //      var x = this.$refs.multipleTable.selection[i].price * this.$refs.multipleTable.selection[i].num;
    //      money = money + x;
    //     }
    //     return money;
    //   }
    // }
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
    fileClick() {
      　this.$refs.inputer.dispatchEvent(new MouseEvent('click'))  // 触发input框的click事件
    },
    handleFileChange() {
    // 上传文件
       var self = this;
       let inputDOM = self.$refs.inputer;
       var file = inputDOM.files[0]; // 通过DOM取文件数据
       var fileName = file.name;
       this.editForm.picture = "http://localhost:8080/image/" + fileName;
    
       let size = Math.floor(file.size / 1024); //计算文件的大小　
       var formData = new FormData(); // new一个formData事件
       formData.append("uploadFile", file); // 将file属性添加到formData里
       task_list_upload(formData).then(res => {
         console.log(res)
       })
    },
    // 获取数据方法
    getdata(parameter) {
      this.loading = true

      productList(parameter).then(res => {
          console.log(res)

          this.userData = res.result 
          this.pageparm.total = this.userData[this.userData.length-1]
          this.userData.splice(this.userData.length-1,1)
          
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
    //搜索事件
    search() {
      this.getdata(this.formInline)
    },
    
    //显示编辑界面
    handleEdit: function(index, row) {
      
      var role = localStorage.getItem("role")
      if(role==='normal') {
        this.$message({
           message: '用户权限不足',
      type: 'error'
        })
      }
      else if(row != undefined && row != 'undefined') {
        this.editFormVisible = true
        this.title = '修改商品'
        this.editForm.productId = row.productId
        this.editForm.num  = row.num 
        this.editForm.unitPrice  = row.unitPrice 
        this.editForm.picture  = row.picture 
        this.editForm.productName  = row.productName 
      } else {
        this.editFormVisible = true
        this.title = '添加商品'
        this.editForm.productId = ''
        this.editForm.num = ''
        this.editForm.unitPrice = ''
        this.editForm.picture = ''
        this.editForm.productName = ''
        
      }
    },
    // 编辑、添加提交方法
    submitForm(editData) {
      if(this.title === '添加商品') {
              this.editFormVisible = false
              this.loading = false
              // this.userData.push({
              //  unitPrice: editData.unitPrice,
              //  picture: editData.picture,
              //  productName: editData.productName,
              //  num: editData.num
              // })
            console.log(editData) 
           addOrUpdate(editData).then(res => {
              console.log(res)
              this.$message({
                  type: 'success',
                  message: '数据保存成功！'
                })
                setTimeout(() =>{
                    this.getdata(this.formInline)
                },1000)
           })      
                
      }else{
          this.editFormVisible = false
              this.loading = false
              console.log(editData) 
          addOrUpdate(editData).then(res => {
              console.log(res)
              this.$message({
                  type: 'success',
                  message: '数据保存成功！'
                })
                setTimeout(() =>{
                    this.getdata(this.formInline)
                },1000)
           })   
            
       }
      },
    // 显示部门设置
    handleunit: function(index, row) {
      this.unitAccessshow = true
      let parms = 0
      UserDeptdeptTree(parms)
        .then(res => {
          if (res.data.success) {
            this.UserDept = this.changeArr(res.data.data)
          } else {
            this.$message({
              type: 'info',
              message: res.msg
            })
          }
        })
        .catch(err => {
          this.loading = false
          this.$message.error('配置权限失败,请稍后再试！')
        })
    },
    handleClick(data, checked, node) {
      if (checked) {
        this.$refs.tree.setCheckedNodes([])
        this.$refs.tree.setCheckedNodes([data])
        this.unitparm.deptId = data.id
        this.unitparm.deptName = data.name
        //交叉点击节点
      } else {
      }
    },
    // 保存部门
    unitPermSave() {
      let len = this.selectdata
      let ids = []
      if (len != 0) {
        for (let i = 0; i < len.length; i++) {
          ids.push(len[i].userId)
        }
      }
      this.unitparm.userIds = ids.join(',')
      UserChangeDept(this.unitparm)
        .then(res => {
          this.unitAccessshow = false
          if (res.success) {
            this.$message({
              type: 'success',
              message: '部门设置成功！'
            })
            this.getdata(this.formInline)
          } else {
            this.$message({
              type: 'info',
              message: res.msg
            })
          }
        })
        .catch(err => {
          this.loading = false
          this.$message.error('部门设置失败,请稍后再试！')
        })
    },
    // 选择复选框事件
    selectChange(val) {
      this.selectdata = val
    },
    // 关闭编辑、增加弹出框
    closeDialog(dialog) {
      if (dialog == 'edit') {
        this.editFormVisible = false
      } else if (dialog == 'perm') {
        this.dataAccessshow = false
      } else if (dialog == 'unit') {
        this.unitAccessshow = false
      }
    },
    //数据格式化
    changeArr(data) {
      var pos = {}
      var tree = []
      var i = 0
      while (data.length != 0) {
        if (data[i].pId == 0) {
          tree.push({
            id: data[i].id,
            name: data[i].name,
            pId: data[i].pId,
            open: data[i].open,
            checked: data[i].checked,
            children: []
          })
          pos[data[i].id] = [tree.length - 1]
          data.splice(i, 1)
          i--
        } else {
          var posArr = pos[data[i].pId]
          if (posArr != undefined) {
            var obj = tree[posArr[0]]
            for (var j = 1; j < posArr.length; j++) {
              obj = obj.children[posArr[j]]
            }

            obj.children.push({
              id: data[i].id,
              name: data[i].name,
              pId: data[i].pId,
              open: data[i].open,
              checked: data[i].checked,
              children: []
            })
            pos[data[i].id] = posArr.concat([obj.children.length - 1])
            data.splice(i, 1)
            i--
          }
        }
        i++
        if (i > data.length - 1) {
          i = 0
        }
      }
      return tree
    },
    // 选中菜单
    changemenu(arr) {
      let change = []
      for (let i = 0; i < arr.length; i++) {
        if (arr[i].checked) {
          change.push(arr[i].id)
        }
      }
      return change
    },
     addUser(index, row) {
      this.$confirm('确定要加入购物车吗?', '信息', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          var userProduct = {
            userid: localStorage.getItem("userid"),
            productId: row.productId,
            num: 1,
            price: row.unitPrice,
            picture: row.picture,
            productName: row.productName
          }
          addUserProduct(userProduct)
            .then(res => {
                this.$message({
                  type: 'success',
                  message: '商品加入成功！'
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
            message: '已取消加入'
          })
        })
    },
    select(){
      if(localStorage.getItem("role") === 'admin'){
        return true
      }
      else {
        return false
      }
    },
    deleteUser(index, row) {
      this.$confirm('确定要删除吗?', '信息', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          deleteProduct(row.productId)
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

 