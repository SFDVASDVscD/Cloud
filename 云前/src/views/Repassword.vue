<template>
<div>
    <el-container>
        <el-header>
            <div style="float: left;margin-left: 200px;">
                <img src="../assets/imgs/logo.jpg"
                     style="height: 60px; width: 75px"/>
            </div>
            <el-link
                    style="pointer-events: none;font-size: 30px;font-family: 微软雅黑;font-weight: bold;float: left;margin-left: 10px;">
                obs云盘
            </el-link>
        </el-header>
        <div style="height: 70px;background-color: #E9EEF3;">
            <div style="margin-top: 25px;margin-left: 270px;"><b>找回密码</b>
                <div style="float: right;margin-right: 50px;">
                    <el-link @click="Login" :underline="false" type="primary">登录
                    </el-link>
                </div>
            </div>
        </div>
        <div>
            <div style="margin-left: 240px;">
                <p style="margin-top: 20px;">请输入你要找回的账号</p>
                <el-input style="width: 300px;float: left"
                          placeholder="邮箱" v-model="email" clearable>
                </el-input>
                <div style="width: 210px;height: 40px;margin-left: 320px;">
                    <el-alert id="alertEmailErro" style="width: 210px;height: 40px;display: none;" title="邮箱不存在，请重新输入"
                              type="error" :closable="false"></el-alert>
                    <el-alert id="alertFormatErro" style="width: 210px;height: 40px;display: none;" title="请正确输入邮箱"
                              type="error" :closable="false"></el-alert>
                </div>
            </div>
        </div>
        <div style="margin-left: 240px;margin-top: 30px">
            <el-button @click="next()" style="width: 300px;margin-bottom: 10px;" type="primary">下一步</el-button>
            <br>
        </div>
        <el-dialog title="安全校验" width="380px" :visible.sync="rePasswordFormVisible">

            <el-form style="width: 350px;"
                     v-loading="loading"
                     element-loading-text="更改中，请耐心等待"
                     element-loading-spinner="el-icon-loading"
                     element-loading-background="rgba(0, 0, 0, 0.6)">
                <div style="height: 36px;margin-top: 2px;margin-bottom: 10px;">
                    <el-alert id="codeAlertMessage" style="display:none;width: 160px;height:36px;" title="验证码错误"
                              type="error" :closable="false">
                    </el-alert>
                    <el-alert id="codeAlertRePassword" style="display:none;width: 160px;height:36px;"
                              title="两次密码不一致" type="error" :closable="false">
                    </el-alert>
                    <el-alert id="alertSendErro" style="display:none;width: 160px;height:36px;"
                              title="验证码发送失败" type="error" :closable="false">
                    </el-alert>
                </div>
                邮箱
                <el-input style="width: 350px;margin-bottom: 10px" v-model="email" :disabled="true"></el-input>
                验证码<br>
                <el-input style="width: 190px;margin-right: 15px;margin-bottom: 10px" placeholder="请输入验证码"
                          v-model="authCode" clearable>
                </el-input>
                <el-button :plain="true" id="getCode" style="width: 120px;height:40px;float: right;"
                           @click="getCode()" type="primary" >获取验证码
                </el-button>
                <el-button id="setCode" style="width: 120px;height:40px;float: right;display: none;" type="primary"
                           plain disabled>{{countdownCode}}
                </el-button>
                <div style="width: 350px;">
                    密码
                    <el-input style="width: 350px;margin-bottom: 10px" placeholder="请输入密码" v-model="password1"
                              show-password></el-input>
                    确认密码
                    <el-input style="width: 350px;" placeholder="请再次输入密码" v-model="password2" show-password>
                    </el-input>
                </div>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="rePasswordFormVisible = false">取 消</el-button>
                <el-button :plain="true" type="primary" @click="rePassWordSubmit">确 定</el-button>
            </div>
        </el-dialog>
    </el-container>
</div>
</template>

