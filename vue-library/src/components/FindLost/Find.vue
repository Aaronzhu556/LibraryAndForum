<template>
    <div>
        <!--面包屑导航区-->
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>失物拾物</el-breadcrumb-item>
            <el-breadcrumb-item>失物信息</el-breadcrumb-item>
        </el-breadcrumb>
        <div style="height: 800px">
            <el-scrollbar style="height:100%">
                <el-card>
                    <!--搜索与添加-->
                    <el-row :gutter="10">
                        <el-col :span="6">
                            <el-input placeholder="请输入内容" v-model="queryInfo.querytext" :clearable="true" @clear="getAllFind">
                                <el-button slot="append" icon="el-icon-search" @click="getAllFind"></el-button>
                            </el-input>
                        </el-col>
                        <el-col :span="10" >
                            <el-date-picker
                                    v-model="queryInfo.querydata"
                                    type="datetimerange"
                                    range-separator="至"
                                    start-placeholder="开始日期"
                                    end-placeholder="结束日期"
                                    :picker-options="pickerBeginDateBefore"

                                    value-format="yyyy/MM/dd HH:mm:ss"
                                    :clearable="true"
                                    @change="getFindDate"
                                    >

                            </el-date-picker>
                            <el-button   icon="el-icon-search" style="position: absolute;left: 720px; " @click="queryFindByTimeRange">查找</el-button>
                        </el-col>
                        <el-col :span="6">
                            <el-button   @click="clearall" type="primary">重置查询条件</el-button>
                        </el-col>



                    </el-row>
                    <el-empty description="暂无数据" v-if="findList.length===0"></el-empty>
                    <el-row v-for="(item,index) in findList_new" :key="index" :gutter="20" >
                        <el-col :span="4" v-for="(find, index) in item" :key="index" :offset="index > 0 ? 2 : 0">
                            <el-card :body-style="{ padding: '0px' }">
                                <el-carousel height="180px">
                                    <el-carousel-item v-for="(img,index1) in find.find_img_list" :key="index1">
                                        <el-image :src="img"></el-image>
                                    </el-carousel-item>
                                </el-carousel>
                                <div style="padding: 14px;">
                                    <span font-family="Helvetica Neu"><strong>{{find.find_name}}</strong></span>
                                    <span style="float: right">
                                <el-tag size="small" type="success" v-if="find.find_status=='0'">未找到</el-tag>
                                <el-tag size="small" type="danger" v-else-if="find.find_status=='1'" >已找到</el-tag>
                                </span>

                                    <div class="bottom clearfix">
                                        <!--                                    <el-link icon="el-icon-more-outline" @click.native="open2(book)"></el-link>-->
                                        <el-button  class="button" @click="showfind(find)">查看失物详情</el-button>
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
                <el-descriptions-item label="失物名">{{this.findData[1]}}</el-descriptions-item>
                <el-descriptions-item label="失物时间">{{this.findData[3]}}</el-descriptions-item>

                <el-descriptions-item label="状  态">
                    <el-tag size="small" type="success" v-if="this.findData[4]=='0'">未找到</el-tag>
                    <el-tag size="small" type="danger" v-else-if="this.findData[4]=='1'" >已找到</el-tag>

                </el-descriptions-item>

                <el-descriptions-item label="失物简介">{{this.findData[2]}}</el-descriptions-item>


            </el-descriptions>
            <span slot="footer" class="dialog-footer">
                <el-button @click="previewDialogVisible = false">取消</el-button>
                <!--                <el-button type="primary" @click="borrowBook()" :disabled="this.findData[4]=='1'?false:true">预约借书</el-button>-->
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
        name:'find',
        data() {
            return{
                pickerBeginDateBefore: {
                    disabledDate(time) {
                        return time.getTime() > Date.now();
                    }
                },
                queryInfo: {
                    querytext: '',
                    pagenum: 1,
                    pagesize: 8,
                    querydata:'',

                },
                findList:[],
                totalFindList:[[]],
                findList_new:[[]],
                total:0,
                max_page:0,
                findData:[],
                queryStartTime:'',
                queryEndTime:'',
                previewDialogVisible: false,



            }
        },
        created() {
            this.getAllFind();
        },
        methods:{
            clearall(){
                this.queryInfo.querydata='';
                this.queryInfo.querytext = '';
                this.getAllFind();
            },
            getFindDate(datetime){

                this.queryInfo.querydata = datetime;
            },
            resetAllData(){
                this.queryInfo.pagenum = 1;
                this.findList = [];
                this.totalFindList =[[]];
                this.findList_new = [[]];

                this.max_page=0;
                this.total= 0;
            },
            queryFindByTimeRange(){
                this.resetAllData();
                console.log( this.queryInfo.querydata[0])
                axios.get('/api/find/getfindbytimerange',{
                  params:{
                      start_time : this.queryInfo.querydata[0],
                      end_time :this.queryInfo.querydata[1]
                  },
                    headers:{
                      'Authorization' :sessionStorage.getItem('token')
                    }
                }
               ).then(response=>{
                   if (parseInt(response.data.code)===200){
                       this.findList = response.data.object;
                       this.max_page = Math.ceil(this.findList.length / this.queryInfo.pagesize) || 1;
                       for (let i = 0; i < this.max_page; i++) {
                           this.totalFindList[i] = this.findList.slice(
                               this.queryInfo.pagesize * i,
                               this.queryInfo.pagesize * (i + 1)
                           );
                           console.log(this.totalFindList[i]);
                       }

                       this.total = parseInt(response.data.info);
                       this.operateFind(this.queryInfo.pagenum);
                   }
                })
            },
            operateFind(current_page){
                this.findList_new= [[]];
                this.findList_new = new Array();
                var count_row = 0;
                count_row = Math.ceil( this.totalFindList[current_page-1].length / 4);


                for (var i = 0;i<count_row;i++){
                    this.findList_new[i] = new Array();
                    for (var j = i *4 , k=0; j < i * 4 +4 && j<this.totalFindList[current_page-1].length && k <4; j++,k++){
                        if (JSON.stringify(this.totalFindList[current_page-1][j]) === '{}'){}
                        else {
                            this.findList_new[i][k] = this.totalFindList[current_page-1][j];
                        }
                    }
                }
            },
            getAllFind(){
                this.queryInfo.pagenum = 1;

                axios.post("/api/find/getallfind",JSON.stringify(this.queryInfo),{
                    headers:{
                        'Authorization' :sessionStorage.getItem('token'),
                        'Content-Type' : 'application/json'
                    }
                }).then(response=>{
                    if (parseInt(response.data.code)===200){
                        this.findList = response.data.object;
                        this.max_page = Math.ceil(this.findList.length / this.queryInfo.pagesize) || 1;
                        for (let i = 0; i < this.max_page; i++) {
                            this.totalFindList[i] = this.findList.slice(
                                this.queryInfo.pagesize * i,
                                this.queryInfo.pagesize * (i + 1)
                            );
                            console.log(this.totalFindList[i]);
                        }

                        this.total = parseInt(response.data.info);
                        this.operateFind(this.queryInfo.pagenum);
                    }else this.$message.info(response.data.msg);
                }).catch(()=>{
                    this.$message.error("发生错误")
                })
            },








            showfind(current_data){
                this.findData = [];
                this.findData.push(current_data.find_id)
                this.findData.push(current_data.find_name)
                this.findData.push(current_data.find_details)
                this.findData.push(current_data.find_time)
                this.findData.push(current_data.find_status)
                this.previewDialogVisible = true;

            },




            //监听pagesize改变的事件
            handleSizeChange(newSize) {
                this.queryInfo.pagesize = newSize
                this.getAllFind()
            },
            //监听页码值改变的事件
            handleCurrentChange(newPage) {
                this.queryInfo.pagenum = newPage
                //this.getAllFind()
                this.operateFind(newPage);
            },
        },
    }
</script>