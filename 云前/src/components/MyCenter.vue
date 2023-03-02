<template>
  <div >
        <el-container>
            <!--头部-->
            <el-header>
                <el-row>
                    <el-col :span="12">
                        <div style="float: left;">
                            <img src="..\src\assets\imgs\logo.png" style="height: 60px; width: 75px" />
                        </div>
                        <el-link @click="Home"
                            style="pointer-events: none;font-size: 30px;font-family: 微软雅黑;font-weight: bold;float: left;margin-left: 10px;">
                            企业云盘</el-link>
                    </el-col>
                    <el-col :span="12">
                        <div>
                            <el-row>
                                <el-col :span="6">
                                    <el-link :underline="false"></el-link>
                                </el-col>
                                <el-col :span="6">
                                    <el-link :underline="false"></el-link>
                                </el-col>
                                <el-col :span="6">
                                    <el-link @click="Home" :underline="false">云盘首页</el-link>
                                </el-col>
                            </el-row>
                        </div>
                    </el-col>
                </el-row>
            </el-header>
            <el-card class="box-card" style="width: 1000px;margin: 30px auto;">
                <el-container>
                    <el-aside width="150px">
                        <el-menu default-active="1" class="el-menu-vertical-demo">
                            <a target="content" style="display: none;" @click="Center"><button
                                    id="getMyCenter">跳一下</button></a>
                            <a target="content" @click="Center">
                                <el-menu-item index="/mycenter/centerinfo">
                                    <i class="el-icon-s-custom"></i>
                                    <span slot="title">个人资料</span>
                                </el-menu-item>
                            </a>
                            <a target="content" @click="Change">
                                <el-menu-item index="/mycenter/centerchange">
                                    <i class="el-icon-lock"></i>
                                    <span slot="title">修改密码</span>
                                </el-menu-item>
                            </a>
                            <a target="content" @click="Email">
                                <el-menu-item index="/mycenter/centeremail">
                                    <i class="el-icon-receiving"></i>
                                    <span slot="title">邮箱换绑</span>
                                </el-menu-item>
                            </a>
                        </el-menu>
                    </el-aside>
                    <el-main class="content" style="width: 850px;height: 580px;">
                        <iframe frameborder="0" class="frame" id="frame" name="content">
                        </iframe>
                            <router-view class="down"></router-view>
                            <el-button type="text" @click="Logout" style="display:block;color:red;position:absolute;right:500px">注销</el-button>
                    </el-main>
                </el-container>
            </el-card>
        </el-container>
    </div>
</template>

<script>
import axios from "axios";
export default {
    name:'MyCenter',
    data() {
            return {
                dialogVisible: false,
            }
        },
        methods: {

            Home(){
                this.$router.push({path:'/home/files'})
            },
            Center(){
                this.$router.push({path:'/mycenter/centerinfo'})
            },
            Change(){
                this.$router.push({path:'/mycenter/centerchange'})
            },
            Email(){
                this.$router.push({path:'/mycenter/centeremail'})
            },
            Logout(){
                
                this.dialogVisible = true;
              this.$alert('是否进行注销', '退出登录', {
                confirmButtonText: '确定',
                // eslint-disable-next-line no-unused-vars
                callback: action => {
                  axios.post("http://localhost/user/logout").then((res) => {
                    if (res.data.code == '1') {
                      sessionStorage.clear();
                      this.$message({
                        type: 'info',
                        message: `action: ${ res.data.data }`

                      });
                      this.$router.push('/login/signin')
                    } else {
                      this.$message({
                        type: 'info',
                        message: `action: ${ res.data.msg }`
                      });
                    }
                  });

                }
              });

            },
            handleClose(done) { 
                this.$confirm('确认关闭？')
                .then(_ => {
                    done();
                })
                .catch(_ => {});
            },
        },
        created: function () {
           
        }
}
</script>

<style scoped>
        /*布局开始*/
        .el-header {
            color: #333;
            text-align: center;
            line-height: 60px;
        }

        .el-aside {
            color: #333;
            text-align: center;
            line-height: 200px;
        }

        .el-main {
            color: #333;
            text-align: center;
            line-height: 30px;
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
        a {
            text-decoration: none
        }

        #frame {
            width: 100%;
            height: 90%;
        }
        .down{
            position: absolute;
            top:720px
        }
    </style>