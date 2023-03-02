<template>
<div class="exhibition1">
  <el-descriptions title="用户信息" direction="vertical" :column="4" border class="container">
    <el-descriptions-item label="用户名"><input class="input" type="text" v-model="userName" readonly="true"></el-descriptions-item>
    <el-descriptions-item label="手机号" ><input class="input" type="text" v-model="phone" readonly="true"></el-descriptions-item>
    <el-descriptions-item label="ID" :span="2"  ><input class="input" type="text" v-model="id" readonly="true"></el-descriptions-item>
    <el-descriptions-item label="身份">
      <el-tag size="small">用户</el-tag>
    </el-descriptions-item>
    <el-descriptions-item label="邮箱地址" ><input class="input" type="text" v-model="email" readonly="true"></el-descriptions-item>
    <el-descriptions-item label="用户密码" :span="2"  ><input class="input" type="text" v-model="password" readonly="true"></el-descriptions-item>
  </el-descriptions>
  <el-button type="text" style="text-decoration:underline ;dispaly:block;position:absolute;top:-9.2vh;right:5vw" @click="Back">返回</el-button>
</div>
</template>

<script>
import axios from "axios";
export default {
  name: "ManageUser",
  data(){
    return{
      userName:'',
      phone:'',
      email:'',
      id:'',
      password:''
    }
  },
  created(){
    this.id = this.$route.query.id;
    this.init();
  },
  methods:{
    //获取用户信息
      async init () {
        axios.get("http://localhost/manager/getuser?id="+this.id).then((res) => {
          if (res.data.code == '1') {
            this.userName=res.data.data.userName;
            this.phone=res.data.data.phone;
            this.email=res.data.data.email;
            this.id=res.data.data.id;
            this.password=res.data.data.password;

          } else {
            this.$message.error(res.msg || '操作失败')
          }
        });
      },
    ChangeMsg(){
      let inputs=document.getElementsByClassName("input")
      for (var i = 0; i < inputs.length; i++){
        // console.log(inputs[i])
        inputs[i].style.border="1px solid rgba(0,0,0,.3)"
        // inputs[i].style.outline="1px solid black"
        inputs[i].removeAttribute('readonly')
      }
    },
    Back(){
      this.$router.push({path:'/managehome/management'})
    }
  },
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
  width: 100%;
  position: absolute;
  top: 10vh;
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
  top: 50vh;
}
</style>