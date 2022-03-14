
<template>
	<div>
		<!--面包屑导航区-->
		<el-breadcrumb separator-class="el-icon-arrow-right">
			<el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
			<el-breadcrumb-item>商品管理</el-breadcrumb-item>
			<el-breadcrumb-item>添加商品</el-breadcrumb-item>
		</el-breadcrumb>

		<el-card>
			<!--提示信息-->
			<el-alert title="添加商品信息" type="info" center show-icon :closable="false"></el-alert>
			<!--步骤条-->
			<el-steps :space="200" :active="activeIndex - 0" finish-status="success" align-center>
				<el-step title="基本信息"></el-step>
				<el-step title="商品参数"></el-step>
				<el-step title="商品属性"></el-step>
				<el-step title="商品图片"></el-step>
				<el-step title="商品内容"></el-step>
				<el-step title="完成"></el-step>
			</el-steps>
			<!--tab标签栏-->
			<el-form :model="addForm" :rules="addFormRules" ref="addFormRef" label-width="70px" label-position="top">
				<el-tabs v-model="activeIndex" tab-position="left" :before-leave="beforeTabLeave">
					<el-tab-pane label="基本信息" name="0">
						<el-form-item label="商品名称" prop="product_name">
							<el-input v-model="addForm.product_name"></el-input>
						</el-form-item>
						<el-form-item label="商品价格" prop="product_price">
							<el-input v-model="addForm.product_price" type="money"></el-input>
						</el-form-item>
						<el-form-item label="商品数量" prop="product_num">
							<el-input v-model="addForm.product_num" type="number"></el-input>
						</el-form-item>
						<el-form-item label="商品分类" prop="product_category_id">
							<el-cascader v-model="addForm.product_category_id" :options="cateList" :props="cascaderProps" @change="handleChange" style="width: 300px"></el-cascader>
						</el-form-item>
					</el-tab-pane>
					<el-tab-pane label="动态属性" name="1">
						<el-form-item :label="item.property_name" v-for="(item,index) in manyTableData" :key="item.property_id">
							<el-checkbox-group v-model="checkedManyTableData[index].property_detail">
								<el-checkbox :label="cb" v-for="(cb,index) in item.property_detail" :key="index" border></el-checkbox>
							</el-checkbox-group>
						</el-form-item>
					</el-tab-pane>
					<el-tab-pane label="静态属性" name="2">
						<el-form-item :label="item.property_name" v-for="item in onlyTableData" :key="item.property_id">
							<el-input v-model="item.property_detail"></el-input>
						</el-form-item>
					</el-tab-pane>
					<el-tab-pane label="商品图片" name="3">
						<el-upload action="/api/product/upload_product_photo" :on-preview="handlePreview"
						           :on-remove="handleRemove" list-type="picture" :headers="headerObj" :on-success="handleSuccess" name="product_photo">
							<el-button type="primary">点击上传</el-button>
						</el-upload>
					</el-tab-pane>
					<el-tab-pane label="商品内容" name="4">
						<quill-editor v-model="addForm.product_context"></quill-editor>
						<el-button type="primary" class="btnAdd" @click="add">添加商品</el-button>
					</el-tab-pane>
				</el-tabs>
			</el-form>
		</el-card>

		<!--图片预览-->
		<el-dialog title="图片预览" width="50%" :visible.sync="previewDialogVisible">
			<!--内容主体-->
			<img :src="previewPath" alt="" class="previewImg">
		</el-dialog>
	</div>
</template>

