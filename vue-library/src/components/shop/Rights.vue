<template>
	<div>
		<!--面包屑导航区-->
		<el-breadcrumb separator-class="el-icon-arrow-right">
			<el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
			<el-breadcrumb-item>店铺管理</el-breadcrumb-item>
			<el-breadcrumb-item>店铺列表</el-breadcrumb-item>
		</el-breadcrumb>

		<!--卡片视图区域-->
		<el-card>
			<!--权限列表-->
			<el-table :data="rightsList" :border="true" :stripe="true">
				<el-table-column label="序号" type="index" width="50"></el-table-column>
				<el-table-column label="权限名称" prop="authName"></el-table-column>
				<el-table-column label="路径" prop="path"></el-table-column>
				<el-table-column label="权限等级" prop="level">
					<template v-slot="scope">
						<el-tag v-if="scope.row.level==='0'">一级</el-tag>
						<el-tag type="success" v-if="scope.row.level==='1'">二级</el-tag>
						<el-tag type="warning" v-if="scope.row.level==='2'">三级</el-tag>
					</template>
				</el-table-column>
			</el-table>
		</el-card>
	</div>
</template>

<script>
	export default {
		name: "Rights",
		data() {
			return {
				//权限列表
				rightsList: []
			}
		},
		created() {
			this.getRightsList()
		},
		methods: {
			//获取权限列表
			getRightsList() {
				axios.get('rights/list').then(response => {
					const res = response.data
					if (res.meta.status === 200) {
						this.rightsList = res.data
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

</style>