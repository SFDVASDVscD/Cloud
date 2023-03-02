<template>
<div class="exhibition1">
  <el-row class="button">
    <el-button type="primary" @click="ChangeMsg">修改个人信息</el-button>
  </el-row>
  <el-descriptions title="个人信息" direction="vertical" :column="4" border class="container">
    <el-descriptions-item label="用户名"><input class="input" type="text" v-model="UserName" readonly="true"></el-descriptions-item>
    <el-descriptions-item label="手机号" ><input class="input" type="text" v-model="UserPhone" readonly="true"></el-descriptions-item>
    <el-descriptions-item label="ID" :span="2"  ><input class="input" type="text" v-model="UserID" readonly="true"></el-descriptions-item>
    <el-descriptions-item label="身份">
      <el-tag size="small">用户</el-tag>
    </el-descriptions-item>
    <el-descriptions-item label="邮箱地址" ><input class="input" type="text" v-model="UserEmail" readonly="true"></el-descriptions-item>
  </el-descriptions>
  <el-button type="text" style="color:red;text-decoration:underline ;dispaly:block;position:absolute;top:3vh;right:5vw" @click="QuitOut">注销</el-button>
</div>
</template>

<script>
export default {
  name: "UserInfo",
  data(){
    return{
      UserName:'1111',
      UserPhone:'18202832031',
      UserEmail:'1365712267@qq.com',
      UserID:'1365712267'
    }
  },
  methods:{
    ChangeMsg(){
      let inputs=document.getElementsByClassName("input")
      for (var i = 0; i < inputs.length; i++){
        // console.log(inputs[i])
        inputs[i].style.border="1px solid rgba(0,0,0,.3)"
        // inputs[i].style.outline="1px solid black"
        inputs[i].removeAttribute('readonly')
      }
    },
    //发送ajax请求修改数据
    QuitOut(){
      this.$alert('是否进行注销', '标题名称', {
        confirmButtonText: '确定',
        callback: action => {
          this.$message({
            type: 'info',
            message: `action: ${ action }`
          });
        }
      });   
    }
  },
}
</script>

<style scoped>
.exhibition1{
  width: 97%;
  height: 97%;
  border: 1px rgb(23, 79, 143) solid;
  position: absolute;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  margin: auto;
  line-height: 4vw;
}
.container{
  width: 90%;
  position: absolute;
  top: 16vh;
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
  top: 55vh;
}
</style>