<script>
import $ from 'jquery'
import axios from "axios";
export default {
    name:'Repassword',
    data() {
            return {
                email: '',
                code: '',
                authCode: '',
                countdown: 60,
                countdownCode: "",
                password1: '',
                password2: '',
                rePasswordFormVisible: false,
                loading: false,
                falseMsg: "验证码发送失败"
            }
        },
        methods: {
            next() {
                var _this = this;
                var regEmail = /^\w+@[a-zA-Z0-9]{2,10}(?:\.[a-z]{2,4}){1,3}$/;
                if (_this.email != null && _this.email != '' && regEmail.test(_this.email)) {
                    let data = {
                        email: _this.email
                    };
                    axios.post("http://localhost/user/isEmail", data).then(function (response) {
                        // console.log(response.data);
                        if (response.data.code == 0) {
                            $("#alertEmailErro").css("display", "block");
                            $("#alertFormatErro").css("display", "none");
                        }
                        if (response.data.code == 1) {
                            $("#alertEmailErro").css("display", "none");
                            $("#alertFormatErro").css("display", "none");
                            _this.rePasswordFormVisible = true;
                        }
                    })
                } else {
                    $("#alertFormatErro").css("display", "block");
                    $("#alertEmailErro").css("display", "none");
                }
            },
            //获取验证码
            getCode() {
                if (this.email != null && this.email != '') {
                    var _this = this;
                    this.sendCode();
                    var interval = window.setInterval(function () {
                        _this.countdownCode = "重新获取(" + _this.countdown + "s)";
                        if (_this.countdown == 0) {
                            $("#setCode").css("display", "none");
                            $("#getCode").css("display", "block");
                            _this.countdown = 60;
                            window.clearInterval(interval);
                        } else {
                            $("#getCode").css("display", "none");
                            $("#setCode").css("display", "block");
                            _this.countdown--;
                        }
                    }, 1000);
                } else {
                    this.$message.error('请输入邮箱！');
                }
            },
            sendCode:function() {
                var _this = this;
                var data = {
                    email: _this.email
                };
                axios.post("http://localhost/user/sendMsg1", data).then(function (response) {
                    let result=response.data;
                    if (result.code != 1) {
                        $("#alertSendErro").css("display", "block");
                    }

                })
            },
            rePassWordSubmit() {
                var _this = this;
                if (this.email != null && this.email != '' && this.authCode != null && this.authCode != '' && this.password1 != null && this.password1 != '' && this.password2 != null && this.password2 != '') {
                    if (this.password2 != this.password1) {
                        $("#codeAlertMessage").css("display", "none");
                        $("#codeAlertRePassword").css("display", "block");
                    } else {
                        _this.loading = true;
                        var data = {
                            Email: _this.email,
                            Code: _this.authCode,
                            NewPwd: _this.password1
                        };
                        axios.post("http://localhost/user/findPwd", data).then(function (response) {
                            if (response.data.code == 0) {
                                $("#codeAlertRePassword").css("display", "none");
                                $("#codeAlertMessage").css("display", "block");
                                _this.loading = false;
                            }
                            if (response.data.code == 1) {
                                _this.rePasswordFormVisible = false;
                                _this.$message({
                                    message: '恭喜您,找回成功！请使用新密码登录！',
                                    type: 'success'
                                });
                                _this.loading = false;
                                window.setTimeout(function () {
                                    location.href = "login.html";
                                }, 1500);
                            }
                        })
                    }
                } else {
                    this.$message.error('请把信息填写完整！');
                }
            },
            Login(){
                this.$router.push({path:'/login/signin'})
            }
        },
}
</script>

<style scoped>
        .el-header,
        .el-footer {

            color: #333;
            line-height: 60px;
        }

        .el-aside {
            background-color: #D3DCE6;
            color: #333;
            text-align: center;
            line-height: 200px;
        }

        body > .el-container {
            margin-bottom: 40px;
        }

        .el-container:nth-child(5) .el-aside,
        .el-container:nth-child(6) .el-aside {
            line-height: 260px;
        }

        .el-container:nth-child(7) .el-aside {
            line-height: 320px;
        }
</style>