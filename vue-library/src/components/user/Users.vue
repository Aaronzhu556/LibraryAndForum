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
				<el-table-column label="邮箱" prop="user_email"></el-table-column>
				<el-table-column label="电话" prop="user_phone"></el-table-column>
<!--				<el-table-column label="头像" prop="user_logo"></el-table-column>-->
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
			               :page-sizes="[1, 5, 10]" :page-size="queryInfo.pagesize" :total="total"
			               layout="total, sizes, prev, pager, next, jumper" background>
			</el-pagination>
		</el-card>


<!--		&lt;!&ndash;添加用户的对话框&ndash;&gt;-->
<!--		<el-dialog title="添加用户" width="50%" :visible.sync="addDialogVisible" :close-on-click-modal="false" @close="addDialogClosed">-->
<!--			&lt;!&ndash;内容主体&ndash;&gt;-->
<!--			<el-form :model="addForm" :rules="addFormRules" ref="addFormRef" label-width="70px">-->
<!--				<el-form-item label="用户名" prop="username">-->
<!--					<el-input v-model="addForm.username"></el-input>-->
<!--				</el-form-item>-->
<!--				<el-form-item label="密码" prop="password">-->
<!--					<el-input v-model="addForm.password"></el-input>-->
<!--				</el-form-item>-->
<!--				<el-form-item label="邮箱" prop="email">-->
<!--					<el-input v-model="addForm.email"></el-input>-->
<!--				</el-form-item>-->
<!--				<el-form-item label="手机号" prop="mobile">-->
<!--					<el-input v-model="addForm.mobile"></el-input>-->
<!--				</el-form-item>-->
<!--			</el-form>-->
<!--			&lt;!&ndash;底部&ndash;&gt;-->
<!--			<span slot="footer">-->
<!--				<el-button @click="addDialogVisible=false">取 消</el-button>-->
<!--				<el-button type="primary" @click="addUser">确 定</el-button>-->
<!--			</span>-->
<!--		</el-dialog>-->

<!--		&lt;!&ndash;修改用户的对话框&ndash;&gt;-->
<!--		<el-dialog title="修改用户" width="50%" :visible.sync="editDialogVisible" :close-on-click-modal="false" @close="editDialogClosed">-->
<!--			&lt;!&ndash;内容主体&ndash;&gt;-->
<!--			<el-form :model="editForm" :rules="editFormRules" ref="editFormRef" label-width="70px">-->
<!--				<el-form-item label="用户名" prop="username">-->
<!--					<el-input v-model="editForm.username" :disabled="true"></el-input>-->
<!--				</el-form-item>-->
<!--				<el-form-item label="邮箱" prop="email">-->
<!--					<el-input v-model="editForm.email"></el-input>-->
<!--				</el-form-item>-->
<!--				<el-form-item label="手机号" prop="mobile">-->
<!--					<el-input v-model="editForm.mobile"></el-input>-->
<!--				</el-form-item>-->
<!--			</el-form>-->
<!--			&lt;!&ndash;底部&ndash;&gt;-->
<!--			<span slot="footer">-->
<!--				<el-button @click="editDialogVisible=false">取 消</el-button>-->
<!--				<el-button type="primary" @click="editUser">确 定</el-button>-->
<!--			</span>-->
<!--		</el-dialog>-->

<!--		&lt;!&ndash;分配角色的对话框&ndash;&gt;-->
<!--		<el-dialog title="分配角色" width="50%" :visible.sync="setRoleDialogVisible" :close-on-click-modal="false" @close="setRoleDialogClosed">-->
<!--			&lt;!&ndash;内容主体&ndash;&gt;-->
<!--			<div>-->
<!--				<p>当前的用户：{{ userInfo.username }}</p>-->
<!--				<p>当前的角色：{{ userInfo.role_name }}</p>-->
<!--				<p>分配新角色：-->
<!--					<el-select v-model="selectRoleId" placeholder="请选择">-->
<!--						<el-option v-for="item in rolesList" :key="item.id" :label="item.roleName" :value="item.id"></el-option>-->
<!--					</el-select>-->
<!--				</p>-->
<!--			</div>-->
<!--			&lt;!&ndash;底部&ndash;&gt;-->
<!--			<span slot="footer">-->
<!--				<el-button @click="setRoleDialogVisible=false">取 消</el-button>-->
<!--				<el-button type="primary" @click="saveRoleInfo">确 定</el-button>-->
<!--			</span>-->
<!--		</el-dialog>-->
	</div>
</template>

<script>
	export default {
		name: "Users",
		data() {
			let checkEmail = (rule, value, callback) => {
				const regEmail = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/
				if (regEmail.test(value)) {
					return callback()
				}
				callback(new Error('请输入合法的邮箱'))
			}
			let checkMobile = (rule, value, callback) => {
				const regMobile = /^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/
				if (regMobile.test(value)) {
					return callback()
				}
				callback(new Error('请输入合法的手机号'))
			}
			return {
				//获取用户列表的参数对象
				queryInfo: {
					querytext: '',
					pagenum: 1,//当前的页数
					pagesize: 5,//当前每页显示多少条数据
					role:'customer'
				},
				userList: [],
				total: 0,
				//添加用户对话框显示状态
				addDialogVisible: false,
				//修改用户对话框显示状态
				editDialogVisible: false,
				//分配角色对话框的显示状态
				setRoleDialogVisible: false,
				//添加用户的表单数据
				// addForm: {
				// 	username: '',
				// 	password: '',
				// 	email: '',
				// 	mobile: ''
				// },
				//添加用户表单的验证规则对象
				addFormRules: {
					username: [
						{required: true, message: '请输入用户名', trigger: 'blur'},
						{min: 3, max: 10, message: '用户名长度在3~10个字符', trigger: 'blur'}
					],
					password: [
						{required: true, message: '请输入密码', trigger: 'blur'},
						{min: 6, max: 15, message: '密码长度在6~15个字符', trigger: 'blur'}
					],
					email: [
						{required: true, message: '请输入邮箱', trigger: 'blur'},
						{validator: checkEmail, trigger: 'blur'}
					],
					mobile: [
						{required: true, message: '请输入手机号', trigger: 'blur'},
						{validator: checkMobile, trigger: 'blur'}
					]
				},
				//修改用户的表单数据
				editForm: {},
				//修改用户表单的验证规则对象
				editFormRules: {
					email: [
						{required: true, message: '请输入邮箱', trigger: 'blur'},
						{validator: checkEmail, trigger: 'blur'}
					],
					mobile: [
						{required: true, message: '请输入手机号', trigger: 'blur'},
						{validator: checkMobile, trigger: 'blur'}
					]
				},
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
				axios.post('/api/user/searchalluser', JSON.stringify({
					querytext:this.queryInfo.querytext,
					pagenum:this.queryInfo.pagenum,
					pagesize:this.queryInfo.pagesize,
					//role:this.queryInfo.role,
				}), {
					headers: {
						'Content-Type': 'application/json'
					}

				}).then(response => {
					if (parseInt(response.data.code) === 200) {
						this.userList = response.data.object;
						this.total = parseInt(response.data.info);
					} else {
						this.$message.info(response.data.msg)
					}
				}).catch(() => {
					this.$message.error("获取失败")
				})
			},
			changeSwitch(current_data,index){
				axios.get('/api/user/update_status',
						{
							params:{
								user_name : current_data.user_name,
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
				this.getUserList()
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