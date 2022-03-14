<template>
	<div>
		<!--面包屑导航区-->
		<el-breadcrumb separator-class="el-icon-arrow-right">
			<el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
			<el-breadcrumb-item>商品管理</el-breadcrumb-item>
			<el-breadcrumb-item>编辑商品</el-breadcrumb-item>
		</el-breadcrumb>

		<el-card>
			<!--提示信息-->
			<el-alert title="编辑商品信息" type="info" center show-icon :closable="false"></el-alert>
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
			<el-form :model="editForm" :rules="editFormRules" ref="editFormRef" label-width="70px" label-position="top">
				<el-tabs v-model="activeIndex" tab-position="left">
					<el-tab-pane label="基本信息" name="0">
						<el-form-item label="商品名称" prop="goods_name">
							<el-input v-model="editForm.goods_name"></el-input>
						</el-form-item>
						<el-form-item label="商品价格" prop="goods_price">
							<el-input v-model="editForm.goods_price" type="number"></el-input>
						</el-form-item>
						<el-form-item label="商品重量" prop="goods_weight">
							<el-input v-model="editForm.goods_weight" type="number"></el-input>
						</el-form-item>
						<el-form-item label="商品数量" prop="goods_number">
							<el-input v-model="editForm.goods_number" type="number"></el-input>
						</el-form-item>
						<el-form-item label="商品分类">
							<el-cascader v-model="editForm.goods_cat" :options="cateList" :props="cascaderProps" style="width: 300px" disabled></el-cascader>
						</el-form-item>
					</el-tab-pane>
					<el-tab-pane label="商品参数" name="1">
						<el-form-item :label="item.attr_name" v-for="item in manyTableData" :key="item.attr_id">
							<el-checkbox-group v-model="item.attr_value">
								<el-checkbox :label="cb" v-for="(cb,index) in item.attr_vals" :key="index" border></el-checkbox>
							</el-checkbox-group>
						</el-form-item>
					</el-tab-pane>
					<el-tab-pane label="商品属性" name="2">
						<el-form-item :label="item.attr_name" v-for="item in onlyTableData" :key="item.attr_id">
							<el-input v-model="item.attr_vals"></el-input>
						</el-form-item>
					</el-tab-pane>
					<el-tab-pane label="商品图片" name="3">
						<el-upload action="http://127.0.0.1:8888/api/private/v1/upload" :on-preview="handlePreview" :file-list="imgList"
						           :on-remove="handleRemove" list-type="picture" :headers="headerObj" :on-success="handleSuccess">
							<el-button type="primary">点击上传</el-button>
						</el-upload>
					</el-tab-pane>
					<el-tab-pane label="商品内容" name="4">
						<quill-editor v-model="editForm.goods_introduce"></quill-editor>
						<el-button type="primary" class="btnEdit" @click="postEdit">编辑商品</el-button>
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
	export default {
		name: "Edit",
		data() {
			return {
				cateList: [],
				//指定级联选择器的配置对象
				cascaderProps: {
					expandTrigger: 'hover',
					value: 'cat_id',
					label: 'cat_name',
					children: 'children'
				},
				activeIndex: '0',
				editForm: {
					goods_name: '',
					goods_price: 0,
					goods_weight: 0,
					goods_number: 0,
					goods_cat: [],
					pics: [],
					goods_introduce: '',
					attrs: []
				},
				editFormRules: {
					goods_name: [
						{required: true, message: '请输入商品名称', trigger: 'blur'}
					],
					goods_price: [
						{required: true, message: '请输入商品价格', trigger: 'blur'}
					],
					goods_weight: [
						{required: true, message: '请输入商品重量', trigger: 'blur'}
					],
					goods_number: [
						{required: true, message: '请输入商品数量', trigger: 'blur'}
					]
				},
				//商品参数列表
				manyTableData: [],
				//商品属性列表
				onlyTableData: [],
				//已上传图片列表
				imgList: [],
				//待上传图片列表
				tmpImgList: [],
				//图片上传请求头
				headerObj: {
					Authorization: window.sessionStorage.getItem('token')
				},
				//预览图片url
				previewPath: '',
				//图片预览对话框显示状态
				previewDialogVisible: false
			}
		},
		created() {
			this.getCateList()
			this.getGoodsInfo()
		},
		methods: {
			//获取商品信息
			getGoodsInfo() {
				axios.get(`goods/${this.$route.params.id}`).then(response => {
					const res = response.data
					if (res.meta.status === 200) {
						this.editForm = res.data
						// split 得到的字符数组不能绑定到级联选择器，用map转数字数组  ['1','3','6'] => [1,3,6]
						this.editForm.goods_cat = this.editForm.goods_cat.split(',').map(Number)
						this.getAttrData()
						this.getImgList()
					} else {
						this.$message.error(res.meta.msg)
					}
				}).catch(() => {
					this.$message.error("请求失败")
				})
			},
			//获取商品分类数据
			getCateList() {
				axios.get('categories').then(response => {
					const res = response.data
					if (res.meta.status === 200) {
						this.cateList = res.data
					} else {
						this.$message.error(res.meta.msg)
					}
				}).catch(() => {
					this.$message.error('请求失败')
				})
			},
			getAttrData() {
				//处理商品参数数据
				this.editForm.attrs.forEach(item => {
					if (item.attr_sel === 'many') {
						//全部参数
						item.attr_vals = item.attr_vals ? item.attr_vals.split(',') : []
						//已选参数
						item.attr_value = item.attr_value ? item.attr_value.split(',') : []
						this.manyTableData.push(item)
					} else if (item.attr_sel === 'only') {
						this.onlyTableData.push(item)
					}
				})
			},
			getImgList() {
				this.editForm.pics.forEach(item => {
					this.imgList.push({
						name: item.pics_id,
						url: item.pics_big_url,
						pic: item.pics_big
					})
				})
			},
			//图片预览
			handlePreview(file) {
				this.previewPath = file.url
				this.previewDialogVisible = true
			},
			//移除图片
			handleRemove(file) {
				if (file.response) {
					const filePath = file.response.data.tmp_path
					const i = this.tmpImgList.findIndex(x => x.pic === filePath)
					this.tmpImgList.splice(i, 1)
				} else {
					const filePath = file.pic
					const i = this.imgList.findIndex(x => x.pic === filePath)
					this.imgList.splice(i, 1)
				}
			},
			//图片上传成功hook
			handleSuccess(response) {
				if (response.meta.status === 200) {
					this.$message.success(response.meta.msg)
					this.tmpImgList.push({pic: response.data.tmp_path})
				} else {
					this.$message.error(response.meta.msg)
				}
			},
			//提交编辑商品
			postEdit() {
				this.$refs.editFormRef.validate(valid => {
					if (valid) {
						/*let pics = []
						this.imgList.forEach(item => {
							pics.push({pic: item.pic})
						})
						pics.push(...this.tmpImgList)*/

						const form = {
							goods_name: this.editForm.goods_name,
							goods_price: this.editForm.goods_price,
							goods_number: this.editForm.goods_number,
							goods_weight: this.editForm.goods_weight,
							goods_introduce: this.editForm.goods_introduce,
							// pics: pics,
							attrs: [],
							goods_cat: ''
						}
						form.goods_cat = this.editForm.goods_cat.join(',')
						//处理商品参数
						this.manyTableData.forEach(item => {
							const newInfo = {
								attr_id: item.attr_id,
								attr_value: item.attr_value.join(',')
							}
							form.attrs.push(newInfo)
						})
						//处理商品属性
						this.onlyTableData.forEach(item => {
							const newInfo = {
								attr_id: item.attr_id,
								attr_value: item.attr_vals
							}
							form.attrs.push(newInfo)
						})

						axios.put(`goods/${this.editForm.goods_id}`, form).then(response => {
							const res = response.data
							if (res.meta.status === 200) {
								this.$message.success(res.meta.msg)
								this.$router.push('/goods')
							} else {
								this.$message.error(res.meta.msg)
							}
						}).catch(() => {
							this.$message.error('操作失败')
						})
					} else {
						return this.$message.error('请填写必要的表单项')
					}
				})
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

	.btnEdit {
		margin-top: 15px;
	}
</style>