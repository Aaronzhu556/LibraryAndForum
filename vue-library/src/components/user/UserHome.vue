<template>
    <div>
        <!--面包屑导航区-->
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path: '/forum_list' }">社区论坛</el-breadcrumb-item>
            <el-breadcrumb-item>个人中心</el-breadcrumb-item>
        </el-breadcrumb>


        <div >
            <div class="content">
                <div id="Leftbar"></div>
                <div id="Rightbar">
                    <div class="sep20"></div>
                    <div class="box">
                        <div class="cell">{{this.article.username}}&nbsp;&nbsp;个人信息</div>

                        <nav class="level is-mobile">
                            <div class="level-item has-text-centered">
                                <div>
                                    <p class="heading">发帖数</p>
                                    <p>{{this.articleListByUser.length}}</p>
                                </div>
                            </div>
                            <div class="level-item has-text-centered">
                                <div>
                                    <p class="heading">粉丝</p>
                                    <p>{{this.userFansList.length}}</p>
                                </div>
                            </div>
                            <div class="level-item has-text-centered">
                                <div>
                                    <p class="heading">关注的人数</p>
                                    <p>{{this.userFollowingList.length}}</p>
                                </div>
                            </div>
                            <div class="level-item has-text-centered">
                                <div>
                                    <p class="heading">获赞数</p>
                                    <p>{{this.total_star}}</p>
                                </div>
                            </div>
                        </nav>
                    </div>
                    <div class="sep20"></div>
                </div>
                <div id="Main">
                    <div class="sep20"></div>
                    <div class="box">
                        <div class="cell">
                            <table cellpadding="0" cellspacing="0" border="0" width="100%">
                                <tbody>
                                <tr>
                                    <td width="73" valign="top" align="center">

                                        <el-upload
                                                class="avatar-uploader"
                                                action="/api/user/uploadimg"
                                                :show-file-list="false"
                                                :headers="headerObj"
                                                :on-success="handleSuccess"
                                                :before-upload="beforeAvatarUpload"

                                                name="user_img">
<!--                                            <img  src="../../assets/avatar.jpg" >-->
                                            <el-avatar v-if="imageUrl" shape="square" :size="50" :src="imageUrl" ></el-avatar>
