<template>
	<div class="login_container">

		<div class="login_box">
			<!--头像区域-->
<!--			<div class="avatar_box">-->
<!--				<img src="../assets/avatar.jpg" alt="">-->
<!--			</div>-->
			<!--登录表单区域-->
			<el-form ref="loginFormRef" :model="loginForm" :rules="loginFormRules" class="login_form">
				<el-form-item prop="name">
					<el-input v-model="loginForm.name" prefix-icon="el-icon-user-solid"></el-input>
				</el-form-item>
				<el-form-item prop="password">
					<el-input v-model="loginForm.password" prefix-icon="el-icon-lock" show-password></el-input>
				</el-form-item>
				<el-radio-group v-model="loginForm.radio">
					<el-radio :label=1>作为用户登录</el-radio>
					<el-radio :label=0>作为管理员登录</el-radio>

				</el-radio-group>
				<el-form-item class="btns">
					<el-button type="primary" @click="login">登录</el-button>

					<el-button type="info" @click="goRegister">去注册</el-button>
				</el-form-item>
			</el-form>
		</div>

	</div>
</template>

<script>
	export default {
		name: "Login",
		data() {

			return {
				loginForm: {
					name: '',
					password: '',
					radio: 1
				},
				loginFormRules: {
					name: [
						{required: true, message: '请输入用户名', trigger: 'blur'},
					],
					password: [
						{required: true, message: '请输入密码', trigger: 'blur'},
					]
				},



			}
		},
		methods: {
			resetLoginForm() {
				this.$refs.loginFormRef.resetFields();
			},
			goRegister(){
				console.log("草拟吗");
				this.$router.push('/register').catch(err=>(console.log(err)));
			},
			login() {
				this.$refs.loginFormRef.validate(valid => {
					if (valid) {
						if (this.loginForm.radio){
							axios.post("/api/user/login",JSON.stringify({
								user_name:this.loginForm.name,
								user_password:this.loginForm.password
							}), {
								headers: {
									'Content-Type': 'application/json'
								}
							}).then(response => {
								console.log(response.data)
								if (parseInt(response.data.code) === 200) {
									console.log(response.headers.token);
									sessionStorage.setItem('token',response.headers.token);
									sessionStorage.setItem('role',"user");
									this.$message({
										showClose: true,
										message: '登录成功',
										type: 'success'
									});
									this.$router.push('/home')
								} else if (parseInt(response.data.code) === 202){
									this.$message.error({
										showClose:true,
										message:'账户被冻结,请联系管理员',
										type:'error'
									});
								}
								else if (parseInt(response.data.code) === 201){
									this.$message.error({
										showClose:true,
										message:'账户名或者密码错误',
										type:'error'
									});
								}
								else if (parseInt(response.data.code) === 203){
									this.$message.error({
										showClose:true,
										message:"无此用户,请检查后重新登录",
										type:'error'
									});
								}
								else  {
									this.$message.error({
										showClose:true,
										message:'登录失败',
										type:'error'
									});
								}
							}).catch(function (error) {
								console.log(error);
							})
						}
						else{
							axios.post("/api/manager/login",JSON.stringify({
								manager_name:this.loginForm.name,
								manager_password:this.loginForm.password
							}), {
								headers: {
									'Content-Type': 'application/json'
								}
							}).then(response=>{
								if (parseInt(response.data.code) === 200){
									sessionStorage.setItem('token',response.headers.token);
									sessionStorage.setItem('role',"manager");
									this.$message({
										showClose:true,
										message:'登录成功',
										type:'success'
									})
									this.$router.push('/home')
								}
							}).catch(function (error) {
								console.log(error);
							})
						}


					}
				})
			},

		}
	}
</script>

<style lang="less" scoped>
	.login_container {
		height: 100%;
		background-color: #2b4b6b;
	}

	.login_box {
		width: 450px;
		height: 300px;
		background-color: #fff;
		border-radius: 3px;
		position: absolute;
		left: 50%;
		top: 50%;
		transform: translate(-50%, -50%);

		.avatar_box {
			height: 130px;
			width: 130px;
			border: 1px solid #eee;
			border-radius: 50%;
			padding: 10px;
			box-shadow: 0 0 10px #ddd;
			position: absolute;
			left: 50%;
			transform: translate(-50%, -50%);
			background-color: #fff;

			img {
				width: 100%;
				height: 100%;
				border-radius: 50%;
				background-color: #eee;
			}
		}
	}


	.login_form {
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