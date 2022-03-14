<template>
	<div>
		<!--面包屑导航区-->
		<el-breadcrumb separator-class="el-icon-arrow-right">
			<el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
			<el-breadcrumb-item>书籍管理</el-breadcrumb-item>
			<el-breadcrumb-item>书籍分类</el-breadcrumb-item>
		</el-breadcrumb>

		<!--卡片视图区域-->
		<el-card>
			<!--添加分类按钮-->
			<el-row>
				<el-col>
					<el-button type="primary" @click="showAddCateDialog">添加分类</el-button>
				</el-col>
			</el-row>
			<!-- Element 树形列表 -->
			<el-table :data="cateList" row-key="category_id" :tree-props="{children: 'category_children'}" :border="true" :stripe="true">
				<el-table-column label="序号" type="index" width="50"></el-table-column>
				<el-table-column label="分类名称" prop="category_name"></el-table-column>
				<el-table-column label="是否有效">
					<template v-slot="scope">
						<i class="el-icon-success" v-if="scope.row.category_status === '1'" style="color: lightgreen"></i>
						<i class="el-icon-error" v-else style="color: red"></i>
					</template>
				</el-table-column>
				<el-table-column label="级别">
					<template slot-scope="scope">
						<el-tag size="mini" v-if="scope.row.category_level==='0'">一级</el-tag>
						<el-tag size="mini" type="success" v-else-if="scope.row.category_level==='1'">二级</el-tag>
						<el-tag size="mini" type="warning" v-else>三级</el-tag>
					</template>
				</el-table-column>
				<el-table-column label="状态变更" prop= "category_status" width="200">
					<template slot-scope="scope">
						<el-switch
								active-color="#13ce66"
								inactive-color="#ff4949"
								v-model="scope.row.category_status"
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
			               :page-sizes="[4,8,16]" :page-size="queryInfo.pagesize" :total="total"
			               layout="total, sizes, prev, pager, next, jumper" background>
			</el-pagination>
		</el-card>

		<!--添加分类的对话框-->
		<el-dialog title="添加分类" width="50%" :visible.sync="addCateDialogVisible" :close-on-click-modal="false" @close="addCateDialogClosed">
			<!--内容主体-->
			<el-form :model="addCategoryForm" :rules="addCateFormRules" ref="addCateFormRef" label-width="80px">
				<el-form-item label="分类名称" prop="category_name">
					<el-input v-model="addCategoryForm.category_name"></el-input>
				</el-form-item>
				<el-form-item label="父级分类">
					<!-- options指定数据源 -->
					<!-- props指定配置对象 -->
					<el-cascader v-model="selectedKeys" :options="parentCateList" :props="cascaderProps" @change="parentCateChange" :clearable="true"></el-cascader>
				</el-form-item>
			</el-form>
			<!--底部-->
			<span slot="footer">
				<el-button @click="addCateDialogVisible=false">取 消</el-button>
				<el-button type="primary" @click="addCategory">确 定</el-button>
			</span>
		</el-dialog>

	</div>
</template>

