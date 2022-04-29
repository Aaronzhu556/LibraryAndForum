<template  >
	<div v-if="role==='user'">
		<!--面包屑导航区-->
		<el-breadcrumb separator-class="el-icon-arrow-right">
			<el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
		</el-breadcrumb>


		<el-card>
			<el-carousel :interval="4000" type="card" height="200px">
				<el-carousel-item v-for="(item,index) in showList" :key="index">
					<div>
						<img :src="item.img" class="image" >

					</div>
				</el-carousel-item>
			</el-carousel>
		</el-card>
		<br>

		<el-card>
			<strong>书籍个性化推荐</strong>
			<el-button type="text" @click="getFour" icon="el-icon-refresh" style="float: right"> 换一批</el-button>
			<el-divider></el-divider>
			<el-row>
				<el-col :span="4" v-for="(book, index) in personalBookList" :key="index" :offset="index > 0 ? 2 : 0">
					<el-card :body-style="{ padding: '0px' }">
						<img :src="book.book_img" class="image"style="height: 162px;width: 162px">
						<div style="padding: 14px;">
							<span font-family="Helvetica Neu"><strong>{{book.book_name}}</strong></span>
							<el-rate
									v-model="book.book_star"
									disabled
									show-score
									text-color="#ff9900"
							>
							</el-rate>
							<div class="bottom clearfix">

								<el-button  class="button" @click="goToBookDetails(book.book_id)">查看图书详情</el-button>
							</div>
						</div>
					</el-card>
				</el-col>
			</el-row>
		</el-card>

		<br>
		<el-card>
			<strong>活跃用户展示</strong>
			<el-divider></el-divider>
			<el-row>
				<el-col :span="4" v-for="(user, index) in userHotList" :key="index" :offset="index > 0 ? 2 : 0">
					<el-card :body-style="{ padding: '0px' }">
<!--						<img :src="book.book_img" class="image"style="height: 162px;width: 162px">-->
						<el-avatar :src="user.user_img" style="position: relative;left: 80px;top: 10px"></el-avatar>
						<div style="padding: 14px;">
							<div style="text-align: center"><span font-family="Helvetica Neu"  ><strong>{{user.user_name}}</strong></span></div>

								<div style="text-align: center">
								<span class="el-icon-document">发帖数:{{user.user_article_num}} </span>
								</div>
								<div style="text-align: center">
								<span class="el-icon-star-off">获赞数:{{user.user_great}}</span>
								</div>
								<div style="text-align: center">
								<span class="el-icon-more-outline">粉丝数:{{user.user_fans}}</span>
								</div>

							<div style="text-align: center">
									<span class="el-icon-user"><el-link @click.native="goToUserDetail(user.user_name)">个人主页</el-link></span>
<!--								<el-button  class="button" @click="goToBookDetails(book.book_id)">查看图书详情</el-button>-->
							</div>
						</div>
					</el-card>
				</el-col>
			</el-row>
		</el-card>

	</div>
	<div v-else>
		<!--面包屑导航区-->
		<el-breadcrumb separator-class="el-icon-arrow-right">
			<el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
		</el-breadcrumb>
		<el-card   style="width: 100%;height: 750px;" >
			<el-card class="introduce">
				<div class="order">
					<el-card class="order-item">
						<template #header>
							<div class="card-header">
								<span>用户总数</span>
							</div>
						</template>
						<div class="item">{{this.userNum}}</div>
					</el-card>
					<el-card class="order-item">
						<template #header>
							<div class="card-header">
								<span>书籍数</span>
							</div>
						</template>
						<div class="item">{{this.bookNum}}</div>
					</el-card>
					<el-card class="order-item">
						<template #header>
							<div class="card-header">
								<span>自习室利用率</span>
							</div>
						</template>
						<div class="item">{{this.userSeatRatio}}%</div>
					</el-card>
				</div>
			</el-card>

			<el-card id="book" style="width: 50%;height: 400px; position: relative;top: 20px"   ></el-card>


			<el-card id="seat" style="width: 50%;height: 400px; position: relative;left: 620px;top: -380px"  ></el-card>


		</el-card>

	</div>


</template>


