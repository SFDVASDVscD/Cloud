<template>
  <div class="management">
    <div class="top">
      <router-link to="/managehome/management"><img src="../assets/imgs/logo.png" alt="" style="width:10%;position:absolute;left:3vw;top:1vh"></router-link>
      <el-button type="text" class="button" @click="Info">我的个人信息</el-button>
      <el-button  type="text" class="button1" @click="Main" >首页</el-button>

    </div>
    <div class="view">
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
export default {
  name:'ManageHome',
  methods:{
    Info(){
      this.$router.push({path:'/managehome/manageinfo'})
    },
    Main(){
      this.$router.push({path:'/managehome/management'})
    }
  }
}
</script>

<style scoped>
.management{
  /* background-color: rgb(155, 155, 211); */
  width: 100vw;
  height: 100vh;
  /* display: flex */
}
.top{
  position: relative;
}
.view{
  width: 90%;
  height: 75%;
  border: 1px solid saddlebrown;
  position: absolute;
  top:19vh;
  right: 0;
  left: 0;
  margin: auto;
}
.button{
  position: absolute;
  right: 18vw;
  top: 11vh;
}
.button1{
  position: absolute;
  right: 15vw;
  top: 11vh;
}
</style>