/**
* 图表界面
*/ 
<template>
  <!-- 组件主盒子 -->
  <div class="stbox">
    <!-- 面包屑导航 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>数据可视化</el-breadcrumb-item>
    </el-breadcrumb>
    <el-row :gutter="0" class="el-row">
    <el-col :span="50" :offset="2">
      <div>
        <div class="camera_outer">
               <div style="margin-left: 920px;margin-top: 150px">
    <el-checkbox v-model="traffic" label="测速" border></el-checkbox>
    <el-checkbox v-model="whether" label="天气" border></el-checkbox>
    </div>
    <div style="margin-left: 920px;margin-top: 20px">
    <el-checkbox v-model="dense" label="密度" border size="medium"></el-checkbox>
    <el-checkbox v-model="clime" label="嫌疑车辆" border size="medium"></el-checkbox>
  </div>
     <div style="margin-left: 920px;margin-top: 20px">
      <el-checkbox v-model="picImprove" label="光照增强" border></el-checkbox>
      <el-checkbox v-model="whImprove" label="图片属性调整" border ></el-checkbox>
      <el-checkbox v-model="draw" label="绘图模块" border></el-checkbox>
  </div>
      <video style="margin-top: -300px" id="videoCamera" :width="videoWidth" :height="videoHeight" autoplay></video>
      <canvas style="display:none;" id="canvasCamera" :width="videoWidth" :height="videoHeight" ></canvas>
      </div>
    <div>
      <el-button  @click="getCompetence()" type="success" >打开摄像头</el-button>
      <el-button  @click="stopNavigator()" type="warning">关闭摄像头</el-button>
      <div style="margin-left: 800px">
        <p style="color:#d71345" v-show="!flag">请先上传文件</p>
　　　 <el-button  @click="setImage()" type="primary" :disabled="!flag">开始运行</el-button>
      </div>
    </div>      
    </div>
    </el-col>
    <el-col :span='4'>
        <div>
           <el-button class="type1" type="primary" @click="handleup()">上传文件<i class="el-icon-upload el-icon--right"></i></el-button>
           <el-dialog  :visible.sync="open2" width="400px" height="500px" append-to-body>
      <el-upload
        class="upload-demo"
        action="UploadUrl()"
        drag
        :file-list="fileList"
        :http-request="uploadFile"
      >
        <i class="el-icon-upload" />
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <!-- <div slot="tip" class="el-upload__tip">只能上传.xlsx、.xls文件了，大小不超过5M</div> -->
      </el-upload>

      <div slot="footer" class="dialog-footer">
        <!-- <el-button @click="submitForm">确 定</el-button> -->
        <el-button @click="cancel2">取 消</el-button>
      </div>
    </el-dialog>
        </div>
    </el-col>
    </el-row>
  </div>
