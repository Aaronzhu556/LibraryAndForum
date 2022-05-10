<template>
    <div id="#app">
        <!--面包屑导航区-->
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path: '/forum_list' }">社区论坛</el-breadcrumb-item>
            <el-breadcrumb-item>帖子详情</el-breadcrumb-item>
        </el-breadcrumb>

        <div>
            <div class="Rightbar">
            <div class="sep20"></div>
                <div class="card" >
                    <header class="card-header">
                        <!--                                <div>-->
                        <!--                                    <el-avatar src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"></el-avatar>-->
                        <!--                                </div>-->
                        <strong class="card-header-title"  >猜你喜欢</strong>

                        <!--                                <p class="card-header-title"  >粉丝:50</p>-->
                        <!--                                <p class="card-header-title"  >发帖数量:5</p>-->
                    </header>

                    <div class="card-content" style="height: 350px"    v-loading="loadingStatus">
                        <el-scrollbar style="height: 100%" >
                            <div v-for="(article,index) in similarArticle" :key="index">

                                <el-tag type="danger">{{index+1}}</el-tag>

                                <el-tooltip class="item" effect="light" placement="top">
                                    <div slot="content">{{article.article_title}}</div>
                                    <el-button type="text" style="position: relative;left: 30px;color: 	#000000" @click="CheckSimilarArticleDetail(article)">{{similarArticle_simple[index]}}</el-button>
                                </el-tooltip>


<!--                                <strong style="color: #C80000;float: right" >2000</strong>-->

                                <br><br>

                            </div>



                        </el-scrollbar>

                    </div>

                </div>
        </div>
            <div class="Main">
                <div class="sep20"></div>
                <div class="box" style="border-bottom:0px;">
                    <div class="header">
                        <div class="fr">

<!--                                <img-->
<!--                                        src="https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg"-->
<!--                                        class="avatar"-->
<!--                                        border="0"-->
<!--                                        align="default"-->
<!--                                        style="max-width: 48px; max-height: 48px;"-->
<!--                                        click.native=""-->
<!--                                />-->
                            <el-avatar :src="article.user_img" @click.native="goToUserHome()"></el-avatar>

                        </div>
                        <div class="sep10"></div>
                        <h1 class="h1">{{article.title}}</h1>
                        <h2 class="small">{{article.username}}</h2>
                    </div>
                    <div class="cell">{{article.content}}</div>
                </div>
                <div class="sep20"></div>
                <div class="box">
                    <div class="cell">回复</div>
                    <div class="fuck"></div>
                    <div class="cell" v-for="(c, index) in commentList" :key="index">
                        <table cellpadding="0" cellspacing="0" border="0" width="100%">
                            <tbody>
                            <tr>
                                <td width="48" valign="top" align="center">
                                    <el-avatar :src="c.comment_user_img"></el-avatar>

                                </td>
                                <td width="10" valign="top"></td>
                                <td width="auto" valign="top" align="left">
                                    <div class="fr">
                                        &nbsp; &nbsp;
                                        <span class="no"></span>
                                    </div>
                                    <div class="sep3"></div>
                                    <strong class="author-name">
                                         {{c.comment_user_name}}
                                    </strong>&nbsp; &nbsp;
                                    <span class="ago">{{c.comment_time}}</span>
                                    <div style="display: inline-block;" class="icon-btn">
                                        <span style="position: relative;right:-500px" @click="deleteComment(c.comment_id)" v-show="c.comment_user_name===user_name"> <i class="el-icon-delete"></i></span>
                                        <span style="position: relative;right:-520px" @click="open(index,c.comment_user_name)"> <i class="el-icon-chat-dot-round"></i></span>
                                        <el-link   style="position: relative;right:-580px" v-if="c.comment_box_show===false" @click.native="openReplyBox(index)"> 打开回复({{c.comment_reply_list.length}})</el-link>
                                        <el-link style="position: relative;right:-580px" v-else @click.native="openReplyBox(index)"> 关闭回复({{c.comment_reply_list.length}})</el-link>

                                    </div>
                                    <div class="sep5"></div>
                                    <div class="reply_content">{{c.comment_content}}</div>

                                    <div class="reply-box" v-show="c.comment_box_show===true" >
                                        <div v-for="(reply,j) in c.comment_reply_list" :key="j" class="author-title">
                                            <el-avatar class="header-img"  :src="reply.reply_from_img"></el-avatar>
                                            <div class="author-info">
                                                <strong class="author-name">{{reply.reply_from}}</strong>
                                                <span class="author-time">{{reply.reply_time}}</span>
                                            </div>
                                            <div class="icon-btn">
                                                <span @click="showReplyInput(index,j,reply.reply_from)" style="float: right"><i class="el-icon-chat-dot-square"></i></span>
                                                <span style="position: relative;right: -180px" v-if="reply.reply_from===user_name" @click="deleteReply(reply.reply_id)"><i class="el-icon-delete"></i></span>

                                            </div>
                                            <div class="talk-box">
                                                <p>
                                                    <span >回复 {{reply.reply_to}}:</span>
                                                    <span class="reply">{{reply.reply_content}}</span>
                                                </p>
                                            </div>
                                            <div class="reply-box">

                                            </div>
                                        </div>
                                    </div>
                                    <div  v-show="_inputShow(index)" class="my-reply my-comment-reply">
