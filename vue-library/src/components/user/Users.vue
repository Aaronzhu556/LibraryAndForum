<template>
	<div>
		<!--面包屑导航区-->
		<el-breadcrumb separator-class="el-icon-arrow-right">
			<el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
			<el-breadcrumb-item>用户管理</el-breadcrumb-item>
			<el-breadcrumb-item>用户列表</el-breadcrumb-item>
		</el-breadcrumb>

		<!--卡片视图区域-->
		<el-card>
			<!--搜索与添加-->
			<el-row :gutter="10">
				<el-col :span="6">
					<el-input placeholder="请输入内容" v-model="queryInfo.querytext" :clearable="true" @clear="getUserList">
						<el-button slot="append" icon="el-icon-search" @click="getUserList"></el-button>
					</el-input>
				</el-col>
<!--				<el-col :span="6">-->
<!--					<el-button type="primary" @click="addDialogVisible=true">添加用户</el-button>-->
<!--				</el-col>-->
			</el-row>

			<!--用户列表-->
			<el-table :data="userList" :border="true" :stripe="true">
				<el-table-column label="序号" prop="user_id" width="50"></el-table-column>
				<el-table-column label="用户名" prop="user_name"></el-table-column>
				<el-table-column label="头像" scope>
					<template slot-scope="scope">
					<el-avatar :src="scope.row.user_img"></el-avatar>
					</template>
				</el-table-column>
				<el-table-column label="电话" prop="user_phone"></el-table-column>
				<el-table-column label="用户正借阅书籍数" prop="user_book_num"></el-table-column>

<!--				<el-table-column label="用户余额" prop="user_money"></el-table-column>-->
				<el-table-column label="状态" prop= "user_status" width="200">
					<template slot-scope="scope">
						<el-switch
								active-color="#13ce66"
								inactive-color="#ff4949"
								v-model="scope.row.user_status"
								active-value="1"
								inactive-value="0"
								active-text="正常"
								inactive-text="冻结"
								@change="changeSwitch(scope.row,scope.$index)">

						</el-switch>
					</template>
				</el-table-column>


			</el-table>

			<!--分页-->
			<el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="queryInfo.pagenum"
			               :page-sizes="[4, 8, 16]" :page-size="queryInfo.pagesize" :total="total"
			               layout="total, sizes, prev, pager, next, jumper" background>
			</el-pagination>
		</el-card>
	</div>
</template>

