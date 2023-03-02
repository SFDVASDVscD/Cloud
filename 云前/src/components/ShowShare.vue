<template>
<div class="out">
    <div class="content">
    <!--图片-->
    <div v-if="imgShow" class="midddle">
      <img :src="URL+str" style="border:5px solid black"/>
    </div>
    <!--doc，excel-->
    <div v-else-if="docShow || excelShow">
      <iframe
        class="child"
        frameborder="0"
        :src="URL+str"
        :style="{ width: width, height: height }"
      >
      </iframe>
    </div>
    <!--视频-->
    <div v-else-if="videoShow" class="midddle">
      <video preload="auto" :height="height" :width="width" align="center" controls="true">
        <source :src="URL+str" type="video/mp4" />
      </video>
    </div>
    <!--其他不能预览的-->
    <div v-else-if="otherShow" class="midddle"> <i class="el-icon-collection"></i> 该类型不支持在线预览，请进行下载</div>
    <el-button  circle style=" position:absolute; top:0;right:-20px" @click="Download"><i class="fa fa-download"></i> </el-button>
  </div>
</div>
</template>

<script>
import axios from "axios";
import Video from 'video.js';
export default {
    name:'ShowShare',
    data(){
        return{
        URL:"http://localhost/cloudfile/",
        str:"",
        // url
        file:"",
 
        // id
        fileId:"",
        // name
        fileName:"",
        imgShow: false,
        docShow: false,
        excelShow: false,
        videoShow: false,
        //其他不能预览的
        otherShow: false,
        height: window.innerHeight + 'px',
        width: '100%'
        }
    },
    created() {
        
        // this.fileName = this.file.fileName;// 文件名称（包括后缀名）
        // this.downloadUrl = this.file.file;// 文件路径
        //.png, .jpeg, .jpg,
        // application/pdf, .txt, .xls, .xlsx, .doc, .docx,.ppt, .pptx,
        //.wps, .rtf,.pps, .ppsx,.mp4,.mp3,
        //.zip
        this.fileId = this.$route.query.id;
        // console.log(this.fileId)
        this.File()
    },
    methods:{
      init () {
        
        return axios.post("http://localhost/fileshare/getfile",{fileId:this.fileId}).then((res) => {
          // console.log(res)
          if (res.data.code == "1") {
                  this.file=res.data.data;
                  // console.log("aa",this.file)
                  
          } else {
                  this.$message.error("文件已过期");
          }
        });
      },
     async File(){
       let re= await this.init();
       console.log(re)

                // alert("111",this.file);
        console.log("file",this.file)
        var str1=this.file.split("\\");
                this.str=str1[3]
                // alert(this.str);
                if (this.file.endsWith('png') || this.file.endsWith('jpg') || this.file.endsWith('jpeg')) {
                //图片
                this.imgShow = true;
                } else if (
                this.file.endsWith('docx') ||
                this.file.endsWith('doc') ||
                this.file.endsWith('pdf') ||
                this.file.endsWith('pptx') ||
                this.file.endsWith('ppt') ||
                this.file.endsWith('txt')
                ) {
                //doc
                this.docShow = true;
                } else if (this.file.endsWith('xlsx') || this.file.endsWith('xls')) {
                //excel
                this.excelShow = true;
                } else if (this.file.endsWith('mp4') || this.file.endsWith('mp3')) {
                this.videoShow = true;
                } else {
                this.otherShow = true;
                }
      },
      Download(){
        
        axios.post("http://localhost/fileshare/download",{id:this.fileId}).then((res) => {
          if (res.data.code == '1') {
            // console.log('111')
                  this.file = res.data.data;
                  this.$message.success("文件下载成功！！！");
                } else {
                  this.$message.error(res.msg || '操作失败')
                }
        });
      },
    },
}
</script>

<style scoped>
.child {
  width: 100%;
  height: 100%;
  /* scrolling: no;
  frameborder: 0; */
  border: 0;
  /* marginwidth: 0;
  marginheight: 0; */
}
.out{
    border: 5px solid #5bb7d6;
    background: linear-gradient(45deg,#ffffff 25%,rgb(154, 204, 239) 25%,rgb(154, 204, 239) 50%,#ffffff 50%,#ffffff 75%,rgb(154, 204, 239) 75%,rgb(154, 204, 239) 100%);
    background-size: 70px 70px;
    border-radius: 10px;
    width: 950px;
    display: flex;
    text-align: center;
    position: relative;
    margin-left: auto;
    margin-right: auto;
    top: 1vh;
    left: 0;
    right: 0;
    min-height: 95vh;
    padding-bottom: 2vh;
}
.content{
    border: 8px solid rgb(91, 183, 214);
    background-color: aliceblue;
    box-shadow: 3px 3px 0.4em rgba(0,0,0,.3), -5px -2px 0.4em rgba(0,0,0,.3);
    border-radius: 10px;
    width: 900px;
    display: flex;
    text-align: center;
    position: relative;
    margin-left: auto;
    margin-right: auto;
    top: 1vh;
    left: 0;
    right: 0;
    min-height: 50vh;
}
.middle{
    width: 500px;
    height:70vh ;
    border: 3px seagreen solid;
    position: absolute;
    top:0;
    bottom: 0;
    left: 0;
    right:0;
    margin: auto;
}
</style>