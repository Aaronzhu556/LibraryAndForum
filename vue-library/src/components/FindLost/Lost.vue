<template>
    <div>
        <!--面包屑导航区-->
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>失物拾物</el-breadcrumb-item>
            <el-breadcrumb-item>拾物信息</el-breadcrumb-item>
        </el-breadcrumb>
        <div style="height: 800px">
        <el-scrollbar style="height:100%">
            <el-card>
                <!--搜索与添加-->
                <el-row :gutter="10">
                    <el-col :span="6">
                        <el-input placeholder="请输入内容" v-model="queryInfo.querytext" :clearable="true" @clear="getAllLost">
                            <el-button slot="append" icon="el-icon-search" @click="getAllLost"></el-button>
                        </el-input>
                    </el-col>
                </el-row>
                <el-empty description="暂无数据" v-if="lostList.length===0"></el-empty>
                <el-row v-for="(item,index) in lostList_new" :key="index" :gutter="20" >
                    <el-col :span="4" v-for="(lost, index) in item" :key="index" :offset="index > 0 ? 2 : 0">
                        <el-card :body-style="{ padding: '0px' }">
                            <el-carousel height="180px">
                                <el-carousel-item v-for="(img,index1) in lost.lost_img_list" :key="index1">
                                    <el-image :src="img"></el-image>
                                </el-carousel-item>
                            </el-carousel>
                            <div style="padding: 14px;">
                                <span font-family="Helvetica Neu"><strong>{{lost.lost_name}}</strong></span>
                                <span style="float: right">
                                <el-tag size="small" type="success" v-if="lost.lost_status=='0'">无人认领</el-tag>
                                <el-tag size="small" type="danger" v-else-if="lost.lost_status=='1'" >已被认领</el-tag>
                                </span>

                                <div class="bottom clearfix">
                                    <!--                                    <el-link icon="el-icon-more-outline" @click.native="open2(book)"></el-link>-->
                                    <el-button  class="button" @click="showLost(lost)">查看失物详情</el-button>
                                </div>
                            </div>
                        </el-card>
                    </el-col>
                </el-row>

                <!--分页-->
                <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="queryInfo.pagenum"
                               :page-sizes="[8,16, 32,64]" :page-size="queryInfo.pagesize" :total="total"
                               layout="total, sizes, prev, pager, next, jumper" background>
                </el-pagination>
            </el-card>

        </el-scrollbar>
        </div>

        <el-dialog title="失物详情" width="50%" :visible.sync="previewDialogVisible">
            <el-descriptions>
                <el-descriptions-item label="失物名">{{this.lostData[1]}}</el-descriptions-item>
                <el-descriptions-item label="失物时间">{{this.lostData[3]}}</el-descriptions-item>

                <el-descriptions-item label="状  态">
                    <el-tag size="small" type="success" v-if="this.lostData[4]=='0'">无人认领</el-tag>
                    <el-tag size="small" type="danger" v-else-if="this.lostData[4]=='1'" >已被认领</el-tag>

                </el-descriptions-item>

                <el-descriptions-item label="失物简介">{{this.lostData[2]}}</el-descriptions-item>


            </el-descriptions>
            <span slot="footer" class="dialog-footer">
                <el-button @click="previewDialogVisible = false">取消</el-button>
<!--                <el-button type="primary" @click="borrowBook()" :disabled="this.lostData[4]=='1'?false:true">预约借书</el-button>-->
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
        name:'lost',
        data() {
            return{
                queryInfo: {
                    querytext: '',
                    pagenum: 1,
                    pagesize: 20,
                    querydata:'',

                },
                lostList:[],
                lostList_new:[[]],
                total:0,
                lostData:[],

                previewDialogVisible: false,



            }
        },
        created() {
            this.getAllLost();
        },
        methods:{

            operateLost(){
                this.lostList_new= [[]];
                this.lostList_new = new Array();
                var count_row = 0;
                count_row = Math.ceil( this.lostList.length / 4);


                for (var i = 0;i<count_row;i++){
                    this.lostList_new[i] = new Array();
                    for (var j = i *4 , k=0; j < i * 4 +4 && j<this.lostList.length && k <4; j++,k++){
                        if (JSON.stringify(this.lostList[j]) === '{}'){}
                        else {
                            this.lostList_new[i][k] = this.lostList[j];
                            for (var o=0;o<this.lostList_new[i][k].lost_img_list.length;o++)
                                this.lostList_new[i][k].lost_img_list[o] = "/api" + this.lostList_new[i][k].lost_img_list[o];
                        }
                    }
                }
            },
            getAllLost(){
                axios.post("/api/lost/getalllost",JSON.stringify(this.queryInfo),{
                    headers:{
                        'Authorization' :sessionStorage.getItem('token'),
                        'Content-Type' : 'application/json'
                    }
                }).then(response=>{
                    if (parseInt(response.data.code)===200){
                        this.lostList = response.data.object;
                        console.log(this.lostList)
                        this.queryInfo.querydata = this.queryInfo.querytext;
                        if (parseInt(response.data.page)===1){
                            this.queryInfo.pagenum = parseInt(response.data.page);
                        }
                        this.total = parseInt(response.data.info);
                        this.operateLost();
                    }else this.$message.info(response.data.msg);
                }).catch(()=>{
                    this.$message.error("发生错误")
                })
            },








            showLost(current_data){
                this.lostData = [];
                this.lostData.push(current_data.lost_id)
                this.lostData.push(current_data.lost_name)
                this.lostData.push(current_data.lost_details)
                this.lostData.push(current_data.lost_time)
                this.lostData.push(current_data.lost_status)
                this.previewDialogVisible = true;

            },




            //监听pagesize改变的事件
            handleSizeChange(newSize) {
                this.queryInfo.pagesize = newSize
                this.getAllLost()
            },
            //监听页码值改变的事件
            handleCurrentChange(newPage) {
                this.queryInfo.pagenum = newPage
                this.getAllLost()
            },
        },
    }
</script>