<template>
    <div>
        <!--面包屑导航区-->
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>图书借阅</el-breadcrumb-item>
            <el-breadcrumb-item>图书检索与借阅</el-breadcrumb-item>
        </el-breadcrumb>
        <el-scrollbar style="height:100%">
            <el-card>
                <!--搜索与添加-->
                <el-row :gutter="10">
                    <el-col :span="6">
                        <el-input placeholder="请输入内容" v-model="queryInfo.querytext" :clearable="true" @clear="getBookList">
                            <el-button slot="append" icon="el-icon-search" @click="getBookList"></el-button>
                        </el-input>
                    </el-col>
                    <el-col :span="6">
                        <el-cascader clearable @clear="clearQueryData" v-model="cate_value" :options="cate_options" :props="cascaderProps" @change="handleChange" style="width: 300px"></el-cascader>
                    </el-col>
                    <el-col :span="6">
                        <el-radio-group v-model="sort_radio" size="small" @change="changeSortMethod">
                            <el-radio-button label="借阅量"></el-radio-button>
                            <el-radio-button label="星级"></el-radio-button>
                            <el-radio-button label="最新上架"></el-radio-button>
                        </el-radio-group>
                    </el-col>
                    <el-col  :span="6">
                        <el-button @click="resetData" size="medium">重置查询条件</el-button>
                    </el-col>
                </el-row>

                <el-row v-for="(item,index) in bookList_new" :key="index" :gutter="20" >
                    <el-col :span="4" v-for="(book, index) in item" :key="index" :offset="index > 0 ? 2 : 0">
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
<!--                                    <el-link icon="el-icon-more-outline" @click.native="open2(book)"></el-link>-->
                                    <el-button  class="button" @click="goToBookDetails(book.book_id)">查看图书详情</el-button>
                                </div>
                            </div>
                        </el-card>
                    </el-col>
                </el-row>

                <!--分页-->
                <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="queryInfo.pagenum"
                               :page-sizes="[4,8,16, 32,64]" :page-size="queryInfo.pagesize" :total="total"
                               layout="total, sizes, prev, pager, next, jumper" background>
                </el-pagination>
            </el-card>

        </el-scrollbar>

        <el-dialog title="图书详情" width="50%" :visible.sync="previewDialogVisible">
            <el-descriptions>
                <el-descriptions-item label="书籍名">{{this.bookData[1]}}</el-descriptions-item>
                <el-descriptions-item label="ISBN">{{this.bookData[3]}}</el-descriptions-item>
                <el-descriptions-item label="价  格">{{this.bookData[5]}}</el-descriptions-item>
                <el-descriptions-item label="状  态">
                    <el-tag size="small" type="success" v-if="this.bookData[4]=='1'">空闲</el-tag>
                    <el-tag size="small" type="danger" v-else-if="this.bookData[4]=='0'" >已被借出</el-tag>
                    <el-tag size="small" type="warning" v-else >已被预约</el-tag>
                </el-descriptions-item>
                <el-descriptions-item label="类  别">
                    <el-tag size="small"  >{{this.bookData[6]}}</el-tag>
                </el-descriptions-item>
                <el-descriptions-item label="出版社">{{this.bookData[7]}}</el-descriptions-item>

                <el-descriptions-item label="封面图">
                    <el-image
                            style="width: 100px; height: 100px"
                            :src="this.bookData[2]"
                            :fit="contain">
                    </el-image>
                </el-descriptions-item>
                <el-descriptions-item label="内容简介">{{this.bookData[8]}}</el-descriptions-item>
            </el-descriptions>
            <span slot="footer" class="dialog-footer">
                <el-button @click="previewDialogVisible = false">取消</el-button>
                <el-button type="primary" @click="borrowBook()" :disabled="this.bookData[4]=='1'?false:true">预约借书</el-button>
            </span>
        </el-dialog>

        <el-dialog title="图书评论" width="50%" :visible.sync="previewDialogVisible2">
            <el-empty   v-if="bookCommentListNew.length ===0"></el-empty>
            <div   v-if="bookCommentListNew.length !==0">
                <div style="display:inline-block">
                <h2>综合评分:</h2>
                </div>
            <div style="display:inline-block">
                <el-rate
                        v-model="total_star"
                        disabled
                        show-score
                        text-color="#ff9900"
                >
                </el-rate>
            </div>

            </el-rate>
                <div style="display:inline-block;float: right">
                    <el-radio-group v-model="radio_star" size="mini" @change="changeComment">

                        <el-radio :label="1">好评({{this.comment_good}})</el-radio>
                        <el-radio :label="2">差评({{this.comment_bad}})</el-radio>
                        <el-radio :label="3">全部({{this.bookCommentList.length}})</el-radio>
                    </el-radio-group>
                </div>

            </div >
            <div class="card-content" style="height: 300px">
                <el-scrollbar style="height: 100%" >
                    <div v-for="(item,index) in bookCommentListNew">
                        <el-card>
                            <div> <el-avatar :src="item.borrow_user_img"></el-avatar></div>
                            <div>
                            <span><strong>{{item.borrow_user_name}}:</strong></span>
                            “{{item.borrow_comment_context}}”

                            <h5 style="float: right">{{item.borrow_comment_time}}</h5>
                            <br>
                            <el-rate
                                    v-model="item.borrow_comment_star"
                                    disabled
                                    show-score
                                    text-color="#ff9900"
                                    score-template="{value}">
                            </el-rate>
                            </div>

                        </el-card>
                        <br>
                    </div>

                </el-scrollbar>
            </div>

            <span slot="footer" class="dialog-footer">
                <el-button @click="closeDialog">取消</el-button>
