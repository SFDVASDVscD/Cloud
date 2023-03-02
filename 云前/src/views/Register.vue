<template>
        <div class="register">
            <el-card class="registerni" shadow="always">
                <p style="font-family: '微软雅黑';font-size: 18px;">注册</p>
                <div style="height: 36px">
                    <p id="getPassword">
                        <el-link type="" @click="Login" :underline="false">已有账号，</el-link>
                        <el-link :underline="false" @click="Login" type="primary">
                            登录</el-link>
                    </p>
                </div>
                <div class="divContainer">
                    <el-input placeholder="用户名" v-model="userName" clearable>
                    </el-input>
                    <div class="divSplit"></div>
                    <el-input placeholder="密码" v-model="password" show-password></el-input>
                    <div class="divSplit"></div>
                    <el-input placeholder=" 请输入邮箱" v-model="email"  maxlength='40' style="z-index:10"></el-input>
                    <el-input style="width: 160px;margin-right: 15px;margin-bottom: 10px;" placeholder="请输入验证码"
                      v-model="code" clearable>
                    </el-input>
                
                    <el-button :plain="true" id="getCode1" style="width: 120px;height:40px;float: right;right:0px"
                       @click="getCode" type="primary"  >获取验证码
                    </el-button>
                    <el-button id="setCode1" style="width: 120px;height:40px;float: right;display: none;right:0px" type="primary"
                       plain disabled>{{countdownCode}}
                    </el-button>
                    
                </div>
                <template></template>
                <el-button @click="register()" :plain="true" style="width: 310px;margin-bottom: 20px;z-index:100;top:270px;position:absolute" type="primary">
                    注册</el-button>
            </el-card>
            
        </div>
</template>

<script>
import $ from 'jquery';
import axios from "axios";
export default {
    name:'Register',
    data() {
            return {
                userName: '',
                password: '',
                email: '',
                code: '',
                countdown: 60,
                countdownCode: "",
                countdown2: 60,
                countdownCode2: ""
            }
        },
        methods: {
            getCode() {
                var _this = this;

                if(this.email){
                    var data='';
                     this.$.ajax({
                        url: "http://localhost/user/sendMsg",
                        type: "post",
                        dataType: "JSON",
                        contentType: "application/json;charset=UTF-8",
                        async: false,
                        data: JSON.stringify(
                            {email: this.email}
                        ),
                        success: function (res) {
                            data=res;
                        }
                    })
                    if(data.code == 1){
                        this.$notify({type: 'warning', message: data.data});
                        var interval = window.setInterval(function () {
                        _this.countdownCode = "重新获取(" + _this.countdown + "s)";
                        if (_this.countdown == 0) {
                            $("#setCode1").css("display", "none");
                            $("#getCode1").css("display", "block");
                            _this.countdown = 60;
                            window.clearInterval(interval);
                        } else {
                            $("#getCode1").css("display", "none");
                            $("#setCode1").css("display", "block");
                            _this.countdown--;
                        }
                    }, 1000);
                    }else{
                      this.$notify({type: 'warning', message: data.msg});
                    }

                }else {
                    this.$notify({type: 'warning', message: '请输入邮箱'});
                }
                


            },
            register() {
                var _this = this;
                var data="";
                if (this.email && this.code && this.userName && this.password) {
                    this.$.ajax({
                        url: "http://localhost/user/register",
                        type: "post",
                        dataType: "JSON",
                        contentType: "application/json;charset=UTF-8",
                        async: false,
                        data: JSON.stringify(
                            {userName: this.userName, password: this.password, email: this.email, code: this.code}
                        ),
                        success: function (res) {
                            if (res.code === 1) {
                                window.location.href = 'login.html'
                            }else{
                                data=res;
                            }
                        }
                    })
                    if(data.code == 0){
                        this.$notify({type: 'warning', message: data.msg});
                    }

                } else {
                    if(this.userName == '')
                       this.$notify({type: 'warning', message: '请输入用户名'});
                    else if(this.password == '')
                        this.$notify({type: 'warning', message: '请输入密码'});
                    else if(this.email == '')
                        this.$notify({type: 'warning', message: '请输入邮箱'});
                    else if(this.code == '')
                        this.$notify({type: 'warning', message: '请输入验证码'});
                }
            },
            Login(){
                this.$router.push({path:'/login/signin'})
            },
        },
}
</script>

<style scoped>
        .registerni {
            position: fixed;
            z-index: 9;
            width: 350px;
            height: 370px;
            left: 70%;
            top: 200px;
            opacity: 0.9;
        }

        .register .divContainer .divSplit {
            height: 1px;
            background-color: #efefef;
            border: 0;
            margin-left: 10rem;
            margin-right: 10rem;
            position: relative;
        }
        .register .divContainer span {
            position: absolute;
            right: 2rem;
            top: 13rem;
            cursor: pointer;
            opacity: 1;
            /*font-size: 11;*/
            font-weight: 400;
            text-align: left;
            color: #ef0c0c;
            letter-spacing: 0px;
        }
</style>