</template>

 <script>
 import { task_list_upload, getObject} from '../../api/payMG'
  export default {
    // 自动触发点击事件
    data () {
      return {
        videoWidth: 800,
        videoHeight: 600,
        imgSrc: '',
        thisCancas: null,
        thisContext: null,
        thisVideo: null,
        fileList: [],
        open2: false,
        tid: 0,
        flag: false,
        taskname: null,
        traffic: false,
        whether: false,
        dense: false,
        clime: false,
        picImprove: false,
        whImprove: false,
        draw: false
      }
    },
    methods: {
// 调用权限（打开摄像头功能）
      getCompetence () {
        var _this = this
        this.thisCancas = document.getElementById('canvasCamera')
        this.thisContext = this.thisCancas.getContext('2d')
        this.thisVideo = document.getElementById('videoCamera')
        // 旧版本浏览器可能根本不支持mediaDevices，我们首先设置一个空对象
        if (navigator.mediaDevices === undefined) {
          navigator.mediaDevices = {}
        }
        // 一些浏览器实现了部分mediaDevices，我们不能只分配一个对象
        // 使用getUserMedia，因为它会覆盖现有的属性。
        // 这里，如果缺少getUserMedia属性，就添加它。
        if (navigator.mediaDevices.getUserMedia === undefined) {
          navigator.mediaDevices.getUserMedia = function (constraints) {
            // 首先获取现存的getUserMedia(如果存在)
            var getUserMedia = navigator.webkitGetUserMedia || navigator.mozGetUserMedia || navigator.getUserMedia
            // 有些浏览器不支持，会返回错误信息
            // 保持接口一致
            if (!getUserMedia) {
              return Promise.reject(new Error('getUserMedia is not implemented in this browser'))
            }
            // 否则，使用Promise将调用包装到旧的navigator.getUserMedia
            return new Promise(function (resolve, reject) {
              getUserMedia.call(navigator, constraints, resolve, reject)
            })
          }
        }
        var constraints = { audio: false, video: { width: this.videoWidth, height: this.videoHeight, transform: 'scaleX(-1)' } }
        navigator.mediaDevices.getUserMedia(constraints).then(function (stream) {
          // 旧的浏览器可能没有srcObject
          if ('srcObject' in _this.thisVideo) {
            _this.thisVideo.srcObject = stream
          } else {
            // 避免在新的浏览器中使用它，因为它正在被弃用。
            _this.thisVideo.src = window.URL.createObjectURL(stream)
          }
          _this.thisVideo.onloadedmetadata = function (e) {
            _this.thisVideo.play()
          }
        }).catch(err => {
          console.log(err)
        })
      },
      setImage () {
        // var _this = this
        // // 点击，canvas画图
        // _this.thisContext.drawImage(_this.thisVideo, 0, 0,450,500)
        // // 获取图片base64链接
        // var image = this.thisCancas.toDataURL('image/png')
        // _this.imgSrc = image
        // this.$emit('refreshDataList', this.imgSrc)
        var traffic = this.traffic
        var whether = this.whether
        var dense = this.dense
        var clime = this.clime
        var picImprove = this.picImprove
        var whImprove = this.whImprove
        var draw = this.draw
       getObject(traffic,whether,dense,clime,picImprove,whImprove,draw).then(
         response => {
            console.log(dense)
          if (response.message === '成功!') {  
            }
        }).catch((err) => {
          console.log(err)
        })

        this.$router.push('/AI/demo')
      },
// // base64转文件
 
//       dataURLtoFile (dataurl, filename) {
//         var arr = dataurl.split(',')
//         var mime = arr[0].match(/:(.*?);/)[1]
//         var bstr = atob(arr[1])
//         var n = bstr.length
//         var u8arr = new Uint8Array(n)
//         while (n--) {
//           u8arr[n] = bstr.charCodeAt(n)
//         }
//         return new File([u8arr], filename, { type: mime })
//       },

// 关闭摄像头
 
      stopNavigator () {
        this.thisVideo.srcObject.getTracks()[0].stop()
      },
       UploadUrl() {
      // 因为action参数是必填项，我们使用二次确认进行文件上传时，直接填上传文件的url会因为没有参数导致api报404，所以这里将action设置为一个返回为空的方法就行，避免抛错
      return ''
      },
     uploadFile(e) {
      var taskname =  this.taskname 
      var username = 'jianz'
      var Author = 'jianz'
      console.log('Author ' + Author)
      const { file } = e
      try {
        const formData = new FormData()
        formData.append('uploadFile', file)
        console.log('---------上传文件---------')
        console.log(formData)
        // 对应的ajax请求不做赘述
        task_list_upload(Author, formData, 1, username, taskname).then(response => {
          // console.log(response.result.list)
          // this.list = response.data.items
          // this.list = response.result.list
           console.log(response.message)
          if (response.message === '成功!') {  
            alert("上传成功！！！") 
            this.flag = true
            }
        }).catch((err) => {
          console.log(err)
        })
        console.log('-------------------------')
      } catch(e) {
        this.$message.error(e.message)
      }
      
      },

      handleRemove(file, fileList) {
        console.log(file, fileList);
      },
      handlePreview(file) {
        console.log(file);
      },
      cancel2() {
      this.open2 = false
      this.reset()
    },
     reset() {
      this.adddata = {
        name: null,
        detail: null,
        time: null
      }
      // this.resetForm('form')
    },
     /** 上传按钮操作 */
    handleup() {
      this.fileList = []
      this.taskname = "show"
       console.log('taskname:' + this.taskname)
      // console.log('tid ids:' + this.tid)
      this.reset()
      this.open2 = true
    }
 },


  mounted() {
      this.getCompetence()
    },
  destroyed(){
    this.stopNavigator()
  }

  }
   
  </script>

  <style lang="less" scoped>
  .camera_outer{
    position: relative;
    overflow: hidden;
    background-size: 100%;
    video,canvas,.tx_img{
      -moz-transform:scaleX(-1);
      -webkit-transform:scaleX(-1);
      -o-transform:scaleX(-1);
      transform:scaleX(-1);
    }
    .btn_camera{
      position: absolute;
      bottom: 4px;
      left: 0;
      right: 0;
      height: 50px;
      background-color: rgba(0,0,0,0.3);
      line-height: 50px;
      text-align: center;
      color: #ffffff;
    }
    .bg_r_img{
      position: absolute;
      bottom: 0;
      left: 0;
      right: 0;
      top: 0;
    }
    .img_bg_camera{
      position: absolute;
      bottom: 0;
      left: 0;
      right: 0;
      top: 0;
      img{
        width: 100%;
        height: 100%;
      }
      .img_btn_camera{
        position: absolute;
        bottom: 0;
        left: 0;
        right: 0;
        height: 50px;
        line-height: 50px;
        text-align: center;
        background-color: rgba(0,0,0,0.3);
        color: #ffffff;
        .loding_img{
          width: 50px;
          height: 50px;
        }
      }
    }
  }
  </style>


<style scoped>
	.lightgreen-box {
	  background-color: lightgreen;
	  height: 24px;
	}
	.orange-box {
	  background-color: orange;
	  height: 24px;
	}
.el-row {
    margin-top: 80px;
    display: flex;
    flex-wrap: wrap
  }
.type1{
  margin-left: 1100px;
  margin-top: -1000px;
}
</style>
