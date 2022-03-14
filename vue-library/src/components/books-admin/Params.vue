<template>
	<div>
		<!--面包屑导航区-->
		<el-breadcrumb separator-class="el-icon-arrow-right">
			<el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
			<el-breadcrumb-item>商品管理</el-breadcrumb-item>
			<el-breadcrumb-item>分类参数</el-breadcrumb-item>
		</el-breadcrumb>

		<el-card>
			<!--警告提示-->
			<el-alert title="注意：只允许为第三级分类设置相关属性！" type="warning" :closable="false" show-icon></el-alert>
			<!--选择商品分类区域-->
			<el-row class="cat_opt">
				<el-col>
					<span>选择商品分类：</span>
					<el-cascader v-model="selectedKeys" :options="cateList" :props="cascaderProps" @change="getParamsData" style="width: 300px"></el-cascader>
				</el-col>
			</el-row>
			<!-- tab标签页 -->
			<el-tabs v-model="property_role" @tab-click="getParamsData">
				<el-tab-pane label="动态属性" name="many">
					<el-button type="primary" :disabled="isBtnDisabled" @click="addDialogVisible=true">添加动态属性</el-button>
					<el-table :data="manyTableData" border stripe>
						<el-table-column type="expand">
							<template v-slot="scope">
								<!--循环渲染tag标签-->
								<el-tag v-for="(item,index) in scope.row.property_detail" :key="index" closable @close="handleTagClose(scope.row,index)">{{ item }}</el-tag>
								<!--添加tag标签-->
								<el-input class="input-new-tag" v-if="scope.row.inputTagVisible" v-model="scope.row.inputTagValue" ref="saveTagInput"
										  size="small" @keyup.enter.native="handleInputTagConfirm(scope.row)" @blur="handleInputTagConfirm(scope.row)"></el-input>
								<el-button v-else class="button-new-tag" size="small" @click="showTagInput(scope.row)">+ 新属性</el-button>
							</template>
						</el-table-column>
						<el-table-column label="序号" type="index" width="50"></el-table-column>
						<el-table-column label="属性名称" prop="property_name"></el-table-column>
						<el-table-column label="属性状态" prop="property_status" width="300">
							<template slot-scope="scope" >
								<el-switch
										active-color="#13ce66"
										inactive-color="#ff4949"
										v-model="scope.row.property_status"
										:active-value="1"
										:inactive-value="0"
										active-text="属性正常"
										inactive-text="属性被冻结"
										@change="changeSwitch(scope.row,scope.$index)">
								</el-switch>
							</template>
						</el-table-column>
						<el-table-column label="操作">
							<template v-slot="scope">
								<el-button size="mini" type="primary" icon="el-icon-edit" @click="showEditDialog(scope.row)">编辑</el-button>
							</template>
						</el-table-column>
					</el-table>
				</el-tab-pane>
				<el-tab-pane label="静态属性" name="only">
					<el-button type="primary" :disabled="isBtnDisabled" @click="addDialogVisible=true">添加静态属性</el-button>
					<el-table :data="onlyTableData" border stripe>
						<el-table-column label="序号" type="index" width="50"></el-table-column>
						<el-table-column label="属性名称" prop="property_name"></el-table-column>
						<el-table-column label="属性状态" prop="property_status" width="300">
							<template slot-scope="scope" >
								<el-switch
										active-color="#13ce66"
										inactive-color="#ff4949"
										v-model="scope.row.property_status"
										:active-value="1"
										:inactive-value="0"
										active-text="属性正常"
										inactive-text="属性被冻结"
										@change="changeSwitch(scope.row,scope.$index)">
								</el-switch>
							</template>
						</el-table-column>
						<el-table-column label="操作">
						<template v-slot="scope">
							<el-button size="mini" type="primary" icon="el-icon-edit" @click="showEditDialog(scope.row)">编辑</el-button>
						</template>
						</el-table-column>
					</el-table>
				</el-tab-pane>
			</el-tabs>


			<el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="queryInfo.pagenum"
						   :page-sizes="[1, 2, 5, 10]" :page-size="queryInfo.pagesize" :total="total"
						   layout="total, sizes, prev, pager, next, jumper" background>
			</el-pagination>

		</el-card>

		<!--添加参数的对话框-->
		<el-dialog title="'添加'+titleText" width="50%" :visible.sync="addDialogVisible" :close-on-click-modal="false" @close="addDialogClosed">
			<!--内容主体-->
			<el-form :model="addForm" :rules="addFormRules" ref="addFormRef" label-width="80px">
				<el-form-item :label="titleText" prop="attr_name">
					<el-input v-model="addForm.attr_name"></el-input>
				</el-form-item>

			</el-form>
			<!--底部-->
			<span slot="footer">
				<el-button @click="addDialogVisible=false">取 消</el-button>
				<el-button type="primary" @click="addParams">确 定</el-button>
			</span>
		</el-dialog>

		<!--修改参数的对话框-->
		<el-dialog :title="'修改'+titleText" width="50%" :visible.sync="editDialogVisible" :close-on-click-modal="false" @close="editDialogClosed">
			<!--内容主体-->
			<el-form :model="editForm" :rules="editFormRules" ref="editFormRef" label-width="80px">
				<el-form-item :label="titleText" prop="property_name">
					<el-input v-model="editForm.property_name"></el-input>
				</el-form-item>

			</el-form>
			<!--底部-->
			<span slot="footer">
				<el-button @click="editDialogVisible=false">取 消</el-button>
				<el-button type="primary" @click="editParams">确 定</el-button>
			</span>
		</el-dialog>
	</div>
