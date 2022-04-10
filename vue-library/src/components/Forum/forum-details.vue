<template>
    <div id="#app">
        <!--面包屑导航区-->
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path: '/forum_list' }">社区论坛</el-breadcrumb-item>
            <el-breadcrumb-item>帖子详情</el-breadcrumb-item>
        </el-breadcrumb>

<!--            &lt;!&ndash;搜索与添加&ndash;&gt;-->
<!--            <el-row :gutter="10">-->
<!--                <el-col :span="6">-->
<!--                    <el-input placeholder="请输入内容" v-model="queryInfo.querytext" :clearable="true" @clear="getArticleList">-->
<!--                        <el-button slot="append" icon="el-icon-search" @click="getArticleList"></el-button>-->
<!--                    </el-input>-->
<!--                </el-col>-->
<!--                &lt;!&ndash;                <el-col :span="6">&ndash;&gt;-->
<!--                &lt;!&ndash;                    <el-button type="primary" @click="goAddPage" >添加书籍</el-button>&ndash;&gt;-->
<!--                &lt;!&ndash;                </el-col>&ndash;&gt;-->
<!--            </el-row>-->
        <div>
            <div class="Rightbar">
            <div class="sep20"></div>
<!--            <div class="box">-->
<!--                <div class="cell">-->
<!--                    <table cellpadding="0" cellspacing="0" border="0" width="100%">-->
<!--                        <tbody>-->
<!--                        <tr>-->
<!--                            <td width="48" valign="top">-->
<!--                                <a href="#">-->
<!--&lt;!&ndash;                                    <img&ndash;&gt;-->
<!--&lt;!&ndash;                                            :src="require(`@/assets/${$store.state.user.userImg}`)"&ndash;&gt;-->
<!--&lt;!&ndash;                                            class="avatar"&ndash;&gt;-->
<!--&lt;!&ndash;                                            border="0"&ndash;&gt;-->
<!--&lt;!&ndash;                                            align="default"&ndash;&gt;-->
<!--&lt;!&ndash;                                            style="max-width: 48px; max-height: 48px;"&ndash;&gt;-->
<!--&lt;!&ndash;                                    />&ndash;&gt;-->
<!--                                </a>-->
<!--                            </td>-->
<!--                            <td width="10" valign="top"></td>-->
<!--                            <td width="auto" align="left">-->
<!--                      <span class="bigger">-->
<!--                       <strong>猜你喜欢</strong>-->
<!--                      </span>-->
<!--                            </td>-->
<!--                        </tr>-->
<!--                        </tbody>-->
<!--                    </table>-->
<!--                    <div class="sep10"></div>-->

<!--                        <tbody>-->
<!--                        <div style="height: 350px;width: 100%">-->
<!--                            <div class="card-content" style="height: 350px">-->
<!--                                <el-scrollbar style="height: 100%" >-->
<!--                                    <div v-for="(article,index) in 10" :key="index">-->

<!--                                        <el-tag type="danger">00</el-tag>-->

<!--                                        <el-tooltip class="item" effect="light" placement="top">-->
<!--                                            <div slot="content">1200</div>-->
<!--                                            <el-button type="text" style="position: relative;left: 30px;color: 	#000000" @click="CheckHotArticleDetail(article)">222</el-button>-->
<!--                                        </el-tooltip>-->


<!--                                        <strong style="color: #C80000;float: right" >2000</strong>-->

<!--                                        <br><br>-->

<!--                                    </div>-->



<!--                                </el-scrollbar>-->

<!--                            </div>-->

<!--                        </div>-->
<!--                        </tbody>-->

<!--                </div>-->
<!--                <div class="cell" id="member-activity">-->
<!--                    <div class="member-activity-bar">-->
<!--                        <div class="member-activity-start" style="width: 15px;"></div>-->
<!--                    </div>-->
<!--                </div>-->
<!--                <div class="cell" style="padding: 5px;">-->
<!--                    <table cellpadding="0" cellspacing="0" border="0" width="100%">-->
<!--                        <tbody>-->
<!--                        <tr>-->
<!--                            <td width="28">-->
<!--                                <a href="/new">-->
<!--                                    <img src width="28" border="0" />-->
<!--                                </a>-->
<!--                            </td>-->
<!--                            <td width="10"></td>-->
<!--                            <td width="auto" valign="middle" align="left">-->
<!--                                &lt;!&ndash; <a @click="newcomment">在此贴发表评论</a> &ndash;&gt;-->
<!--                            </td>-->
<!--                        </tr>-->
<!--                        </tbody>-->
<!--                    </table>-->
<!--                </div>-->

