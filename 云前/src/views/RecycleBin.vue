<template>
<div class="exhibition1">
  
  <div class="name" >
    <span>回收站</span>
  </div>
  <div class="files">
      <el-table
      :data="tableData.filter(data => !search || data.filename.toLowerCase().includes(search.toLowerCase()))"
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
        prop="updateTime"
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
        <el-button @click="del(scope.row)" type="text" size="small" icon="el-icon-delete">删除</el-button>
        <el-button @click="recover(scope.row)" type="text" size="small" icon="el-icon-refresh-left">恢复</el-button>
      </template>
        
      </el-table-column>
    </el-table>
  </div>
</div>
</template>

<script>
import axios from "axios";
export default {
  name:'RecycleBin',
  data() {
    return {
      tableData: [
        
      ],
      search:"",
    }
  },
  created() {
          this.init()
        },
  methods:{
    async init () {
        axios.get("http://localhost/recylebin").then((res) => {
        if (res.data.code == '1') {
                this.tableData = res.data.data;
              } else {
                this.$message.error(res.msg || '操作失败')
              }
      });
    },
    recover(row){
      this.$confirm("此操作会将恢复该文件, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        // 确认删除
        .then(() => {
          //删除接口只需要传个id就行了 id是当前点击事件传过来的的id
        
          axios.post("http://localhost/recylebin",row).then((res) => {
        if (res.data.code == '1') {
                this.tableData = res.data.data;
                this.$message.success("文件恢复成功！！！");
              } else {
                this.$message.error(res.msg || '操作失败')
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
    del(row){
      this.$confirm("此操作会将该文件彻底删除, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        // 确认删除
        .then(() => {
          //删除接口只需要传个id就行了 id是当前点击事件传过来的的id
        
          axios.post("http://localhost/recylebin/delete",row).then((res) => {
        if (res.data.code == '1') {
                this.$message.success("文件删除成功！！！");
                this.tableData = res.data.data;
              } else {
                this.$message.error(res.msg || '操作失败')
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