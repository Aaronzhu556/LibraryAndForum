<template>
	<div>
		<!--面包屑导航区-->
		<el-breadcrumb separator-class="el-icon-arrow-right">
			<el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
			<el-breadcrumb-item>反馈管理</el-breadcrumb-item>
			<el-breadcrumb-item>反馈列表</el-breadcrumb-item>
		</el-breadcrumb>

		<el-card>
			<!--搜索与添加-->
			<el-row :gutter="10">
				<el-col :span="6">
					<el-input placeholder="请输入内容" v-model="queryInfo.querytext" :clearable="true" @clear="getGoodsList">
						<el-button slot="append" icon="el-icon-search" @click="getGoodsList"></el-button>
					</el-input>
				</el-col>
<!--				<el-col :span="6">-->
<!--					<el-button type="primary" @click="goAddPage" :disabled="managerRole=='saler' ? false :true">添加商品</el-button>-->
<!--				</el-col>-->
			</el-row>

			<!--table表格-->
			<el-table :data="goodsList" border stripe>
				<el-table-column type="expand" width="50">
					<template slot-scope="props">
						<el-form label-position="left" inline class="demo-table-expand">
							<el-form-item label="反馈内容:">
								<span>{{props.row.feedback_text}}</span>
							</el-form-item>
						</el-form>
					</template>
				</el-table-column>
				<el-table-column label="反馈编号" prop="feedback_id"></el-table-column>

				<el-table-column label="反馈人" prop="feedback_name"></el-table-column>
<!--				<el-table-column label="商品图片" >-->
<!--					<template slot-scope="scope">-->
<!--						<el-button type="info" icon="el-icon-zoom-in" circle @click="PreviewPhoto(scope.row.product_photo_list)"></el-button>-->
<!--					</template>-->
<!--				</el-table-column>-->
				<el-table-column label="反馈标题" prop="feedback_title"></el-table-column>
<!--				<el-table-column label="反馈内容" prop="feedback_title"></el-table-column>-->

<!--				<el-table-column label="商品价格(元)" prop="product_price" width="130"></el-table-column>-->
<!--				<el-table-column label="商品库存" prop="product_num" width="130"></el-table-column>-->
<!--				<el-table-column label="商家名" prop="product_shop_name" width="130"></el-table-column>-->
				<el-table-column label="状态变更" prop= "feedback_status" width="200">
					<template slot-scope="scope">
						<el-switch
								active-color="#13ce66"
								inactive-color="#ff4949"
								v-model="scope.row.feedback_status"
								active-value="1"
								inactive-value="0"
								active-text="已解决"
								inactive-text="未解决"
								@change="changeSwitch(scope.row,scope.$index)">
						</el-switch>
					</template>
				</el-table-column>
			</el-table>

			<!--分页-->
			<el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="queryInfo.pagenum"
			               :page-sizes="[5, 10, 15, 20]" :page-size="queryInfo.pagesize" :total="total"
			               layout="total, sizes, prev, pager, next, jumper" background>
			</el-pagination>
		</el-card>
		<!--图片预览-->
		<el-dialog title="图片预览" width="50%" :visible.sync="previewDialogVisible" scroll-container>
			<!--内容主体-->
			<el-image v-for="photo in product_photos"  :src="photo" lazy></el-image>
<!--			<img :src="previewPath" alt="" class="previewImg">-->
		</el-dialog>
	</div>
</template>

<script>
	export default {
		name: "List",
		data() {
			return {
				queryInfo: {
					querytext: '',
					pagenum: 1,
					pagesize: 5
				},
				goodsList: [],
				total: 0,
				managerRole:'',
				previewDialogVisible: false,
				product_photos : [],
			}
		},
		created() {
			this.getGoodsList();
			this.managerRole = sessionStorage.getItem('managerRole');
			console.log(this.managerRole);
		},
		methods: {
			getGoodsList() {
				axios.post('/api/feedback/Searchall',JSON.stringify({
					//querytext:this.queryInfo.querytext,
					pagenum:this.queryInfo.pagenum,
					pagesize:this.queryInfo.pagesize,

				}), {
					headers: {
						'Content-Type': 'application/json'
					}

				}).then(response => {
					if (parseInt(response.data.code) === 200) {
						this.goodsList = response.data.object;

						this.total = parseInt(response.data.info);
					} else {
						this.$message.info(response.data.msg)
					}
				}).catch(() => {
					this.$message.error("获取失败")
				})
			},
			/*
			*预览图片 懒加载模式
			* */
			PreviewPhoto(current_data){
				this.product_photos = current_data;
				console.log(this.product_photos)
				for (var i=0;i<this.product_photos.length;i++){
					this.product_photos[i] = "/api" + this.product_photos[i];
				}
				console.log(this.product_photos);
				this.previewDialogVisible = true;
			},
			changeSwitch(current_data,index){
				axios.get('/api/feedback/update_status',
						{
							params:{
								feedback_id : current_data.feedback_id,
								feedback_status : current_data.feedback_status,

							},
							headers: {Authorization: sessionStorage.getItem('token')}
						}

				).then((response)=>{

					if (parseInt(response.data.code)===200){
						this.getGoodsList();
						this.$message.success(response.data.msg);

					}
					else if (parseInt(response.data.code)===201){
						this.goodsList[index].feedback_status = current_data.feedback_status;
						this.$message.error(response.data.msg);
					}
					else {
						this.goodsList[index].feedback_status = current_data.feedback_status;
						this.$message.error(response.data.msg);
					}
				}).catch(() => {
					this.goodsList[index].feedback_status = current_data.feedback_status;
					this.$message.error('操作失败');
				})
			},
			//监听pagesize改变的事件
			handleSizeChange(newSize) {
				this.queryInfo.pagesize = newSize
				this.getGoodsList()
			},
			//监听页码值改变的事件
			handleCurrentChange(newPage) {
				this.queryInfo.pagenum = newPage
				this.getGoodsList()
			},
			removeById(id){
				axios.delete(`goods/${id}`).then(response => {
					const res = response.data
					if (res.meta.status === 200) {
						this.$message.success(res.meta.msg)
						this.getGoodsList()
					} else {
						this.$message.error(res.meta.msg)
					}
				}).catch(() => {
					this.$message.error("操作失败")
				})
			},
			goAddPage(){
				this.$router.push('/goods/add')
			},
			goEditPage(id){
				this.$router.push(`/goods/edit/${id}`)
			}
		}
	}
</script>

<style lang="less" scoped>
	.el-button {
		margin-right: 10px;
	}
</style>