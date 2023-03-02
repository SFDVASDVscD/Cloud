<template>
  <div id="content">
    <div style="margin-top: -600px">
        邮箱换绑<br>

        <div style="float: left;">
            <div style="width: 210px;height: 40px;margin-bottom: 10px;">
                <el-alert id="alertEmailNull" style="width: 210px;height: 40px;display: none;" title="请输入验证码"
                          type="error" :closable="false"></el-alert>
                <el-alert id="alertEmailErro" style="width: 210px;height: 40px;display: none;" title="验证码错误"
                          type="error" :closable="false"></el-alert>
                <el-alert id="alertSendErro" style="width: 210px;height: 40px;display: none;" title="验证码发送失败"
                          type="error" :closable="false"></el-alert>
                <el-alert id="haveSent" style="width: 210px;height: 40px;display: none;" title="已发送验证码"
                          type="error" :closable="false"></el-alert>
            </div>
            <span class="center-name" style="float:left">邮箱地址：</span><span class="center-uname" style="float:left">{{ oldEmail }}</span><br><br>
            <el-input style="width: 240px;margin-right: 15px;margin-bottom: 10px" placeholder="请输入验证码"
                      v-model="authCode" clearable>
            </el-input>
            <el-button :plain="true" id="getCode1" style="width: 120px;height:40px;float: right;z-index:1;position:relative"
                       @click="getOldCode()" type="primary" >获取验证码
            </el-button>
            <el-button id="setCode1" style="width: 120px;height:40px;float: right;display: none;" type="primary"
                       plain disabled>{{countdownCode}}
            </el-button>
            <div style="margin-top: 30px">
                <el-button @click="next()" style="width: 300px;margin-bottom: 10px;z-index:1;position:relative" type="primary">下一步</el-button>
                <br>
            </div>
        </div>

    </div>
    <el-dialog title="邮箱换绑" width="380px" :visible.sync="dialogDataVisible">

        <el-form style="width: 350px;"
                 v-loading="loading"
                 element-loading-text="更改中，请耐心等待"
                 element-loading-spinner="el-icon-loading"
                 element-loading-background="rgba(0, 0, 0, 0.6)">
            <div style="height: 36px;margin-top: 2px;margin-bottom: 10px;z-index:111;position:absolute">
                <el-alert id="codeAlertMessage" style="display:none;width: 160px;height:36px;" title="验证码错误"
                          type="error" :closable="false">
                </el-alert>
                <el-alert id="haveSent" style="display:none;width: 160px;height:36px;z-index:1111" title="已发送验证码"
                          type="error" :closable="false">
                </el-alert>
                <el-alert id="alertSendErro" style="width: 210px;height: 40px;display: none;" title="验证码发送失败"
                          type="error" :closable="false"></el-alert>
                <el-alert id="alertUpdateErro" style="width: 210px;height: 40px;display: none;" title="修改失败"
                          type="error" :closable="false"></el-alert>
            </div>
            新邮箱:
            <el-input style="width: 350px;margin-bottom: 10px" v-model="newEmail"></el-input>
            验证码:<br>
            <el-input style="width: 190px;margin-right: 15px;margin-bottom: 10px" placeholder="请输入验证码"
                      v-model="code" clearable>
            </el-input>
            <el-button :plain="true" id="getCode2" style="width: 120px;height:40px;float: right;"
                       @click="getNewCode()" type="primary"  >获取验证码
            </el-button>
            <el-button id="setCode2" style="width: 120px;height:40px;float: right;display: none;" type="primary"
                       plain disabled>{{countdownCode2}}
            </el-button>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button @click="dialogDataVisible = false">取 消</el-button>
            <el-button :plain="true" type="primary" @click="unBindEmailSubmit">确 定</el-button>
        </div>
    </el-dialog>
</div>
</template>