<!--            </div>-->
                <div class="card" >
                    <header class="card-header">
                        <!--                                <div>-->
                        <!--                                    <el-avatar src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"></el-avatar>-->
                        <!--                                </div>-->
                        <strong class="card-header-title"  >猜你喜欢</strong>

                        <!--                                <p class="card-header-title"  >粉丝:50</p>-->
                        <!--                                <p class="card-header-title"  >发帖数量:5</p>-->
                    </header>

                    <div class="card-content" style="height: 350px">
                        <el-scrollbar style="height: 100%" >
                            <div v-for="(article,index) in 10" :key="index">

                                <el-tag type="danger">{{index+1}}</el-tag>

                                <el-tooltip class="item" effect="light" placement="top">
                                    <div slot="content">霍乱时期的爱情</div>
                                    <el-button type="text" style="position: relative;left: 30px;color: 	#000000" @click="CheckHotArticleDetail(article)">霍乱时期的爱情</el-button>
                                </el-tooltip>


<!--                                <strong style="color: #C80000;float: right" >2000</strong>-->

                                <br><br>

                            </div>



                        </el-scrollbar>

                    </div>

                </div>
        </div>

<!--            <div class="Rightbar" style="position: relative;top: 200px;left: 290px">-->
<!--                <div class="sep20"></div>-->
<!--                <div class="box">-->
<!--                    <div class="cell">-->
<!--                        <table cellpadding="0" cellspacing="0" border="0" width="100%">-->
<!--                            <tbody>-->
<!--                            <tr>-->
<!--                                <td width="48" valign="top">-->
<!--                                    <a href="#">-->
<!--                                        &lt;!&ndash;                                    <img&ndash;&gt;-->
<!--                                        &lt;!&ndash;                                            :src="require(`@/assets/${$store.state.user.userImg}`)"&ndash;&gt;-->
<!--                                        &lt;!&ndash;                                            class="avatar"&ndash;&gt;-->
<!--                                        &lt;!&ndash;                                            border="0"&ndash;&gt;-->
<!--                                        &lt;!&ndash;                                            align="default"&ndash;&gt;-->
<!--                                        &lt;!&ndash;                                            style="max-width: 48px; max-height: 48px;"&ndash;&gt;-->
<!--                                        &lt;!&ndash;                                    />&ndash;&gt;-->
<!--                                    </a>-->
<!--                                </td>-->
<!--                                <td width="10" valign="top"></td>-->
<!--                                <td width="auto" align="left">-->
<!--                      <span class="bigger">-->
<!--                          <strong>猜你喜欢</strong>-->
<!--                      </span>-->
<!--                                </td>-->
<!--                            </tr>-->
<!--                            </tbody>-->
<!--                        </table>-->
<!--                        <div  style="height: 350px;position: relative;left: 290px;width: 50px;length:100px">-->

<!--                        </div>-->



<!--                    </div>-->

<!--                </div>-->
<!--            </div>-->
            <div class="Main">
                <div class="sep20"></div>
                <div class="box" style="border-bottom:0px;">
                    <div class="header">
                        <div class="fr">
                            <a href="#">
                                <img
                                        src="https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg"
                                        class="avatar"
                                        border="0"
                                        align="default"
                                        style="max-width: 48px; max-height: 48px;"
                                />
                            </a>
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
                                    <img src="https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg" class="size" />

                                </td>
                                <td width="10" valign="top"></td>
                                <td width="auto" valign="top" align="left">
                                    <div class="fr">
                                        &nbsp; &nbsp;
                                        <span class="no"></span>
                                    </div>
                                    <div class="sep3"></div>
                                    <strong>
                                        <a href="#" class="dark">{{c.comment_user_name}}</a>
                                    </strong>&nbsp; &nbsp;
                                    <span class="ago">{{c.comment_time}}</span>
                                    <div class="sep5"></div>
                                    <div class="reply_content">{{c.comment_content}}</div>
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
                    content : ''
                },
                commentList:[],

                commentContent:'',
                total: 0,
                similarArticle:[],

                previewDialogVisible: false,
                previewPath : "",
            }
        },
        created() {
            this.article.id = this.$route.params.articleid;
            this.article.title = this.$route.params.articletitle;
            this.article.username = this.$route.params.articleusername;
            this.getArticleContent();
            this.getCommentList();

           // this.user_name_article = sessionStorage.getItem('user_name');
            // console.log(this.managerRole);
        },
        methods: {
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
                axios.get('/api/comment/queryallcomment',
                    {
                        params:{ comment_article_id:  this.article.id  },
                        headers: {
                            'Authorization': sessionStorage.getItem('token')}

                    }).then(response => {
                    if (parseInt(response.data.code) === 200) {
                        this.commentList = response.data.object;

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
                this.getArticleList()
            },
            //监听页码值改变的事件
            handleCurrentChange(newPage) {
                this.queryInfo.pagenum = newPage
                this.getArticleList()
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
</style>