<script>
	import _ from 'lodash'

	export default {
		name: "Add",
		data() {
			return {
				cateList:[],
				//指定级联选择器的配置对象
				cascaderProps: {
					expandTrigger: 'hover',
					checkStrictly: true,
					value: 'category_id',
					label: 'category_name',
					children: 'category_children'
				},
				activeIndex: '0',
				addForm: {
					product_name: '',
					product_price: 0,
					product_num: 0,
					product_category_id: [],
					product_photo_list: [],
					product_context: '',
					property: [],
					attribute : [],
				},
				queryInfo:{
					querytext:'',
					pagenum:1,
					pagesize:5
				},
				addFormRules: {
					product_name: [
						{required: true, message: '请输入商品名称', trigger: 'blur'}
					],
					product_price: [
						{required: true, message: '请输入商品价格', trigger: 'blur'}
					],

					product_num: [
						{required: true, message: '请输入商品数量', trigger: 'blur'}
					],
					product_category_id: [
						{required: true, message: '请选择商品分类', trigger: 'blur'}
					]
				},
				//供选择的商品参数列表
				manyTableData: [],
				//真正双向绑定的选中的商品参数列表
				checkedManyTableData: [],
				//商品属性列表
				onlyTableData: [],
				//图片上传请求头
				headerObj: {
					Authorization: window.sessionStorage.getItem('token'),

				},
				//预览图片url
				previewPath: '',
				//图片预览对话框显示状态
				previewDialogVisible: false
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
						this.cateList = response.data.object;
						console.log(this.cateList);
					} else {
						this.$message.error(res.meta.msg)
					}
				}).catch(() => {
					this.$message.error('请求失败')
				})
			},
			handleChange() {
				if (this.addForm.product_category_id.length !== 3) {
					this.addForm.product_category_id = []
					return
				}
				//应该在选择商品分类时获取数据，如果在切换标签时获取，无法保存修改后的数据
				//获取商品动态参数数据
				axios.get(`/api/property/search_property_withoutparams`,
						{
							params:
							{
								property_category_id: this.addForm.product_category_id[2],
								property_role :'many',
							}
						}).then(response =>
				{

					if (parseInt(response.data.code) === 200) {
						response.data.object.forEach(item => {
							item.property_detail = item.property_detail ? item.property_detail.split(',') : []
						})
						this.manyTableData = response.data.object;
						//深拷贝一份manyTableData
						this.checkedManyTableData = _.cloneDeep(response.data.object)
					}
					else if (parseInt(response.data.code)===201){
						this.$message.info(response.data.msg);
						this.manyTableData = [];
					}
					else {
						this.$message.error(response.data.msg);
						this.manyTableData = [];
					}
				}).catch(() => {
					this.$message.error('请求失败')
				})
				//获取商品静态属性数据
				axios.get(`/api/property/search_property_withoutparams`,
						{
							params:
									{
										property_category_id: this.addForm.product_category_id[2],
										property_role :'only',
									}
						}).then(response =>
				{

					if (parseInt(response.data.code) === 200) {
						this.onlyTableData = response.data.object;
						//深拷贝一份manyTableData

					}
					else if (parseInt(response.data.code)===201){
						this.$message.info(response.data.msg);
						this.onlyTableData = [];
					}
					else {
						this.$message.error(response.data.msg);
						this.onlyTableData = [];
					}
				}).catch(() => {
					this.$message.error('请求失败')
				})
			},
			//阻止tab标签切换
			beforeTabLeave(activeName, oldActiveName) {
				if (oldActiveName === '0' && this.addForm.product_category_id.length !== 3) {
					this.$message.error('请选择商品分类')
					return false
				}
			},
			//图片预览
			handlePreview(file) {
				this.previewPath = "/api"+ file.response.info
				console.log(this.previewPath)
				this.previewDialogVisible = true
			},
			//移除图片
			handleRemove(file) {
				const filePath = file.response.info;
				const i = this.addForm.product_photo_list.findIndex(x => x.pic === filePath)
				this.addForm.product_photo_list.splice(i, 1)
				console.log(this.addForm.product_photo_list)
			},
			//图片上传成功hook
			handleSuccess(res) {
				console.log(res);
				if (parseInt(res.code) === 200) {
					this.$message.success(res.msg)
					this.addForm.product_photo_list.push(res.info)
					console.log(this.addForm.product_photo_list);
				} else {
					this.$message.error(res.msg)
				}
			},
			//添加商品
			add() {
				this.$refs.addFormRef.validate(valid => {
					if (valid) {
						//深拷贝一份form表单数据
						const form = _.cloneDeep(this.addForm)
						form.product_category_id = form.product_category_id[2];
						//处理商品参数
						this.checkedManyTableData.forEach(item => {
							const newInfo = {
								property_name: item.property_name,
								property_detail: item.property_detail.join(',')
							}
							form.property.push(newInfo)
						})
						//处理商品属性
						this.onlyTableData.forEach(item => {
							const newInfo = {
								property_name: item.property_name,
								property_detail: item.property_detail
							}
							form.attribute.push(newInfo)
						})
						console.log(form);
						axios.post('/api/product/add_product', form,{
							headers: {
								'Content-Type': 'application/json',
								'Authorization' : sessionStorage.getItem('token')
							}
						}).then(response => {

							if (parseInt(response.data.code)=== 200) {
								this.$message.success(response.data.msg)
								this.$router.push('/goods')
							} else {
								this.$message.error(response.data.msg)
							}
						}).catch(() => {
							this.$message.error('操作失败')
						})
					} else {
						return this.$message.error('请填写必要的表单项')
					}
				})
			}
		},
		computed: {
			cateId() {
				if (this.addForm.product_category_id.length === 3) {
					return this.addForm.product_category_id[2]
				}
				return null
			}
		}
	}
</script>

<style lang="less" scoped>
	.el-steps .el-step {
		flex-basis: 16.6667% !important
	}

	.el-checkbox {
		margin: 0 10px 0 0 !important;
	}

	.previewImg {
		width: 100%;
	}

	.btnAdd {
		margin-top: 15px;
	}
</style>