<!--                                            <i v-else class="el-icon-plus "></i>-->
                                        </el-upload>
                                        <div class="sep10"></div>

                                    </td>
                                    <td width="10"></td>
                                    <td width="auto" valign="top" align="left">
                                        <div class="fr"></div>
                                        <h1 style="margin-bottom: 5px;">{{this.article.username}}</h1>
                                        <el-button v-if="this.isGuest===false " type="primary" icon="el-icon-edit" size="mini" style="float: right"  @click="openEditDialog"></el-button>
                                        <span class="gray">

                        <div class="sep5"></div>
                      {{this.user.user_motto}}
                      </span>
                                        <el-button v-if="this.isGuest===true && this.isFollow===false" style="float: right" type="primary" class="el-icon-plus" size="mini" @click="followUser()">关注</el-button>
                                        <el-button v-else-if="this.isGuest===true && this.isFollow===true" style="float: right" type="primary"  class="el-icon-minus" size="mini"  @click="unFollowUser()">取消关注</el-button>


                                        <div class="sep10"></div>

                                    </td>

                                </tr>

                                </tbody>

                            </table>

                            <div class="sep5"></div>
                        </div>
                    </div>
                    <div class="sep20"></div>

                    <div class="sep20"></div>
                    <div class="box" >
                        <el-empty description="用户账号为私密" v-if="this.user.user_privacy==='0'&&this.isGuest===true"></el-empty>
                        <div v-if="this.user.user_privacy==='1' ||this.user.user_privacy==='0'&& this.isGuest===false">
                        <div class="inner"  >
                            <el-tabs v-model="activeName" @tab-click="handleClick">
                                <el-tab-pane label="帖子" name="0"></el-tab-pane>
                                <el-tab-pane label="已借阅的书籍" name="1"></el-tab-pane>
                                <el-tab-pane label="评论归还的书籍" name="2" v-if="isGuest===false"></el-tab-pane>
                                <el-tab-pane label="自习室签到签退" name="3" v-if="isGuest===false"></el-tab-pane>
                            </el-tabs>
                        </div>
                        <div class="inner" v-if="activeName==='0'" style="height: 350px">
                            <el-empty description="暂无数据" v-if="articleListByUser.length===0"></el-empty>

                            <span class="chevron"></span>
                            <el-scrollbar style="height: 100%" >
                            <div class="box" v-for="(article, index) in articleListByUser" :key="index">
                                <article class="media">
                                    <div class="media-content">
                                        <div class="content">
                                            <p>
                                                <strong>{{article.article_title}}</strong>
                                                <br />
                                                {{article.article_user_name}}

                                                <el-button  type="primary"  icon="el-icon-search"  style="position: relative;left: 550px"circle @click="goToDetail(article)"></el-button>
                                                <el-button v-if="isGuest===false" type="danger" icon="el-icon-delete" circle style="float: right" @click="deleteArticleByUser(article)"></el-button>

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

                                            </div>
                                        </nav>
                                    </div>
                                </article>
                            </div>

                        </el-scrollbar>
                        </div>
                        <div class="inner" v-if="activeName==='1'" style="height: 350px">
                            <el-empty description="暂无数据" v-if="bookListByUser.length===0"></el-empty>
                            <el-empty description="用户设置为私密" v-if="this.user.user_privacy===0"></el-empty>
                            <span class="chevron"></span>

                            <el-scrollbar style="height: 100%" >

                                <div class="box"  v-for="(book, index) in bookListByUser" :key="index" >
                                    <article class="media">
                                        <div class="media-content">
                                            <div class="content">
                                                <p>
                                                    <strong>{{book.book_name}}</strong>
                                                    <br />
                                                    应在{{book.book_borrow_end_time}}之前归还
                                                    <el-button v-if="isGuest===false"  type="text"  style="float: right" @click="returnBook(book)" >立即归还</el-button>
                                                </p>
                                                <el-tag v-if="book.book_is_expired===true" type="danger">已逾期</el-tag>
                                                <el-tag v-else type="success">未逾期</el-tag>

                                            </div>

                                        </div>
                                    </article>

                                </div>
                                </el-badge>

                            </el-scrollbar>


                        </div>
                        <div class="inner" v-if="activeName==='2'" style="height: 350px">
                            <el-empty description="暂无数据" v-if="bookListWithoutComment.length===0"></el-empty>

                            <span class="chevron"></span>

                            <el-scrollbar style="height: 100%" >

                                <div class="box"  v-for="(borrow, index) in bookListWithoutComment" :key="index" >


                                    <article class="media">
                                        <div class="media-content">
                                            <div class="content">

                                                <p>
                                                    <el-image :src="borrow.book_img"></el-image>
                                                    <strong>{{borrow.borrow_book_name}}</strong>
                                                    <br />
                                                    归还时间:{{borrow.borrow_end_time_true}}
                                                    <el-button type="text"  style="float: right" @click="openComment(borrow.borrow_id)">添加评价</el-button>
                                                </p>
                                                <el-dialog
                                                        title="添加评价"
                                                        :visible.sync="centerDialogVisible"
                                                        width="30%"
                                                        show-text
                                                        center>
                                                    <el-form :label-position="labelPosition" label-width="50px" :model="formComment">
                                                        <el-form-item label="评分">
                                                            <el-rate
                                                                v-model="formComment.comment_star"
                                                                :colors="colors"
                                                                show-text>
                                                        </el-rate>
                                                        </el-form-item>
                                                        <el-form-item label="内容">
                                                            <el-input type="textarea" v-model="formComment.comment_context" ></el-input>
                                                        </el-form-item>

                                                    </el-form>

                                                    <span slot="footer" class="dialog-footer">
    <el-button @click="centerDialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="addComment(borrow.borrow_id)">添加评论</el-button>
  </span>
                                                </el-dialog>
<!--                                                <el-tag v-if="book.book_is_expired===true" type="danger">已逾期</el-tag>-->
<!--                                                <el-tag v-else type="success">未逾期</el-tag>-->

                                            </div>

                                        </div>
                                    </article>

                                </div>
                                </el-badge>

                            </el-scrollbar>


                        </div>
                        <div class="inner" v-if="activeName==='3'" style="height: 350px">
                            <el-empty description="暂无数据" v-if="seatInfoList_user.length===0"></el-empty>
                            <el-empty description="用户设置为私密" v-if="this.user.user_privacy===0"></el-empty>
                            <span class="chevron"></span>

                            <el-scrollbar style="height: 100%" >

                                <div class="box"  v-for="(seatinfo, index) in seatInfoList_user" :key="index" >


                                    <article class="media">
                                        <div class="media-content">
                                            <div class="content">

                                                <p>
