<template>
	<div>
		<!--面包屑导航区-->
		<el-breadcrumb separator-class="el-icon-arrow-right">
			<el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
			<el-breadcrumb-item>文件管理</el-breadcrumb-item>
			<el-breadcrumb-item>文件列表</el-breadcrumb-item>
		</el-breadcrumb>

		<!--卡片视图区域-->
		<el-card>
			<!--添加角色按钮-->
<!--			<el-row>-->
<!--				<el-col>-->
<!--					<el-button type="primary" @click="addRoleDialogVisible=true">添加角色</el-button>-->
<!--				</el-col>-->
<!--			</el-row>-->

			<!--店铺列表-->
			<el-table :data="shopList" :border="true" :stripe="true">
<!--				<el-table-column type="expand" width="50">-->
<!--					<template slot-scope="props">-->
<!--						<el-form label-position="left" inline class="demo-table-expand">-->
<!--							<el-form-item label="商品总数:">-->
<!--								<span>{{props.row.product_amount}}</span>-->
<!--							</el-form-item>-->

<!--							<el-form-item label="总交易额:">-->
<!--								<span>{{ props.row.transaction_amount}}</span>-->
<!--							</el-form-item>-->
<!--							<el-form-item label="店主名称:">-->
<!--								<span>{{ props.row.user_name }}</span>-->
<!--							</el-form-item>-->
<!--							<el-form-item label="店铺开办时间:">-->
<!--								<span>{{ props.row.open_time }}</span>-->
<!--							</el-form-item>-->
<!--							<el-form-item label="店铺所属领域:">-->
<!--								<span>{{ props.row.shop_area }}</span>-->
<!--							</el-form-item>-->
<!--						</el-form>-->
<!--					</template>-->
<!--				</el-table-column>-->
				<el-table-column label="序号" prop="id" width="50"></el-table-column>
				<el-table-column label="文件编号" prop="file_id"></el-table-column>
				<el-table-column label="文件转化人" prop="file_user_name"></el-table-column>
				<el-table-column label="文件转化时间" prop="file_time"></el-table-column>
				<el-table-column label="文件状态" prop="file_status_code" width="300">
					<template slot-scope="scope" >
						<el-switch
								active-color="#13ce66"
								inactive-color="#ff4949"
								v-model="scope.row.file_status_code"
								active-value="1"
								inactive-value="0"
								active-text="文件共享"
								inactive-text="文件私密"
								@change="changeSwitch(scope.row,scope.$index)">
						</el-switch>
					</template>

				</el-table-column>
			</el-table>
			<el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="queryInfo.pagenum"
						   :page-sizes="[1,5, 10]" :page-size="queryInfo.pagesize" :total="total"
						   layout="total, sizes, prev, pager, next, jumper" background>
			</el-pagination>
		</el-card>

		<!--添加角色的对话框-->
		<el-dialog title="添加角色" width="50%" :visible.sync="addRoleDialogVisible" :close-on-click-modal="false" @close="addRoleDialogClosed">
			<!--内容主体-->
			<el-form :model="addRoleForm" :rules="addRoleFormRules" ref="addRoleFormRef" label-width="80px">
				<el-form-item label="角色名称" prop="roleName">
					<el-input v-model="addRoleForm.roleName"></el-input>
				</el-form-item>
				<el-form-item label="角色描述" prop="roleDesc">
					<el-input v-model="addRoleForm.roleDesc"></el-input>
				</el-form-item>
			</el-form>
			<!--底部-->
			<span slot="footer">
				<el-button @click="addRoleDialogVisible=false">取 消</el-button>
				<el-button type="primary" @click="addRole">确 定</el-button>
			</span>
		</el-dialog>
		
		<!--修改角色的对话框-->
		<el-dialog title="修改角色" width="50%" :visible.sync="editRoleDialogVisible" :close-on-click-modal="false" @close="editRoleDialogClosed">
			<!--内容主体-->
			<el-form :model="editRoleForm" :rules="editRoleFormRules" ref="editRoleFormRef" label-width="80px">
				<el-form-item label="角色名称" prop="roleName">
					<el-input v-model="editRoleForm.roleName"></el-input>
				</el-form-item>
				<el-form-item label="角色描述" prop="roleDesc">
					<el-input v-model="editRoleForm.roleDesc"></el-input>
				</el-form-item>
			</el-form>
			<!--底部-->
			<span slot="footer">
				<el-button @click="editRoleDialogVisible=false">取 消</el-button>
				<el-button type="primary" @click="editRole">确 定</el-button>
			</span>
		</el-dialog>

		<!--分配权限的对话框-->
		<el-dialog title="分配权限" width="30%" :visible.sync="setRightDialogVisible" :close-on-click-modal="false" @close="setRightsDialogClosed">
			<!--内容主体-->
			<el-tree :data="rightsList" :props="treeProps" ref="treeRef" :show-checkbox="true" :default-expand-all="true"
			         node-key="id" :default-checked-keys="defaultKeys"></el-tree>
			<!--底部-->
			<span slot="footer">
				<el-button @click="setRightDialogVisible=false">取 消</el-button>
				<el-button type="primary" @click="allotRights">确 定</el-button>
			</span>
		</el-dialog>
	</div>
