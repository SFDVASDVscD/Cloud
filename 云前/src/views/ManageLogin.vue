<template>
  <div class="ablogin">
      <el-card class="ablogini" shadow="always">
        <p style="font-family: '微软雅黑';font-size: 18px;">管理员登录</p>
        <div style="height: 36px">
          <p id="getPassword" style="display: none">
            <el-link type="danger" :underline="false">账号或密码错误，请重新登陆</el-link>
          </p>
        </div>
        <el-input placeholder="用户名" v-model="managerName" clearable>
        </el-input>
        <el-input placeholder="密码" v-model="password" show-password></el-input>
        <el-button @click="login()" :plain="true" style="width: 310px;margin-bottom: 20px;" type="primary">
          登录</el-button>
        <el-link :underline="false" @click="User" type="info">用户登录
        </el-link>

      </el-card>
    </div>
</template>

<script>
import axios from "axios";
import $ from 'jquery'
export default {
	name:'ManageLogin',
	data() {
      return {
        managerName: '',
        password: '',
      }
    },
	methods:{
	  login() {
        // var _this = this;
        axios.post("http://localhost/manager/login",{ managerName: this.managerName, password: this.password}).then(res=>{
          if (res.data.code === 0) {
            document.getElementById("getPassword").style.display='block';
          }
          if (res.data.code===1) {
            localStorage.setItem("managerName",res.data.data.userName);
            localStorage.setItem("id",res.data.data.id);

            sessionStorage.setItem('manager', JSON.stringify(res.data.data));
            this.$router.push('/managehome/management')
          }
        })
      },
	  User(){
		this.$router.push({path:'/login/signin'})
	  }
	}
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