<!--                                                    <el-image :src="borrow.book_img"></el-image>-->
                                                    <strong>{{index+1}}</strong>
                                                    <el-tag v-if="seatinfo.seatinfo_isarrive===0 && seatinfo.seatinfo_isleft===0">未签到</el-tag>
                                                    <el-tag v-if="seatinfo.seatinfo_isarrive===1&& seatinfo.seatinfo_isleft===0" type="success">已签到,未签退</el-tag>
                                                    <el-tag v-if="seatinfo.seatinfo_isarrive===1&&seatinfo.seatinfo_isleft===1" type="info">已签退</el-tag>
                                                    <br />
                                                    您预约了:{{seatinfo.seatinfo_parent}}楼{{seatinfo.seatinfo_row}}排{{seatinfo.seatinfo_col}}列的自习位置
                                                    <el-button type="primary" size="mini" @click="signin(seatinfo)" style="position: relative;right: -320px" :disabled="seatinfo.seatinfo_isarrive=='0'?false:true">签到</el-button>
                                                    <el-button type="warning" size="mini" @click="signout(seatinfo.seatinfo_id)" style="position: relative;right: -360px" :disabled="seatinfo.seatinfo_isleft=='0'&&seatinfo.seatinfo_isarrive=='1'?false:true">签退</el-button>
                                                </p>

                                                <!--                                                <el-tag v-if="book.book_is_expired===true" type="danger">已逾期</el-tag>-->
                                                <!--                                                <el-tag v-else type="success">未逾期</el-tag>-->

                                            </div>

                                        </div>
                                    </article>

                                </div>
                                </el-badge>

                            </el-scrollbar>


                        </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="c"></div>
            <div class="sep20"></div>
        </div>
        <!--修改用户的对话框-->
        <el-dialog title="修改个人信息" width="50%" :visible.sync="editDialogVisible" :close-on-click-modal="false" @close="editDialogClosed">
            <!--内容主体-->
            <el-form :model="editForm" :rules="editFormRules" ref="editFormRef" label-width="70px">
                <el-form-item label="用户名" prop="user_name">
                    <el-input v-model="editForm.user_name" :disabled="true"></el-input>
                </el-form-item>

                <el-form-item label="手机号" prop="user_phone">
                    <el-input v-model="editForm.user_phone"></el-input>
                </el-form-item>
                <el-form-item label="隐私状态" prop="email">
                    <el-switch
                            active-color="#13ce66"
                            inactive-color="#ff4949"
                            v-model="editForm.user_privacy"
                            active-value="1"
                            inactive-value="0"
                            active-text="公开"
                            inactive-text="私密"
                            >

                    </el-switch>
                </el-form-item>
                <el-form-item label="个性签名">
                    <el-input type="textarea" v-model="editForm.user_motto"></el-input>
                </el-form-item>
            </el-form>
            <!--底部-->
            <span slot="footer">
				<el-button @click=" editDialogClosed">取 消</el-button>
				<el-button type="primary" @click="editUser">确 定</el-button>
			</span>
        </el-dialog>
    </div>