</template>

<script>
	export default {
		name: "Params",
		data() {
			return {
				//商品分类列表
				cateList: [],
				//指定级联选择器的配置对象
				cascaderProps: {
					expandTrigger: 'hover',
					//设置为可选中一级分类
					checkStrictly: true,
					value: 'category_id',
					label: 'category_name',
					children: 'category_children'
				},

				property_role :'many',
				radio: 0,
				total: 0,
				queryInfo: {
					pagenum: 1,//当前的页数
					pagesize: 5//当前每页显示多少条数据
				},
				//级联选择框选中的数组
				selectedKeys: [],
				//被激活的tab名称
				activeName: 'many',
				propertyList:[],
				//动态参数数据列表 比如颜色这种可以选择的
				manyTableData: [],
				//静态属性数据列表
				onlyTableData: [],
				//添加属性对话框显示状态
				addDialogVisible: false,
				addForm: {
					attr_name: ''
				},
				addFormRules: {
					attr_name: [
						{required: true, message: '请输入参数名称', trigger: 'blur'}
					]
				},
				editDialogVisible: false,
				editForm: {},
				editFormRules: {
					attr_name: [
						{required: true, message: '请输入参数名称', trigger: 'blur'}
					]
				},
			}
		},
		created() {
			this.getCateList()

		},
		methods: {
			//获取商品分类数据
			getCateList() {
				axios.get('/api/category/getparent', {params: {category_level: "3"}}).then(response => {
					if (parseInt(response.data.code) === 200) {
						this.cateList = response.data.object;
					}
					else {
						this.$message.error(response.data.msg);
					}
				}).catch(() => {
					this.$message.error('请求失败')
				})
			},
			//监听pagesize改变事件
			handleSizeChange(newSize) {
				this.queryInfo.pagesize = newSize
				this.getCateList()
			},
			//监听pagenum改变事件
			handleCurrentChange(newPage) {
				this.queryInfo.pagenum = newPage
				this.getCateList()
			},
			//冻结or解冻
			changeSwitch(current_data,index){
				axios.get('/api/property/update_status',
						{
							params:{
								property_id : current_data.property_id,
								property_status : current_data.property_status
							},
							headers: {Authorization: sessionStorage.getItem('token')}
						}

				).then((response)=>{
					if (parseInt(response.data.code)===200){
						this.$message.success(response.data.msg);
					}
					else{
						this.propertyList[index].property_status = current_data.property_status;
						this.$message.error(response.data.msg);
					}
				}).catch(() => {
					this.$message.error('操作失败')
				})
			},
			//获取参数的列表数据
			getParamsData() {
				//必须选中三级分类
				if (this.selectedKeys.length === 3) {
					//根据所选分类的id，和当前所处的面板，获取对应的参数
					console.log(this.selectedKeys);
					axios.get(`/api/property/search_property`, {params: {
						    property_category_id: this.selectedKeys[2],
							property_role : this.property_role,
							pagenum:this.queryInfo.pagenum,
							pagesize:this.queryInfo.pagesize,
					}}).then(response => {

						response.data.object.forEach(item => {
							item.property_detail = item.property_detail ? item.property_detail.split(',') : []
							//添加tag的显示状态
							item.inputTagVisible = false
							//添加tag的值
							item.inputTagValue = ''
						})
						if (parseInt(response.data.code) === 200) {
							if (this.property_role==='many'){
								this.manyTableData = response.data.object;
								this.total = parseInt(response.data.info);
							}else if (this.property_role==='only'){
								this.onlyTableData = response.data.object;
								this.total = parseInt(response.data.info);
							}
							//this.$message.success(response.data.msg);

						}
						else if (parseInt(response.data.code) ===201){
							this.$message.info(response.data.msg);
							this.manyTableData=[];
							this.onlyTableData =[];
							this.total = 0;
							this.queryInfo.pagenum=1;
							this.queryInfo.pagesize=5;
						}

						else {
							this.$message.error(response.data.msg)
						}
					}).catch(() => {
						this.$message.error('请求失败')
					})
				} else {
					this.selectedKeys = []
					this.manyTableData = []
					this.onlyTableData = []
				}
			},
			addDialogClosed() {
				this.$refs.addFormRef.resetFields()
			},
			//确定添加属性
			addParams() {
				this.$refs.addFormRef.validate(valid => {
					if (valid) {
						console.log(this.addForm);
						axios.post('/api/property/add_property',JSON.stringify({
							property_name : this.addForm.attr_name,
							property_category_id : this.selectedKeys[2],
							property_role : this.property_role,
						}),{
							headers: {
								'Content-Type': 'application/json',
								'Authorization' : sessionStorage.getItem('token')
							}
						}).then(response => {
							if (parseInt(response.data.code) === 200) {
								this.$message.success(response.data.msg)
								this.getParamsData();
								this.addDialogVisible = false
							} else {
								this.$message.error(response.data.msg)
							}
						}).catch(() => {
							this.$message.error('操作失败')
						})
					}
				})
			},
			showEditDialog(paramsInfo) {
				//paramsInfo中没有对象，直接深拷贝一份，也没必要从服务端重新获取了
				//如果直接赋值，则为引用，表格上的数据也会随对话框中数据的修改而实时改变
				this.editForm = {...paramsInfo}
				this.editDialogVisible = true
			},
			editDialogClosed() {
				this.$refs.editFormRef.resetFields()
			},
			editParams() {
				this.$refs.editFormRef.validate(valid => {
					if (valid) {
						axios.put(`/api/property/update_property_name`, JSON.stringify({
							property_id: this.editForm.property_id,
							property_name : this.editForm.property_name,
						}),{
							headers: {
								'Content-Type': 'application/json',
								'Authorization' : sessionStorage.getItem('token')
							}
						}
						).then(response => {

							if (parseInt(response.data.code) === 200) {
								this.$message.success(response.data.msg)
								this.getParamsData()
								this.editDialogVisible = false
							} else {
								this.$message.error(response.data.msg)
							}
						}).catch(() => {
							this.$message.error('操作失败')
						})
					}
				})
			},
			removeParamsById(id) {
				axios.delete(`categories/${this.cateId}/attributes/${id}`).then(response => {
					const res = response.data
					if (res.meta.status === 200) {
						this.$message.success(res.meta.msg)
						this.getParamsData()
					} else {
						this.$message.error(res.meta.msg)
					}
				}).catch(() => {
					this.$message.error('操作失败')
				})
			},
			//确定添加tag
			handleInputTagConfirm(row) {
				if (row.inputTagValue.trim().length !== 0) {
					//将新添加的tag push进数组
					row.property_detail.push(row.inputTagValue.trim())
					//向服务端put新数据
					this.saveAttrVals(row)
				}
				row.inputTagValue = ''
				row.inputTagVisible = false
			},
			//将 property_detail 保存到服务端
			saveAttrVals(row) {
				axios.put(`/api/property/add_property_detail`, JSON.stringify({
					property_id: row.property_id,
					property_detail: row.property_detail.join(',')
				}),{
					headers: {
						'Content-Type': 'application/json',
						'Authorization': sessionStorage.getItem('token')
					}
				}).then(response => {

					if (parseInt(response.data.code) === 200) {
						this.$message.success(response.data.msg)
					} else {
						this.$message.error(response.data.msg)
					}
				}).catch(() => {
					this.$message.error('操作失败')
				})
			},
			//显示tag输入框
			showTagInput(row) {
				row.inputTagVisible = true
				//让输入框自动获得焦点
				//$nextTick方法的作用，当页面上元素被重新渲染之后，才执行回调函数
				//当设置row.inputTagVisible = true，需要将tag渲染成input输入框，在渲染完成之前，获取不到this.$refs.saveTagInput.$refs.input
				this.$nextTick(_ => {
					this.$refs.saveTagInput.$refs.input.focus();
				});
			},
			//删除tag
			handleTagClose(row, index) {
				row.property_detail.splice(index, 1)
				//向服务端put新数据
				this.saveAttrVals(row)
			}
		},
		computed: {
			isBtnDisabled() {
				return this.selectedKeys.length !== 3;
			},
			//当前选中的三级分类的id
			cateId() {
				if (this.selectedKeys.length === 3) {
					return this.selectedKeys[2]
				}
				return null
			},
			//动态计算标题的文本
			titleText() {
				if (this.property_role === 'many') {
					return '动态属性'
				} else {
					return '静态属性'
				}
			}
		}
	}
</script>

<style lang="less" scoped>
	.cat_opt {
		margin: 15px 0;
	}

	.el-button {
		margin-right: 10px;
	}

	.el-tag {
		margin: 10px;
	}

	.input-new-tag {
		width: 120px;
	}

	.button-new-tag {
		width: 120px;
	}
</style>