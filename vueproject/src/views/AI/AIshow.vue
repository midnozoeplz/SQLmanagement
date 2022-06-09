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
      <video style="margin-top: -300px" id="videoCamera" ref="video" :width="videoWidth" :height="videoHeight"
       class="mt20"
       autoplay ></video>
      <canvas ref="canvas" width="500" height="376" v-if="isImg" ></canvas>
      <!-- <img id="img" :src="this.datas" width="500" height="376" v-if="notImg">  -->
      <canvas id="canvas2" width="500" height="376" v-show="notImg" ></canvas>
      </div>
    <div>
      <el-button  @click="callCamera()" type="success" >打开摄像头</el-button>
      <el-button  @click="closeCamera()" type="warning">关闭摄像头</el-button>
        <div style="margin-left: 800px">
　　　 <el-button  @click="setImage()" type="primary">开始运行</el-button>
      
      </div>
    </div>      
    </div>
    </el-col>
    <el-col :span='4'>
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
        isCameraFlag: true,
        base64: "",
        acc: "",
        str1: "",
        isImg: false,
        notImg: false,
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
        draw: false,
        datas:[],
        websock: null,
        reconnectData:null,
        lockReconnect:false,    //避免重复连接，因为onerror之后会立即触发 onclose
        timeout:10000,          //10s一次心跳检测
        timeoutObj:null,
        serverTimeoutObj:null
      }
    },
    created(){
         this.initWebSocket();
    },
      // 循环控制器
  mounted() {
    
    // this.timer = setInterval(this.photograph, 1000);
    this.timer = setInterval(() => {
      // 要执行的函数
      // console.log(this.isImg)
    if(this.flag){
    if (this.isImg) {
        this.photograph();

        let img = new Image();
        this.datas = "data:image/jpeg;base64," + this.datas;
        img.src = this.datas;
        console.log(this.datas)
        let myCanvas = document.getElementById("canvas2").getContext("2d");
        img.onload = () => {
          myCanvas.drawImage(img,0,0);
        }
        // this.$refs.img.setAttribute('src',this.datas)
        // document.getElementById('img').setAttribute('src',this.datas)
      }
    }
    }, 60);
    
    
  },

    destroyed() {
      // 页面销毁时关闭长连接
      this.websock.close()
    },
    methods: {
       // 调用摄像头
    callCamera() {
      this.isImg = true
      this.isCamera = true;
      // console.log(this.isCamera);
      // H5调用电脑摄像头API
      navigator.mediaDevices
        .getUserMedia({
          audio: true, //开启音频
          video: true, //开启视频
        })
        .then((success) => {
          var videoTracks = success.getVideoTracks();
          this.$refs["video"].srcObject = success;
          this.isCameraFlag = false;
          // 实时拍照效果
          this.$refs["video"].play();
        })
        .catch((error) => {
          console.error("摄像头开启失败，请检查摄像头是否可用！");
          this.isCameraFlag = true;
        });
    },

// 关闭摄像头
 
      stopNavigator () {
        this.thisVideo.srcObject.getTracks()[0].stop()
      },
        // 拍照
    async photograph() {
      let ctx = this.$refs["canvas"].getContext("2d");
       ctx.drawImage(this.$refs["video"], 0, 0, 500, 376);
      let imgBase64 = this.$refs["canvas"].toDataURL("image/jpeg", 0.7);
      let str = imgBase64.replace("data:image/jpeg;base64,", "");
      this.base64 = str;
      const { data: res } = await this.$http.post("/img", this.base64);
        
        // console.log(this.datas)
        this.notImg = true;
      this.acc = res.acc;
    },
     // 关闭摄像头
    closeCamera() {
      this.isImg=false
      this.notImg=false
      if (!this.$refs["video"].srcObject) return;
      let stream = this.$refs["video"].srcObject;
      let tracks = stream.getTracks();
      tracks.forEach((track) => {
        track.stop();
      });
      this.$refs["video"].srcObject = null;
      this.isCameraFlag = true;
    },
    setImage () {
        var traffic = this.traffic
        var whether = this.whether
        var dense = this.dense
        var clime = this.clime
        var picImprove = this.picImprove
        var whImprove = this.whImprove
        var draw = this.draw
       getObject(traffic,whether,dense,clime,picImprove,whImprove,draw).then(
         response => {
            // console.log(dense)
          if (response.message === '成功!') {  
            }
        }).catch((err) => {
          console.log(err)
        })
        this.flag = true
        console.log(this.flag)
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
    },
    // websocket
    initWebSocket(){
          console.log('启动中')
           //连接WebSocket节点 
          this.websock = new WebSocket("ws://127.0.0.1:8080/productWebSocket/001")
          this.websock.onopen = this.websocketOnopen;
          this.websock.onerror = this.websocketOnerror;
          this.websock.onmessage = this.websocketOnmessage;
          this.websock.onclose = this.websocketOnclose;
    },
         // 客户端和服务端建立链接时触发，此时可向服务端传递参数
    websocketOnopen(){
         console.log("连接成功")
        let actions = {"test":"12345"};
        this.websocketsend(JSON.stringify(actions)); //传递参数
    },
     // 客户端收到服务端发来的消息时，会触发onmessage事件，参数res.data中包含server传输过来的数据
    websocketOnmessage(res){
        //接收到消息的回调方法，我这里是处理返回数据
          console.log(res.data)
            var maparr = res.data
            this.datas=maparr
    
        //  console.log(this.arrisrun)
        
    },
     //如果出现连接，处理，接收，发送数据失败的时候就会触发onerror事件
    websocketOnerror(){
      console.log('连接失败')
    },
    websocketsend(Data){//数据发送
          this.websock.send(Data);

      },
    // //客户端收到服务端发送的关闭连接的请求时，触发onclose事件
     websocketOnclose() {
     //   this.websock.close()
        console.log('关闭连接')
     }
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
