<template>
<div class="exhibition1">
  
  <div class="name" >
    <span>下载</span>
  </div>
  <div class="files">
      <el-table
      :data="tableData.filter(data => !search || data.filename.toLowerCase().includes(search.toLowerCase()))"
      style="width: 100%"
      height="620">
      <el-table-column
        fixed
        prop="filename"
        label="文件名"
        width="250">
      </el-table-column>
      <el-table-column
        prop="type"
        label="类型"
        width="220">
      </el-table-column>
      <el-table-column
        prop="size"
        label="大小"
        sortable
        width="220">
      </el-table-column>
      <el-table-column
        prop="process"
        label="进度"
        width="220">
      </el-table-column>
      <el-table-column
        
        width="280">
        <template slot="header"  slot-scope="{}">
        <el-input
          v-model="search"
          size="mini"
          placeholder="输入关键字搜索"/>
      </template>
        <el-button @click="stop" type="text" size="small" icon="el-icon-caret-right">暂停</el-button>
        <el-button @click="del" type="text" size="small" icon="el-icon-delete">删除</el-button>
      </el-table-column>
    </el-table>
  </div>
</div>
</template>

<script>

import axios from "axios";
export default {
  name:'DownLoad',
  data() {
    return {
              tableData: [{
          date: '2016-05-02',
          name: '王小虎',
          province: '上海',
          city: '普陀区',
          address: '上海市普陀区金沙江路 1518 弄',
          zip: 200333
        }, {
          date: '2016-05-04',
          name: '王小虎',
          province: '上海',
          city: '普陀区',
          address: '上海市普陀区金沙江路 1517 弄',
          zip: 200333
        }, {
          date: '2016-05-01',
          name: '王小虎',
          province: '上海',
          city: '普陀区',
          address: '上海市普陀区金沙江路 1519 弄',
          zip: 200333
        }, {
          date: '2016-05-03',
          name: '王小虎',
          province: '上海',
          city: '普陀区',
          address: '上海市普陀区金沙江路 1516 弄',
          zip: 200333
        }]
    }
  },
  methods:{
    // 文件上传失败
    loadFileError() {
      this.$message.error("文件上传失败！！！");
    },
    // 上传文件之前
    beforeUpload(file) {
      const extension = file.name.split(".")[1] === "txt"; // 是否以txt为后缀
      const isLt2M = file.size / 1024 / 1024 < 100;  // 文件大小是否小于100M
      if (!extension) {
        this.$message.error("上传文件的格式只能是.txt！！！");
      }
      if (!isLt2M) {
        this.$message.error("上传文件大小不能超过100MB！！！");
      }
      return extension && isLt2M;  // 若为false则停止上传
    },
    // 上传文件
    uploadFile(param){
      const File = param.file;
      let formDataInfo = new FormData();
      formDataInfo.append("file", File); // 将文件传向后端
      axios.post("/fileUpload",formDataInfo).then((res) => {
        if(res.data=="ok"){
          this.$message.success("文件上传成功！！！");
        }else {
          this.$message.error("文件上传失败！！！");
        }
      });
    },
    // 删除文件  如果beforeUpload函数返回值为false,会自动触发handleRemove
    handleRemove(file, fileList){
      if(file!=""){
        let data = new FormData();
        data.append("fileName",file.name); // 将文件名传向后端
        axios.post("/delete",data).then(res=>{
          if(res.data=="ok"){
            this.$message.success("删除成功！！！");
          }else {
            this.$message.error("删除失败！！！");
          }
        })
      }
    },
    // 文件超出个数限制
    handleExceed(){
      this.$message.warning("超出文件个数限制！！！");
    },
    stop(){
      alert(1)
    },
    del(){
      alert(1)
    }
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
.button{
  float: right;
  margin-top: 7vh;
  margin-right: 10vw;
}
.upload-demo{
  /* border: 1px solid rebeccapurple; */
  position: absolute;
  top: 7vh;
  right: 4vw;
}
.files{
  position: absolute;
  top: 15vh;
  width: 98%;
  left: 0;
  right: 0;
  margin-left: auto;
  margin-right: auto;
}
</style>