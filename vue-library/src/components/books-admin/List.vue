<template>
	<div>
		<!--面包屑导航区-->
		<el-breadcrumb separator-class="el-icon-arrow-right">
			<el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
			<el-breadcrumb-item>图书管理</el-breadcrumb-item>
			<el-breadcrumb-item>图书列表</el-breadcrumb-item>
		</el-breadcrumb>

		<el-card>
			<!--搜索与添加-->
			<el-row :gutter="10">
				<el-col :span="6">
					<el-input placeholder="请输入内容" v-model="queryInfo.querytext" :clearable="true" @clear="getBookList">
						<el-button slot="append" icon="el-icon-search" @click="getBookList"></el-button>
					</el-input>
				</el-col>
				<el-col :span="6">
					<el-button type="primary" @click="goAddPage" >添加书籍</el-button>
				</el-col>
			</el-row>

			<!--table表格-->
			<el-table :data="booksList_admin" border stripe>
				<el-table-column label="书籍编号" prop="book_id"></el-table-column>

				<el-table-column label="书籍名" prop="book_name"></el-table-column>
				<el-table-column label="书籍图片" >
					<template slot-scope="scope">
						<el-button type="info" icon="el-icon-zoom-in" circle @click="PreviewPhoto(scope.row.product_photo_list)"></el-button>
					</template>
				</el-table-column>
				<el-table-column label="书籍ISBN码" prop="book_isbn"></el-table-column>


				<el-table-column label="书籍价格(元)" prop="book_money" ></el-table-column>
				<el-table-column label="出版社" prop="book_address" ></el-table-column>
				<el-table-column label="书籍状态" prop= "book_status" width="200">
					<template slot-scope="scope">
						<el-tag size="small" type="success" v-if="scope.row.book_status=='1'">空闲</el-tag>
						<el-tag size="small" type="danger" v-else-if="scope.row.book_status=='0'" >已被借出</el-tag>
						<el-tag size="small" type="warning" v-else >已被预约</el-tag>
					</template>
				</el-table-column>
				<el-table-column label="操作"  width="200">
					<template slot-scope="scope">
						<el-button type="danger" icon="el-icon-delete" @click="open(scope.row)"  circle></el-button>
					</template>
				</el-table-column>
			</el-table>

			<!--分页-->
			<el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="queryInfo.pagenum"
			               :page-sizes="[4, 8, 16]" :page-size="queryInfo.pagesize" :total="total"
			               layout="total, sizes, prev, pager, next, jumper" background>
			</el-pagination>
		</el-card>
		<!--图片预览-->
		<el-dialog title="图片预览" width="50%" :visible.sync="previewDialogVisible" scroll-container>
			<!--内容主体-->

			<img :src="previewPath" alt="" class="previewImg">
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
					pagesize: 4,
					querydata:'',
				},
				booksList_admin: [],
				total: 0,
				previewDialogVisible: false,
				previewPath : "",
			}
		},
		created() {
			this.getBookList();
			this.managerRole = sessionStorage.getItem('managerRole');
			console.log(this.managerRole);
		},
		methods: {
			getBookList(){
				axios.post('/api/book/querybooks',JSON.stringify({
					querytext:this.queryInfo.querytext,
					pagenum:this.queryInfo.pagenum,
					pagesize:this.queryInfo.pagesize,
					querydata: this.queryInfo.querydata,

				}), {
					headers: {
						'Content-Type': 'application/json'
					}

				}).then(response => {
					if (parseInt(response.data.code) === 200) {
						this.booksList_admin = response.data.object;
						this.queryInfo.querydata = this.queryInfo.querytext;
						if (parseInt(response.data.page)===1){
							this.queryInfo.pagenum = parseInt(response.data.page);
						}
						console.log("Hi 这里出错啦")
						console.log(this.bookList)
						this.total = parseInt(response.data.info);
					} else {
						this.$message.info(response.data.msg)
					}
				}).catch((e) => {
					this.$message.error("获取失败")
					console.log(e);
				})
			},
			/*
			*预览图片 懒加载模式
			* */
			/*
			PreviewPhoto(current_data){
				this.product_photos = current_data;
				console.log(this.product_photos)
				for (var i=0;i<this.product_photos.length;i++){
					this.product_photos[i] = "/api" + this.product_photos[i];
				}
				console.log(this.product_photos);
				this.previewDialogVisible = true;
			},

			 */
			PreviewPhoto(current_data){
				this.previewPath = "https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png";
				this.previewDialogVisible = true;
			},
			deleteBooks(current_data){
				axios.get('/api/book/deletebooks',{
					params:{
						book_id : current_data.book_id,
					},
					headers:{Authorization: sessionStorage.getItem('token')}
				}).then((response) => {
					if (parseInt(response.data.code) === 200){
						this.getBookList();
						this.$message.success(response.data.msg);
					}
					else if (parseInt(response.data.code) === 201){
						this.$message.error(response.data.msg);
					}else {
						this.$message.error(response.data.msg);
					}
				}).catch(()=> {
					this.$message.error('操作失败');
				})
			},
			open(current_data){
				this.$confirm('此操作将永久删除该图书, 是否继续?', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					this.deleteBooks(current_data);
				}).catch(() => {
					this.$message({
						type: 'info',
						message: '已取消删除'
					});
				});
			},

			//监听pagesize改变的事件
			handleSizeChange(newSize) {
				this.queryInfo.pagesize = newSize
				this.getBookList()
			},
			//监听页码值改变的事件
			handleCurrentChange(newPage) {
				this.queryInfo.pagenum = newPage
				this.getBookList()
			},

			goAddPage(){
				this.$router.push('/books_admin/add')
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