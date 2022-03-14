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
                </el-row>

                <el-row v-for="(item,index) in bookList_new" :key="index" :gutter="20" >
                    <el-col :span="4" v-for="(book, index) in item" :key="index" :offset="index > 0 ? 2 : 0">
                        <el-card :body-style="{ padding: '0px' }">
                            <img src="https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png" class="image">
                            <div style="padding: 14px;">
                                <span font-family="Helvetica Neu">{{book.book_name}}</span>
                                <div class="bottom clearfix">
                                    <el-button  class="button" @click="showbook(book)">查看图书详情</el-button>
                                </div>
                            </div>
                        </el-card>
                    </el-col>
                </el-row>

                <!--分页-->
                <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="queryInfo.pagenum"
                               :page-sizes="[4,16, 32,64]" :page-size="queryInfo.pagesize" :total="total"
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
                            :src="this.url"
                            :fit="contain">
                    </el-image>
                </el-descriptions-item>
            </el-descriptions>
            <span slot="footer" class="dialog-footer">
                <el-button @click="previewDialogVisible = false">取消</el-button>
                <el-button type="primary" @click="borrowBook()" :disabled="this.bookData[4]=='1'?false:true">预约借书</el-button>
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
                queryInfo: {
                    querytext: '',
                    pagenum: 1,
                    pagesize: 4,
                    querydata:''
                },
                total:0,
                previewDialogVisible: false,
                bookList:[],
                bookList_new : [[]],
                bookData:[],
                url:"https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png",
                //bookInfo:["book_id","book_name","book_img","book_isbn","book_status","book_money","book_category","book_address"]
            }
        },
        created() {
            this.getBookList()
        },
        methods:{
            borrowBook(){
                var  current_id = parseInt(this.bookData[0]);
                console.log(current_id)
                axios.get('/api/book/borrowbooks',
                    {
                        params:{ book_id: current_id},
                        headers: {
                            'Authorization': sessionStorage.getItem('token')}

                    }
                ).then(response =>{
                    if(parseInt(response.data.code)===200){
                        this.$message.success(response.data.msg)
                        this.getBookList();
                    }else if (parseInt(response.data.code)===201){
                        this.$message.info(response.data.msg)
                    }else this.$message.error(response.data.msg)
                }).catch((e)=>{
                    this.$message.error("发生错误")
                })
            },



            showbook(current_data){
                this.bookData = [];
                this.bookData.push(current_data.book_id)
                this.bookData.push(current_data.book_name)
                this.bookData.push(current_data.book_img)
                this.bookData.push(current_data.book_isbn)
                this.bookData.push(current_data.book_status)
                this.bookData.push(current_data.book_money)
                this.bookData.push(current_data.book_category)
                this.bookData.push(current_data.book_address)

                this.previewDialogVisible = true;
                console.log(this.bookData)
            },
            operateBook(){
                this.bookList_new= [[]];
                this.bookList_new = new Array();
                var count_row = 0;
                count_row = Math.ceil( this.bookList.length / 4);
                /*
                if (this.bookList.length%4 !==0 ){
                    count_row = this.bookList.length / 4 + 1
                }else {
                    count_row = this.bookList.length;
                }
                */

                console.log(this.bookList.length);
                for (var i = 0;i<count_row;i++){
                    this.bookList_new[i] = new Array();
                    for (var j = i *4 , k=0; j < i * 4 +4 && j<this.bookList.length && k <4; j++,k++){
                        if (JSON.stringify(this.bookList[j]) === '{}'){}
                        else {
                            this.bookList_new[i][k] = this.bookList[j];
                        }
                    }
                }
            },
            getBookList(){
                axios.post('/api/book/querybooks',JSON.stringify({
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
                        this.bookList = response.data.object;
                        this.queryInfo.querydata = this.queryInfo.querytext;
                        if (parseInt(response.data.page)===1){
                            this.queryInfo.pagenum = parseInt(response.data.page);
                        }
                        console.log("Hi 这里出错啦")
                        console.log(this.bookList)
                        this.operateBook();
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
                this.getBookList()
            },
        },
    }
</script>