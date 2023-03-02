<template>
  <div class="footer">
    <template>
        <div class="head">
            <router-link to="/mycenter/centerinfo"><el-avatar icon="el-icon-user-solid"></el-avatar></router-link>
        </div>
    </template>
    <div class="el">
        <el-progress :percentage="percentage"></el-progress>
        内存占用
    </div>
  </div>
</template>

<script>
import axios from "axios";
import UserInfo from '../views/UserInfo.vue'
export default {
    name:'Footer',
    data(){
        return {
            spaceSize: 0,
            usedSize: 0,
            percentage: 0
        }

    },
    created() {
          this.init()
        },
    methods:{
        async init () {
        axios.get("http://localhost/user/space").then((res) => {
        if (res.data.code == '1') {
            
            this.spaceSize=res.data.data.spaceSize;
            this.usedSize=res.data.data.usedSize;
            this.percentage=100*this.usedSize/this.spaceSize;
              } else {
                this.$message.error(res.msg || '操作失败')
              }
      });

    }
    },
    
    components:{UserInfo}
}

 
</script>

<style>
.footer{
    height: 9vh;
    /* border: 1px solid black; */
    width: 100%;
    position: absolute;
    bottom: 0;
    right: 0;
    background-color: aliceblue;
}
.head{
    position: absolute;
    bottom: 1vh;
    left: 1vw;
    font-size: 2vw;
}
.el{
    width: 70%;
    position: absolute;
    right: 1vw;
    bottom: 2vh;
    font-size: 3px;
}
</style>