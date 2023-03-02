<template>
<div class="exhibition1">
  
  <div class="name" >
    <span>用户文件</span>
      <el-button type="text" style="text-decoration:underline ;dispaly:block;position:absolute;top:-9.2vh;right:5vw" @click="Back">返回</el-button>
  </div>
  <div class="files">
      <el-table
      :data="tableData.filter(data => !search || data.fileName.toLowerCase().includes(search.toLowerCase()))"
      style="width: 100%"
      height="440">
      <el-table-column
        fixed
        prop="fileName"
        label="文件名"
        sortable
        width="280">
      </el-table-column>
      <el-table-column
        prop="fileType"
        label="类型"
        width="200">
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
        width="250">
      </el-table-column>
      <el-table-column
        
        
        width="280" >
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
      </template>
      </el-table-column>
    </el-table>
  </div>
    <!-- 引用组件 -->
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
</div>
</template>

<script>
import Classify from './Classify'
import axios from "axios";
export default {
  name: "ManageFile",
  data() {
    return {
      id:"",
      fileList:[],
      tableData: [
        
      ],
      search:"",
      dialogVisible: false,
      previewFile:{
        loadUrl:"",
        fileName:""
      }, // 传递的参数 
      previewFileShow:false, // 默认预览框是关闭状态
    };
  },
  components:{Classify},
  created(){
    this.id = this.$route.query.id;
    this.init();
  },
  methods: {
    //获取文件信息
    async init () {
        axios.get("http://localhost/manager/getuserfile?id="+this.id).then((res) => {
        if (res.data.code == '1') {
                this.tableData = res.data.data;
                
              } else {
                this.$message.error(res.msg || '操作失败')
              }
      });
    },
    Back(){
        this.$router.push({path:'/managehome/management'})
    },
    Del(row){
      this.$confirm("此操作会将该用户文件彻底删除, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        // 确认删除
        .then(() => {
          //删除接口只需要传个id就行了 id是当前点击事件传过来的的id
        
          axios.post("http://localhost/file/manager/delete",row).then((res) => {
        if (res.data.code == "1") {
          this.$message.success("文件删除成功！！！");
        } else {
          this.$message.error("文件删除失败！！！");
        }
        window.location.reload()
      });r
          
        })
        //取消删除
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除",
          });
        });
    },
    handleClose(done) {
        this.$confirm('确认关闭？')
          .then(_ => {
            done();
          })
          .catch(_ => {});
      },
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
  },
};
</script>

<style scoped>
.exhibition1 {
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
.name {
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
.name span {
  display: block;
  /* border: 1px springgreen solid; */
  margin-left: 1vw;
  position: absolute;
  top: 5vh;
}
.button {
  float: right;
  margin-top: 7vh;
  margin-right: 12vw;
}
.upload-demo {
  /* border: 1px solid rebeccapurple; */
  position: absolute;
  top: 7vh;
  right: 4vw;
}
.files {
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