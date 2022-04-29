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
import Appointment from "../components/order/Appointment";
import Forum_List from "../components/Forum/List-forum";
import Forum_details from "../components/Forum/forum-details";
import Forum_List_admin from "../components/forum-admin/List";
import test from "../components/seats/test";
import UserHome from"../components/user/UserHome";
import BookDetails from "../components/books/Book_Details";
import Appointment_seat from "../components/order/Appointment_seat";
import Seat_Admin from "../components/seats/seat_admin";
import Lost from  "../components/FindLost/Lost";
import Lost_admin from "../components/FindLost/Lost_admin";
import Find from "../components/FindLost/Find";
import Find_admin from "../components/FindLost/Find_admin";

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
				path: '/find_admin',
				component: Find_admin
			},
			{
				path: '/find',
				component: Find
			},
			{
				path: '/lost',
				component: Lost
			},
			{
				path: '/lost_admin',
				component : Lost_admin
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
				path: '/appointment',
				component: Appointment
			},
			{
				path: '/forum_list',
				component: Forum_List
			},
			{
				path: '/seat_admin',
				component: Seat_Admin
			},
			{
				path: '/forum_details',
				name: 'forum_details',
				component: Forum_details
			},
			{
				path: '/forum_list_admin',
				component: Forum_List_admin
			},
			{
				path:'/seat_test',
				component: test
			},
			{
				path: "/appointment_seat",
				component: Appointment_seat
			},
			{
				path: '/user_home',
				component: UserHome,
				name: 'user_home'
			},
			{
				path: '/orders',
				component: Order
			},
			{
				path: '/reports',
				component: Report
			},
			{
				path: '/book_details',
				component: BookDetails,
				name: 'bookdetails'
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


const whiteRouter=['/login','/register'];
//挂载路由导航守卫
router.beforeEach((to, from, next) => {
	// to将要访问的路径
	// from代表从哪个路径跳转而来
	// next是一个函数，表示放行
	// next()放行  next('/login')强制跳转
	if (to.path === '/login') return next()
	//获取token
	const tokenStr = window.sessionStorage.getItem('token')
	if (!tokenStr) {
		if (whiteRouter.indexOf(to.path)!==-1){
			return next();
		}

		return next("/login")
	}
	next()
})


export default router
