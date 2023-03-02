<template>
  <div id="content">
        <div style="margin-top: -600px">
            <span style="font-size: 16px;color: #606266;">基础信息</span>
            <el-divider content-position="right">
                <el-link :underline="false" @click="dialogDataVisible = true" type="primary">编辑资料</el-link>
            </el-divider>


            <div style="float: left;text-align: left;" >
                <span class="center-name">用户名：</span><span class="center-uname" >{{ user.userName }}</span>
                <br><br>
                <span class="center-name">联系电话：</span><span class="center-uname">{{ user.phone }}</span>
                <br><br>
                <span class="center-name">用户角色：</span><span class="center-uname">{{ userPart }}</span>
            </div>
            <div style="margin-left: 360px;text-align: left;">
                <span class="center-name">真实姓名：</span><span class="center-uname">{{ user.realName }}</span>
                <br><br>
                <span class="center-name">邮箱地址：</span><span class="center-uname">{{ user.email }}</span>
                <br><br>
                <span class="center-name">所在部门：</span><span class="center-uname">{{ departmentName }}</span>
            </div>


        </div>
        <el-dialog title="编辑资料" width="300px" :visible.sync="dialogDataVisible">
            <form>
                用 户 名：<el-input style="margin-bottom: 16px;" v-model="userName" readonly="readonly" clearable></el-input>
                真实姓名：<el-input style="margin-bottom: 16px;" v-model="userRealname" clearable></el-input>
                联系电话：<el-input  v-model="userMobile" clearable></el-input>

            </form>
            <div slot="footer" class="dialog-footer" >
                <el-button @click="dialogDataVisible = false">取 消</el-button>
                <el-button type="primary" :plain="true" @click="submitData()">确 定</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
import axios from "axios";
export default {
    name:'CenterInfo',
            data() {
            return {
                user:JSON.parse(sessionStorage.getItem('user')),
                userId:JSON.parse(sessionStorage.getItem('user')).id,
                userName:JSON.parse(sessionStorage.getItem('user')).userName,
                userMobile:JSON.parse(sessionStorage.getItem('user')).phone,
                userRealname:JSON.parse(sessionStorage.getItem('user')).realName,
                userEmail:JSON.parse(sessionStorage.getItem('user')).email,
                userPart:"普通用户",
                dialogDataVisible:false,
                dialogFormVisible: false,
                departmentName:"暂无"

            }
        },
        methods: {
            submitData() {
                var _this = this;
                if (this.userMobile.length!=11) {
                    _this.$message({
                        showClose: true,
                        message: "请输入正确的联系电话",
                        type: "warning"
                    });
                    return;
                }
                this.$.ajax({
                    url: "http://localhost/user/updateUser",
                    type: "post",
                    dataType: "JSON",
                    contentType: "application/json;charset=UTF-8",
                    data: JSON.stringify(
                        {userName:_this.userName,realName:_this.userRealname, phone:_this.userMobile}
                    ),
                    success: function (data) {
                        if (data.code==1) {
                            sessionStorage.setItem('user', JSON.stringify(data.data));
                            window.setTimeout(function () {
                                location.reload();
                            }, 1000)
                        } else {
                            console.log("修改失败！");
                        }

                    }
                });
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