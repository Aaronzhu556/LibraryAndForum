<template>
	<div>
		<!--面包屑导航区-->
		<el-breadcrumb separator-class="el-icon-arrow-right">
			<el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
			<el-breadcrumb-item>反馈管理</el-breadcrumb-item>
			<el-breadcrumb-item>反馈列表</el-breadcrumb-item>
		</el-breadcrumb>

		<el-card>
			<el-row>
				<el-col :span="6">
					<el-input placeholder="请输入内容" v-model="queryInfo.query" :clearable="true" @clear="getOrderList">
						<el-button slot="append" icon="el-icon-search" @click="getOrderList"></el-button>
					</el-input>
				</el-col>
			</el-row>

			<!--table表格-->
			<el-table :data="orderList" border stripe>
				<el-table-column label="序号" type="index" width="50"></el-table-column>
				<el-table-column label="订单编号" prop="order_number"></el-table-column>
				<el-table-column label="订单价格" prop="order_price"></el-table-column>
				<el-table-column label="是否付款">
					<template v-slot="scope">
						<el-tag type="success" v-if="scope.row.pay_status === '1'">已付款</el-tag>
						<el-tag type="danger" v-else>未付款</el-tag>
					</template>
				</el-table-column>
				<el-table-column label="是否发货" prop="is_send"></el-table-column>
				<el-table-column label="下单时间">
					<template v-slot="scope">{{ scope.row.create_time | dateFormat}}</template>
				</el-table-column>
				<el-table-column label="操作" width="200">
					<template v-slot="scope">
						<el-button size="mini" type="primary" icon="el-icon-edit" @click="showEditDialog">编辑</el-button>
						<el-button size="mini" type="success" icon="el-icon-location" @click="showProgressDialog(scope.row)">物流</el-button>
					</template>
				</el-table-column>
			</el-table>

			<!--分页-->
			<el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="queryInfo.pagenum"
			               :page-sizes="[5, 10, 15, 20]" :page-size="queryInfo.pagesize" :total="total"
			               layout="total, sizes, prev, pager, next, jumper" background>
			</el-pagination>
		</el-card>

		<!--修改地址对话框-->
		<el-dialog title="修改地址" width="50%" :visible.sync="editDialogVisible" :close-on-click-modal="false" @close="editDialogClosed">
			<!--内容主体-->
			<el-form :model="editForm" :rules="editFormRules" ref="editFormRef" label-width="90px">
				<el-form-item label="省市区/县" prop="address1">
					<el-cascader :options="citydata" v-model="editForm.address1"></el-cascader>
				</el-form-item>
				<el-form-item label="详细地址" prop="address2">
					<el-input v-model="editForm.address2"></el-input>
				</el-form-item>
			</el-form>
			<!--底部-->
			<span slot="footer">
				<el-button @click="editDialogVisible=false">取 消</el-button>
				<el-button type="primary" @click="editDialogVisible=false">确 定</el-button>
			</span>
		</el-dialog>

		<!--物流进度对话框-->
		<el-dialog title="物流进度" width="50%" :visible.sync="progressDialogVisible">
			<!--内容主体-->
			<el-timeline>
				<el-timeline-item v-for="(activity, index) in progressInfo" :key="index" :timestamp="activity.time">
					{{activity.context}}
				</el-timeline-item>
			</el-timeline>
		</el-dialog>
	</div>
</template>

<script>
	import citydata from "./citydata";

	export default {
		name: "Order",
		data() {
			return {
				queryInfo: {
					query: '',
					pagenum: 2,
					pagesize: 10
				},
				orderList: [],
				total: 0,
				editDialogVisible: false,
				progressDialogVisible: false,
				editForm: {
					address1: [],
					address2: ''
				},
				editFormRules: {
					address1: [
						{required: true, message: '请选择省市区/县', trigger: 'blur'},
					],
					address2: [
						{required: true, message: '请输入详细地址', trigger: 'blur'},
					]
				},
				citydata,
				progressInfo: []
			}
		},
		created() {
			this.getOrderList()
		},
		methods: {
			getOrderList() {
				axios.get('orders', {params: this.queryInfo}).then(response => {
					const res = response.data
					if (res.meta.status === 200) {
						this.orderList = res.data.goods
						this.total = res.data.total
					} else {
						this.$message.error(res.meta.msg)
					}
				}).catch(() => {
					this.$message.error('请求失败')
				})
			},
			//监听pagesize改变的事件
			handleSizeChange(newSize) {
				this.queryInfo.pagesize = newSize
				this.getOrderList()
			},
			//监听页码值改变的事件
			handleCurrentChange(newPage) {
				this.queryInfo.pagenum = newPage
				this.getOrderList()
			},
			showEditDialog() {
				this.editDialogVisible = true
			},
			editDialogClosed() {
				this.$refs.editFormRef.resetFields()
			},
			showProgressDialog() {
				axios.get('kuaidi/1106975712662').then(response => {
					const res = response.data
					if (res.meta.status === 200) {
						this.progressInfo = res.data
						this.progressDialogVisible = true
					} else {
						this.$message.error(res.meta.msg)
					}
				}).catch(() => {
					this.$message.error('请求失败')
				})
			}
		}
	}
</script>

<style lang="less" scoped>
	.el-cascader {
		width: 100%;
	}
</style>