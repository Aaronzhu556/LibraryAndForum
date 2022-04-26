<template>
	<el-container class="home-container">
		<!--头部-->
		<el-header>
			<div>
				<img src="../assets/logo.png" alt="" height="60">
				<span>社区图书馆服务平台</span>

			</div>


			<el-button v-if="this.user_role==='user'" type="info" class="el-icon-user"  @click="goToUserHome">个人中心</el-button>
			<el-button type="info" @click="logout">退出</el-button>

		</el-header>
		<!--页面主体-->
		<el-container>
			<!--侧边栏-->
			<el-aside :width="isCollapse? '64px' : '200px'">
				<div class="toggle-button" @click="isCollapse=!isCollapse"><i :class="isCollapse?'el-icon-s-unfold':'el-icon-s-fold'"></i></div>
				<!--菜单-->
				<el-menu background-color="#333744" text-color="#fff" active-text-color="#409eff" :default-openeds="defaultOpeneds"
				         :unique-opened="false" :collapse="isCollapse" :collapse-transition="false"
				         :router="true" :default-active="activePath">
					<!-- 一级菜单 -->
					<el-submenu :index="item.id + ''" v-for="item in menulist" :key="item.id">
						<!-- 一级菜单的模板区域 -->
						<template slot="title">
							<i :class="iconsObj[item.id]"></i>
							<span>{{ item.authName }}</span>
						</template>
						<!-- 二级菜单 -->
						<el-menu-item :index="'/'+subItem.path" v-for="subItem in item.children" :key="subItem.id" @click="saveNavState('/'+subItem.path)">
							<template slot="title">
								<i class="el-icon-menu"></i>
								<span>{{ subItem.authName }}</span>
							</template>
						</el-menu-item>
					</el-submenu>
				</el-menu>
			</el-aside>
			<!--右侧内容主体-->
			<el-main>
				<router-view></router-view>
			</el-main>
		</el-container>
	</el-container>
</template>

<script>
	export default {
		name: "Home",
		data() {
			return {
				menulist: [],
				iconsObj: {
					'125': 'iconfont el-icon-user-solid',
					'103': 'iconfont el-icon-s-grid',
					'101': 'iconfont el-icon-s-management',
					'102': 'iconfont el-icon-s-order',
					'145': 'iconfont el-icon-s-data',

					'150': 'iconfont el-icon-collection',
					'151': 'iconfont el-icon-s-grid',
					'152': 'el-icon-tickets',
					'153': 'el-icon-user-solid',
					'155' : 'el-icon-reading',

				},
				//是否折叠
				user_name:'',
				isCollapse: false,
				//被激活的链接地址
				activePath: '',
				//默认打开的菜单
				defaultOpeneds: [],
				userInfo:{}
			}
		},
		created() {
			this.activePath = window.sessionStorage.getItem('activePath')
			this.user_name_special = window.sessionStorage.getItem('user_name');
			this.user_role = sessionStorage.getItem('role');
			this.getMenuList()

			console.log(this.user_name)
		},
		methods: {
			logout() {
				window.sessionStorage.clear()
				this.$router.push('/login')
			},
			//获取所有的菜单
			getMenuList() {

				//权限管理最后弄  怕写不完
				const role = sessionStorage.getItem('role');
				console.log(role);
				if (role==="user") {
					this.menulist = [
						{
							"id": 150,
							"authName": "图书借阅",
							"path" :"books",
							"children" :[{"id" : 151,"authName" : "图书检索与借阅", "path" : "books", "children" : [],"order": null},
							 ],
							"order": 2
						},
						 {
						"id": 103,
						"authName": "书友论坛",
						"path": "rights",
						"children": [{
							"id": 111,
							"authName": "书友论坛",
							"path": "forum_list",
							"children": [],
							"order": null
						}],
						"order": 2
					}, {
						"id": 101,
						"authName": "位置预约",
						"path": "seat_test",
						"children": [{
							"id": 104,
							"authName": "位置预约",
							"path": "seat_test",
							"children": [],
							"order": 1
						}],
						"order": 3
					}]
				}
				else if (role==="manager"){
					this.menulist=[{
						"id": 101,
						"authName": "书籍管理",
						"path": "books_admin",
						"children": [{
							"id": 104,
							"authName": "书籍列表",
							"path": "books_admin",
							"children": [],
							"order": 1
						},  {
							"id": 121,
							"authName": "书籍分类",
							"path": "categories",
							"children": [],
							"order": 3
						}],
						"order": 3
					}, {
						"id": 102,
						"authName": "预约管理",
						"path": "orders",
						"children": [{"id": 107, "authName": "书籍预约列表", "path": "appointment", "children": [], "order": 1}
							],
						"order": 4
					},
						{
						"id": 152,
						"authName": "论坛管理",
						"path": "forum_list_admin",
						"children": [{"id": 107, "authName": "论坛管理", "path": "forum_list_admin", "children": [], "order": null}],
						"order": 4
					},
						{
						"id": 125,
						"authName": "用户管理",
						"path": "users",
						"children": [{"id": 110, "authName": "用户列表", "path": "users", "children": [], "order": null}],
						"order": 2
					},
						{
							"id": 155,
							"authName": "自习室管理",
							"path": "seat_admin",
							"children": [{"id": 155, "authName": "自习室列表", "path": "seat_admin", "children": [], "order": null}],
							"order": 2
						},
						{
						"id": 145,
						"authName": "数据统计",
						"path": "reports",
						"children": [{"id": 146, "authName": "数据报表", "path": "reports", "children": [], "order": null}],
						"order": 5
					}]
				}
				// axios.get('menus').then(response => {
				// 	const res = response.data
				// 	if (res.meta.status === 200) {
				// 		res.data.forEach(item => {
				// 			this.defaultOpeneds.push('' + item.id)
				// 		})
				// 		this.menulist = res.data
				// 	} else {
				// 		this.$message.error(res.meta.msg)
				// 	}
				// }).catch(() => {
				// 	this.$message.error("获取失败")
				// })
			},
			//保存链接的激活状态
			saveNavState(activePath) {
				this.activePath = activePath
				window.sessionStorage.setItem('activePath', activePath)
			},
			goToUserHome(){
				this.$router.push({path: '/user_home', query: {articleusername:this.user_name_special}})
			},
		}
	}
</script>

<style lang="less" scoped>
	.home-container {
		height: 100%;
	}

	.el-header {
		background-color: #373D41;
		display: flex;
		justify-content: space-between;
		padding-left: 10px;
		align-items: center;
		color: #ffffff;
		font-size: 22px;

		> div {
			display: flex;
			align-items: center;

			span {
				margin-left: 15px;
			}
		}
	}

	.el-aside {
		background-color: #333744;

		.el-menu {
			border-right: none;
		}
	}

	.el-main {
		background-color: #eaedf1;
	}

	.iconfont {
		margin-right: 20px;
		font-size: 20px;
	}

	.toggle-button {
		background-color: #4a5064;
		font-size: 20px;
		line-height: 40px;
		color: #ffffff;
		text-align: center;
		cursor: pointer;
	}
</style>