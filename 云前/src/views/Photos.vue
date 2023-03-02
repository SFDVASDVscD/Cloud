<template>
<div class="exhibition1">
   <div class="name" >
    <span>图片</span>
    <div class="up">
      <el-container>
        <el-aside width="200px">
        </el-aside>
        <el-main>
        </el-main>
      </el-container>
    </div>
  </div>
  
    <div class="container">
      <div class="photo">
        <div class="demo-image__preview">
          <el-image 
          v-for="url in urls"
          :key="url"
          style="width: 200px; height: 150px;margin:15px;"
          :src="URL+url"
          :preview-src-list="srcList"
          fit="contain">
          </el-image>
        </div>
    </div>
  </div>

</div>
</template>

<script>
import axios from "axios";
export default {

  name:'Photos',
  data() {
    return {
      URL:"http://localhost/cloudfile/",
      fileList:[],
      urls:[],
      srcList: [],
    }
  },
  created() {
          this.init()
  },
  methods:{
    //获取文件信息
    async init () {
        axios.get("http://localhost/file?type=.bmp,.jpg,.jpeg,.png,.gif").then((res) => {
        if (res.data.code == '1') {
          console.log(res)
          this.fileList = res.data.data;
          console.log(this.fileList)
          for(let i=0;i<this.fileList.length;i++ ){
            let url=this.fileList[i].file.split("\\")[3]
            this.urls.push(url)
            this.srcList.push("http://localhost/cloudfile/"+url)
          }

          console.log(this.urls)
          console.log("srcList",this.srcList)
          // this.srcList = res.data.data;

              } else {
                this.$message.error(res.msg || '操作失败')
              }
      });
    },
  }
}
</script>

<style scoped>
.exhibition1{
  width: 97%;
  height: 97%;
  /* border: 1px saddlebrown solid; */
  position: absolute;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  margin: auto;
  overflow-x:hidden;
}
.upload-demo{
  width: 620px;
  height: 150px;
}
.btn{
  margin-left: 100px;
}
.name{
  height: 15vh;
  /* border: 1px slateblue solid; */
  /* line-height: 15vh; */
  font-size: 2vw;
  font-weight: 700;
  text-align: left;
  width: 100%;
  padding-left: 2vw;
  position: relative;
}
.name span{
  display: block;
  /* border: 1px springgreen solid; */
  margin-left: 1vw;
  position: absolute;
  top: 5vh;
}
.up{
  position: absolute;
  right: -25vw;
  top: 5vh;
  /* border: 1px solid saddlebrown; */
}
.container{
  width: 96%;
  height: 84%;
  border: 1px solid seagreen;
  position: absolute;
  top:15vh;
  text-align: left;
  /* background-color: rgb(98, 221, 221); */
}
</style>