<script>
	export default {
		name: "Users",
		data() {

			return {
				//获取用户列表的参数对象
				queryInfo: {
					querytext: '',
					pagenum: 1,//当前的页数
					pagesize: 4,//当前每页显示多少条数据

				},
				userList: [],
				totalUserList:[[]],
				users:[],
				max_page:0,
				total: 0,
				//添加用户对话框显示状态
				addDialogVisible: false,
				//修改用户对话框显示状态
				editDialogVisible: false,
				//分配角色对话框的显示状态
				setRoleDialogVisible: false,



				//需要被分配角色的用户信息
				userInfo: {},
				//所有角色的数据列表
				rolesList: [],
				//已选中的角色id
				selectRoleId: ''
			}
		},
		created() {
			this.getUserList()
		},
		methods: {
			getUserList() {
				axios.post('/api/user/getalluser', JSON.stringify({
					querytext:this.queryInfo.querytext,
					pagenum:this.queryInfo.pagenum,
					pagesize:this.queryInfo.pagesize,

				}), {
					headers: {
						'Content-Type': 'application/json'
					}

				}).then(response => {
					if (parseInt(response.data.code) === 200) {
						this.users = response.data.object;

						this.max_page = Math.ceil(this.users.length / this.queryInfo.pagesize) || 1;
						for (let i = 0; i < this.max_page; i++) {
							this.totalUserList[i] = this.users.slice(
									this.queryInfo.pagesize * i,
									this.queryInfo.pagesize * (i + 1)
							);
							console.log(this.totalUserList[i]);
						}
						this.userList = this.totalUserList[this.queryInfo.pagenum-1];

						console.log(this.userList)
						this.total = parseInt(response.data.info);
					} else {
						this.$message.info(response.data.msg)
					}
				}).catch(() => {
					this.$message.error("获取失败")
				})
			},
			changeSwitch(current_data,index){
				axios.get('/api/user/changestatus',
						{
							params:{
								user_id : current_data.user_id,
								user_status : current_data.user_status
							},
							headers: {Authorization: sessionStorage.getItem('token')}
						}

				).then((response)=>{
					if (parseInt(response.data.code)===200){
						this.$message.success(response.data.msg);
					}
					else{
						this.userList[index].user_status = current_data.user_status;
						this.$message.error(response.data.msg);
					}
				}).catch(() => {
					this.userList[index].user_status = current_data.user_status;
					this.$message.error('操作失败');
				})
			},
			//监听pagesize改变的事件
			handleSizeChange(newSize) {
				this.queryInfo.pagesize = newSize
				this.getUserList()
			},
			//监听页码值改变的事件
			handleCurrentChange(newPage) {
				this.queryInfo.pagenum = newPage
				//this.getUserList()
				this.userList = this.totalUserList[newPage-1];
			},
			//监听添加用户对话框的关闭事件
			addDialogClosed() {
				this.$refs.addFormRef.resetFields()
			},
			//确定添加用户
			addUser() {
				this.$refs.addFormRef.validate(valid => {
					if (valid) {
						axios.post('users', this.addForm).then(response => {
							const res = response.data
							if (res.meta.status === 201) {
								//隐藏对话框
								this.addDialogVisible = false
								this.getUserList()
								this.$message.success(res.meta.msg)
							} else {
								this.$message.error(res.meta.msg)
							}
						}).catch(() => {
							this.$message.error("操作失败")
						})
					}
				})
			},
			//展示修改用户的对话框
			showEditDialog(id) {
				axios.get(`users/${id}`).then(response => {
					const res = response.data
					if (res.meta.status === 200) {
						this.editForm = res.data
					} else {
						return this.$message.error(res.meta.msg)
					}
				}).catch(() => {
					return this.$message.error("请求失败")
				})
				this.editDialogVisible = true
			},
			//监听修改用户对话框的关闭事件
			editDialogClosed() {
				this.$refs.editFormRef.resetFields()
			},
			//确定修改用户
			editUser() {
				this.$refs.editFormRef.validate(valid => {
					if (valid) {
						axios.put(`users/${this.editForm.id}`, {email: this.editForm.email, mobile: this.editForm.mobile}).then(response => {
							const res = response.data
							if (res.meta.status === 200) {
								//隐藏对话框
								this.editDialogVisible = false
								this.getUserList()
								this.$message.success(res.meta.msg)
							} else {
								this.$message.error(res.meta.msg)
							}
						}).catch(() => {
							this.$message.error("操作失败")
						})
					}
				})
			},
			//根据id删除对应的用户
			removeUserById(id) {
				console.log("我他妈的傻了");
				axios.delete(`/api/user/deleteuser/`,
						{
							params:{
								user_id : id,
							},
							headers: {Authorization: sessionStorage.getItem('token')}
						}
						).then(response => {

						if ( parseInt(response.data.code)=== 200) {
							this.$message.success ( response.data.msg)
							this.getUserList()
						} else {
							this.$message.error( response.data.msg)
						}
					}).catch(() => {
						this.$message.error('操作失败')
					}).catch(() => {
					this.$message.info('已取消删除')
				});
			},
			//显示分配角色对话框
			showSetRoleDialog(userInfo) {
				this.userInfo = userInfo
				axios.get('roles').then(response => {
					const res = response.data
					if (res.meta.status === 200) {
						this.rolesList = res.data
					} else {
						this.$message.error(res.meta.msg)
					}
				}).catch(() => {
					this.$message.error('请求失败')
				})
				this.setRoleDialogVisible = true
			},
			//分配角色对话框关闭事件
			setRoleDialogClosed() {
				this.selectRoleId = ''
				this.userInfo = {}
			},
			//确定分配角色
			saveRoleInfo() {
				if (!this.selectRoleId) {
					return this.$message.error('请选择要分配的角色')
				}
				axios.put(`users/${this.userInfo.id}/role`, {rid: this.selectRoleId}).then(response => {
					const res = response.data
					if (res.meta.status === 200) {
						this.getUserList()
						this.setRoleDialogVisible = false
						this.$message.success(res.meta.msg)
					} else {
						this.$message.error(res.meta.msg)
					}
				}).catch(() => {
					this.$message.error('操作失败')
				})
			}
		}
	}
</script>

<style lang="less" scoped>

</style>