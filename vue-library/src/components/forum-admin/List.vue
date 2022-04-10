<template>
    <div>
        <!--面包屑导航区-->
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>论坛管理</el-breadcrumb-item>
            <el-breadcrumb-item>帖子列表</el-breadcrumb-item>
        </el-breadcrumb>

        <el-card>
            <!--搜索与添加-->
            <el-row :gutter="10">
                <el-col :span="6">
                    <el-input placeholder="请输入内容" v-model="queryInfo.querytext" :clearable="true" @clear="getArticleList">
                        <el-button slot="append" icon="el-icon-search" @click="getArticleList"></el-button>
                    </el-input>
                </el-col>
<!--                <el-col :span="6">-->
<!--                    <el-button type="primary" @click="goAddPage" >添加书籍</el-button>-->
<!--                </el-col>-->
            </el-row>

            <!--table表格-->
            <el-table :data="articleList_admin" border stripe>
                <el-table-column label="帖子编号" prop="article_id"></el-table-column>

                <el-table-column label="帖子标题" prop="article_title"></el-table-column>
                <el-table-column label="帖子内容" prop="article_content"></el-table-column>
                <el-table-column label="发帖人" prop="article_user_name"></el-table-column>

                <el-table-column label="操作"  width="200">
                    <template slot-scope="scope">
                        <el-button type="danger" icon="el-icon-delete" @click="open(scope.row)"  circle></el-button>
                    </template>
                </el-table-column>
            </el-table>

            <!--分页-->
            <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="queryInfo.pagenum"
                           :page-sizes="[4, 8, 16]" :page-size="queryInfo.pagesize" :total="total"
                           layout="total, sizes, prev, pager, next, jumper" background>
            </el-pagination>
        </el-card>
        <!--图片预览-->
        <el-dialog title="图片预览" width="50%" :visible.sync="previewDialogVisible" scroll-container>
            <!--内容主体-->

            <img :src="previewPath" alt="" class="previewImg">
        </el-dialog>
    </div>
</template>

<script>
    export default {
        name: "forum_list_admin",
        data() {
            return {
                queryInfo: {
                    querytext: '',
                    pagenum: 1,
                    pagesize: 4,
                    querydata:'',
                },
                articleList_admin: [],
                total: 0,
                previewDialogVisible: false,
                previewPath : "",
            }
        },
        created() {
            this.getArticleList();
            this.managerRole = sessionStorage.getItem('managerRole');
            console.log(this.managerRole);
        },
        methods: {
            getArticleList(){
                axios.post('/api/article/getallarticle',JSON.stringify({
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
                        this.articleList_admin = response.data.object;
                        this.queryInfo.querydata = this.queryInfo.querytext;
                        if (parseInt(response.data.page)===1){
                            this.queryInfo.pagenum = parseInt(response.data.page);
                        }
                        console.log("Hi 这里出错啦")
                        console.log(this.bookList)
                        this.total = parseInt(response.data.info);
                    } else {
                        this.$message.info(response.data.msg)
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
            PreviewPhoto(current_data){
                this.previewPath = "https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png";
                this.previewDialogVisible = true;
            },
            deleteArticle(current_data){
                axios.get('/api/article/deletearticle',{
                    params:{
                        article_id : current_data.article_id,
                    },
                    headers:{Authorization: sessionStorage.getItem('token')}
                }).then((response) => {
                    if (parseInt(response.data.code) === 200){
                        this.getArticleList();
                        this.$message.success(response.data.msg);
                    }
                    else {
                        this.$message.error(response.data.msg);
                    }
                }).catch(()=> {
                    this.$message.error('操作失败');
                })
            },
            open(current_data){
                this.$confirm('此操作将永久删除该帖子, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.deleteArticle(current_data);
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            },

            //监听pagesize改变的事件
            handleSizeChange(newSize) {
                this.queryInfo.pagesize = newSize
                this.getArticleList()
            },
            //监听页码值改变的事件
            handleCurrentChange(newPage) {
                this.queryInfo.pagenum = newPage
                this.getArticleList()
            },

            goAddPage(){
                this.$router.push('/books_admin/add')
            },
            goEditPage(id){
                this.$router.push(`/goods/edit/${id}`)
            }
        }
    }
</script>

<style lang="less" scoped>
    .el-button {
        margin-right: 10px;
    }
</style>