<!--                                        <el-avatar class="header-img" :size="40" :src="myHeader"></el-avatar>-->


                                        <div class="reply-info" >
                                            <el-input v-model="inputForm.text" placeholder="请输入回复"></el-input>
<!--                                            <div tabindex="0" contenteditable="true" spellcheck="false" placeholder="输入评论..."   class="reply-input reply-comment-input"></div>-->
                                        </div>
                                        <div class=" reply-btn-box">
                                            <el-button class="reply-btn" size="mini" @click="sendCommentReply(c.comment_id)" type="primary">发表回复</el-button>
                                        </div>
                                    </div>


                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
                <!--分页-->
                <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="queryInfo.pagenum"
                               :page-sizes="[ 8, 16,32]" :page-size="queryInfo.pagesize" :total="total"
                               layout="total, sizes, prev, pager, next, jumper" background>
                </el-pagination>
                <div class="sep20"></div>
                <div class="box">
                    <div class="cell">
                        <div class="fr">
                            <a>
                                <strong>↑</strong> 注意用语规范
                            </a>
                        </div>添加一条新回复
                    </div>
                    <div class="cell">
                        <form>
                <textarea
                        v-model="commentContent"
                        name="content"
                        maxlength="10000"
                        class="mll"
                        id="reply_content"
                        style="overflow: hidden; overflow-wrap: break-word; resize: none; height: 112px;"
                ></textarea>
                            <div class="sep10"></div>
                            <div class="fr">

                                <span class="gray">请尽量让自己的回复能够对别人有帮助</span>
                            </div>

                            <input type="button" value="回复" class="" @click="addNewComment()" />
                        </form>
                    </div>
                    <div class="inner">

                    </div>


                </div>
            </div>


        </div>



    </div>