</template>

<script>
	export default {
		name: "Roles",
		data() {
			return {

				//店铺查询的信息
				queryInfo:{

					pagenum: 1,//当前的页数
					pagesize: 5//当前每页显示多少条数据
				},
				total:0,
				//店铺列表
				shopList: [],
				//分配权限对话框的显示与隐藏
				setRightDialogVisible: false,
				//所有权限的树形数据
				rightsList: [],
				//树形控件的属性绑定对象
				treeProps: {
					label: 'authName',
					children: 'children'
				},
				//默认选中的节点id值数组
				defaultKeys: [],
				//当前即将分配权限的角色id
				roleId: '',
				//添加角色对话框的显示状态
				addRoleDialogVisible: false,
				//修改角色对话框的显示状态
				editRoleDialogVisible: false,
				//添加角色的表单数据
				addRoleForm: {
					roleName: '',
					roleDesc: '',
				},
				//添加角色表单的验证规则对象
				addRoleFormRules: {
					roleName: [
						{required: true, message: '请输入角色名称', trigger: 'blur'},
					],
					roleDesc: [
						{required: true, message: '请输入角色描述', trigger: 'blur'},
					]
				},
				//修改角色的表单数据
				editRoleForm: {
					roleName: '',
					roleDesc: '',
				},
				//修改角色表单的验证规则对象
				editRoleFormRules: {
					roleName: [
						{required: true, message: '请输入角色名称', trigger: 'blur'},
					],
					roleDesc: [
						{required: true, message: '请输入角色描述', trigger: 'blur'},
					]
				},
			}
		},
		created() {
			this.getShopsList()
		},
		methods: {
			getShopsList() {
				axios.post('/api/file/search_all_file',JSON.stringify({
					pagenum:this.queryInfo.pagenum,
					pagesize:this.queryInfo.pagesize
				}),{
					headers: {
						'Content-Type': 'application/json'
					}
				}).then(response => {
					console.log(response.data)
					if (parseInt(response.data.code) === 200) {
						this.shopList = response.data.object;
						this.total = parseInt(response.data.info);
						console.log("我尼玛");
						console.log(this.total);
					} else {
						this.$message.info(response.data.msg)
					}
				}).catch(() => {
					this.$message.error('发生错误')
				})
			},
			changeSwitch(current_data,index){
				axios.get('/api/file/update_status',
						{
							params:{
								file_id : current_data.file_id,
								file_status_code : current_data.file_status_code
							},
							headers: {Authorization: sessionStorage.getItem('token')}
						}

				).then((response)=>{
					if (parseInt(response.data.code)===200){
						this.$message.success(response.data.msg);
					}
					else{
						this.shopList[index].file_status_code = current_data.file_status_code;
						this.$message.error(response.data.msg);
					}
				}).catch(() => {
					this.$message.error('操作失败')
				})
			},
			//监听pagesize改变的事件
			handleSizeChange(newSize) {
				this.queryInfo.pagesize = newSize;
				this.getShopsList();
			},
			//监听页码值改变的事件
			handleCurrentChange(newPage) {
				this.queryInfo.pagenum = newPage;
				this.getShopsList();
			},
			//根据id删除角色对应的权限
			removeRightById(role, rightId) {
				this.$confirm('此操作将永久删除该权限, 是否继续?', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					axios.delete(`roles/${role.id}/rights/${rightId}`).then(response => {
						const res = response.data
						if (res.meta.status === 200) {
							role.children = res.data
							this.$message.success(res.meta.msg)
						} else {
							this.$message.error(res.meta.msg)
						}
					}).catch(() => {
						this.$message.error('操作失败')
					})
				}).catch(() => {
					this.$message.info('已取消删除')
				});
			},
			//显示分配权限对话框
			showSetRightDialog(role) {
				this.roleId = role.id
				axios.get('rights/tree').then(response => {
					const res = response.data
					if (res.meta.status === 200) {
						this.rightsList = res.data
						this.getLeafKeys(role, this.defaultKeys)
						this.setRightDialogVisible = true
					} else {
						this.$message.error(res.meta.msg)
					}
				}).catch(() => {
					this.$message.error('请求失败')
				})
			},
			//通过递归方式 获取角色下所有三级权限的id
			getLeafKeys(node, arr) {
				//如果当前node节点不含children属性 则为三级节点
				if (!node.children) {
					return arr.push(node.id)
				}
				node.children.forEach(item => this.getLeafKeys(item, arr))
			},
			//分配权限对话框关闭事件
			setRightsDialogClosed() {
				this.defaultKeys = []
			},
			//为角色分配权限
			allotRights() {
				const keys = [
					...this.$refs.treeRef.getCheckedKeys(),
					...this.$refs.treeRef.getHalfCheckedKeys()
				]
				const idStr = keys.join(',')
				axios.post(`roles/${this.roleId}/rights`, {rids: idStr}).then(response => {
					const res = response.data
					if (res.meta.status === 200) {
						this.getRolesList()
						this.setRightDialogVisible = false
						this.$message.success(res.meta.msg)
					} else {
						this.$message.error(res.meta.msg)
					}
				}).catch(() => {
					this.$message.error('操作失败')
				})
			},
			//添加角色对话框的关闭事件
			addRoleDialogClosed() {
				this.$refs.addRoleFormRef.resetFields()
			},
			//确认添加角色
			addRole(){
				this.$refs.addRoleFormRef.validate(valid => {
					if (valid) {
						axios.post('roles', this.addRoleForm).then(response => {
							const res = response.data
							if (res.meta.status === 201) {
								//隐藏对话框
								this.addRoleDialogVisible = false
								this.getRolesList()
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
			showEditDialog(id){
				axios.get(`roles/${id}`).then(response => {
					const res = response.data
					if (res.meta.status === 200) {
						this.editRoleForm = res.data
					} else {
						return this.$message.error(res.meta.msg)
					}
				}).catch(() => {
					return this.$message.error("请求失败")
				})
				this.editRoleDialogVisible = true
			},
			//修改角色对话框的关闭事件
			editRoleDialogClosed() {
				this.$refs.editRoleFormRef.resetFields()
			},
			//确认修改角色
			editRole(){
				this.$refs.editRoleFormRef.validate(valid => {
					if (valid) {
						axios.put(`roles/${this.editRoleForm.roleId}`, this.editRoleForm).then(response => {
							const res = response.data
							if (res.meta.status === 200) {
								//隐藏对话框
								this.editRoleDialogVisible = false
								this.getRolesList()
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
			//根据id删除对应的角色
			removeRoleById(id) {
				this.$confirm('此操作将永久删除该角色, 是否继续?', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					axios.delete(`roles/${id}`).then(response => {
						const res = response.data
						if (res.meta.status === 200) {
							this.$message.success(res.meta.msg)
							this.getRolesList()
						} else {
							this.$message.error(res.meta.msg)
						}
					}).catch(() => {
						this.$message.error('操作失败')
					})
				}).catch(() => {
					this.$message.info('已取消删除')
				});
			},
		}
	}
</script>

<style lang="less" scoped>
	.el-tag {
		margin: 10px;
	}

	.bdtop {
		border-top: 1px solid #eeeeee;
	}

	.bdbottom {
		border-bottom: 1px solid #eeeeee;
	}

	.vcenter {
		display: flex;
		align-items: center;
	}

	 .demo-table-expand {
		 font-size: 0;
	 }
	.demo-table-expand label {
		width: 90px;
		color: #99a9bf;
	}
	.demo-table-expand .el-form-item {
		margin-right: 0;
		margin-bottom: 0;
		width: 50%;
	}

</style>