<script>
import $ from 'jquery'
import axios from "axios";
export default {
    name:'CenterEmail',
    data() {
            return {
                userId: JSON.parse(sessionStorage.getItem('user')).id,
                oldEmail: JSON.parse(sessionStorage.getItem('user')).email,
                // circleUrl: "../../img/user/tx3.jpg",
                email: "-",
                sign: "-",
                dialogFormVisible: false,
                dialogDataVisible: false,
                authCode: '',
                code: '',
                sendEmail: '',
                newEmail: '',
                countdown: 60,
                countdownCode: "",
                countdown2: 60,
                countdownCode2: "",
                loading: false
            }
        },
        methods: {
            next() {
                var _this = this;
                var regEmail = /^\w+@[a-zA-Z0-9]{2,10}(?:\.[a-z]{2,4}){1,3}$/;
                let data = {
                    email: _this.oldEmail,
                    code: _this.authCode
                };
                if (_this.authCode == null || _this.authCode == '' || !regEmail.test(_this.oldEmail)) {
                    $("#alertEmailNull").css("display", "block");
                    $("#alertEmailErro").css("display", "none");
                    $("#haveSent").css("display", "none");
                } else {
                    axios.post("http://localhost/user/verifyEmail", data).then(function (response) {
                        if (response.data.code == 0) {
                            $("#alertEmailErro").css("display", "block");
                            $("#alertEmailNull").css("display", "none");
                            $("#haveSent").css("display", "none");
                        }
                        if (response.data.code == 1) {
                            $("#alertEmailErro").css("display", "none");
                            $("#alertEmailNull").css("display", "none");
                            $("#haveSent").css("display", "none");
                            _this.dialogDataVisible = true;
                        }
                    })
                }
            },
            //获取旧邮箱验证码
            getOldCode() {
                var regEmail = /^\w+@[a-zA-Z0-9]{2,10}(?:\.[a-z]{2,4}){1,3}$/;
                if (this.oldEmail != null && this.oldEmail != '' && regEmail.test(this.oldEmail)) {
                    this.sendEmail = this.oldEmail;
                    this.sendCode();
                    this.sendEmail = '';
                    var _this = this;
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
                } else {
                    this.$message.error('请正确输入邮箱地址！');
                }
            },
            getNewCode() {
                var regEmail = /^\w+@[a-zA-Z0-9]{2,10}(?:\.[a-z]{2,4}){1,3}$/;
                if (this.newEmail != null && this.newEmail != '' && regEmail.test(this.newEmail) && this.newEmail != this.oldEmail) {
                    this.sendEmail = this.newEmail;
                    this.sendCode();
                    this.sendEmail = '';
                    var _this = this;
                    var interval = window.setInterval(function () {
                        _this.countdownCode2 = "重新获取(" + _this.countdown2 + "s)";
                        if (_this.countdown2 == 0) {
                            $("#setCode2").css("display", "none");
                            $("#getCode2").css("display", "block");
                            _this.countdown2 = 60;
                            window.clearInterval(interval);
                        } else {
                            $("#getCode2").css("display", "none");
                            $("#setCode2").css("display", "block");
                            _this.countdown2--;
                        }
                    }, 1000);
                } else {
                    this.$message.error('请正确输入邮箱！');
                }
            },
            sendCode() {
                var _this = this;
                var data = {
                    email: _this.sendEmail
                };
                axios.post("http://localhost/user/sendMsg1", data).then(function (response) {
                    if(response.data.code==1){
                        $("#alertEmailErro").css("display", "none");
                        $("#haveSent").css("display", "block");
                    }
                })
            },
            unBindEmailSubmit() {
                var _this = this;
                if (this.newEmail != null && this.newEmail != '' && this.code != null && this.code != '') {
                    _this.loading = true;
                    var data = {
                        Email: _this.newEmail,
                        Code: _this.code,
                        OldEmail: _this.oldEmail
                    };
                    axios.post("http://localhost/user/unbind", data).then(function (response) {
                        _this.loading = false;
                        if(response.data.code==1){
                            sessionStorage.setItem('user', JSON.stringify(response.data.data));
                            _this.dialogDataVisible = false;
                            _this.$message({
                                message: '恭喜您,修改成功！',
                                type: 'success'
                            });
                            window.setTimeout(function () {
                                location.reload();
                            }, 1500);
                        }
                        else{
                            _this.$message({
                                message: '验证码错误！',
                                type: 'error'
                            });
                            $("alertUpdateErro").css("display", "block");
                        }

                    })
                } else {
                    this.$message.error('请把信息填写完整！');
                }
            },
            mounted(){
                
            }
        }
}
</script>

<style scoped>
.center-name {
            color: #909399;
        }

        .center-uname {
            color: #606266;
        }
</style>