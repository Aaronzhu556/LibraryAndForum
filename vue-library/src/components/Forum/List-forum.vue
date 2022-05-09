<template>
    <div>
        <!--面包屑导航区-->
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>社区论坛</el-breadcrumb-item>
            <el-breadcrumb-item>书友交流</el-breadcrumb-item>
        </el-breadcrumb>

        <el-card>
            <!--搜索与添加-->
            <el-row :gutter="10">
                <el-col :span="6">
                    <el-input placeholder="请输入内容" v-model="queryInfo.querytext" :clearable="true" @clear="getArticleList">
                        <el-button slot="append" icon="el-icon-search" @click="getArticleList"></el-button>
                    </el-input>
                </el-col>
<!--                <el-col :span="6">-->
<!--                    <el-button type="primary" @click="goAddPage" >添加书籍</el-button>-->
<!--                </el-col>-->
            </el-row>

            <div class="tile is-ancestor">
                <div class="tile is-parent is-8">
                    <article class="tile is-child box">
                        <ul
                                v-infinite-scroll="loadMore"
                                infinite-scroll-disabled="busy"
                                infinite-scroll-distance="limit"
                                style="height: 100vh;
                        overflow-y: auto;"
                        >
                            <div class="box" v-for="(article,index) in articleList" :key="index">
                                <article class="media">
                                    <figure class="media-left">
                                        <p class="image is-64x64">
                                            <el-avatar :src="article.article_user_img"></el-avatar>
                                        </p>
                                    </figure>
                                    <div class="media-content">
                                        <div class="content">
                                            <p>
                                                <strong>发帖标题:{{article.article_title}}</strong>

                                                <br />
                                                发帖用户名:{{article.article_user_name}}
                                            </p>
                                        </div>
                                        <nav class="level is-mobile">
                                            <div class="level-left">
                                                                                        <a class="level-item" @click="changeStar(article,index)">
                                                                      <span class="icon is-small">
                                                                        <i class="el-icon-star-off" v-if="article.article_user_like===false" ></i>
                                                                          <i class="el-icon-star-on" v-else></i>
                                                                      </span>
                                                                                            {{article.article_star}}
                                                                                        </a>

                                                                                        <a class="level-item">
                                                                      <span class="icon is-small">
                                                                        <i class="el-icon-chat-line-round"></i>
                                                                      </span>
                                                                                            {{article.article_reply_num}}
                                                                                        </a>

<!--                                                                                        <a class="level-item">-->
<!--                                                                      <span class="icon is-small">-->
<!--                                                                        <i class="fas fa-heart"></i>-->
<!--                                                                      </span>-->
<!--                                                                                           30-->
<!--                                                                                        </a>-->
                                            </div>
                                        </nav>
                                    </div>
                                    <div class="media-right">
                                        <a class="navbar-item" slot="trigger" role="button">
                                            <b-button type="is-info" outlined @click="detail(article.article_id,article.article_title,article.article_user_name,article.article_user_img)">查看详情</b-button>
                                        </a>
                                    </div>
                                </article>
                            </div>
                            <div v-if="flag===1">没有更多了</div>

                            <b-notification :closable="false" v-if="flag===2">
                                <div style="text-align:center">正在加载</div>
                                <b-loading :is-full-page="isFullPage" :active.sync="isLoading" :can-cancel="true"></b-loading>
                            </b-notification>
                        </ul>
                    </article>
                </div>

                <div class="tile is-parent">
                    <article class="tile is-child box">
                        <p class="subtitle">个人信息</p>

                        <div class="card">
                            <header class="card-header">
                                <div>
                                    <el-avatar :src="imageUrl"></el-avatar>
                                </div>
                                <p class="card-header-title"  >{{user_name_article}}</p>
                                <p class="card-header-title"  >粉丝:{{this.fans_num}}</p>
                                <p class="card-header-title"  >发帖数量:{{this.article_user_num}}</p>
                            </header>

                            <div class="card-content">
                                <div class="content">
                                    <section>
                                        <el-badge :value="this.unReadReply.length" v-if="this.unReadReply.length!==0" class="item">
                                        <el-button @click="open2">您有新的回复通知</el-button>
                                        </el-badge>
                                    </section>
                                    <br />
                                </div>
                            </div>
                            <footer class="card-footer">
