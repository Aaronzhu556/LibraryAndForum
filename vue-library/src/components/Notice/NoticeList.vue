<template>
    <div>
        <!--面包屑导航区-->
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>公告管理</el-breadcrumb-item>
            <el-breadcrumb-item>公告列表</el-breadcrumb-item>
        </el-breadcrumb>

        <!--卡片视图区域-->
        <el-card>
            <!--搜索与添加-->
            <el-row :gutter="10">
                <el-col :span="6">
                    <el-input placeholder="请输入内容" v-model="queryInfo.querytext" :clearable="true" @clear="getNoticeList">
                        <el-button slot="append" icon="el-icon-search" @click="getNoticeList"></el-button>
                    </el-input>
                </el-col>
                				<el-col :span="6">
                					<el-button type="primary" @click="addDialogVisible=true">添加公告</el-button>
                				</el-col>
            </el-row>

            <!--用户列表-->
            <el-table :data="noticeList" :border="true" :stripe="true">
                <el-table-column label="序号" prop="notice_id" width="50"></el-table-column>
                <el-table-column label="公告标题" prop="notice_title"></el-table-column>
                <el-table-column label="公告内容"  prop="notice_content">

                </el-table-column>
                <el-table-column label="公告时间" prop="notice_time"></el-table-column>



                <el-table-column label="操作" width="200">
                    <template slot-scope="scope">

                    <el-button type="danger" icon="el-icon-delete" circle @click="deleteNotice(scope.row)"></el-button>

                    </template>
                </el-table-column>


            </el-table>

            <!--分页-->
            <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="queryInfo.pagenum"
                           :page-sizes="[4, 8, 16]" :page-size="queryInfo.pagesize" :total="total"
                           layout="total, sizes, prev, pager, next, jumper" background>
            </el-pagination>
        </el-card>


        		<!--添加用户的对话框-->
        		<el-dialog title="添加公告" width="50%" :visible.sync="addDialogVisible" :close-on-click-modal="false" >
        			<!--内容主体-->
        			<el-form :model="addForm" :rules="addFormRules" ref="addFormRef" label-width="70px">
        				<el-form-item label="标题" prop="notice_title">
        					<el-input v-model="addForm.notice_title"></el-input>
        				</el-form-item>
        				<el-form-item label="内容" prop="notice_content">
        					<el-input v-model="addForm.notice_content"></el-input>
        				</el-form-item>
        			</el-form>
        			<!--底部-->
        			<span slot="footer">
        				<el-button @click="addDialogVisible=false">取 消</el-button>
        				<el-button type="primary" @click="addNotice">确 定</el-button>
        			</span>
        		</el-dialog>


    </div>
</template>

<script>
    export default {
        name: "Notices",
        data() {

            return {
                //获取用户列表的参数对象
                queryInfo: {
                    querytext: '',
                    pagenum: 1,//当前的页数
                    pagesize: 4,//当前每页显示多少条数据

                },
                noticeList: [],

                totalNoticeList:[[]],
                notices:[],
                max_page:0,
                total: 0,
                //添加用户对话框显示状态
                addDialogVisible: false,

                addForm:{
                    notice_title:'',
                    notice_content:'',
                },
                addFormRules:{
                    notice_title:[
                        { required: true, message: '公告标题不能为空', trigger: 'blur' },
                    ],
                    notice_content: [
                        { required: true, message: '公告内容不能为空', trigger: 'blur' },
                    ],

                }



            }
        },
        created() {
            this.getNoticeList()
        },
        methods: {
            resetAllData(){
                this.queryInfo.pagenum = 1;
                this.articleList_admin = [];
                this.totalArticleList_admin =[[]];
                this.articles=[];
                this.max_page=0;
                this.total= 0;
            },
            getNoticeList() {
                this.resetAllData();
                axios.post('/api/notice/getallnotice', JSON.stringify({
                    querytext:this.queryInfo.querytext,
                    pagenum:this.queryInfo.pagenum,
                    pagesize:this.queryInfo.pagesize,

                }), {
                    headers: {
                        'Content-Type': 'application/json'
                    }

                }).then(response => {
                    if (parseInt(response.data.code) === 200) {
                        this.notices = response.data.object;
                        this.max_page = Math.ceil(this.notices.length / this.queryInfo.pagesize) || 1;
                        for (let i = 0; i < this.max_page; i++) {
                            this.totalNoticeList[i] = this.notices.slice(
                                this.queryInfo.pagesize * i,
                                this.queryInfo.pagesize * (i + 1)
                            );
                            console.log(this.totalNoticeList[i]);
                        }
                        this.noticeList = this.totalNoticeList[this.queryInfo.pagenum-1];


                        this.total = parseInt(response.data.info);
                    } else {
                        this.$message.info(response.data.msg)
                    }
                }).catch(() => {
                    this.$message.error("获取失败")
                })
            },
            deleteNotice(current_data){
                axios.get('/api/notice/deletenotice',
                    {
                        params:{
                            notice_id : current_data.notice_id,
                        },
                        headers: {Authorization: sessionStorage.getItem('token')}
                    }

                ).then((response)=>{
                    if (parseInt(response.data.code)===200){
                        this.$message.success(response.data.msg);
                        this.getNoticeList();
                    }
                }).catch(() => {
                    this.$message.error('操作失败');
                })
            },
            //监听pagesize改变的事件
            handleSizeChange(newSize) {
                this.queryInfo.pagesize = newSize
                this.getNoticeList()
            },
            //监听页码值改变的事件
            handleCurrentChange(newPage) {
                this.queryInfo.pagenum = newPage
                //this.getNoticeList()
                this.noticeList = this.totalNoticeList[newPage-1];
            },

        }
    }
</script>

<style lang="less" scoped>

</style>