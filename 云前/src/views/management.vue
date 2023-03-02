<template>
  <div>
    <el-table
    :data="tableData.filter(data => !search || data.userName.toLowerCase().includes(search.toLowerCase()))"
    height="565"
    border
    style="width: 100%">
    <el-table-column
      prop="id"
      label="用户ID"
      width="300">
    </el-table-column>
    <el-table-column
      prop="userName"
      label="用户姓名"
      width="280">
    </el-table-column>
    <el-table-column
      prop="phone"
      label="用户电话" width="300">
    </el-table-column>
    <el-table-column
      prop="email"
      label="用户邮箱" width="300">
    </el-table-column>
    <el-table-column
      width="0">
      <template slot="header"  slot-scope="{}">
        <el-input
          v-model="search"
          size="mini"
          placeholder="输入关键字搜索"/>
      </template>
      <template slot-scope="scope"> 
        <el-button @click="LookOver(scope.row)" type="text" size="small" icon="el-icon-view">查看</el-button>
        <el-button @click="Del(scope.row)" type="text" size="small" icon="el-icon-delete">删除</el-button>
      </template>
      
    </el-table-column>
  </el-table>
  
  <el-dialog
  :visible.sync="videoVisible"
  width="30%"
  title="请选择您的操作"
  @close="closeDialog"
>
  <div style="height:100px;text-align:center;">
    <!--需要弹出的内容部分-->
    <router-link :to="{path:'/managehome/manageuser',query:{id,id}}">用户信息管理</router-link><br><br>
    <router-link :to="{path:'/managehome/managefile',query:{id,id}}">用户文件管理</router-link>
    <div
      width="100%"
      height="100%"
      ref="video"
    ></div>
  </div>
</el-dialog>

  </div>
</template>

<script>
import axios from "axios";

export default {
    name:'Management',
    data() {
      return {
        tableData: [ 
        ],
        id:"",
        search:"",
        //默认false 弹框隐藏状态    
        videoVisible: false,
      }
    },
  created() {
    this.init()
  },
    methods:{
      //获取文件信息
      async init () {
        axios.get("http://localhost/manager").then((res) => {
          if (res.data.code == '1') {
            this.tableData = res.data.data;

          } else {
            this.$message.error(res.msg || '操作失败')
          }
        });
      },
        Del(row){
        this.$confirm("此操作会将该用户删除, 是否继续?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        })
            // 确认删除
            .then(() => {
              //删除接口只需要传个id就行了 id是当前点击事件传过来的的id

              axios.post("http://localhost/manager/deleteuser",row).then((res) => {
                if (res.data.code == "1") {
                  this.$message.success("用户删除成功！！！");

                } else {
                  this.$message.error("用户删除失败！！！");
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
        LookOver(row){
            //触发点击时,让其显示
            this.videoVisible = true,
            this.id=row.id;
        },
        //事件操作 (表格、表单 同理 需要执行的事件 在此处)
        closeDialog() {
          //点击播放视频
          this.$refs.video.pause()
        },
    }
}
</script>

<style>
.q{
  border-radius: 10px;
}
</style>