import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from "../components/Login";
import Home from "../components/Home";
import Welcome from "../components/Welcome";
import Users from "../components/user/Users";
import Managers from "../components/user/Managers";
import Rights from "../components/shop/Rights";
import Roles from "../components/shop/Shops";
import Cate from "../components/books-admin/Cate";
import Params from "../components/books-admin/Params";
import BooksList from "../components/books-admin/List";
import BooksAdd from "../components/books-admin/Add";
import BooksEdit from "../components/books-admin/Edit";
import Order from "../components/order/Order";
import Report from "../components/report/Report";
import Register from "../components/Register";
import Books from "../components/books/Books";

Vue.use(VueRouter)

const routes = [
	{
		path: '/',
		redirect: '/login'
	},
	{
		path: '/login',
		component: Login
	},
	{
		path: '/register',
		component: Register
	},
	{
		path: '/home',
		component: Home,
		redirect: '/welcome',
		children: [
			{
				path: '/welcome',
				component: Welcome
			},
			{
				path: '/books',
				component: Books
			},
			{
				path: '/users',
				component: Users
			},
			{
				path: '/managers',
				component: Managers
			},
			{
				path: '/rights',
				component: Rights
			},
			{
				path: '/roles',
				component: Roles
			},
			{
				path: '/categories',
				component: Cate
			},
			{
				path: '/params',
				component: Params
			},
			{
				path: '/books_admin',
				component: BooksList
			},
			{
				path: '/books_admin/add',
				component: BooksAdd
			},
			{
				path: '/books_admin/edit/:id',
				component: BooksEdit
			},
			{
				path: '/orders',
				component: Order
			},
			{
				path: '/reports',
				component: Report
			}
		]
	}
	// {
	//   path: '/about',
	//   name: 'About',
	//   // route level code-splitting
	//   // this generates a separate chunk (about.[hash].js) for this route
	//   // which is lazy-loaded when the route is visited.
	//   component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
	// }
]

const router = new VueRouter({
	mode: 'history',
	base: process.env.BASE_URL,
	routes
})

const originalPush = VueRouter.prototype.push;
VueRouter.prototype.push = function(location) {
	return originalPush.call(this, location).catch(err => err)
}

//挂载路由导航守卫
router.beforeEach((to, from, next) => {
	// to将要访问的路径
	// from代表从哪个路径跳转而来
	// next是一个函数，表示放行
	// next()放行  next('/login')强制跳转
	if (to.path === '/login') return next()
	//获取token
	const tokenStr = window.sessionStorage.getItem('token')
	if (!tokenStr) return next("/login")
	next()
})


export default router
