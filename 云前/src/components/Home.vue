<template>
  <div class="exhibition">
    <div class="navigation">
      <Header/>
      <!-- <router-link to="/home/filemanagement" active-class="active" ><label><i class="fas fa-file"></i><span>文件</span> </label></router-link>
      <router-link to="/home/usermanagement" active-class="active"><i class="fa fa-user-circle"></i></router-link>
      <router-link to="/home/recyclebin" active-class="active"><i class="fa fa-recycle"></i></router-link> -->
      <el-menu
      class="el-menu-vertical-demo"
      @open="handleOpen"
      @close="handleClose"
      :default-active="$route.path"
      background-color="#545c64"
      text-color="#fff"
      active-text-color="#ffd04b">
      <el-submenu index="1">
        <template slot="title">
          <i class="el-icon-folder"></i>
          <span>我的文件</span>
        </template>
        <el-menu-item-group>
          <el-menu-item index="/home/files" @click="ToFiles"><i class="el-icon-document"></i> 文件</el-menu-item>
          <el-menu-item index="/home/photos" @click="ToPhotos"><i class="el-icon-picture-outline"></i> 图片</el-menu-item>
          <el-menu-item index="/home/audio" @click="ToAudio"><i class="el-icon-headset"></i> 音频</el-menu-item>
          <el-menu-item index="/home/videos" @click="ToVideos"><i class="el-icon-video-camera-solid"></i> 视频</el-menu-item>
          <el-menu-item index="/home/zipfile" @click="ToZip"><i class="el-icon-collection"></i> 压缩包</el-menu-item>
        </el-menu-item-group>
      </el-submenu>
      <el-menu-item index="/home/recentlybrowse" @click="Torecently">
        <i class="el-icon-view"></i>
        <span slot="title">最近浏览</span>
      </el-menu-item>
      <el-menu-item index="/home/recyclebin"  @click="ToBin">
        <i class="el-icon-delete"></i>
        <span slot="title">回收站</span>
      </el-menu-item>
    </el-menu>
    <Footer></Footer>
    </div>
    <div class="content">
      <router-view class="contaner"></router-view>
    </div>
  </div>
</template>

<script>

import Header from '../components/Header'
import Footer from '../components/Footer'
import UserManagement from "../views/UserManagement";
import RecycleBin from "../views/RecycleBin";
export default {
  name: "Home",
  data(){
    return{

    }
  },
  components: {UserManagement,RecycleBin,Footer,Header},
  methods:{
      handleOpen(key, keyPath) {
        console.log(key, keyPath);
      },
      handleClose(key, keyPath) {
        console.log(key, keyPath);
      },
      ToPhotos(){
        this.$router.push({path:'/home/photos'})
      },
      ToFiles(){
        this.$router.push({path:'/home/files'})
      },
      ToVideos(){
        this.$router.push({path:'/home/videos'})
      },
      ToAudio(){
        this.$router.push({path:'/home/audio'})
      },
      ToZip(){
        this.$router.push({path:'/home/zipfile'})
      },
      Torecently(){
        this.$router.push({path:'/home/recentlybrowse'})
      },
      ToBin(){
        this.$router.push({path:'/home/recyclebin'})
      },
      ToInfo(){
        this.$router.push({path:'/home/information'})
      },
  }
}
</script>

<style scoped>
.exhibition{
  margin: 0vw auto 0 auto;
  height: 100vh;
  display: flex;
  background-color: #efc4ff;
}
.navigation{
  flex: 1.5;
  background-color: rgb(146, 183, 211);
  box-sizing: border-box;
  padding-top: 2vh;
  padding-left: 0.5vw;
  padding-right: 0.5vw;
  position: relative;
  box-shadow: 0 10px 10px rgba(0,0,0,.5);
  z-index: 11;
}
.content{
  flex: 8.5;
  background-color: rgb(224, 236, 245);
  text-align: center;
  position: relative;
}
.contaner{
  display: flex;
}
.el-menu-vertical-demo{
  top: 20vh;
}
.item{
  line-height: 35px;
}
</style>
