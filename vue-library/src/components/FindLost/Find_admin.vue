<template>
    <div>
        <!--面包屑导航区-->
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>失物招领管理</el-breadcrumb-item>
            <el-breadcrumb-item>失物登记列表</el-breadcrumb-item>
        </el-breadcrumb>

        <!--卡片视图区域-->
        <el-card>
            <!--搜索与添加-->
            <el-row :gutter="10">
                <el-col :span="6">
                    <el-input placeholder="请输入内容" v-model="queryInfo.querytext" :clearable="true" @clear="getFindList">
                        <el-button slot="append" icon="el-icon-search" @click="getFindList"></el-button>
                    </el-input>
                </el-col>
                <el-col :span="6">
                    <el-button type="primary" @click="addDialogVisible=true">添加失物信息</el-button>
                </el-col>
            </el-row>

            <!--用户列表-->
            <el-table :data="findList" :border="true" :stripe="true">
                <el-table-column label="序号" prop="find_id" width="50"></el-table-column>
                <el-table-column label="失物名" prop="find_name"></el-table-column>
                <el-table-column label="失物细节" prop="find_details"></el-table-column>
                <el-table-column label="失物图片" scope>
                    <template slot-scope="scope">
                        <el-button icon="el-icon-search" circle @click="openDialog2(scope.row)"></el-button>
                    </template>
                </el-table-column>
                <el-table-column label="登记时间" prop="find_time"></el-table-column>
                <el-table-column label="失物等级" prop="find_level">
                   <template slot-scope="scope">
                       <el-tag type="info" v-if="scope.row.find_level==='不紧急'">不紧急</el-tag>
                       <el-tag type="warning" v-else-if="scope.row.find_level==='一般'">一般</el-tag>
                       <el-tag type="danger" v-else-if="scope.row.find_level==='紧急'">紧急</el-tag>
                   </template>

                </el-table-column>

                <!--				<el-table-column label="用户余额" prop="user_money"></el-table-column>-->
                <el-table-column label="状态" prop= "find_status" width="200">
                    <template slot-scope="scope">
                        <el-switch
                                active-color="#13ce66"
                                inactive-color="#ff4949"
                                v-model="scope.row.find_status"
                                active-value="1"
                                inactive-value="0"
                                active-text="已找到"
                                inactive-text="未找到"
                                @change="changeSwitch(scope.row,scope.$index)">

                        </el-switch>
                    </template>
                </el-table-column>
                <el-table-column

                        label="操作">
                    <template slot-scope="scope">
                        <el-button @click="deleteFind(scope.row)" type="text" size="small">删除</el-button>

                    </template>
                </el-table-column>


            </el-table>

            <!--分页-->
            <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="queryInfo.pagenum"
                           :page-sizes="[ 8, 16]" :page-size="queryInfo.pagesize" :total="total"
                           layout="total, sizes, prev, pager, next, jumper" background>
            </el-pagination>
        </el-card>


        <!--添加用户的对话框-->
        <el-dialog title="添加失物" width="50%" :visible.sync="addDialogVisible" :close-on-click-modal="false" @close="closeDialog" :label-position="labelPosition" >
            <!--内容主体-->
            <el-form :model="addForm" :rules="addFormRules" ref="addFormRef" label-width="100px">
                <el-form-item label="失物名" prop="find_name">
                    <el-input v-model="addForm.find_name"></el-input>
                </el-form-item>
                <el-form-item label="失物细节" prop="find_details">
                    <el-input v-model="addForm.find_details"></el-input>
                </el-form-item>
                <el-form-item label="失物时间" prop="find_time">
                    <el-date-picker type="datetime" :picker-options="pickerBeginDateBefore" placeholder="选择日期" format="yyyy/MM/DD HH:mm:ss" value-format="yyyy/MM/dd HH:mm:ss"  v-model="addForm.find_time" @change="getFindDate" ></el-date-picker>
                </el-form-item>
                <el-form-item label="失物等级" prop="find_level">
                    <el-radio-group v-model="addForm.find_level" size="small">
                        <el-radio-button label="紧急"  ></el-radio-button>
                        <el-radio-button label="一般"  ></el-radio-button>
                        <el-radio-button label="不紧急"  ></el-radio-button>
                    </el-radio-group>
                    </el-form-item>
                <el-form-item label="失物图">
                    <el-upload action="/api/find/uploadfindimg" :on-preview="handlePreview"
                               :on-remove="handleRemove" list-type="picture" :headers="headerObj"  :limit="3" :on-success="handleSuccess" name="find_img">
                        <el-button type="primary">点击上传</el-button>
                    </el-upload>
                </el-form-item>
            </el-form>
            <!--底部-->
            <span slot="footer">
        				<el-button @click="closeDialog">取 消</el-button>
        				<el-button type="primary" @click="addFind">确 定</el-button>
        			</span>
        </el-dialog>

        <!--图片预览-->
        <el-dialog title="图片预览" width="50%" :visible.sync="previewDialogVisible2"  @close="closeDialog2">
            <!--内容主体-->
            <el-image v-for="url in previewPathList" :key="url" :src="url" lazy></el-image>
        </el-dialog>
    </div>
</template>