</template>
<script>
    export default {
        inject:['reload'],
        name: 'user_home',
        data() {

            return {

                article:{
                    id:0,
                    title : '',
                    username : '',
                    content : ''
                },
                user:{},
                editDialogVisible:false,
                editForm:{},
                editFormRules:{

                    user_phone: [
                        { required: true, message: '手机号不能为空', trigger: 'blur' },
                        { type: 'string', pattern: /^1[3|4|5|6|7|8][0-9]{9}$/, message: '手机号格式出错', trigger: 'blur' ,max:11}
                    ],
                    user_motto: [
                        { required: true, message: '个性签名不能为空', trigger: 'blur' ,max:100},

                    ]
                },
                centerDialogVisible:false,
                imageUrl:'',
                articleListByUser:[],
                bookListByUser:[],
                bookListWithoutComment:[],
                isGuest : false, //判断当前访问人员是本人还是访客
                isFollow : false,
                activeName : '0',
                formComment:{
                    comment_star:0,
                    comment_context:'',
                },
                headerObj: {
                    Authorization: window.sessionStorage.getItem('token'),

                },
                total: 0,
                total_star:0,
                userFollowingList:[],
                userFansList:[],
                similarArticle:[],
                current_borrow_id:0,
                previewDialogVisible: false,
                colors: ['#99A9BF', '#F7BA2A', '#FF9900'],
                seatInfoList_user:[],
                allUserName:[],
            }
        },
        created() {

            this.article.username = this.$route.query.articleusername;
            this.queryUserImg();
            this.queryUserInfo();
            this.getAllArticleByUser();
            this.getAllBookByUser();
            this.getBookWithoutComment();
            this.getUserFollowing();
            this.getUserFans();
            this.getSeatByUser();
            this.user_name = sessionStorage.getItem('user_name');
            if (this.user_name !== this.article.username) this.isGuest = true;

            console.log(this.isGuest)


        },
        methods: {
            editDialogClosed(){

                this.editDialogVisible = false;
                this.reload();

            },
            editUser(){
                this.$refs.editFormRef.validate(valid => {
                    if (valid){

                        axios.post("/api/user/updateuserinfo",JSON.stringify(this.editForm),
                            {
                                headers:{
                                    'Content-Type': 'application/json'
                                }
                            }
                        ).then(response=>{
                            if (parseInt(response.data.code)===200){
                                this.$message({
                                    showClose:true,
                                    message:'更新用户信息成功',
                                    type:'success'
                                });
                                this.reload();
                            }else {
                                this.$message({
                                    showClose:true,
                                    message:'更新用户信息失败',
                                    type:'error'
                                });
                            }

                        }).catch(function (error) {
                            this.$message.error("发生错误")
                        })
                    }
                })
            },
            openEditDialog(){

                this.editDialogVisible=true;

            },


            queryUserInfo(){
                axios.get("/api/user/queryuserinfo",{
                    params:{
                        user_name :  this.article.username
                    },
                    headers:{
                        'Authorization' : sessionStorage.getItem('token')
                    }
                }).then((response)=>{
                    if (parseInt(response.data.code)===200){

                        this.editForm = response.data.object;
                        this.user = response.data.object;

                    }
                }).catch(()=>{
                    this.$message.error("发生错误")
                })


            },
            queryUserImg(){
                axios.get("/api/user/queryuserimg",{
                    params:{
                        user_name :  this.article.username
                    },
                    headers:{
                        'Authorization' : sessionStorage.getItem('token')
                    }
                }).then((response)=>{
                    if (parseInt(response.data.code)===200){

                        this.imageUrl =  response.data.info;
                    }
                }).catch(()=>{
                    this.$message.error("发生错误")
                })


            },
            signout(seatinfo_id){
                axios.get("/api/seatinfo/signout",{
                    params:{
                        seatinfo_id : seatinfo_id
                    },
                    headers:{
                        'Authorization' : sessionStorage.getItem('token')
                    }
                }).then((response)=>{
                    if (parseInt(response.data.code)===200){
                        this.$message.success(response.data.msg);
                        this.reload();
                    }
                }).catch(()=>{
                    this.$message.error("发生错误")
                })
            },
            signin(seatinfo){
                axios.post("/api/seatinfo/signin",JSON.stringify({
                    seatinfo_id:seatinfo.seatinfo_id,
                    seatinfo_row:seatinfo.seatinfo_row,
                    seatinfo_col:seatinfo.seatinfo_col,
                    seatinfo_parent:seatinfo.seatinfo_parent
                }), {
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization':sessionStorage.getItem('token')
                    }
                }).then((response)=>{
                    if (parseInt(response.data.code)===200){
                        this.$message.success(response.data.msg);
                        this.reload();
                    }
                }).catch(()=>{
                    this.$message.error("发生错误")
                })
            },
            getSeatByUser() {
                axios.get("/api/seatinfo/getseatinfobyuser",{
                   headers:{
                       'Authorization' : sessionStorage.getItem('token')
                   }
                }).then((response)=>{
                    if (parseInt(response.data.code)===200){
                        this.seatInfoList_user = response.data.object;
                        console.log(this.seatInfoList_user)
                    }
                }).catch(()=>{
                    this.$message.error("发生错误");
                })
            },




            goToDetail(current_data){
                this.$router.push({path: '/forum_details', query: {articleid: current_data.article_id, articletitle:current_data.article_title, articleusername:current_data.article_user_name}})
            },


            unFollowUser(){
                axios.get('/api/follow/unfollowuser',{
                    params:{
                        follow_user_from: this.user_name,
                        follow_user_to:this.article.username
                    },
                    headers:{
                        'Authorization':sessionStorage.getItem('token')
                    }
                }).then((response)=>{
                    if (parseInt(response.data.code)===200){
                        this.$message.success(response.data.msg);
                        this.isFollow = false
                        this.reload();
                    }
                }).catch(()=>{
                    this.$message.error("发生错误")
                })
            },
            followUser(){
                axios.get('/api/follow/followuser',{
                    params:{
                        follow_user_from: this.user_name,
                        follow_user_to:this.article.username
                    },
                    headers:{
                        'Authorization':sessionStorage.getItem('token')
                    }
                }).then((response)=>{
                    if (parseInt(response.data.code)===200){
                        this.$message.success(response.data.msg);
                        this.isFollow = true
                        this.reload();
                    }
                }).catch(()=>{
                    this.$message.error("发生错误")
                })

            },
            getUserFollowing(){
                axios.get('/api/follow/getfollowing',{
                    params:{
                        follow_user_from: this.article.username
                    },
                    headers:{
                        'Authorization' : sessionStorage.getItem('token')
                    }
                }).then((response)=>{
                    if (parseInt(response.data.code)===200){
                        this.userFollowingList = response.data.object;
                    }
                }).catch(()=>{
                    this.$message.error("发生错误");
                })
            },
            getUserFans(){
                axios.get('/api/follow/getfans',{
                    params:{
                        follow_user_to:  this.article.username
                    },
                    headers:{
                        'Authorization' : sessionStorage.getItem('token')
                    }
                }).then((response)=>{
                    if (parseInt(response.data.code)===200){
                        this.userFansList = response.data.object;

                        for (var i=0;i<this.userFansList.length;i++){
                            if (this.userFansList[i]===this.user_name) this.isFollow =true;
                        }

                    }
                }).catch(()=>{
                    this.$message.error("发生错误");
                })
            },


            handleSuccess(res){
                if (parseInt(res.code)===200){
                    this.imageUrl = "/api"  + res.info;
                    this.$message.success(res.msg);

                } else {
                    this.$message.error(res.msg)
                }
            },



            openComment(current_data){
                this.centerDialogVisible=true;
                this.current_borrow_id = current_data;
                console.log(current_data)
            },
            addComment(){


                axios.get('/api/borrow/addcomment',{
                    params:{

                        borrow_comment_context : this.formComment.comment_context,
                        borrow_comment_star : this.formComment.comment_star,
                        borrow_id : this.current_borrow_id,
                    },
                    headers:{
                        'Authorization' : sessionStorage.getItem('token')
                    }
                }).then((response)=>{
                    if (parseInt(response.data.code)===200){
                        this.$message.success(response.data.msg)
                    }else this.$message.info(response.data.msg)
                }).catch(()=>{
                    this.$message.error("发生错误")
                })
                this.centerDialogVisible = false;
                this.getBookWithoutComment();
                this.reload();

            },
            getBookWithoutComment(){
                axios.get('/api/borrow/queryborrowwithoutcomment',{
                    params:{
                        user_name : this.article.username
                    },
                    headers:{
                        'Authorization' : sessionStorage.getItem('token')
                    }
                }).then((response)=>{
                    if (parseInt(response.data.code)===200){
                        this.bookListWithoutComment = response.data.object
                        //this.$message.success(response.data.msg)
                    }else {

                    }
                }).catch(()=>{
                    this.$message.error("发生错误")
                })
            },
            returnBook(current_data){
                axios.get('/api/book/returnbook',{
                    params:{
                        book_id: current_data.book_id,
                        user_name: this.user_name
                    },
                    headers:{
                        'Authorization' :sessionStorage.getItem('token')
                    }
                }).then((resposne)=>{
                    if (parseInt(resposne.data.code)===200){

                        this.getAllBookByUser();
                        this.reload();
                        this.$message.success(resposne.data.msg);
                    }else this.$message.info(resposne.data.msg);
                }).catch((e)=>{
                    this.$message.error("发生错误");
                })
            },
            deleteArticleByUser(current_data){
                console.log(current_data)
                axios.get('/api/article/deletearticle',{
                    params:{
                        article_id : current_data.article_id
                    },
                    headers:{
                        'Authorization' : sessionStorage.getItem('token')
                    }
                }).then((response)=>{
                    if (parseInt(response.data.code)===200){

                        this.getAllArticleByUser();
                        this.reload();
                        this.$message.success(response.data.msg)
                    }else {
                        this.$message.info(response.data.msg)
                    }
                }).catch((e)=>{
                    this.$message.error("发生错误");
                })
            },

            handleAvatarSuccess(res, file) {
                this.imageUrl = URL.createObjectURL(file.raw);
            },
            beforeAvatarUpload(file) {
                const isJPG = file.type === 'image/jpeg';
                const isLt2M = file.size / 1024 / 1024 < 2;

                if (!isJPG) {
                    this.$message.error('上传头像图片只能是 JPG 格式!');
                }
                if (!isLt2M) {
                    this.$message.error('上传头像图片大小不能超过 2MB!');
                }
                return isJPG && isLt2M;
            },
            changeStar(article,index){
                console.log("changestar生效")
                if (article.article_user_like === false) {
                    this.articleListByUser[index].article_user_like = true;
                    this.articleListByUser[index].article_star = this.articleListByUser[index].article_star + 1;
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
                    this.articleListByUser[index].article_user_like = false;
                    this.articleListByUser[index].article_star = this.articleListByUser[index].article_star - 1;
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
            handleClick(tab){
                this.activeName = tab.index;
            },
            getAllArticleByUser(){
                axios.get('/api/article/getallarticlebyuser',{
                    params:{
                        article_user_name: this.article.username
                    },
                    headers:{
                        'Authorization' : sessionStorage.getItem('token')
                    }
                }).then((response)=>{
                    if (parseInt(response.data.code)===200){
                        this.articleListByUser = response.data.object;
                        for (var i=0;i<this.articleListByUser.length;i++) this.total_star = this.total_star + this.articleListByUser[i].article_star;

                        console.log(this.articleListByUser)
                       // this.$message.success(response.data.msg);
                    }else if (parseInt(response.data.code)===201){

                    }
                }).catch((e)=>{
                    this.$message.error("发生错误")
                    console.log(e);
                })
            },
            getAllBookByUser(){
                axios.get("/api/book/getallbookbyuser",{
                    params:{
                        user_name : this.article.username
                    },
                    headers:{
                        'Authorization' : sessionStorage.getItem('token')
                    }
                }).then((response)=>{
                    if (parseInt(response.data.code)===200){
                        this.bookListByUser = response.data.object;
                       // console.log(this.bookListByUser)
                    }
                }).catch((e)=>{
                    this.$message.error("发生错误")
                })
            },


            /*

            //监听pagesize改变的事件
            handleSizeChange(newSize) {
                this.queryInfo.pagesize = newSize
                this.getArticleList()
            },
            //监听页码值改变的事件
            handleCurrentChange(newPage) {
                this.queryInfo.pagenum = newPage
                this.getArticleList()
            },

             */

        },
        watch:{
            $route:{
                handler(){
                    var user_name = this.$route.query.articleusername
                    if (this.article.username!==''&&user_name !== this.article.username) {
                        console.log("草你妈的"+user_name+this.article.username)

                        this.$router.go(0);
                    }
                },
                immediate: true
            }
        }
    }


</script>



<style scoped>
    .gray {
        color: #999;
    }
    .inner {
        padding: 10px;
        font-size: 14px;
        line-height: 150%;
        text-align: left;
    }
    #Wrapper {
        text-align: center;
        background-color: #e2e2e2;
        background-image: url(/static/img/shadow_light.png);
        background-repeat: repeat-x;
    }
    #Rightbar {
        width: 270px;
        float: right;
        margin-right: 20px;
    }
    .box {
        background-color: var(--box-background-color);
        border-radius: var(--box-border-radius);
        box-shadow: 0 2px 3px rgba(0, 0, 0, 0.1);
        border-bottom: 1px solid #e2e2e2;
    }
    table[Attributes] {
        -webkit-border-horizontal-spacing: 0px;
        -webkit-border-vertical-spacing: 0px;
        border-top-width: 0px;
        border-right-width: 0px;
        border-bottom-width: 0px;
        border-left-width: 0px;
        width: 100%;
    }
    user agent stylesheet table {
        display: table;
        border-collapse: separate;
        border-spacing: 2px;
        border-color: grey;
    }
    .cell_tabs {
        padding: 10px 10px 0;
        border-bottom: 1px solid var(--box-border-color);
        text-align: center;
    }
    .cell_tab:link,
    .cell_tab:visited {
        font-size: 14px;
        border-bottom: 3px solid transparent;
        display: inline-block;
        text-decoration: none;
        margin-right: 15px;
        padding: 0 5px 5px;
        color: #99a;
    }
    a:active,
    a:link,
    a:visited {
        color: #778087;
        text-decoration: none;
        word-break: break-word;
    }
    user agent stylesheet a:-webkit-any-link {
        color: -webkit-link;
        cursor: pointer;
        text-decoration: underline;
    }
    .cell_tabs {
        padding: 10px 10px 0;
        border-bottom: 1px solid var(--box-border-color);
        text-align: center;
    }
    user agent stylesheet a:-webkit-any-link {
        color: -webkit-link;
        cursor: pointer;
        text-decoration: underline;
    }
    .fl {
        float: left;
    }
    img[Attributes] {
        width: 18px;
        vertical-align: middle;
    }
    .cell {
        padding: 10px;
        font-size: 14px;
        line-height: 120%;
        text-align: left;
        border-bottom: 1px solid #e2e2e2;
    }
    .box {
        background-color: #fff;
        border-radius: 3px;
        box-shadow: 0 2px 3px rgba(0, 0, 0, 0.1);
        border-bottom: 1px solid #e2e2e2;
    }
    #Main {
        width: auto;
        margin: 0 310px 0 20px;
    }
    #Leftbar {
        width: 0;
        float: left;
    }
    .c {
        clear: both;
    }
    .sep20 {
        height: 20px;
    }
    .content {
        min-width: 600px;
        max-width: 1100px;
        margin: 0 auto;
    }
    #Top {
        text-align: center;
        background-color: #fff;
        height: 44px;
        font-size: 15px;
        font-weight: 500;
        background-size: 44px 44px;
        border-bottom: 1px solid rgba(0, 0, 0, 0.22);
        padding: 0 20px;
    }
    user agent stylesheet div {
        display: block;
    }
    body {
        padding: 0px;
        margin: 0px;
        font-family: "Helvetica Neue", "Luxi Sans", "DejaVu Sans", "Segoe UI",
        "Hiragino Sans GB", "Microsoft Yahei", sans-serif;
    }
    :root {
        --box-background-color: #fff;
        --box-background-alt-color: #f9f9f9;
        --box-background-hover-color: #fafafa;
        --box-foreground-color: #000;
        --box-border-color: #e2e2e2;
        --box-border-radius: 3px;
    }
    user agent stylesheet html {
        color: -internal-root-color;
    }
    #Top > .content {
        max-width: 1060px;
    }
    .content {
        min-width: 600px;
        max-width: 1100px;
        margin: 0 auto;
    }
    table[Attributes] {
        -webkit-border-horizontal-spacing: 0px;
        -webkit-border-vertical-spacing: 0px;
        border-top-width: 0px;
        border-right-width: 0px;
        border-bottom-width: 0px;
        border-left-width: 0px;
        width: 100%;
    }
    table {
        display: table;
        border-collapse: separate;
        border-spacing: 2px;
        border-color: grey;
    }
    tbody {
        display: table-row-group;
        vertical-align: middle;
        border-color: inherit;
    }
    tr {
        display: table-row;
        vertical-align: inherit;
        border-color: inherit;
    }
    td[Attributes] {
        width: 110px;
        text-align: -webkit-left;
    }
    user agent stylesheet td {
        display: table-cell;
        vertical-align: inherit;
    }
    a:active,
    a:link,
    a:visited {
        color: #778087;
        text-decoration: none;
        word-break: break-word;
    }
    user agent stylesheet a:-webkit-any-link {
        color: -webkit-link;
        cursor: pointer;
        text-decoration: underline;
    }
    #Logo {
        width: 94px;
        height: 30px;
        background-image: url(/static/img/v2ex@2x.png);
        background-size: 94px 30px;
        background-repeat: no-repeat;
        display: inline-block;
    }
    .avatar-uploader .el-upload {
        border: 1px dashed #d9d9d9;
        border-radius: 6px;
        cursor: pointer;
        position: relative;
        overflow: hidden;
    }
    .avatar-uploader .el-upload:hover {
        border-color: #409EFF;
    }
    .avatar-uploader-icon {
        font-size: 28px;
        color: #8c939d;
        width: 50px;
        height: 50px;
        /*line-height: 178px;*/
        text-align: center;
    }
    .avatar {
        width: 178px;
        height: 178px;
        display: block;
    }
</style>