</template>
<script>
    export default {
        name: 'Forum_details',
        inject:['reload'],
        data() {
            return {
                queryInfo: {
                    querytext: '',
                    pagenum: 1,
                    pagesize: 8,
                    querydata:'',
                },
                article:{
                    id:0,
                    title : '',
                    username : '',
                    content : '',
                    user_img : '',
                },
                commentList:[],
                comments:[],
                totalComments:[[]],
                max_page:0,
                inputForm:{
                    text:'',
                    from:'',
                    to:'',
                },
                commentContent:'',
                total: 0,
                similarArticle:[],
                similarArticle_simple:[],
                user_name:'',
                previewDialogVisible: false,
                previewPath : "",
                input:'',
                loadingStatus:true,
                btnShow: false,


            }
        },
        created() {
            this.user_name = sessionStorage.getItem('user_name');
            this.article.id = this.$route.query.articleid;
            this.article.title = this.$route.query.articletitle;
            this.article.username = this.$route.query.articleusername;
            this.article.user_img = this.$route.query.articleuserimg;
            this.getArticleContent();
            this.getCommentList();
            this.getSimilarArticle();

            // console.log(this.managerRole);
        },
        methods: {
            CheckSimilarArticleDetail(current_data){
                console.log(current_data)
                this.detail(current_data.article_id,current_data.article_title,current_data.article_user_name,current_data.article_user_img);
            },
            changeArticleTitle(){
                var i=0;
                for (i=0;i<this.similarArticle.length;i++){
                    if (this.similarArticle[i].article_title.length>10) {
                        this.similarArticle_simple[i] = this.similarArticle[i].article_title.slice(0, 9) + "...";
                    }else this.similarArticle_simple[i] = this.similarArticle[i].article_title;

                }
                console.log(this.similarArticle_simple)
            },
            getSimilarArticle(){
                axios.get("/api/article/getsimilararticle",{
                    params:{
                        article_id:this.article.id
                    },
                    headers:{
                        'Authorization':sessionStorage.getItem('token')
                    }
                }).then(response=>{
                    if (parseInt(response.data.code)===200){
                        this.similarArticle = response.data.object;
                        console.log(this.similarArticle)

                        this.changeArticleTitle();
                        this.loadingStatus = false;
                    }
                })
            },


            resetAllData(){
                this.queryInfo.pagenum = 1;
                this.commentList = [];
                this.totalComments =[[]];
                this.comments=[];
                this.max_page=0;
                this.total= 0;
            },
            deleteComment(comment_id){
                axios.get('/api/comment/deletecomment',{
                    params:{
                        comment_id : comment_id
                    },
                    headers:{
                        'Authorization' :sessionStorage.getItem('token')
                    }
                }).then((response)=>{
                    if (parseInt(response.data.code)===200){
                        this.$message.success(response.data.msg)
                        this.reload();
                    }else   this.$message.error(response.data.msg)
                }).catch(()=>{
                    this.$message.error("发生错误")
                })
            },



            openReplyBox(index){
                if (this.commentList[index].comment_box_show===false && this.commentList[index].comment_reply_list.length!==0) this.commentList[index].comment_box_show=true;
                else if (this.commentList[index].comment_box_show===false && this.commentList[index].comment_reply_list.length===0) this.$message.info("暂无回复");
                else if (this.commentList[index].comment_box_show===true && this.commentList[index].comment_reply_list.length!==0)this.commentList[index].comment_box_show=false;

            },
            deleteReply(reply_id){
                axios.get('/api/reply/deletereply',{
                    params:{
                        reply_id:reply_id
                    },
                    headers:{
                        'Authorization' :sessionStorage.getItem('token')
                    }
                }).then((response)=>{
                    if (parseInt(response.data.code)===200){
                        this.$message.success(response.data.msg)
                        this.reload();
                    }else  this.$message.error(response.data.msg)
                }).catch(()=>{
                    this.$message.error("发生错误")
                })
            },
            sendCommentReply(comment_id){
                axios.post('/api/reply/addnewreply',JSON.stringify({
                    reply_from : this.inputForm.from,
                    reply_to : this.inputForm.to,
                    reply_content : this.inputForm.text,
                    reply_comment_id : comment_id
                }),{
                    headers:{
                        'Authorization' : sessionStorage.getItem('token'),
                        'Content-Type': 'application/json'
                    }
                }).then((response)=>{
                    if (parseInt(response.data.code)===200){
                        this.$message.success(response.data.msg);
                        this.reload();

                    }else this.$message.error(response.data.msg);
                }).catch(()=>{
                    this.$message.error("发生错误");
                })
            },
            open(index,toName){
                if (this.commentList[index].comment_input_show===false) this.commentList[index].comment_input_show = true;
                else this.commentList[index].comment_input_show= false;
                this.inputForm.to = toName;
                this.inputForm.from = this.user_name

            },

            // inputFocus(){
            //     var replyInput = document.getElementById('replyInput');
            //     replyInput.style.padding= "8px 8px"
            //     replyInput.style.border ="2px solid blue"
            //     replyInput.focus()
            // },
            // showReplyBtn(){
            //     this.btnShow = true
            // },
            // hideReplyBtn(){
            //     this.btnShow = false
            //     replyInput.style.padding= "10px"
            //     replyInput.style.border ="none"
            // },

            showReplyInput(comment_index,reply_index,toName){

                if (this.commentList[comment_index].comment_input_show===false) this.commentList[comment_index].comment_input_show = true;
                else this.commentList[comment_index].comment_input_show = false;
                this.inputForm.to = toName;
                this.inputForm.from = this.user_name


            },
            _inputShow(i){
                return this.commentList[i].comment_input_show;
            },




            goToUserHome(){
                this.$router.push({path: 'user_home', query: {articleusername:this.article.username}})
            },
            addNewComment(){
                axios.post('/api/comment/addnewcomment',JSON.stringify({
                    comment_user_name: sessionStorage.getItem('user_name'),
                    comment_content:this.commentContent,
                    comment_article_id: this.article.id,
                }), {
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization':sessionStorage.getItem('token')
                    }
                }).then(response => {
                    if (parseInt(response.data.code) === 200) {

                        this.$message.success(response.data.msg)
                        this.getCommentList();
                        this.commentContent = '';

                    } else {
                        this.$message.error(response.data.msg)
                    }
                }).catch((e) => {
                    this.$message.error("获取失败")
                    console.log(e);
                })
            },
            getArticleContent(){
                axios.get('/api/article/getcontent',
                    {
                        params:{ article_id:  this.article.id }}
                        ).then(response => {
                   this.article.content = response.data;
                }).catch((e) => {
                    this.$message.error("获取失败")
                    console.log(e);
                })
            },

            getCommentList(){
                this.resetAllData();
                axios.get('/api/comment/queryallcomment',
                    {
                        params:{
                            comment_article_id:  this.article.id,
                            pagesize:this.queryInfo.pagesize,
                            pagenum: this.queryInfo.pagenum
                        },
                        headers: {
                            'Authorization': sessionStorage.getItem('token')}

                    }).then(response => {
                    if (parseInt(response.data.code) === 200) {
                        this.comments = response.data.object;
                        this.max_page = Math.ceil(this.comments.length / this.queryInfo.pagesize) || 1;
                        for (let i = 0; i < this.max_page; i++) {
                            this.totalComments[i] = this.comments.slice(
                                this.queryInfo.pagesize * i,
                                this.queryInfo.pagesize * (i + 1)
                            );
                            console.log(this.totalComments[i]);
                        }
                        this.commentList = this.totalComments[this.queryInfo.pagenum-1];
                        console.log(this.commentList)
                        this.total = parseInt(response.data.info);
                    } else {
                        this.$message.info(response.data.msg)
                    }
                }).catch((e) => {
                    this.$message.error("获取失败")
                    console.log(e);
                })
            },



            //监听pagesize改变的事件
            handleSizeChange(newSize) {
                this.queryInfo.pagesize = newSize
                this.getCommentList()
            },
            //监听页码值改变的事件
            handleCurrentChange(newPage) {
                this.queryInfo.pagenum = newPage
               //this.getCommentList()
                this.commentList = this.totalComments[newPage-1];
            },

        }
    }

