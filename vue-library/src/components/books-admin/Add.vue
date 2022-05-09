
<template>
	<div>
		<!--面包屑导航区-->
		<el-breadcrumb separator-class="el-icon-arrow-right">
			<el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
			<el-breadcrumb-item>书籍管理</el-breadcrumb-item>
			<el-breadcrumb-item>添加书籍</el-breadcrumb-item>
		</el-breadcrumb>

		<el-card>
			<!--提示信息-->
			<el-alert title="添加书籍信息" type="info" center show-icon :closable="false"></el-alert>
			<!--步骤条-->
			<el-steps :space="200" :active="activeIndex - 0" finish-status="success" align-center>
				<el-step title="基本信息"></el-step>
<!--				<el-step title="商品参数"></el-step>-->
<!--				<el-step title="商品属性"></el-step>-->
				<el-step title="书籍图片"></el-step>
				<el-step title="书籍内容"></el-step>
				<el-step title="完成"></el-step>
			</el-steps>
			<!--tab标签栏-->
			<el-form :model="addForm" :rules="addFormRules" ref="addFormRef" label-width="70px" label-position="top">
				<el-tabs v-model="activeIndex" tab-position="left" :before-leave="beforeTabLeave">
					<el-tab-pane label="基本信息" name="0">
						<el-form-item label="书籍名称" prop="book_name">
							<el-input v-model="addForm.book_name"></el-input>
						</el-form-item>
						<el-form-item label="书籍价格" prop="book_money">
							<el-input v-model="addForm.book_money" type="money"></el-input>
						</el-form-item>
						<el-form-item label="书籍ISBN" prop="book_isbn">
							<el-input v-model="addForm.book_isbn" type="money"></el-input>
						</el-form-item>
						<el-form-item label="书籍出版社" prop="book_address">
							<el-input v-model="addForm.book_address" ></el-input>
						</el-form-item>
						<el-form-item label="书籍作者" prop="book_author">
							<el-input v-model="addForm.book_author" ></el-input>
						</el-form-item>

						<el-form-item label="书籍分类" prop="book_category">
							<el-cascader v-model="addForm.book_category" :options="cateList" :props="cascaderProps" @change="handleChange" style="width: 300px"></el-cascader>
						</el-form-item>
					</el-tab-pane>
<!--					<el-tab-pane label="动态属性" name="1">-->
<!--						<el-form-item :label="item.property_name" v-for="(item,index) in manyTableData" :key="item.property_id">-->
<!--							<el-checkbox-group v-model="checkedManyTableData[index].property_detail">-->
<!--								<el-checkbox :label="cb" v-for="(cb,index) in item.property_detail" :key="index" border></el-checkbox>-->
<!--							</el-checkbox-group>-->
<!--						</el-form-item>-->
<!--					</el-tab-pane>-->
<!--					<el-tab-pane label="静态属性" name="2">-->
<!--						<el-form-item :label="item.property_name" v-for="item in onlyTableData" :key="item.property_id">-->
<!--							<el-input v-model="item.property_detail"></el-input>-->
<!--						</el-form-item>-->
<!--					</el-tab-pane>-->
					<el-tab-pane label="书籍图片" name="1">
						<el-upload action="/api/book/uploadbookimg" :on-preview="handlePreview"
						           :on-remove="handleRemove" list-type="picture" :headers="headerObj"  :limit="1" :on-success="handleSuccess" name="book_img">
							<el-button type="primary">点击上传</el-button>
						</el-upload>
					</el-tab-pane>
					<el-tab-pane label="书籍内容" name="2">
<!--						<quill-editor v-model="addForm.book_context"></quill-editor>-->

						<el-input
								type="textarea"
								:rows="10"
								placeholder="请输入书籍简介"
								v-model="addForm.book_context">
						</el-input>

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
					book_name: '',
					book_money: 0,
					book_isbn: '',
					book_address:'',
					book_context: '',
					book_category: [],
					book_img:'',
					book_author:'',
				},
				queryInfo:{
					querytext:'',
					pagenum:1,
					pagesize:5
				},
				addFormRules: {
					book_name: [
						{required: true, message: '请输入书籍名称', trigger: 'blur'}
					],
					book_money: [
						{required: true, message: '请输入书籍价格', trigger: 'blur'}
					],
					book_address: [
						{required: true, message: '请输入书籍出版社', trigger: 'blur'}
							],
					book_isbn: [
						{required: true, message: '请输入书籍ISBN码', trigger: 'blur'}
					],
					book_category: [
						{required: true, message: '请选择书籍分类', trigger: 'blur'}
					],
					book_author: [
						{required: true, message: '请输入书籍作者', trigger: 'blur'}
					],
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
					querytext:'',
					pagenum:1,
					pagesize:1000,

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
				if (this.addForm.book_category.length !== 2) {
					this.addForm.book_category = []
					return
				}
			},
			//阻止tab标签切换
			beforeTabLeave(activeName, oldActiveName) {
				if (oldActiveName === '0' && this.addForm.book_category.length !== 2) {
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
				/*
				const filePath = file.response.info;
				const i = this.addForm.product_photo_list.findIndex(x => x.pic === filePath)
				this.addForm.product_photo_list.splice(i, 1)
				console.log(this.addForm.product_photo_list)
				 */
				this.addForm.book_img = '';
			},
			//图片上传成功hook
			handleSuccess(res) {
				console.log(res);
				if (parseInt(res.code) === 200) {
					this.$message.success(res.msg)
					this.addForm.book_img = "/api"+ res.info
					console.log(this.addForm.book_img);
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
						form.book_category = this.addForm.book_category[1];


						console.log(form);
						axios.post('/api/book/addbook', form,{
							headers: {
								'Content-Type': 'application/json',
								'Authorization' : sessionStorage.getItem('token')
							}
						}).then(response => {
							if (parseInt(response.data.code)=== 200) {
								this.$message.success(response.data.msg)
								this.$router.push('/books_admin')
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
				if (this.addForm.book_category.length === 2) {
					return this.addForm.book_category[1]
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