<script>
	export default {
		name: "Cate",
		data() {
			return {
				//添加分类对话框显示状态
				addCateDialogVisible: false,
				//添加分类的表单数据对象
				addCategoryForm: {
					category_name: '',
					category_parent: 0,
					category_level: '0',
				},
				addCateFormRules: {
					category_name: [
						{required: true, message: '请输入分类名称', trigger: 'blur'}
					]
				},
				//父级分类的列表
				parentCateList: [],
				//指定级联选择器的配置对象
				cascaderProps: {
					expandTrigger: 'hover',
					//设置为可选中一级分类
					checkStrictly: true,
					value: 'category_id',
					label: 'category_name',
					children: 'category_children'
				},
				//选中的父级分类的id数组
				selectedKeys: [],

				//获取分类列表的参数对象
				queryInfo: {
					//type: 3,
					querytext:'',
					pagenum: 1,//当前的页数
					pagesize: 5//当前每页显示多少条数据
				},
				//商品分类的数据列表
				cateList: [],
				//总数据条数
				total: 0,
				columns: [
					{
						label: '分类名称',
						prop: 'cat_name'
					},
					{
						label: '是否有效',
						//将当前列定义为模板列
						type: 'template',
						//模板名称
						template: 'isok'
					},
					{
						label: '排序',
						//将当前列定义为模板列
						type: 'template',
						//模板名称
						template: 'order'
					},
					{
						label: '操作',
						//将当前列定义为模板列
						type: 'template',
						//模板名称
						template: 'opt'
					}
				]
			}
		},
		created() {
			this.getCateList()
		},
		methods: {
			//获取商品分类数据
			getCateList() {
				axios.post('/api/category/searchallcategory', JSON.stringify({
					querytext:this.queryInfo.querytext,
					pagenum:this.queryInfo.pagenum,
					pagesize:this.queryInfo.pagesize,

				}), {
					headers: {
						'Content-Type': 'application/json'
					}

				}).then(response => {
					if (parseInt(response.data.code) === 200) {
						console.log(response.data.object);
						this.cateList = response.data.object;
						this.total = parseInt(response.data.info);
					}
					else if(parseInt(response.data.msg)===201) {
						this.$message.info(response.data.msg)
					}
					else {
						this.$message.error(response.data.msg);
					}
				}).catch(() => {
					this.$message.error("获取失败")
				})
			},
			changeSwitch(current_data,index){
				axios.get('/api/category/update_status',
						{
							params:{
								category_id : current_data.category_id,
								category_status : current_data.category_status,

							},
							headers: {Authorization: sessionStorage.getItem('token')}
						}

				).then((response)=>{
					if (parseInt(response.data.code)===200){
						this.getCateList();
						this.$message.success(response.data.msg);

					}
					else if (parseInt(response.data.code)===201){
						this.cateList[index].category_status = current_data.category_status;
						this.$message.error(response.data.msg);
					}
					else {
						this.cateList[index].category_status = current_data.category_status;
						this.$message.error(response.data.msg);
					}
				}).catch(() => {
					this.cateList[index].category_status = current_data.category_status;
					this.$message.error('操作失败');
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
			//显示添加分类对话框
			showAddCateDialog() {
				this.getParentCateList()
				this.addCateDialogVisible = true
			},
			//获取父级分类的数据列表
			getParentCateList() {
				axios.get('/api/category/getparent', {params: {category_level: "1"}}).then(response => {
					if (parseInt(response.data.code) === 200) {
						this.parentCateList = response.data.object;
					} else {
						this.$message.error(response.data.msg);
					}
				}).catch(() => {
					this.$message.error('请求失败')
				})
			},
			//选择项发生变化
			parentCateChange() {
				//如果 selectedKeys 数组中的 length 大于0 则选中了父级分类，否则为没有选中任何父级分类
				if (this.selectedKeys.length > 0) {
					//父级分类的id
					this.addCategoryForm.category_parent = this.selectedKeys[this.selectedKeys.length - 1]
					//当前添加的分类等级
					this.addCategoryForm.category_level = this.selectedKeys.length.toString();
				} else {
					//父级分类的id
					this.addCategoryForm.category_parent = 0
					//当前添加的分类等级
					this.addCategoryForm.category_level = '0'
				}
			},
			//监听添加分类对话框关闭事件
			addCateDialogClosed() {
				this.$refs.addCateFormRef.resetFields()
				this.selectedKeys = []
				this.addCategoryForm.category_parent = 0
				this.addCategoryForm.category_level = '0'
			},
			//确定添加分类
			addCategory() {
				this.$refs.addCateFormRef.validate(valid => {
					if (valid) {
						console.log(this.addCategoryForm);
						axios.post('/api/category/addcategory',JSON.stringify({
							category_name: this.addCategoryForm.category_name,
							category_parent : this.addCategoryForm.category_parent,
							category_level : this.addCategoryForm.category_level,
						}),{
							headers: {
								'Content-Type': 'application/json'
							}
						}).then(response => {
							if (parseInt(response.data.code) === 200) {
								this.$message.success(response.data.msg)
								this.getCateList()
								this.addCateDialogVisible = false
							} else {
								this.$message.error(response.data.msg)
							}
						}).catch(() => {
							this.$message.error('操作失败')
						})
					}
				})
			},
			showEditDialog(cateInfo) {
				this.editCateForm.cat_id = cateInfo.cat_id
				this.editCateForm.cat_name = cateInfo.cat_name
				this.editCateDialogVisible = true
			},


		}
	}
</script>

<style scoped>
	.treeTable {
		margin-top: 15px;
		margin-bottom: 15px;
	}

	.el-cascader {
		width: 100%;
	}
	.el-button{
		margin-right: 10px;
	}
</style>