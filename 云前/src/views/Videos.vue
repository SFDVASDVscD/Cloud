<template>
<div class="exhibition1">
  
  <div class="name" >
    <span>视频</span>
    
    <el-container class="btn">
      <!-- <el-aside width="200px"> -->
      <!-- </el-aside> -->
      <el-main>
        
      </el-main>
    </el-container>
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
        width="350">
      </el-table-column>
      <el-table-column
        prop="createTime"
        label="日期"
        sortable
        width="350">
      </el-table-column>
      <el-table-column
        prop="fileSize"
        label="大小"
        sortable
        width="0">
      </el-table-column>
      <el-table-column
        
        width="350">
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
  <el-dialog
    :visible.sync="videoVisible"
    width="65%"
    title="视频内容"
    @close="closeDialog"
  >
    <div style="height:500px;text-align:center">
      <!--需要弹出的内容部分-->
      <video
        width="100%"
        height="100%"
        :src="URL+VideoUrl"
        controls="controls"
        ref="video"
      ></video>
    </div>
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

import axios from "axios";
export default {
  name:'Videos',
  data() {
    return {
      URL:"http://localhost/cloudfile/",
      tableData: [],
      VideoUrl:"",
      //默认false 弹框隐藏状态 
      dialogVisible: false,   
       videoVisible: false,
       search:"",
    }
  },
  created() {
          this.init()
  },
  methods:{
    async init () {
        axios.get("http://localhost/file?type=.mp4,.avi,.wmv,.rm,.mv").then((res) => {
        if (res.data.code == '1') {
                this.tableData = res.data.data;
              } else {
                this.$message.error(res.msg || '操作失败')
              }
      });
    },
    LookOver(row){//点击按钮时,触发弹出效果
        //触发点击时,让其显示
        this.videoVisible = true
      var str=row.file.split("\\")
      this.VideoUrl=str[3];
    },
    //事件操作 (表格、表单 同理 需要执行的事件 在此处)
    closeDialog() {
      //点击播放视频
      this.$refs.video.pause()
    },
    Del(row){
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
</style>