<!--                                <a href="#" class="card-footer-item">去发帖</a>-->
                                <el-button @click="drawer = true" type="primary" class="card-footer-item">
                                    去发帖
                                </el-button>

                                <el-drawer
                                        title="发帖"
                                        :visible.sync="drawer"
                                        :direction="direction"
                                        :before-close="handleClose">
                                    <el-form :label-position="labelPosition" label-width="50px" :model="formArticle">
                                        <el-form-item label="标题">
                                            <el-input v-model="formArticle.article_title"></el-input>
                                        </el-form-item>
                                        <el-form-item label="内容">
                                            <el-input type="textarea" v-model="formArticle.article_content" ></el-input>
                                        </el-form-item>
                                        <el-form-item>
                                            <el-button type="primary" @click="addArticle">发帖</el-button>
                                        </el-form-item>
                                    </el-form>
                                </el-drawer>
                            </footer>
                        </div>
                        <div class="card" style="margin-top: 100px">
                            <header class="card-header">
<!--                                <div>-->
<!--                                    <el-avatar src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"></el-avatar>-->
<!--                                </div>-->
                                <p class="card-header-title"  >最热的帖子</p>
<!--                                <p class="card-header-title"  >粉丝:50</p>-->
<!--                                <p class="card-header-title"  >发帖数量:5</p>-->
                            </header>

                            <div class="card-content" style="height: 350px">
                                <el-scrollbar style="height: 100%" >
                                    <div v-for="(article,index) in articleList_Hot" :key="index">

                                        <el-tag type="danger">{{index+1}}</el-tag>

                                        <el-tooltip class="item" effect="light" placement="top">
                                            <div slot="content">{{article.article_title}}</div>
                                            <el-button type="text" style="position: relative;left: 30px;color: 	#000000" @click="CheckHotArticleDetail(article)">{{articleList_Hot_Simple[index]}}</el-button>
                                        </el-tooltip>


                                        <strong style="color: #C80000;float: right" >{{article.article_hot}}</strong>

                                        <br><br>

                                    </div>



                                </el-scrollbar>

                            </div>

                            <footer class="card-footer">
                                <!--                                <a href="#" class="card-footer-item">去发帖</a>-->
<!--                                <el-button @click="drawer = true" type="primary" class="card-footer-item">-->
<!--                                    去发帖-->
<!--                                </el-button>-->

                                <el-drawer
                                        title="发帖"
                                        :visible.sync="drawer"
                                        :direction="direction"
                                        :before-close="handleClose">
                                    <el-form :label-position="labelPosition" label-width="50px" :model="formArticle">
                                        <el-form-item label="标题">
                                            <el-input v-model="formArticle.article_title"></el-input>
                                        </el-form-item>
                                        <el-form-item label="内容">
                                            <el-input type="textarea" v-model="formArticle.article_content" ></el-input>
                                        </el-form-item>
                                        <el-form-item>
                                            <el-button type="primary" @click="addArticle">发帖</el-button>
                                        </el-form-item>
                                    </el-form>
                                </el-drawer>
                            </footer>
                        </div>
                    </article>
                </div>
            </div>


            <!--分页-->
            <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="queryInfo.pagenum"
                           :page-sizes="[ 8, 16,32]" :page-size="queryInfo.pagesize" :total="total"
                           layout="total, sizes, prev, pager, next, jumper" background>
            </el-pagination>

        </el-card>

        <el-dialog  title="未读信息" :visible.sync="dialogVisible">

            <el-empty description="暂无回复" v-if="unReadReply.length===0"></el-empty>

                <el-button size="mini"  v-if="unReadReply.length!==0" @click="readAll">全部已读</el-button>

               <div class="reply-box"  style="height: 350px;width: 100%">
                   <el-scrollbar style="height: 100%" >
                   <div v-for="(reply,j) in unReadReply" :key="j" class="author-title">
                       <el-badge is-dot  style="width: 100%">
                       <el-card>
                       <el-avatar class="header-img"  :src="reply.reply_from_img"></el-avatar>
                       <div class="author-info">
                           <strong class="author-name">{{reply.reply_from}}</strong>
                           <span class="author-time">{{reply.reply_time}}</span>

                       </div>
                           <el-button icon="el-icon-search" circle style="float: right" @click="readReply(reply)"></el-button>
                       <div class="talk-box">
                           <p>
                               <span >回复了您:</span>
                               <span class="reply">{{reply.reply_content}}</span>
                           </p>

                       </div>

                       <div class="reply-box">


                       </div>
                       </el-card>
                       </el-badge>

                   </div>
                   </el-scrollbar>

               </div>
        </el-dialog>
    </div>