<script>
	export default {
		name: "Welcome",
		data(){
			return {
				showList:[
					{id:0,img:require('../assets/1.jpg')},
					{id:1,img:require('../assets/2.jpg')},
					{id:2,img:require('../assets/3.jpg')}
				],
				personalBookList:[],
				bookList:[],
				role:'',
				bookNum:0,
				userNum:0,
				userHotList:[],
				seatList:[],
				userSeatRatio:0,
				mychart_book_data_x:[],
				mychart_book_data_y:[],
				BookChartBar:'',
				BookChartBaroption:{
					title: {
						text: '图书馆书籍借阅信息统计',
					},
					grid:{//直角坐标系内绘图网格
						show:true,//是否显示直角坐标系网格。[ default: false ]
						left:"20%",//grid 组件离容器左侧的距离。
						right:"30px",
						borderColor:"#c45455",//网格的边框颜色
						bottom:"20%" //
					},

					legend: {
						data: ['借阅量']
					},
					xAxis: {
						type:'category',
						data: [],
						axisTick: {
							alignWithLabel: true,

						},
						axisLabel : {//坐标轴刻度标签的相关设置。
							interval:0,
							rotate:"45"
						}
					},
					yAxis: {
						type:'value'
					},
					series: [{
						name: '借阅量',
						type: 'bar',
						barWidth:30,
						data: [],
						showBackground: true,
						backgroundStyle: {
							color: 'rgba(180, 180, 180, 0.2)'
						}
					}]
				},

				mychart_seat_data_x:[],
				mychart_seat_data_y:[],
				SeatChartBar:'',
				SeatChartBaroption:{
					title: {
						text: '自习室一周数据统计',
					},
					grid:{//直角坐标系内绘图网格
						show:true,//是否显示直角坐标系网格。[ default: false ]
						left:"20%",//grid 组件离容器左侧的距离。
						right:"30px",
						borderColor:"#c45455",//网格的边框颜色
						bottom:"20%" //
					},
					legend: {
						data: ['自习室入座人数']
					},
					xAxis: {
						type: 'category',
						data: [],
						axisLabel : {//坐标轴刻度标签的相关设置。
							interval:0,
							rotate:"45"
						}
					},
					yAxis: {
						type: 'value'
					},
					series: [
						{
							data: [],
							name:'自习室入座人数',
							type: 'line',

						}
					]
				}

			}
		},
		created(){
			this.role = sessionStorage.getItem('role');
			console.log(this.role)
			this.getPersonalBook();
			this.getHotBookForChart();
			this.getSeatInfoData();
			this.getHotUser()
			this.getAllUserBookSeatNum();




		},

		methods:{
			goToUserDetail(current_data){
				this.$router.push({path: 'user_home', query: {articleusername:current_data}})
			},
			getHotUser(){
				axios.get('/api/user/gethotuser',{
					headers:{
						'Authorization':sessionStorage.getItem('token')
					}
				}).then(response=>{
					if (parseInt(response.data.code)===200){
						this.userHotList = response.data.object;

					}
				}).catch(()=>{
					this.$message.error("发生错误")
				})
			},



			getAllUserBookSeatNum(){
				axios.get("/api/user/getallusernum",{
					headers:{
						'Authorization' : sessionStorage.getItem('token')
					}
				}).then(response=>{
					if (parseInt(response.data.code)===200){
						this.userNum = parseInt(response.data.info);
					}
				});
				axios.get("/api/book/getallbooknum",{
					headers:{
						'Authorization' : sessionStorage.getItem('token')
					}
				}).then(response=>{
					if (parseInt(response.data.code)===200){
						this.bookNum = parseInt(response.data.info);
					}
				});
				axios.get("/api/seat/getallseat",{
					headers:{
						'Authorization' : sessionStorage.getItem('token')
					}
				}).then(response=>{
					if (parseInt(response.data.code)===200){
						this.seatList = response.data.object;
						var temp = 0;
						for (var i=0;i<this.seatList.length;i++){
							temp = temp+this.seatList[i].seat_row * this.seatList[i].seat_col
						}
						var user_temp=0;
						for (var i=0;i<this.mychart_seat_data_y.length;i++) user_temp = user_temp + this.mychart_seat_data_y[i]

						this.userSeatRatio = Math.round((user_temp/temp * 10000)/100.00)
						console.log(user_temp)
					}
				})
			},

			initBookChart(){
				if (this.role==='manager') {
					this.BookChartBar = this.$echarts.init(document.getElementById('book'),null,{width:600,height:400});
					this.BookChartBar.setOption(this.BookChartBaroption)
				}
			},
			initSeatChart(){
				if (this.role==='manager') {
					this.SeatChartBar = this.$echarts.init(document.getElementById('seat'),null,{width:600,height:400});
					this.SeatChartBar.setOption(this.SeatChartBaroption)
				}
			},
			//
			// refreshBookChart(){
			// 	console.log("refreshbook")
			// 	this.option.xAxis.data = [];
			// 	this.option.series[0].data=[];
			// 	for (var i=0;i<6;i++){
			// 		this.option.xAxis.data[i]=this.mychart_book_data_x[i];
			// 		this.option.series[0].data.push(this.mychart_book_data_y[i])
			// 		console.log("我进来了")
			// 	}
			//
			// 	this.BookChartBar.setOption(this.option);
			// },
			getHotBookForChart(){
				axios.get('/api/borrow/getallborrow',{
					headers:{
						'Authorization' : sessionStorage.getItem('token')
					}
				}).then((response)=>{
					if (parseInt(response.data.code)===200){
					 	var list = response.data.object;
					 	for (var i=0;i<list.length;i++){
					 		this.mychart_book_data_x[i]=list[i].book_name;
					 		this.mychart_book_data_y[i]=list[i].book_borrow_num;
						}
						this.BookChartBaroption.xAxis.data = [];
						this.BookChartBaroption.series[0].data=[];
						for (var i=0;i<this.mychart_book_data_x.length;i++){
							this.BookChartBaroption.xAxis.data.push(this.mychart_book_data_x[i]);
							this.BookChartBaroption.series[0].data.push(this.mychart_book_data_y[i])
						}


					}
				}).catch(()=>{
					this.$message.error("发生错误")
				})
			},

			getSeatInfoData(){
				axios.get('/api/seatinfo/getallseatinfo',{
					headers:{
						'Authorization':sessionStorage.getItem('token'),
					}
				}).then(response=>{
					if (parseInt(response.data.code)===200){
						var list = response.data.object;
						for (var i=0;i<list.length;i++){
							this.mychart_seat_data_x.push(list[i].seatinfo_date)
							this.mychart_seat_data_y.push(list[i].seatinfo_num)
						}
						this.SeatChartBaroption.xAxis.data = [];
						this.SeatChartBaroption.series[0].data=[];
						for (var i=0;i<this.mychart_seat_data_x.length;i++){
							this.SeatChartBaroption.xAxis.data.push(this.mychart_seat_data_x[i]);
							this.SeatChartBaroption.series[0].data.push(this.mychart_seat_data_y[i])
						}
						console.log(this.SeatChartBaroption.xAxis.data)
					}
				})
			},



			goToBookDetails(current_data){
				this.$router.push({path: '/book_details', query: {bookid: current_data}})
			},

			getPersonalBook(){
				axios.get('/api/book/getnewbook',{
					headers:{
						'Authorization' : sessionStorage.getItem('token')
					}
				}).then((response)=>{
					if (parseInt(response.data.code)===200){
						this.bookList = response.data.object;
				 		this.getFour();

						console.log(this.personalBookList)
					}
				}).catch(()=>{
					this.$message.error("发生错误")
				})


			},
			getFour(){
				var list=[],flag=1;
				this.personalBookList= [];
				for (let i=0;i<4;){
					flag=1;
					let j =this.getNewRandom(1,this.bookList.length);
					for (let k=0;k<list.length;k++) {
						if (list[k]===j) {
							flag=0;
						}
					}
					if (flag===1){
						this.personalBookList[i] = this.bookList[j];
						this.personalBookList[i].book_img="/api" + this.personalBookList[i].book_img
						console.log(j)
						i++;
						list.push(j);
					}

				}

			},
			getNewRandom(min,max){
				return parseInt(Math.random()*(max-min+1));
			}

		},
		mounted(){
			this.$nextTick(()=>{
				this.initBookChart();
				this.initSeatChart();
			})

		},
		watch:{
			BookChartBaroption:{
				handler(newVal,oldVal){
					if (this.BookChartBar){
						if (newVal){
							this.BookChartBar.setOption(newVal);
						}else {
							this.BookChartBar.setOption(oldVal);
						}
					}else {
						this.initBookChart();
					}
				},
				deep: true
			},
			SeatChartBaroption:{
				handler(newVal,oldVal){
					if (this.SeatChartBar){
						if (newVal){
							this.SeatChartBar.setOption(newVal);
						}else {
							this.SeatChartBar.setOption(oldVal);
						}
					}else {
						this.initSeatChart();
					}
				},
				deep: true
			},
			personalBookList:{
				handler(newVal,oldVal){
					if (this.personalBookList){
						if (newVal){
							this.personalBookList= newVal;
						}else {
							this.personalBookList= oldVal;
						}
					}
				},
				deep: true
			}
		}

	}
</script>

<style lang="less" scoped>
	.el-carousel__item h3 {
		color: #475669;
		font-size: 14px;
		opacity: 0.75;
		line-height: 200px;
		margin: 0;
	}

	.el-carousel__item:nth-child(2n) {
		background-color: #99a9bf;
	}

	.el-carousel__item:nth-child(2n+1) {
		background-color: #d3dce6;
	}
	.introduce .order {
		display: flex;
		margin-bottom: 50px;
	}
	.introduce .order .order-item {
		flex: 1;
		margin-right: 20px;
	}
	.introduce .order .order-item:last-child{
		margin-right: 0;
	}
	#zoom {
		min-height: 300px;
	}
</style>

