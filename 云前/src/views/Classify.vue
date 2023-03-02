<template>
  <div>
    
    <!--图片-->
    <div v-if="imgShow">
      <img :src="URL+str" :height="height" :width="width" align="center" controls="true" style="object-fit:contain;"/>
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
    <div v-else-if="videoShow">
      <video preload="auto" :height="height" :width="width" align="center" controls="true" >
        <source :src="URL+str" type="video/mp4" />
      </video>
    </div>
    <!--其他不能预览的-->
    <div v-else-if="otherShow">此类型文件暂时无法预览，请下载后进行查看</div>
  </div>
</template>
<script>
import Video from 'video.js'
import 'video.js/dist/video-js.css'
import axios from "axios";
export default {
  props: { file: {} },
  data() {
    return {
      URL:"http://localhost/cloudfile/",
      fileName: '',
      loadUrl: '',
      imgShow: false,
      docShow: false,
      excelShow: false,
      videoShow: false,
      //其他不能预览的
      otherShow: false,
      height: 450 + 'px',
      width: '100%',
      str:""
    };
  },
    created() {
        this.fileName = this.file.fileName;// 文件名称（包括后缀名）
        this.loadUrl = this.file.loadUrl;// 文件路径
        var str1=this.loadUrl.split("\\");
        this.str=str1[3]
        console.log(this)
        // alert(this.fileName+"......."+this.loadUrl);
        //.png, .jpeg, .jpg,
        // application/pdf, .txt, .xls, .xlsx, .doc, .docx,.ppt, .pptx,
        //.wps, .rtf,.pps, .ppsx,.mp4,.mp3,
        //.zip
        if (this.fileName.endsWith('png') || this.fileName.endsWith('jpg') || this.fileName.endsWith('jpeg')) {
        //图片
        this.imgShow = true;
        } else if (
        this.fileName.endsWith('docx') ||
        this.fileName.endsWith('doc') ||
        this.fileName.endsWith('pdf') ||
        this.fileName.endsWith('pptx') ||
        this.fileName.endsWith('ppt') ||
        this.fileName.endsWith('txt')
        ) {
        //doc
        this.docShow = true;
        } else if (this.fileName.endsWith('xlsx') || this.fileName.endsWith('xls')) {
        //excel
        this.excelShow = true;
        } else if (this.fileName.endsWith('mp4') || this.fileName.endsWith('mp3')) {
        this.videoShow = true;
        } else {
        this.otherShow = true;
        }
        this.init();
        
    },
    methods:{
      async init () {
        axios.post("http://localhost/file/browse",{file:this.loadUrl}).then((res) => {
        if (res.data.code == '1') {
                
              } else {
                this.$message.error(res.msg || '操作失败')
              }
      });
    }
    }
  
};
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
</style>