</template>

<script>
	export default {
		name: "Forum_List",
        inject:['reload'],
		data() {
			return {
				queryInfo: {
					querytext: '',
					pagenum: 1,
					pagesize: 8,
					querydata:'',
				},
				articleList:[],
                totalArticleList:[],
                articles:[],
                max_page:0,
                articleList_Hot:[],
                articleList_Hot_Simple:[],
                unReadReply:[],
                formArticle:{
				    article_title:'',
                    article_content:'',
                    article_user_name : '',
                },
                drawer: false,
				booksList_admin: [],
				total: 0,
                user_name_article:'',
				previewDialogVisible: false,
                dialogVisible: false,
				previewPath : "",
                fans_num:0,
                article_user_num:0,
                imageUrl:'',


			}
		},
		created() {
            this.user_name_article = sessionStorage.getItem('user_name');
			this.getArticleList();
			this.getUserFans();
			this.queryUserImg();
			this.getHotArticle();
            this.getAllUnReadReply();

			// console.log(this.managerRole);
		},
		methods: {

            readAll(){
                var unreadreplylist=[];
                for (var i=0;i<this.unReadReply.length;i++) unreadreplylist.push(this.unReadReply[i].reply_id)
                axios.post("/api/reply/readall",JSON.stringify(unreadreplylist),{
                    headers:{
                        'Authorization' :sessionStorage.getItem('token'),
                        'Content-Type': 'application/json'
                    }
                }).then(response=>{
                    if (parseInt(response.data.code)===200){
                        this.$message.success(response.data.msg)
                        this.getAllUnReadReply();
                    }
                }).catch(()=>{
                    this.$message.error("发生错误")
                })
            },
		    readReply(current_data){
		        axios.get("/api/reply/readreply",{
		            params:{
		                reply_id :current_data.reply_id,
                        reply_comment_id : current_data.reply_comment_id
                    },
                    headers:{
		                'Authorization' : sessionStorage.getItem('token')
                    }
                }).then(response=>{
                    if (parseInt(response.data.code)===200){
                        var article = response.data.object;
                        this.detail(article.article_id,article.article_title,article.article_user_name,article.article_user_img);
                    }
                })
            },


		    getAllUnReadReply(){
		        axios.get("/api/reply/getunreadreply",{
		            params:{
		                user_name : this.user_name_article
                    },
                    headers:{
		                'Authorization' : sessionStorage.getItem('token')
                    }
                }).then(response=>{
                    if (parseInt(response.data.code)===200){
                        this.unReadReply = response.data.object;
                        console.log( this.unReadReply)
                    }
                }).catch(()=>{
                    this.$message.error("发生错误")
                })
            },


		    open2(){
		        this.dialogVisible = true;
		        console.log("why")
            },
		    getHotArticle(){
		        axios.get('/api/article/gethotarticle',{
		            headers:{
		                'Authorization' : sessionStorage.getItem('token')
                    }
                }).then(response=>{
                    if (parseInt(response.data.code)===200){
                        this.articleList_Hot = response.data.object;
                        for (var i=0;i<this.articleList_Hot.length;i++){
                            this.articleList_Hot[i].article_hot = this.articleList_Hot[i].article_hot.toFixed(2)
                        }
                        console.log(this.articleList_Hot )
                        this.changeArticleTitle();
                    }
                })
            },
            queryUserImg(){
                axios.get("/api/user/queryuserimg",{
                    params:{
                        user_name :  this.user_name_article
                    },
                    headers:{
                        'Authorization' : sessionStorage.getItem('token')
                    }
                }).then((response)=>{
                    if (parseInt(response.data.code)===200){
                        this.imageUrl = response.data.info;
                        console.log(this.imageUrl)
                    }
                }).catch(()=>{
                    this.$message.error("发生错误")
                })


            },
            getUserFans(){
                axios.get('/api/follow/getfans',{
                    params:{
                        follow_user_to:  this.user_name_article
                    },
                    headers:{
                        'Authorization' : sessionStorage.getItem('token')
                    }
                }).then((response)=>{
                    if (parseInt(response.data.code)===200){
                       var list = response.data.object;
                       this.fans_num = list.length;

                    }
                }).catch(()=>{
                    this.$message.error("发生错误");
                })
            },
            CheckHotArticleDetail(current_data){
		        console.log(current_data)
                this.detail(current_data.article_id,current_data.article_title,current_data.article_user_name,current_data.article_user_img);
            },
		    changeArticleTitle(){
		        var i=0;
		        for (i=0;i<this.articleList_Hot.length;i++){
		            if (this.articleList_Hot[i].article_title.length>10) {
                        this.articleList_Hot_Simple[i] = this.articleList_Hot[i].article_title.slice(0, 9) + "...";
                    }else this.articleList_Hot_Simple[i] = this.articleList_Hot[i].article_title;

                }
            },

            changeStar(article,index){
                if (article.article_user_like === false) {
                    this.articleList[index].article_user_like = true;
                    this.articleList[index].article_star = this.articleList[index].article_star + 1;
                    axios.get('/api/article/addlikearticle',{
                        params:{
                            article_id:article.article_id,
                            article_user_name : article.article_user_name
                        },
                        headers:{Authorization: sessionStorage.getItem('token')}
                    }).then((response) =>{

                    }).catch(()=> {
                        this.$message.error('操作失败');
                    })
                }
                else {
                    this.articleList[index].article_user_like = false;
                    this.articleList[index].article_star = this.articleList[index].article_star - 1;
                    axios.get('/api/article/deletelikearticle',{
                        params:{
                            article_id:article.article_id,
                            article_user_name : article.article_user_name
                        },
                        headers:{Authorization: sessionStorage.getItem('token')}
                    }).then((response) =>{

                    }).catch(()=> {
                        this.$message.error('操作失败');
                    })
                }


            },
            addArticle(){
                axios.post('/api/article/addarticle',JSON.stringify({
                    article_title : this.formArticle.article_title,
                    article_content : this.formArticle.article_content,
                    article_user_name : this.user_name_article,

                }), {
                    headers: {
                        'Content-Type': 'application/json'
                    }

                }).then(response => {
                    if (parseInt(response.data.code) === 200) {
                      this.$message.success(response.data.msg);
                      this.getArticleList();
                      this.formArticle={};
                    } else {
                        this.$message.error(response.data.msg)
                    }
                }).catch((e) => {
                    this.$message.error("获取失败")
                    console.log(e);
                })

            },
            handleClose(done) {
                this.$confirm('确认关闭？')
                    .then(_ => {
                        done();
                    })
                    .catch(_ => {});
            },
            resetAllData(){
                this.queryInfo.pagenum = 1;
                this.articleList = [];
                this.totalArticleList =[];
                this.articles=[];
                this.max_page=0;
                this.total= 0;
            },
            getArticleList(){
                this.resetAllData();
				axios.post('/api/article/getallarticle',JSON.stringify({
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
						this.articles = response.data.object;
						for (var i=0;i<this.articles.length;i++){
						    if (this.articles[i].article_user_name===this.user_name_article) this.article_user_num++;
                        }
                        this.max_page = Math.ceil(this.articles.length / this.queryInfo.pagesize) || 1;
                        for (let i = 0; i < this.max_page; i++) {
                            this.totalArticleList[i] = this.articles.slice(
                                this.queryInfo.pagesize * i,
                                this.queryInfo.pagesize * (i + 1)
                            );
                            console.log(this.totalArticleList[i]);
                        }
                        this.articleList = this.totalArticleList[this.queryInfo.pagenum-1];



						if (this.queryInfo.querytext!=="") this.$message.success("已显示关于"+this.queryInfo.querytext+"的帖子");

						this.total = parseInt(response.data.info);
						//this.articleList_Hot = this.articleList.slice(0,5);
						//this.changeArticleTitle();
					} else {
						this.$message.info(response.data.msg)
					}
				}).catch((e) => {
					this.$message.error("获取失败")
					console.log(e);
				})
			},
            detail(id,title,user_name,user_img) {
                axios.get('/api/article/addclicknum',{
                    params:{
                        article_id:id,
                    },
                    headers:{
                        'Authorization' : sessionStorage.getItem('token')
                    }
                }).then(response=>{

                }).catch(()=>{
                    this.$message.error("发生错误")
                })


                this.$router.push({path: '/forum_details', query: {articleid: id, articletitle:title, articleusername:user_name, articleuserimg:user_img}})
                // this.$router.push({
                //     path: '/forum_details',
                //     query: {
                //         id: 1
                //     }
                // })
                //this.$router.push(`/forum_details/${id}`);
            },


			//监听pagesize改变的事件
			handleSizeChange(newSize) {
				this.queryInfo.pagesize = newSize
				this.getArticleList()
			},
			//监听页码值改变的事件
			handleCurrentChange(newPage) {
				this.queryInfo.pagenum = newPage
                this.articleList = this.totalArticleList[newPage-1];
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
<style>
    .el-scrollbar__wrap{

        overflow-x: hidden!important;

    }

</style>
<style lang="less" scoped>
	.el-button {
		margin-right: 10px;
	}
</style>
<style scoped>
	.size {
		width: 64px;
		height: 64px;
	}
	.fuck {
		text-align: center;
	}
	.tile.is-ancestor {
		margin-left: 0;
		margin-right: 0;
		margin-top: 0;
	}
    .el-link-hot{
        /*文字自动变省略号步骤1 :宽度*/
        width: 50px;
        /*文字自动变省略号步骤2 :不允许文字(在空白处)折行*/
        white-space: nowrap; /*wrap :换行*/
        /*文字自动变省略号步骤3 :溢出的文本- -律隐藏 */
        overflow: hidden;
        text-overflow: ellipsis;

        /*文字自动变省略号步骤4 :如果发生文本溢出,则使用省略号代替*/


    }

    .my-reply {
        padding: 10px;
        background-color: #fafbfc;
    }
    .my-reply .header-img {
        display: inline-block;
        vertical-align: top;
    }
    .my-reply .reply-info {
        display: inline-block;
        margin-left: 5px;
        width: 90%;
    }
    @media screen and (max-width: 1200px) {
        .my-reply .reply-info {
            width: 80%;
        }
    }
    .my-reply .reply-info .reply-input {
        min-height: 20px;
        line-height: 22px;
        padding: 10px 10px;
        color: #ccc;
        background-color: #fff;
        border-radius: 5px;
    }
    .my-reply .reply-info .reply-input:empty:before {
        content: attr(placeholder);
    }
    .my-reply .reply-info .reply-input:focus:before {
        content: none;
    }
    .my-reply .reply-info .reply-input:focus {
        padding: 8px 8px;
        border: 2px solid #00f;
        box-shadow: none;
        outline: none;
    }
    .my-reply .reply-btn-box {
        height: 25px;
        margin: 10px 0;
    }
    .my-reply .reply-btn-box .reply-btn {
        position: relative;
        float: right;
        margin-right: 15px;
    }
    .my-comment-reply {
        margin-left: 50px;
    }
    .my-comment-reply .reply-input {
        width: flex;
    }
    .author-title:not(:last-child) {
        border-bottom: 1px solid rgba(178,186,194,0.3);
    }
    .author-title {
        padding: 10px;
    }
    .author-title .header-img {
        display: inline-block;
        vertical-align: top;
    }
    .author-title .author-info {
        display: inline-block;
        margin-left: 5px;
        width: 60%;
        height: 40px;
        line-height: 20px;
    }
    .author-title .author-info >span {
        display: block;
        cursor: pointer;
        overflow: hidden;
        white-space: nowrap;
        text-overflow: ellipsis;
    }
    .author-title .author-info .author-name {
        color: #000;
        font-size: 18px;
        font-weight: bold;
    }
    .author-title .author-info .author-time {
        font-size: 14px;
    }
    .author-title .icon-btn {
        width: 30%;
        padding: 0 !important;
        float: right;
    }
    @media screen and (max-width: 1200px) {
        .author-title .icon-btn {
            width: 20%;
            padding: 7px;
        }
    }
    .author-title .icon-btn >span {
        cursor: pointer;
    }
    .author-title .icon-btn .iconfont {
        margin: 0 5px;
    }
    .author-title .talk-box {
        margin: 0 50px;
    }
    .author-title .talk-box >p {
        margin: 0;
    }
    .author-title .talk-box .reply {
        font-size: 16px;
        color: #000;
    }
    .author-title .reply-box {
        margin: 10px 0 0 50px;
        background-color: #efefef;
    }

</style>

