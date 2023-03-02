<template>
<div class="exhibition1">
  
  <div class="name" >
    <span>最近浏览</span>
  </div>
  <div class="files">
      <el-table
      :data="tableData.filter(data => !search || data.fileName.toLowerCase().includes(search.toLowerCase()))"
      style="width: 100%"
      height="620">
      <el-table-column
        fixed
        prop="fileName"
        label="文件名"
        width="300">
      </el-table-column>
      <el-table-column
        prop="fileType"
        label="类型"
        width="250">
      </el-table-column>
      <el-table-column
        prop="createTime"
        label="日期"
        sortable
        width="250">
      </el-table-column>
      <el-table-column
        prop="fileSize"
        label="大小"
        sortable
        width="0">
      </el-table-column>
      <el-table-column
        
        width="300">
        <template slot="header"  slot-scope="{}">
        <el-input
          v-model="search"
          size="mini"
          placeholder="输入关键字搜索"/>
      </template>
        <template slot-scope="scope">
        <el-button @click="LookOver(scope.row)" type="text" size="small" icon="el-icon-view">查看</el-button>
        <!-- <el-popconfirm  title="这是一段内容确定删除吗？"> -->
        <el-button @click="Del(scope.row)" type="text" size="small" icon="el-icon-delete">删除</el-button>
        <!-- </el-popconfirm> -->
        <el-button @click="Down(scope.row)" type="text" size="small" icon="el-icon-bottom">下载</el-button>
        <el-button @click="Share(scope.row)" type="text" size="small" icon="el-icon-share">分享</el-button>
      </template>
      </el-table-column>
    </el-table>
  </div>
  <el-dialog title="预览" 
        :visible.sync="previewFileShow" 
        idth="80%" 
        :modal-append-to-body='false'
        :close-on-press-escape='false'
        :close-on-click-modal='false'
        :before-close="handleClose" 
        style="height:auto">
        <Classify :file="previewFile" v-if="previewFileShow" ></Classify>
    </el-dialog>
    <el-dialog
      title="分享详情"
      :visible.sync="dialogVisible"
      width="30%"
      :before-close="handleClose">
      <span>这是您的分享链接：{{shareurl}}</span><br>
      <span>有效期：3天</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
      </span>
    </el-dialog> 
</div>

</template>

<script>
import Classify from "../views/Classify"
import axios from "axios";
export default {
  name:'RecentlyBrowse',
  components:{Classify},
  data() {
    return {
      search:"",
      tableData: [
        
      ],
      shareurl:"",
      fileurl:"",
      search:"",
      dialogVisible: false,
      previewFile:{
        loadUrl:"",
        fileName:""
      }, // 传递的参数 
      previewFileShow:false, // 默认预览框是关闭状态
    }
  },
  created(){
    this.init();
  },
  methods:{
    //获取文件信息
    async init () {
        axios.get("http://localhost/file/recentbrowse").then((res) => {
        if (res.data.code == '1') {
                this.tableData = res.data.data;
                
              } else {
                this.$message.error(res.msg || '操作失败')
              }
      });
    },
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
    Del(row) {
      this.$confirm("此操作会将该文件移至回收站, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        // 确认删除
        .then(() => {
          //删除接口只需要传个id就行了 id是当前点击事件传过来的的id
        
          axios.post("http://localhost/file/delete",row).then((res) => {
        if (res.data.code == "1") {
          this.$message.success("文件删除成功！！！");
        } else {
          this.$message.error("文件删除失败！！！");
        }
        window.location.reload()
      });
          
        })
        //取消删除
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除",
          });
        });
    },
    // <!-- 关闭组件-->
    handleClose(){
      this.previewFileShow = false 
    },
    // <!-- 点击预览触发的事件-->
    // preview(row){
    //   },   
    LookOver(row){
      this.previewFileShow = true
      console.log(row.fileName)
      this.previewFileShow = true 
      this.previewFile.fileName = row.fileName
      this.previewFile.loadUrl = row.file
      console.log(this.previewFile.loadUrl)
      axios.post("http://localhost/file/browse",row).then((res) => {
        if (res.data.code == '1') {
                
              } else {
                this.$message.error(res.msg || '操作失败')
              }
      });
    },
    Down(row){
      axios.post("http://localhost/file/download",row).then((res) => {
        if (res.data.code == '1') {
                this.tableData = res.data.data;
                this.$message.success("文件下载成功！！！");
              } else {
                this.$message.error(res.msg || '操作失败')
              }
      });
    },
    Share(row){
      axios.post("http://localhost/fileshare/share",row).then((res) => {
        if (res.data.code == '1') {
                this.shareurl = res.data.data;
                
              } else {
                this.$message.error(res.msg || '操作失败')
              }
      });
      this.dialogVisible=true;

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
.search{
  position: absolute;
  right: 22vw;
  border: 1px solid saddlebrown;
  text-align: center;
  font-size: 1vw;
  background-color: white;
  border-radius: 10px;
  padding: 5px;
  display: flex;
  top:8.5vh;
}
.search input{
  border: none;
  font-size: 1vw;
  outline: none;
}
.search button{
  border: none;
  font-size: 1vw;
  background-color: white;
}
.search button:hover{
  cursor:pointer
}
</style>