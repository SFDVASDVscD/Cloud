<template>
  <div>
    <div id="app1">
  <el-container>
    <!--头部-->
    <el-header>
      <el-row>
        <el-col :span="12">
          <div style="float: left;">
            <img src="../assets/imgs/logo.jpg" style="height: 60px; width: 75px" />
          </div>
          <el-link style="pointer-events: none;font-size: 30px;font-family: 微软雅黑;font-weight: bold;float: left;margin-left: 10px;">
            企业云盘</el-link>
        </el-col>
        <el-col :span="12">
        </el-col>
      </el-row>
    </el-header>
    <el-main>
      <template>
        <el-carousel :interval="4000" type="card" height="450px">
          <el-carousel-item v-for="item in 3" :key="item">
            <!--<h3 class="medium">{{ item }}</h3>-->
          </el-carousel-item>
        </el-carousel>
      </template>
    </el-main>
    <div>
      <router-view></router-view>
    </div>
    
  </el-container>
</div>
  </div>
</template>

<script>
import axios from "axios";
// import Register from '../views/Register.vue'
// import SignIn from '../views/SignIn.vue'
export default {
  // components: { SignIn ,Register},
    name:'Login',
}
</script>


<style scoped>
    #app1 {
      position: relative;
    }

    /*布局开始*/
    .el-header,
    .el-footer {
      color: #333;
      text-align: center;
      line-height: 60px;
    }

    .el-aside {
      background-color: #D3DCE6;
      color: #333;
      text-align: center;
      line-height: 200px;
    }

    .el-main {
      background-color: #E9EEF3;
      color: #333;
      text-align: center;
      line-height: 90px;
    }

    body>.el-container {
      margin-bottom: 40px;
    }

    .el-container:nth-child(5) .el-aside,
    .el-container:nth-child(6) .el-aside {
      line-height: 260px;
    }

    .el-container:nth-child(7) .el-aside {
      line-height: 320px;
    }

    /*布局结束*/
    /*走马灯开始*/
    .el-carousel__item h3 {
      color: #475669;
      font-size: 14px;
      opacity: 0.75;
      line-height: 200px;
      margin: 0;
    }

    .el-carousel__item:nth-child(n) {
      background-color: #99a9bf;
      background-image: url(../assets/imgs/yp1.jpg);
      background-size: 100% 100%;
    }

    .el-carousel__item:nth-child(2n) {
      background-color: #d3dce6;
      background-image: url(../assets/imgs/yp2.jpg);
      background-size: 100% 100%;
    }

    .el-carousel__item:nth-child(3n) {
      background-color: #d3dce6;
      background-image: url(../assets/imgs/yp3.jpg);
      background-size: 100% 100%;
    }

    /*走马灯结束*/
  </style>