<script>
    export default {
        name: "find_admin",
        inject:['reload'],
        data() {

            return {
                pickerBeginDateBefore: {
                    disabledDate(time) {
                        return time.getTime() > Date.now();
                    }
                },
                //获取用户列表的参数对象
                queryInfo: {
                    querytext: '',
                    pagenum: 1,//当前的页数
                    pagesize: 8,//当前每页显示多少条数据
                    querydata:'',

                },
                labelPosition :'left',
                findList: [],
                totalFindList_admin:[[]],
                finds:[],
                max_page:0,
                total: 0,
                addForm:{
                    find_name:'',
                    find_details:'',
                    find_time:'',
                    find_level:'',
                    find_img_list:[],
                },
                addFormRules:{
                    find_name: [
                        {required: true, message: '请输入失物名称', trigger: 'blur'}
                    ],
                    find_details: [
                        {required: true, message: '请输入失物细节', trigger: 'blur'}
                    ],
                    find_time:[
                        {required: true, message: '请选择失物时间', trigger: 'blur'}
                    ],
                    find_level:[
                        {required: true, message: '请选择失物等级', trigger: 'blur'}
                    ]

                },
                //添加用户对话框显示状态
                addDialogVisible: false,

                headerObj:{
                    'Authorization' : sessionStorage.getItem('token')
                },
                previewPath:'',
                previewPathList:[],
                previewDialogVisible2:false,

            }
        },
        created() {
            this.getFindList()
        },
        methods: {
            resetAllData(){
                this.queryInfo.pagenum = 1;
                this.findList = [];
                this.totalFindList_admin =[[]];
                this.finds=[];
                this.max_page=0;
                this.total= 0;
            },
            deleteFind(current_data){
                axios.delete("/api/find/deletefind",{
                    params:{
                        find_id: current_data.find_id
                    },
                    headers:{
                        'Authorization' : sessionStorage.getItem('token')
                    }
                }).then(response =>{
                    if (parseInt(response.data.code)===200){
                        this.$message.success("删除成功");
                        this.getFindList();
                    }
                }).catch(()=>{
                    this.$message.error("发生错误")
                })
            },
            getFindDate(datetime){

                this.addForm.find_time = datetime;
            },
            closeDialog2(){
                this.previewDialogVisible2 = false;
            },
            openDialog2(current_data){
                this.previewPathList=[];
                for (var i=0;i<current_data.find_img_list.length;i++){
                    this.previewPathList.push(current_data.find_img_list[i]);
                }
                this.previewDialogVisible2 =true;
            },


            closeDialog(){
                this.addForm = {};
                this.addDialogVisible = false;
            },
            getFindList() {
                this.resetAllData();
                axios.post('/api/find/getallfind', JSON.stringify(this.queryInfo), {
                    headers: {
                        'Content-Type': 'application/json'
                    }

                }).then(response => {
                    if (parseInt(response.data.code) === 200) {
                        this.finds = response.data.object;
                        this.max_page = Math.ceil(this.finds.length / this.queryInfo.pagesize) || 1;
                        for (let i = 0; i < this.max_page; i++) {
                            this.totalFindList_admin[i] = this.finds.slice(
                                this.queryInfo.pagesize * i,
                                this.queryInfo.pagesize * (i + 1)
                            );
                            console.log(this.totalFindList_admin[i]);
                        }
                        this.findList = this.totalFindList_admin[this.queryInfo.pagenum-1];
                        this.total = parseInt(response.data.info);
                    } else {
                        this.$message.info(response.data.msg)
                    }
                }).catch(() => {
                    this.$message.error("获取失败")
                })
            },
            changeSwitch(current_data,index){
                axios.get('/api/find/updatestatus',
                    {
                        params:{
                            find_id : current_data.find_id,
                            find_status : current_data.find_status
                        },
                        headers: {Authorization: sessionStorage.getItem('token')}
                    }

                ).then((response)=>{
                    if (parseInt(response.data.code)===200){
                        this.$message.success(response.data.msg);
                    }
                    else{
                        this.findList[index].find_status = current_data.find_status;
                        this.$message.error(response.data.msg);
                    }
                }).catch(() => {
                    this.findList[index].find_status = current_data.find_status;
                    this.$message.error('操作失败');
                })
            },
            //图片预览
            handlePreview(file) {
                this.previewPath = file.response.info
                console.log(this.previewPath)
                this.previewDialogVisible = true
            },
            //移除图片
            handleRemove(file) {

                const filePath = file.response.info;
                const i = this.addForm.find_img_list.findIndex(x => x.pic === filePath)
                this.addForm.find_img_list.splice(i, 1)
                console.log(this.addForm.find_img)


            },
            //图片上传成功hook
            handleSuccess(res) {
                console.log(res);
                if (parseInt(res.code) === 200) {
                    this.$message.success(res.msg)
                    this.addForm.find_img_list.push("/api"+res.info)
                    console.log(this.addForm.find_img_list);
                } else {
                    this.$message.error(res.msg)
                }
            },
            //监听pagesize改变的事件
            handleSizeChange(newSize) {
                this.queryInfo.pagesize = newSize
                this.getFindList()
            },
            //监听页码值改变的事件
            handleCurrentChange(newPage) {
                this.queryInfo.pagenum = newPage
                this.findList = this.totalFindList_admin[newPage-1];
            },

            //确定添加用户
            addFind() {

                this.$refs.addFormRef.validate(valid => {
                    if (valid) {

                        axios.post('/api/find/addfind', JSON.stringify(this.addForm),{
                            headers:{
                                'Content-Type': 'application/json',
                                'Authorization': sessionStorage.getItem('token')
                            }
                        }).then(response => {

                            if (parseInt(response.data.code) === 200) {
                                //隐藏对话框

                                this.$message.success(response.data.msg)
                                this.reload();
                            } else {
                                this.$message.error(response.data.msg)
                            }
                        }).catch(() => {
                            this.$message.error("操作失败")
                        })
                    }
                })
            },

        }
    }
</script>

<style lang="less" scoped>

</style>