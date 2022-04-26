<template>
    <div class="register_container">
        <div class="register_box">

            <!--注册表单区域-->
            <el-form ref="registerFormRef" :model="registerForm" :rules="registerFormRules" class="register_form" >
                <el-form-item prop="user_name">
                    <el-input v-model="registerForm.user_name" prefix-icon="el-icon-user-solid"></el-input>
                </el-form-item>
                <el-form-item prop="user_phone">
                    <el-input v-model="registerForm.user_phone" prefix-icon="el-icon-phone"></el-input>
                </el-form-item>
                <el-form-item prop="user_password">
                    <el-input v-model="registerForm.user_password" prefix-icon="el-icon-lock" show-password></el-input>
                </el-form-item>
                <el-form-item prop="user_password_confirm">
                    <el-input v-model="registerForm.user_password_confirm" prefix-icon="el-icon-lock" show-password></el-input>
                </el-form-item>
                <el-form-item class="btns">
                    <el-button type="primary" @click="register">注册</el-button>
                    <el-button type="info" @click="goLogin">去登陆</el-button>
                </el-form-item>
            </el-form>
        </div>

    </div>
</template>

<script>
    export default {
        name: "Register",
        data() {
            const validatePassCheck = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请再次输入密码'));
                } else if (value !== this.registerForm.user_password) {
                    callback(new Error('两次输入的密码不一样'));
                } else {
                    callback();
                }
            };
            const validateUserName = (rule,value,callback) =>{
                if (value===''){
                    callback(new Error('请输入用户名'))
                }else {
                    for (var i = 0; i < this.allUserName.length; i++) {
                        if (this.allUserName[i] === value) callback(new Error("该用户名已存在"))
                        else callback();
                    }
                }
            };
            return {
                allUserName:[],
                registerForm:{
                    user_name:'',
                    user_phone:'',
                    user_password: '',
                    user_password_confirm:''
                },
                registerFormRules:{
                    user_name:[
                        {trigger:'blur',max:10,validator:validateUserName}
                    ],
                    user_phone: [
                        { required: true, message: '手机号不能为空', trigger: 'blur' },
                        { type: 'string', pattern: /^1[3|4|5|6|7|8][0-9]{9}$/, message: '手机号格式出错', trigger: 'blur' ,max:11}
                    ],
                    user_password:[
                        {required:true,message:'请输入密码',trigger:'blur'},
                        {type:'string',min:6,max:10,message: '密码长度在6到10之间',trigger: 'blur'}
                    ],
                    user_password_confirm: [
                        {validator:validatePassCheck,trigger:'blur'}
                    ]
                }

            }
        },
        created() {
            this.getAllUserName();
        },
        methods: {
            getAllUserName(){


                axios.get('/api/user/getallusername').then((response)=>{
                    console.log(response)
                    if (parseInt(response.data.code)===200){
                        this.allUserName = response.data.object;
                        console.log(this.allUserName)
                    }
                }).catch(()=>{
                    this.$message.error("获取用户名失败")
                })
            },

            goLogin(){
                this.$router.push('/login').catch(err=>(console.log(err)));
            },
            register(){
                this.$refs.registerFormRef.validate(valid => {
                    if (valid){
                        axios.post("/api/user/register",JSON.stringify({
                                user_name: this.registerForm.user_name,
                                user_phone: this.registerForm.user_phone,
                                user_password: this.registerForm.user_password
                            }
                            ),
                            {
                                headers:{
                                    'Content-Type': 'application/json'
                                }
                            }
                        ).then(response=>{
                            if (parseInt(response.data.code)===200){
                                this.$message({
                                    showClose:true,
                                    message:'注册成功',
                                    type:'success'
                                });
                                this.$router.push('/login')
                            }else {
                                this.$message({
                                    showClose:true,
                                    message:'注册失败',
                                    type:'error'
                                });
                            }

                        }).catch(function (error) {
                            console.log(error);
                        })
                    }
                })
            }
        }
    }
</script>

<style lang="less" scoped>
    .register_container {
        height: 100%;
        background-color: #2b4b6b;
        background: url("../assets/background.jpg") center center no-repeat;
    }

    .register_box {
        width: 450px;
        height: 300px;
        background-color: #fff;
        border-radius: 3px;
        position: absolute;
        left: 50%;
        top: 50%;
        transform: translate(-50%, -50%);

    }


    .register_box {
        width: 450px;
        height: 400px;
        background-color: #fff;
        border-radius: 3px;
        position: absolute;
        left: 50%;
        top: 50%;
        transform: translate(-50%, -50%);

    }
    .register_form {
        position: absolute;
        bottom: 0;
        width: 100%;
        padding: 0 20px;
        box-sizing: border-box;
    }

    .btns {
        display: flex;
        justify-content: flex-end;
    }

</style>