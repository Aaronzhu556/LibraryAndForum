<template>
    <div>
        <!--面包屑导航区-->
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>自习室管理</el-breadcrumb-item>
            <el-breadcrumb-item>自习室列表</el-breadcrumb-item>
        </el-breadcrumb>

        <el-card>
            <el-row :gutter="10">

                <el-col :span="6">
                    <el-button type="primary" @click="dialogVisible = true" >添加自习室</el-button>
                </el-col>
            </el-row>

            <!--table表格-->
            <el-table :data="class_seat" border stripe>
                <el-table-column label="自习室编号" prop="seat_id"></el-table-column>
                <el-table-column label="自习室楼层" prop="seat_name"></el-table-column>

                <el-table-column label="行数" prop="seat_row"></el-table-column>

                <el-table-column label="列数" prop="seat_col"></el-table-column>
                <el-table-column label="入座率" prop="seat_people_ratio"></el-table-column>

                <el-table-column label="操作"  width="200">
                    <template slot-scope="scope">
                        <el-popconfirm
                                title="你确定删除该自习室吗？"
                                @confirm="deleteSeat(scope.row)"
                        >
                        <el-button type="primary" icon="el-icon-delete" @click="open(scope.row)"  circle  slot="reference"></el-button>
                        </el-popconfirm>
                    </template>
                </el-table-column>
            </el-table>

            <!--分页-->
            <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="queryInfo.pagenum"
                           :page-sizes="[4, 8, 16]" :page-size="queryInfo.pagesize" :total="total"
                           layout="total, sizes, prev, pager, next, jumper" background>
            </el-pagination>


            <el-dialog  :visible.sync="dialogVisible">
                <el-form ref="seatFormRef" :model="seatForm" label-width="80px" :rules="seatFormRules">
                    <el-form-item label="自习室名" prop="seat_name" >
                        <el-input v-model="seatForm.seat_name"></el-input>
                    </el-form-item>
                    <el-form-item label="座椅行数" prop="seat_row">
                        <el-input v-model="seatForm.seat_row"></el-input>
                    </el-form-item>
                    <el-form-item label="座椅列数" prop="seat_col">
                        <el-input v-model="seatForm.seat_col"></el-input>
                    </el-form-item>
                </el-form>
                <span slot="footer" class="dialog-footer">
                    <el-button @click="dialogVisible = false">取 消</el-button>
                    <el-button type="primary" @click="addSeat">确 定</el-button>
                </span>
            </el-dialog>
        </el-card>

    </div>
</template>
<script>
    export default {
        name: "admin_seat",
        data() {
            const validateSeatName = (rule,value,callback) =>{
                if (value===''){
                    callback(new Error('请输入自习室名'))
                }else {
                    for (var i = 0; i < this.class_seat.length; i++) {
                        if (this.class_seat[i].seat_name === value) callback(new Error("该自习室已存在"))
                        else callback();
                    }
                }
            };
            return {
                queryInfo: {
                    querytext: '',
                    pagenum: 1,
                    pagesize: 4,
                    querydata:'',
                },
                class_seat: [],
                classs :[],
                totalclass_seat:[[]],

                max_page:0,

                seatForm:{
                    seat_name:'',
                    seat_row:'',
                    seat_col:'',
                },
                seatFormRules:{
                    seat_name: [
                        {required: true, trigger: 'blur',max:5,validator:validateSeatName},
                    ],
                    seat_row:[
                        {required:true,message:'请输入座椅行数',trigger: 'blur'},
                        { type: 'string', pattern: /[0-9]$/, message: '座椅行数格式出错,只允许两位位数字', trigger: 'blur' ,max:2}
                    ],
                    seat_col:[
                        {required:true,message:'请输入座椅列数',trigger: 'blur'},
                        { type: 'string', pattern: /[0-9]{1}$/, message: '座椅列数格式出错,只允许一位数字', trigger: 'blur' ,max:1}
                    ]
                },
                total: 0,
                dialogVisible :false,

            }
        },
        created() {
            this.getSeat();
            this.managerRole = sessionStorage.getItem('managerRole');
            console.log(this.managerRole);
        },
        methods: {



            addSeat(){
                this.$refs.seatFormRef.validate(valid => {
                    if (valid){
                        axios.post('/api/seat/addseat',JSON.stringify(this.seatForm),{
                            headers:{
                                'Content-Type': 'application/json',
                                'Authorization' :sessionStorage.getItem('token')
                            }
                        }).then(response=>{
                            if (parseInt(response.data.code)===200){
                                this.$message.success(response.data.msg);
                                this.seatForm = {};
                                this.getSeat();
                            }else this.$message.error(response.data.msg);
                        }).catch(()=>{
                            this.$message.error("发生错误");
                        })
                    }
                })

            },

            getSeat(){
                axios.post('/api/seat/getallseatbyquery',JSON.stringify({
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
                        this.classs = response.data.object;
                        this.max_page = Math.ceil(this.classs.length / this.queryInfo.pagesize) || 1;
                        for (let i = 0; i < this.max_page; i++) {
                            this.totalclass_seat[i] = this.classs.slice(
                                this.queryInfo.pagesize * i,
                                this.queryInfo.pagesize * (i + 1)
                            );
                            console.log(this.totalclass_seat[i]);
                        }
                        this.class_seat = this.totalclass_seat[this.queryInfo.pagenum-1];

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
            deleteSeat(seat_data){
                if (seat_data.seat_people_ratio===0){
                    axios.get("/api/seat/deleteseat",{
                        params:{
                            seat_id:seat_data.seat_id
                        },
                        headers:{
                            'Authorization' : sessionStorage.getItem('token')
                        }
                    }).then(response=>{
                        if (parseInt(response.data.code)===200){
                            this.$message.success(response.data.msg)
                            this.getSeat()
                        }else    this.$message.error(response.data.msg)
                    }).catch(()=>{
                        this.$message.error("发生错误")
                    })
                }else this.$message.error("该自习室正有人使用,无法删除!")
            },

            //监听pagesize改变的事件
            handleSizeChange(newSize) {
                this.queryInfo.pagesize = newSize
                this.getSeat()
            },
            //监听页码值改变的事件
            handleCurrentChange(newPage) {
                this.queryInfo.pagenum = newPage
               // this.getSeat()
                this.class_seat = this.totalclass_seat[newPage-1];
            },

        }
    }
</script>

<style lang="less" scoped>
    .el-button {
        margin-right: 10px;
    }
</style>