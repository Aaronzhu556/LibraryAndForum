<template>
    <div>
        <!--面包屑导航区-->
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>失物拾物管理</el-breadcrumb-item>
            <el-breadcrumb-item>拾物列表</el-breadcrumb-item>
        </el-breadcrumb>

        <!--卡片视图区域-->
        <el-card>
            <!--搜索与添加-->
            <el-row :gutter="10">
                <el-col :span="6">
                    <el-input placeholder="请输入内容" v-model="queryInfo.querytext" :clearable="true" @clear="getLostList">
                        <el-button slot="append" icon="el-icon-search" @click="getLostList"></el-button>
                    </el-input>
                </el-col>
                				<el-col :span="6">
                					<el-button type="primary" @click="addDialogVisible=true">添加拾物信息</el-button>
                				</el-col>
            </el-row>

            <!--用户列表-->
            <el-table :data="lostList" :border="true" :stripe="true">
                <el-table-column label="序号" prop="lost_id" width="50"></el-table-column>
                <el-table-column label="拾物名" prop="lost_name"></el-table-column>
                <el-table-column label="拾物细节" prop="lost_details"></el-table-column>
                <el-table-column label="拾物图片" scope>
                    <template slot-scope="scope">
                        <el-button icon="el-icon-search" circle @click="openDialog2(scope.row)"></el-button>
                    </template>
                </el-table-column>
                <el-table-column label="拾取时间" prop="lost_time"></el-table-column>


                <!--				<el-table-column label="用户余额" prop="user_money"></el-table-column>-->
                <el-table-column label="状态" prop= "lost_status" width="200">
                    <template slot-scope="scope">
                        <el-switch
                                active-color="#13ce66"
                                inactive-color="#ff4949"
                                v-model="scope.row.lost_status"
                                active-value="0"
                                inactive-value="1"
                                active-text="无人认领"
                                inactive-text="已被认领"
                                @change="changeSwitch(scope.row,scope.$index)">

                        </el-switch>
                    </template>
                </el-table-column>
                <el-table-column

                        label="操作">
                    <template slot-scope="scope">
                        <el-button @click="deleteLost(scope.row)" type="text" size="small">删除</el-button>

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
        		<el-dialog title="添加拾物" width="50%" :visible.sync="addDialogVisible" :close-on-click-modal="false" @close="addDialogClosed" :label-position="labelPosition" >
        			<!--内容主体-->
        			<el-form :model="addForm" :rules="addFormRules" ref="addFormRef" label-width="100px">
        				<el-form-item label="拾物名" prop="lost_name">
        					<el-input v-model="addForm.lost_name"></el-input>
        				</el-form-item>
        				<el-form-item label="拾物细节" prop="lost_details">
        					<el-input v-model="addForm.lost_details"></el-input>
        				</el-form-item>
                        <el-form-item label="拾物时间" prop="lost_time">
                            <el-date-picker type="datetime"   :picker-options="pickerBeginDateBefore" placeholder="选择日期" format="yyyy/MM/DD HH:mm:ss" value-format="yyyy/MM/dd HH:mm:ss"  v-model="addForm.lost_time" style="width: 100%;" @change="getLostDate"></el-date-picker>
                        </el-form-item>
                        <el-form-item label="拾物图">
                            <el-upload action="/api/lost/uploadlostimg" :on-preview="handlePreview"
                                       :on-remove="handleRemove" list-type="picture" :headers="headerObj"  :limit="3" :on-success="handleSuccess" name="lost_img">
                                <el-button type="primary">点击上传</el-button>
                            </el-upload>
                        </el-form-item>
        			</el-form>
        			<!--底部-->
        			<span slot="footer">
        				<el-button @click="closeDialog">取 消</el-button>
        				<el-button type="primary" @click="addLost">确 定</el-button>
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
        name: "lost_admin",
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
                lostList: [],
                totalLostList_admin:[[]],
                losts:[],
                max_page:0,
                total: 0,
                addForm:{
                    lost_name:'',
                    lost_details:'',
                    lost_time:'',
                    lost_img_list:[],
                },
                labelPosition :'left',
                addFormRules:{
                    lost_name: [
                        {required: true, message: '请输入拾物名称', trigger: 'blur'}
                    ],
                    lost_details: [
                        {required: true, message: '请输入拾物细节', trigger: 'blur'}
                    ],
                    lost_time:[
                        {required: true, message: '请选择拾物时间', trigger: 'blur'}
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
            this.getLostList()
        },
        methods: {
            resetAllData(){
                this.queryInfo.pagenum = 1;
                this.lostList = [];
                this.totalLostList_admin =[[]];
                this.losts=[];
                this.max_page=0;
                this.total= 0;
            },
            deleteLost(current_data){
                axios.delete("/api/lost/deletelost",{
                    params:{
                        lost_id: current_data.lost_id
                    },
                    headers:{
                        'Authorization' : sessionStorage.getItem('token')
                    }
                }).then(response =>{
                    if (parseInt(response.data.code)===200){
                        this.$message.success("删除成功");
                        this.getLostList();
                    }
                }).catch(()=>{
                    this.$message.error("发生错误")
                })
            },
            getLostDate(datetime){
                this.addForm.lost_time = datetime;
            },
            closeDialog2(){
                this.previewDialogVisible2 = false;
            },
            openDialog2(current_data){
                this.previewPathList=[];
                for (var i=0;i<current_data.lost_img_list.length;i++){
                    this.previewPathList.push(current_data.lost_img_list[i]);
                }
                this.previewDialogVisible2 =true;
            },


            closeDialog(){
                this.addForm = {};
                this.addDialogVisible = false;
            },
            getLostList() {
                this.resetAllData();
                axios.post('/api/lost/getalllost', JSON.stringify(this.queryInfo), {
                    headers: {
                        'Content-Type': 'application/json'
                    }

                }).then(response => {
                    if (parseInt(response.data.code) === 200) {
                        this.losts = response.data.object;
                        this.max_page = Math.ceil(this.losts.length / this.queryInfo.pagesize) || 1;
                        for (let i = 0; i < this.max_page; i++) {
                            this.totalLostList_admin[i] = this.losts.slice(
                                this.queryInfo.pagesize * i,
                                this.queryInfo.pagesize * (i + 1)
                            );
                            console.log(this.totalLostList_admin[i]);
                        }
                        this.lostList = this.totalLostList_admin[this.queryInfo.pagenum-1];

                        this.total = parseInt(response.data.info);
                    } else {
                        this.$message.info(response.data.msg)
                    }
                }).catch(() => {
                    this.$message.error("获取失败")
                })
            },
            changeSwitch(current_data,index){
                axios.get('/api/lost/updatestatus',
                    {
                        params:{
                            lost_id : current_data.lost_id,
                            lost_status : current_data.lost_status
                        },
                        headers: {Authorization: sessionStorage.getItem('token')}
                    }

                ).then((response)=>{
                    if (parseInt(response.data.code)===200){
                        this.$message.success(response.data.msg);
                    }
                    else{
                        this.lostList[index].lost_status = current_data.lost_status;
                        this.$message.error(response.data.msg);
                    }
                }).catch(() => {
                    this.lostList[index].lost_status = current_data.lost_status;
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
                const i = this.addForm.lost_img_list.findIndex(x => x.pic === filePath)
                this.addForm.lost_img_list.splice(i, 1)
                console.log(this.addForm.lost_img)


            },
            //图片上传成功hook
            handleSuccess(res) {
                console.log(res);
                if (parseInt(res.code) === 200) {
                    this.$message.success(res.msg)
                    this.addForm.lost_img_list.push("/api"+res.info)
                    console.log(this.addForm.lost_img_list);
                } else {
                    this.$message.error(res.msg)
                }
            },
            //监听pagesize改变的事件
            handleSizeChange(newSize) {
                this.queryInfo.pagesize = newSize
                this.getLostList()
            },
            //监听页码值改变的事件
            handleCurrentChange(newPage) {
                this.queryInfo.pagenum = newPage
                //this.getLostList()
                this.lostList = this.totalLostList_admin[newPage-1];
            },
            //监听添加用户对话框的关闭事件
            addDialogClosed() {
                this.$refs.addFormRef.resetFields()
            },
            //确定添加用户
            addLost() {
                this.$refs.addFormRef.validate(valid => {
                    if (valid) {
                        axios.post('/api/lost/addlost', JSON.stringify(this.addForm),{
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
            //展示修改用户的对话框
            showEditDialog(id) {
                axios.get(`users/${id}`).then(response => {
                    const res = response.data
                    if (res.meta.status === 200) {
                        this.editForm = res.data
                    } else {
                        return this.$message.error(res.meta.msg)
                    }
                }).catch(() => {
                    return this.$message.error("请求失败")
                })
                this.editDialogVisible = true
            },

            //确定修改用户
            editUser() {
                this.$refs.editFormRef.validate(valid => {
                    if (valid) {
                        axios.put(`users/${this.editForm.id}`, {email: this.editForm.email, mobile: this.editForm.mobile}).then(response => {
                            const res = response.data
                            if (res.meta.status === 200) {
                                //隐藏对话框
                                this.editDialogVisible = false
                                this.getUserList()
                                this.$message.success(res.meta.msg)
                            } else {
                                this.$message.error(res.meta.msg)
                            }
                        }).catch(() => {
                            this.$message.error("操作失败")
                        })
                    }
                })
            },
            //根据id删除对应的用户
            removeUserById(id) {
                console.log("我他妈的傻了");
                axios.delete(`/api/user/deleteuser/`,
                    {
                        params:{
                            user_id : id,
                        },
                        headers: {Authorization: sessionStorage.getItem('token')}
                    }
                ).then(response => {

                    if ( parseInt(response.data.code)=== 200) {
                        this.$message.success ( response.data.msg)
                        this.getUserList()
                    } else {
                        this.$message.error( response.data.msg)
                    }
                }).catch(() => {
                    this.$message.error('操作失败')
                }).catch(() => {
                    this.$message.info('已取消删除')
                });
            },



        }
    }
</script>

<style lang="less" scoped>

</style>