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

                <el-row v-for="(item,index) in 4" :key="item" :gutter="20" >
                    <el-col :span="4" v-for="(o, index) in 4" :key="o" :offset="index > 0 ? 2 : 0">
                        <el-card :body-style="{ padding: '0px' }">
                            <img src="https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png" class="image">
                            <div style="padding: 14px;">
                                <span>好吃的汉堡</span>
                                <div class="bottom clearfix">
                                    <el-button type="text" class="button">操作按钮</el-button>
                                </div>
                            </div>
                        </el-card>
                    </el-col>
                </el-row>


            </el-card>

        </el-scrollbar>



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
                    pagesize: 16,
                },

                bookList:[],
                bookList_new :[],
            }
        },
        created() {
            this.getBookList()
        },
        methods:{


            getBookList() {
                axios.post('/api/book/querybooks',JSON.stringify({
                    //querytext:this.queryInfo.querytext,
                    pagenum:this.queryInfo.pagenum,
                    pagesize:this.queryInfo.pagesize,

                }), {
                    headers: {
                        'Content-Type': 'application/json'
                    }

                }).then(response => {
                    if (parseInt(response.data.code) === 200) {
                        this.bookList = response.data.object;


                        var count = this.bookList.length/4

                        //this.total = parseInt(response.data.info);  这是为了记录一页最大数量   因为大多数时候都是不满一页的
                    } else {
                        this.$message.info(response.data.msg)
                    }
                }).catch(() => {
                    this.$message.error("获取失败")
                })
            },
        },
    }
</script>