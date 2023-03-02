<template>
<div class="head">
  <router-link to="/home/files"><img src="../assets/imgs/logo.png" alt="" style="display:block;width:90%;position:absolute;top:-5vh;left:-3vw"></router-link>
  <div class="i"> 
    <router-link to="/home/download"><i class="el-icon-download" style="color:rgba(122, 162, 237)"></i></router-link>
    <!-- <el-button icon="el-icon-upload2" circle></el-button> -->
  </div>
  <div class="i i2"> 
    <router-link to="/home/upload"><i class="el-icon-upload2" style="color:rgba(122, 162, 237)"></i></router-link>
    <!-- <el-button icon="el-icon-download" circle></el-button> -->
  </div>
</div>
</template>
<script>
import DownLoad from '../views/DownLoad'
import UpLoad from "../views/UpLoad";
export default {
  name: "Header",
  components:{UpLoad,DownLoad}
}
</script>
<style scoped>
.head{
  height: 20vh;
  /* border: 1px solid black; */
  width: 100%;
  /* z-index: 11111; */
  position: absolute;
  display: flex;
  top: 0;
  left: 0;

}
.i{
  position: absolute;
  right: 4vw;
  z-index: 100;
  bottom: 2vh;
  width: 15%;
  height: 21%;
  background-color: azure;
  border-radius: 50%;
  text-align: center;
  line-height: 21%;
  color: rgb(122, 162, 237);
}
.i2{
  bottom: 2vh;
  right: 1vw;
}
</style>