<!--                <el-button type="primary" @click="borrowBook()" :disabled="this.bookData[4]=='1'?false:true">预约借书</el-button>-->
            </span>
        </el-dialog>
    </div>
</template>
<style>
    .time {
        font-size: 13px;
        color: #999;
    }
    .el-row {
        margin-bottom: 20px;
    }

    .bottom {
        margin-top: 13px;
        line-height: 12px;
    }

    .button {
        padding: 0;
        float: right;
    }

    .image {
        width: 100%;
        display: block;
    }

    .clearfix:before,
    .clearfix:after {
        display: table;
        content: "";
    }

    .clearfix:after {
        clear: both
    }
</style>
<script>
    export default {
        data() {
            return{
                //指定级联选择器的配置对象
                cascaderProps: {
                    expandTrigger: 'hover',
                    checkStrictly: true,
                    value: 'category_id',
                    label: 'category_name',
                    children: 'category_children'
                },
                queryInfo: {
                    querytext: '',
                    pagenum: 1,
                    pagesize: 4,
                    querydata:'',
                    querycateid:0,
                },

                total:0,
                total_star:0,
                radio_star:3,
                comment_good:0,
                comment_bad:0,
                comment_all:0,
                previewDialogVisible: false,
                previewDialogVisible2 :false,

                current_page:0,
                max_page:0,
                totalBookList:[[]],
                bookList:[],
                bookList_new : [[]],
                bookCommentList:[],
                bookCommentListNew:[],
                bookData:[],
                cate_options:[],
                cate_value:[],
                sort_radio:'',
                url:"https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png",
                //bookInfo:["book_id","book_name","book_img","book_isbn","book_status","book_money","book_category","book_address"]
            }
        },
        created() {
            this.getBookList()
            this.getAllCategory()
        },
        methods:{
            resetData(){
                this.queryInfo.querycateid = 0;
                this.queryInfo.querytext = "";
                this.queryInfo.querydata = "";
                this.sort_radio="";
                this.cate_value="",
                this.getBookList();
            },

            changeSortMethod(){
                this.queryInfo.querycateid = 0;
                this.queryInfo.querytext = "";
                console.log(this.sort_radio)
                if (this.sort_radio==="借阅量"){
                    axios.post("/api/book/getbookbyborrow",JSON.stringify({
                        querytext: '',
                        pagenum: 1,
                        pagesize: 8,
                        querydata:'',
                        querycateid:0,
                    }),{

                        headers: {
                            'Content-Type': 'application/json',
                            'Authorization':sessionStorage.getItem('token')

                        }

                    }).then(response=>{
                        if (parseInt(response.data.code)===200){
                            this.$message.success(response.data.msg);
                            this.bookList = response.data.object;
                            this.max_page = Math.ceil(this.bookList.length / this.queryInfo.pagesize) || 1;
                            for (let i = 0; i < this.max_page; i++) {
                                this.totalBookList[i] = this.bookList.slice(
                                    this.queryInfo.pagesize * i,
                                    this.queryInfo.pagesize * (i + 1)
                                );
                                console.log(this.totalBookList[i]);
                            }
                            this.operateBook(this.queryInfo.pagenum);
                        }
                    }).catch(()=>{
                        this.$message.error("发生错误")
                    })
                }
                else if (this.sort_radio==="星级"){
                    axios.post("/api/book/getbookbystar",JSON.stringify({
                        querytext: '',
                        pagenum: 1,
                        pagesize: 8,
                        querydata:'',
                        querycateid:0,
                    }),{

                        headers: {
                            'Content-Type': 'application/json',
                            'Authorization':sessionStorage.getItem('token')
                        }

                    }).then(response=>{
                        if (parseInt(response.data.code)===200){
                            this.$message.success(response.data.msg);
                            this.bookList = response.data.object;
                            this.max_page = Math.ceil(this.bookList.length / this.queryInfo.pagesize) || 1;
                            for (let i = 0; i < this.max_page; i++) {
                                this.totalBookList[i] = this.bookList.slice(
                                    this.queryInfo.pagesize * i,
                                    this.queryInfo.pagesize * (i + 1)
                                );
                                console.log(this.totalBookList[i]);
                            }
                            this.operateBook(this.queryInfo.pagenum);
                        }
                    }).catch(()=>{
                        this.$message.error("发生错误")
                    })
                }
                else if (this.sort_radio==="最新上架"){
                    axios.post("/api/book/getbookbytime",JSON.stringify({
                        querytext: '',
                        pagenum: 1,
                        pagesize: 8,
                        querydata:'',
                        querycateid:0,
                    }),{

                        headers: {
                            'Content-Type': 'application/json',
                            'Authorization':sessionStorage.getItem('token')
                        }

                    }).then(response=>{
                        if (parseInt(response.data.code)===200){
                            this.$message.success(response.data.msg);
                            this.bookList = response.data.object;
                            this.max_page = Math.ceil(this.bookList.length / this.queryInfo.pagesize) || 1;
                            for (let i = 0; i < this.max_page; i++) {
                                this.totalBookList[i] = this.bookList.slice(
                                    this.queryInfo.pagesize * i,
                                    this.queryInfo.pagesize * (i + 1)
                                );
                                console.log(this.totalBookList[i]);
                            }
                            this.operateBook(this.queryInfo.pagenum);
                        }
                    }).catch(()=>{
                        this.$message.error("发生错误")
                    })
                }
            },

            clearQueryData(){
                this.queryInfo.querycateid = 0;
                this.queryInfo.querytext = "";
                console.log("进来了")
                this.getBookList();
            },



            handleChange() {
                if (this.cate_value.length !== 2) {
                    this.cate_value = []
                    return
                }
                this.queryInfo.querycateid = this.cate_value[1];
                this.queryInfo.querytext = "根据类型排序";
                this.getBookList();
            },
            getAllCategory() {
                axios.post('/api/category/searchallcategory', JSON.stringify({
                    querytext:'',
                    pagenum:1,
                    pagesize:1000,

                }), {
                    headers: {
                        'Content-Type': 'application/json'
                    }

                }).then(response => {
                    if (parseInt(response.data.code) === 200) {
                        this.cate_options = response.data.object;
                        console.log(this.cateList);
                    }
                }).catch(() => {
                    this.$message.error('请求失败')
                })
            },
            goToBookDetails(current_data){
                this.$router.push({path: '/book_details', query: {bookid: current_data}})
            },
            closeDialog(){
                this.previewDialogVisible2 = false;
                this.radio_star = 3;
            },
            changeComment(){
                this.bookCommentListNew = [];
                if (this.radio_star===1){
                    for (var i=0;i<this.bookCommentList.length;i++){
                        if (this.bookCommentList[i].borrow_comment_star>=3) this.bookCommentListNew.push(this.bookCommentList[i]);
                    }
                }else if (this.radio_star===2){
                    for (var i=0;i<this.bookCommentList.length;i++){
                        if (this.bookCommentList[i].borrow_comment_star<3) this.bookCommentListNew.push(this.bookCommentList[i]);
                    }
                }else if (this.radio_star===3) this.bookCommentListNew = this.bookCommentList.slice();
            },
            open2(current_data){
                this.total_star = current_data.book_star
                this.comment_good=0;
                this.comment_bad=0;
                console.log(current_data)
                this.bookCommentList = [];
                this.bookCommentListNew=[];
                axios.get('/api/borrow/querybookcommentbyid',{
                    params:{
                        book_id : current_data.book_id
                    },
                    headers:{
                        'Authorization' : sessionStorage.getItem('token')
                    }
                }).then((response)=>{
                    if (parseInt(response.data.code)===200){
                        this.bookCommentList = response.data.object;
                        this.bookCommentListNew = this.bookCommentList.slice();

                        for (var i=0;i<this.bookCommentList.length;i++){
                            if (this.bookCommentList[i].borrow_comment_star>=3) this.comment_good++;
                            else if (this.bookCommentList[i].borrow_comment_star<3) this.comment_bad++;
                        }
                        console.log(  this.bookCommentList);
                    }
                }).catch(()=>{
                    this.$message.error("发生错误");
                })


                this.previewDialogVisible2 = true;




            },





            operateBook(current_page){
                this.bookList_new= [[]];
                this.bookList_new = new Array();
                var count_row = 0;
                count_row = Math.ceil( this.totalBookList[current_page-1].length / 4);

                for (var i = 0;i<count_row;i++){
                    this.bookList_new[i] = new Array();
                    for (var j = i *4 , k=0; j < i * 4 +4 && j<this.totalBookList[current_page-1].length && k <4; j++,k++){
                        if (JSON.stringify(this.totalBookList[current_page-1][j]) === '{}'){}
                        else {
                            this.bookList_new[i][k] = this.totalBookList[current_page-1][j];
                        }
                    }
                }
            },
            getBookList(){
                this.queryInfo.pagenum = 1;
                axios.post('/api/book/querybooks',JSON.stringify({
                    querytext:this.queryInfo.querytext,
                    pagenum:this.queryInfo.pagenum,
                    pagesize:this.queryInfo.pagesize,
                    querydata: this.queryInfo.querydata,
                    querycateid:this.queryInfo.querycateid,

                }), {
                    headers: {
                        'Content-Type': 'application/json'
                    }

                }).then(response => {
                    if (parseInt(response.data.code) === 200) {
                        this.bookList = response.data.object;
                        this.max_page = Math.ceil(this.bookList.length / this.queryInfo.pagesize) || 1;
                        for (let i = 0; i < this.max_page; i++) {
                            this.totalBookList[i] = this.bookList.slice(
                                this.queryInfo.pagesize * i,
                                this.queryInfo.pagesize * (i + 1)
                            );
                            console.log(this.totalBookList[i]);
                        }


                        // this.queryInfo.querydata = this.queryInfo.querytext;
                        // if (parseInt(response.data.page)===1){
                        //     this.queryInfo.pagenum = parseInt(response.data.page);
                        // }
                        // console.log("Hi 这里出错啦")
                        // console.log(this.bookList)
                        this.operateBook(this.queryInfo.pagenum);
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
                this.getBookList()
            },
            //监听页码值改变的事件
            handleCurrentChange(newPage) {
                this.queryInfo.pagenum = newPage
                //this.getBookList()
                this.operateBook(newPage)
            },
        },
    }
</script>