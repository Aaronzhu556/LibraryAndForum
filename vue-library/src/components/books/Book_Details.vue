<template>

    <div>
    <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/books' }">图书借阅</el-breadcrumb-item>
        <el-breadcrumb-item>图书详细信息</el-breadcrumb-item>
    </el-breadcrumb>
    <div class="book-detail">
        <el-card style="width: 2000px">
            <el-row class="detail-box">
                <el-col :span="12" class="book-image">
                    <div style="position: relative;right: -60px">
                    <img :src="book.book_img"  referrerPolicy="no-referrer" style="width: 200px">
                    </div>
                </el-col>
                <el-row class="content">
                    <el-col :span="12" class="title">
                        <div style="position: relative;right: -450px">
                        <span>{{ book.book_name }}</span>
                        </div>
                    </el-col>
                    <el-col :span="12" class="rate">
                        <div  style="position: relative;right: -670px;display:inline-block">
                        <el-rate
                                v-model="book.book_star"
                                disabled
                                show-score
                                text-color="#ff9900"
                                score-template="{value}">
                        </el-rate>
                            <el-link icon="el-icon-more-outline" @click.native="open2()"></el-link>
                        </div>
                    </el-col>
                    <el-col :span="12" class="author-press">
                        <div style="position: relative;right: -450px">
                        <el-button type="text" @click.native="goToSearch(book.press)">{{ book.book_address }}</el-button> 编
                        <el-button type="text" @click.native="goToSearch(book.author)">{{ book.book_author }}</el-button> 著
                        </div>
                    </el-col>
                    <el-col :span="12" >
                        <div style="position: relative;right: -450px">
                            <el-tag >{{ book.book_category_name }}</el-tag>
                        </div>
                    </el-col>
                    <el-col :span="12" >
                        <div style="position: relative;right: -450px">
                            <el-tag size="small" type="success" v-if="book.book_status=='1'">空闲</el-tag>
                            <el-tag size="small" type="danger" v-else-if="book.book_status=='0'" >已被借出</el-tag>
                            <el-tag size="small" type="warning" v-else >已被预约</el-tag>
                        </div>
                    </el-col>



                    <el-col>
                        <el-card header="简介" body-style="text-align: left" class="desc-card">
                            {{ book.book_context }}
                        </el-card>
                    </el-col>
                    <!-- <el-col :span="12" class="adress">
                      <span>配送至</span>
                      <el-dropdown split-button type="primary" @click="selectAddress" @command="test">
                        {{ defaultAddress.detail_address }}
                        <el-dropdown-menu slot="dropdown">
                          <div v-for="(address, index) in addressesList" :key="address.addr_id">
                            <el-dropdown-item v-if="defaultAddress.addr_id!=address.addr_id">{{ address.detail_address + index }}</el-dropdown-item>
                          </div>
                        </el-dropdown-menu>
                      </el-dropdown>
                    </el-col> -->
                </el-row>
            </el-row>
            <el-row>
                <el-col :span="24" class="button-group">
                    <el-button-group>
                        <!-- <el-button type="sucess" @click.native="addToCart">加入购物车</el-button> -->
                        <el-button type="primary" @click.native="borrowBook" :disabled="book.book_status=='1'?false:true">立即预定</el-button>
                    </el-button-group>
                </el-col>
            </el-row>

            <!-- <BookItem :book="book" /> -->
        </el-card>
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

                <!--                <el-button type="primary" @click="borrowBook()" :disabled="this.bookData[4]=='1'?false:true">预约借书</el-button>-->
            </span>
        </el-dialog>


    </div>
    </div>
</template>

<script>

    export default {
        name: 'bookdetails',
        inject:['reload'],

        data() {
            return {

                book: {
                },

                total_star:0,
                radio_star:3,
                comment_good:0,
                comment_bad:0,
                comment_all:0,
                previewDialogVisible: false,
                previewDialogVisible2 :false,
                bookList:[],
                bookList_new : [[]],
                bookCommentList:[],
                bookCommentListNew:[],
                bookData:[],


            }
        },

        created() {
            this.getBookDetails();
        },
        methods: {
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
            open2(){
                this.total_star = this.book.book_star
                this.comment_good=0;
                this.comment_bad=0;
                this.bookCommentList = [];
                this.bookCommentListNew=[];
                axios.get('/api/borrow/querybookcommentbyid',{
                    params:{
                        book_id : this.book.book_id
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
            getBookDetails(){
                axios.get('/api/book/getbookdetails',{
                    params:{
                        book_id:this.$route.query.bookid
                    },
                    headers:{
                        'Authorization' :sessionStorage.getItem('token')
                    }
                }).then((response)=>{
                    if (parseInt(response.data.code)===200){
                        this.book = response.data.object;
                        this.book.book_img = "/api" + this.book.book_img
                        console.log( this.book)
                    }
                })
            },
            borrowBook(){
                var  current_id = this.$route.query.bookid;
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
                        this.reload();
                    }else if (parseInt(response.data.code)===201){
                        this.$message.info(response.data.msg)
                    }else this.$message.error(response.data.msg)
                }).catch((e)=>{
                    this.$message.error("发生错误")
                })
            },

        }
    }
</script>
<style lang="less" scoped>
    .book-detail {
        display: flex;
        margin: 0 50px;
    .detail-box {
        text-align: center;
    .book-image {
        padding-left: 20px;
    width: 165px;
     height: 240px;
     max-width: 165px;
        max-height: 240px;
    }
    .content {
    .title {
        color: #333333;
        font-size: 25px;
    }
    .rate {
        margin-top: 10px;
    }
    .price {
        font-size: 31px;
        color: #f63515;
        line-height: 30px;
        font-weight: 700;
    }

    .desc-card {
        margin: 50px 60px;
    }
    }
    }
    .button-group {
        text-align: center;
    }
    }
</style>