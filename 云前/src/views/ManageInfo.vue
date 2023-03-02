<template>
<div class="exhibition1">
  <el-row class="button">
    <el-button type="primary" plain @click="update">修改信息</el-button>
  </el-row>
  <el-descriptions title="个人信息" direction="vertical" :column="4" border class="container">
    <el-descriptions-item label="管理员姓名"><input type="text" v-model="managerName" readonly="true"></el-descriptions-item>
    <el-descriptions-item label="手机号" ><input type="text" v-model="phone" readonly="true"></el-descriptions-item>
    <el-descriptions-item label="管理员工号" :span="2"  ><input type="text" v-model="id" readonly="true"></el-descriptions-item>
    <el-descriptions-item label="身份">
      <el-tag size="small">管理员</el-tag>
    </el-descriptions-item>
    <el-descriptions-item label="邮箱地址" ><input type="text" v-model="email" readonly="true"></el-descriptions-item>
  </el-descriptions>
  <el-button type="text" style="color:red;text-decoration:underline ;dispaly:block;position:absolute;top:3vh;right:5vw" @click="QuitOut">注销</el-button>
  <el-dialog title="编辑资料" width="300px" :visible.sync="dialogDataVisible">
    <form>
      管理员用户名：<el-input style="margin-bottom: 16px;" v-model="managerName" readonly="readonly" clearable></el-input>
      管理员  邮箱：<el-input style="margin-bottom: 16px;" v-model="email" clearable readonly="readonly"></el-input>
      联 系 电 话 ：<el-input  v-model="phone" clearable></el-input>

    </form>
    <div slot="footer" class="dialog-footer" >
      <el-button @click="dialogDataVisible = false">取 消</el-button>
      <el-button type="primary" :plain="true" @click="submitData()">确 定</el-button>
    </div>
  </el-dialog>  
</div>

</template>


<script>
import axios from "axios";

export default {
  name: "ManageInfo",
  data(){
    return{
      manager:JSON.parse(sessionStorage.getItem('manager')),
      managerName:JSON.parse(sessionStorage.getItem('manager')).managerName,
      phone:JSON.parse(sessionStorage.getItem('manager')).phone,
      email:JSON.parse(sessionStorage.getItem('manager')).email,
      id:JSON.parse(sessionStorage.getItem('manager')).id,
      dialogDataVisible:false
    }
  },
  methods:{
    QuitOut(){
    this.$alert('是否进行注销', '退出登录', {
      confirmButtonText: '确定',
      callback: action => {
        axios.put("http://localhost/manager/logout").then((res) => {
          if (res.data.code == '1') {
            sessionStorage.clear();
            this.$message({
              type: 'info',
              message: `action: ${ res.data.data }`

            });
            this.$router.push('/login/managelogin')
          } else {
            this.$message({
              type: 'info',
              message: `action: ${ res.data.data }`
            });
          }
        });

      }
    });   
  },
    update(){this.dialogDataVisible=true},
    submitData() {
      var _this = this;
      if (this.phone.length!=11) {
        _this.$message({
          showClose: true,
          message: "请输入正确的联系电话",
          type: "warning"
        });
        return;
      }
      this.$.ajax({
        url: "http://localhost/manager/updatemanager",
        type: "post",
        dataType: "JSON",
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify(
            {managerName:_this.managerName,email:_this.email, phone:_this.phone}
        ),
        success: function (data) {
          if (data.code==1) {
            sessionStorage.setItem('manager', JSON.stringify(data.data));
            window.setTimeout(function () {
              location.reload();
            }, 1000)
          } else {
            console.log("修改失败！");
          }

        }
      });
    }
  }
}
</script>

<style scoped>
.exhibition1{
  width: 97%;
  height: 97%;
  /* border: 1px rgb(23, 79, 143) solid; */
  position: absolute;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  margin: auto;
  line-height: 4vw;
}
.container{
  /* width: 90%; */
  position: absolute;
  top: 7vh;
  text-align: center;
  left: 0;
  right: 0;
  margin-left:auto ;
  margin-right:auto;
}
.container input{
  border: 0px;
  outline:none;
}
.button{
  position: absolute;
  right: 10vw;
  top: 45vh;
}
form{
  line-height: 20px;
}
</style>