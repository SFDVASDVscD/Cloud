<template>
  <div style="margin-top: -600px;position:absolute">
        <div style="width: 280px;margin-left:200px">
        设置新密码<br><br><br>
            <el-input style="width: 280px;float: left" placeholder="请输入旧密码" v-model="password" show-password></el-input>
            <el-alert id="password" :title="errormessage" style="width: 142px;display:none;margin-left:10px;float: left"
                type="error" :closable="false">
            </el-alert><br><br>
            <el-input style="width: 280px;float: left" placeholder="请输入新密码" v-model="newPassword1" show-password></el-input>
            <br><br>
            <el-input style="width: 280px;float: left" placeholder="再次输入新密码" v-model="newPassword2" show-password>
            </el-input>
            <el-alert id="newPassword" :title="errormessage" style="width: 142px;float: left;display:none;margin-left: 10px"
                type="error" :closable="false">
            </el-alert><br><br>
            <el-button @click="setPassword" :plain="true" type="primary" round style="width:280px;">确定</el-button>
        </div>
    </div>
</template>

<script>
import axios from "axios";
export default {
    name:'CenterChange',
    data() {
            return {
                password: '',
                newPassword1: '',
                newPassword2: '',
                errormessage:'',
                id: JSON.parse(sessionStorage.getItem('user')).id
            }
        },
        methods: {
            setPassword() {
                if (this.password != null && this.password != '' && this.newPassword1 != null && this.newPassword1 != '' && this.newPassword2 != null && this.newPassword2 != '') {
                    if (this.newPassword2 != this.newPassword1) {
                        this.errormessage = "两次密码不一致";
                        $("#password").css("display", "none");
                        $("#newPassword").css("display", "block");

                    } else {
                        var _this = this;
                        this.$.ajax({
                            url: "http://localhost/user/resetPwd",
                            type: "post",
                            data: JSON.stringify({ id:this.id,oldPwd: _this.password, newPwd: _this.newPassword2 }),
                            headers:{'Content-Type':'application/json;charset=utf8'},
                            dataType: "JSON",
                            async: false,
                            beforeSend: function(request) {
                                request.setRequestHeader("token", sessionStorage.getItem("token"));
                            },
                            success: function (data) {
                                if(data.code == 0){
                                    _this.errormessage = data.msg;
                                    $("#newPassword").css("display", "none");
                                    $("#password").css("display", "block");
                                }
                                else if (data.code == 1) {
                                    _this.$message({
                                        message: '修改成功！',
                                        type: 'success'
                                    });
                                    window.setTimeout(function () {
                                        location.reload();
                                    }, 1000);
                                }
                            }
                        })
                    }
                } else {
                    this.$message.error('信息不能为空！');
                }
            },
            mounted(){
               
            }
        }
}
</script>

<style scoped>

</style>
