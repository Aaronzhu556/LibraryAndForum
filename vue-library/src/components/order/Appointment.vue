<template>
    <div>
        <!--面包屑导航区-->
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>预约管理</el-breadcrumb-item>
            <el-breadcrumb-item>书籍预约列表</el-breadcrumb-item>
        </el-breadcrumb>

        <el-card>


            <!--table表格-->
            <el-table :data="appointmentList" border stripe>
                <el-table-column label="预约编号" prop="appointment_id"></el-table-column>
                <el-table-column label="书籍编号" prop="appointment_book_id"></el-table-column>

                <el-table-column label="用户名" prop="appointment_user_name"></el-table-column>

                <el-table-column label="过期时间(分)" prop="appointment_expire_time"></el-table-column>


                <el-table-column label="操作"  width="200">
                    <template slot-scope="scope">
                        <el-button type="primary" icon="el-icon-star-off" @click="open(scope.row)"  circle></el-button>
                    </template>
                </el-table-column>
            </el-table>

            <!--分页-->
            <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="queryInfo.pagenum"
                           :page-sizes="[4, 8, 16]" :page-size="queryInfo.pagesize" :total="total"
                           layout="total, sizes, prev, pager, next, jumper" background>
            </el-pagination>
        </el-card>

    </div>
</template>

<script>
    export default {
        name: "List",
        data() {
            return {
                queryInfo: {
                    querytext: '',
                    pagenum: 1,
                    pagesize: 4,
                    querydata:'',
                },
                appointmentList: [],
                total: 0,

                previewPath : "",
            }
        },
        created() {
            this.getAppointmentList();
            this.managerRole = sessionStorage.getItem('managerRole');
            console.log(this.managerRole);
        },
        methods: {
            getAppointmentList(){
                axios.post('/api/appointment/queryallappointment',JSON.stringify({
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
                        this.appointmentList = response.data.object;
                        this.queryInfo.querydata = this.queryInfo.querytext;
                        if (parseInt(response.data.page)===1){
                            this.queryInfo.pagenum = parseInt(response.data.page);
                        }
                        console.log("Hi 这里出错啦")
                        console.log(this.appointmentList)
                        this.total = parseInt(response.data.info);
                    } else {
                        this.$message.info(response.data.msg)
                        this.appointmentList = response.data.object;
                    }
                }).catch((e) => {
                    this.$message.error("获取失败")
                    console.log(e);
                })
            },
            /*
            *预览图片 懒加载模式
            * */
            /*
            PreviewPhoto(current_data){
                this.product_photos = current_data;
                console.log(this.product_photos)
                for (var i=0;i<this.product_photos.length;i++){
                    this.product_photos[i] = "/api" + this.product_photos[i];
                }
                console.log(this.product_photos);
                this.previewDialogVisible = true;
            },

             */
            open(current_data) {
                this.$prompt('请输入借书人体温', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    inputPattern : /[1-9]\d*.\d*|0.\d*[1-9]\d*/,
                    inputErrorMessage: '邮箱格式不正确'
                }).then(({ value }) => {
                    this.affirmBook(current_data,value)

                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '取消输入'
                    });
                });
            },
            affirmBook(current_data,temperature){
                axios.post('/api/borrow/borrowbook',JSON.stringify({
                    borrow_user_name :current_data.appointment_user_name,
                    borrow_book_id : current_data.appointment_book_id,
                    borrow_user_temperature : temperature,
                }), {
                        headers: {
                            'Content-Type': 'application/json'
                        }

                    }
                ).then(response =>{
                    if (parseInt(response.data.code)===200){
                        this.getAppointmentList();
                        this.$message.success(response.data.msg)
                    }else this.$message.error(response.data.msg)
                }).catch(()=>{
                    this.$message.error('发生错误')
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

        }
    }
</script>

<style lang="less" scoped>
    .el-button {
        margin-right: 10px;
    }
</style>