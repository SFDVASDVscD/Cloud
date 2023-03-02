<template>
<div class="ablogin">
      <el-card class="ablogini" shadow="always">
        <p style="font-family: '微软雅黑';font-size: 18px;">登录</p>
        <div style="height: 36px">
          <p id="getPassword" style="display: none">
            <el-link type="danger" :underline="false">账号或密码错误，</el-link>
            <el-link :underline="false" @click="Repassword" type="primary">
              找回密码</el-link>
          </p>
        </div>
        <el-input placeholder="用户名" v-model="userName" clearable>
        </el-input>
        <el-input placeholder="密码" v-model="password" show-password></el-input>
        <template>
          <el-checkbox v-model="checked" style="margin-top: 20px;margin-bottom: 20px;">记住密码
          </el-checkbox>
        </template>
        <el-button @click="login()" :plain="true" style="width: 310px;margin-bottom: 20px;" type="primary">
          登录</el-button>
        <el-link :underline="false" @click="Register" type="info">无账号，点此注册。
        </el-link>
        <el-link :underline="false" @click="Repassword" type="info">忘记密码？
        </el-link>
        <br>
        <el-link :underline="false" @click="Manager" type="info">管理员登录
        </el-link>

      </el-card>
    </div>
</template>

<script>
import axios from "axios";
import $ from 'jquery'
export default {
    name:'SignIn',
    
    data() {
      return {
        userName: '',
        password: '',
        checked: false
      }
    },
    methods: {
      login() {
        // var _this = this;
        axios.post("http://localhost/user/login",{ userName: this.userName, password: this.password}).then(res=>{
          if (res.data.code === 0) {
            document.getElementById("getPassword").style.display='block';
          }
          if (res.data.code===1) {
            localStorage.setItem("userName",res.data.data.userName);
            localStorage.setItem("id",res.data.data.id);

            sessionStorage.setItem('user', JSON.stringify(res.data.data));
            this.$router.push({path:'/home/files'})
          }
        })
      },
      Manager(){
        this.$router.push({path:'/login/managelogin'})
      },
      Repassword(){
        this.$router.push({path:'/repassword'})
      },
      Register(){
        this.$router.push({path:'/login/register'})
      }
    },
}
</script>

<style scoped>
  .ablogini {
      position: fixed;
      z-index: 9;
      width: 350px;
      height: 370px;
      left: 70%;
      top: 200px;
      opacity: 0.9;
    }
</style>