</script>
<style scoped>
    textarea {
        -webkit-writing-mode: horizontal-tb !important;
        text-rendering: auto;
        color: -internal-light-dark-color(black, white);
        letter-spacing: normal;
        word-spacing: normal;
        text-transform: none;
        text-indent: 0px;
        text-shadow: none;
        display: inline-block;
        text-align: start;
        -webkit-appearance: textarea;
        background-color: -internal-light-dark-color(white, black);
        -webkit-rtl-ordering: logical;
        flex-direction: column;
        resize: auto;
        cursor: text;
        white-space: pre-wrap;
        overflow-wrap: break-word;
        margin: 0em;
        font: 400 13.3333px Arial;
        border-width: 1px;
        border-style: solid;
        border-color: rgb(169, 169, 169);
        border-image: initial;
        padding: 2px;
        width: 720px;
    }
    form {
        display: block;
        margin-top: 0em;
    }
    #Rightbar {
        width: 270px;
        float: right;
        margin-right: 20px;
    }
    .bigger {
        text-align: center;
        font-size: 20px;
    }
    .sep20 {
        height: 20px;
    }
    .Wrapper {
        background-color: #e2e2e2;
        background-repeat: repeat-x, repeat-x;
        text-align: center;
    }
    .content {
        min-width: 600px;
        max-width: 1100px;
        margin: 0 auto;
    }
    .Leftbar {
        width: 0;
        float: left;
    }
    .Rightbar {
        width: 270px;
        float: right;
        margin-right: 20px;
    }
    .Main {
        width: auto;
        margin: 0 310px 0 20px;
    }
    .div {
        display: block;
    }
    .box {
        background-color: #fff;
        box-shadow: 0 2px 3px rgba(0, 0, 0, 0.1);
        border-bottom: 1px solid #e2e2e2;
    }
    .header {
        padding: 10px;
        font-size: 15px;
        line-height: 120%;
        text-align: left;
        border-bottom: 1px solid #e2e2e2;
        overflow: auto;
    }
    .cell {
        padding: 10px;
        font-size: 14px;
        line-height: 120%;
        text-align: left;
        border-bottom: 1px solid #e2e2e2;
    }
    .table {
        display: table;
        border-collapse: separate;
        border-color: grey;
    }
    .tbody {
        display: table-row-group;
        vertical-align: middle;
        border-color: inherit;
    }
    .tr {
        display: table-row;
        vertical-align: inherit;
        border-color: inherit;
    }
    /*.reply-box {*/
    /*    margin: 10px 0 0 50px;*/
    /*    background-color :#efefef*/


    /*}*/
    /*.talk-box{*/
    /*    margin:0 50px;*/
    /*}*/
    /*.talk-box.reply{*/
    /*    font-size :16px;*/
    /*    color :#000*/
    /*}*/
    .fuck {
        border-bottom: 1px solid #e2e2e2;
    }
    .fr {
        float: right;
        text-align: right;
    }
    .sep10 {
        height: 10px;
    }
    .h1 {
        font-size: 24px;
        font-weight: 500;
        line-height: 150%;
        margin: 0 0 10px;
        padding: 0;
    }
    .gray {
        color: #999999;
    }
    .small {
        font-size: smaller;
        color: #999999;
    }
    #Bottom {
        border-top: 1px solid rgba(0, 0, 0, 0.22);
        background-color: var(--box-background-color);
        text-align: center;
        color: #999;
        padding: 0 10px;
    }
    :root {
        --box-background-color: #fff;
        --box-background-alt-color: #f9f9f9;
        --box-background-hover-color: #fafafa;
        --box-foreground-color: #000;
        --box-border-color: #e2e2e2;
        --box-border-radius: 3px;
    }
    .inner {
        padding: 10px;
        font-size: 14px;
        line-height: 150%;
        text-align: left;
    }
    strong {
        font-weight: bold;
    }
    a.dark:active,
    a.dark:link,
    a.dark:visited {
        color: gray;
        text-decoration: none;
    }
    a:active,
    a:link,
    a:visited {
        color: #778087;
        text-decoration: none;
        word-break: break-word;
    }
    a {
        color: #000;
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


