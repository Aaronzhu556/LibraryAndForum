<template>
    <div id="#app">
        <!--面包屑导航区-->
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path: '/forum_list' }">社区论坛</el-breadcrumb-item>
            <el-breadcrumb-item>帖子详情</el-breadcrumb-item>
        </el-breadcrumb>

        <div>
            <div id="Top">
                <div class="content">
                    <div style="padding-top: 6px;">
                        <table cellpadding="0" cellspacing="0" border="0" width="100%">
                            <tbody>
                            <tr>
                                <td width="570" align="left" style="padding-top: 2px;">
                                    <a href="/" class="top">首页</a>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            <div id="Wrapper">
                <div class="content">
                    <div id="Leftbar"></div>
                    <div id="Rightbar">
                        <div class="sep20"></div>
                        <div class="box">
                            <div class="cell">&nbsp;{{this.$store.state.user.userName}} 个人信息</div>

                            <nav class="level is-mobile">
                                <div class="level-item has-text-centered">
                                    <div>
                                        <p class="heading">发帖数</p>
                                        <p>{{mypostnum}}</p>
                                    </div>
                                </div>
                                <div class="level-item has-text-centered">
                                    <div>
                                        <p class="heading">粉丝</p>
                                        <p>123</p>
                                    </div>
                                </div>
                                <div class="level-item has-text-centered">
                                    <div>
                                        <p class="heading">你关注的人数</p>
                                        <p>456K</p>
                                    </div>
                                </div>
                                <div class="level-item has-text-centered">
                                    <div>
                                        <p class="heading">获赞数</p>
                                        <p>789</p>
                                    </div>
                                </div>
                            </nav>
                        </div>
                        <div class="sep20"></div>
                    </div>
                    <div id="Main">
                        <div class="sep20"></div>
                        <div class="box">
                            <div class="cell">
                                <table cellpadding="0" cellspacing="0" border="0" width="100%">
                                    <tbody>
                                    <tr>
                                        <td width="73" valign="top" align="center">
                                            <img
                                                    :src="require(`@/assets/${$store.state.user.userImg}`)"
                                                    class="avatar"
                                                    border="0"
                                                    align="default"
                                            />
                                            <!-- <img
                                              src="//cdn.v2ex.com/gravatar/873548b247e76e0e16ac5f8280c0004c?s=73&amp;d=retro"
                                              class="avatar"
                                              border="0"
                                              align="default"
                                            />-->
                                            <div class="sep10"></div>
                                            <strong class="online">ONLINE</strong>
                                        </td>
                                        <td width="10"></td>
                                        <td width="auto" valign="top" align="left">
                                            <div class="fr"></div>
                                            <h1 style="margin-bottom: 5px;">{{this.$store.state.user.userName}}</h1>
                                            <span class="gray">
                        <div class="sep5"></div>
                        {{$store.state.user.userShow}}
                      </span>
                                            <div class="sep10"></div>
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                                <div class="sep5"></div>
                            </div>
                        </div>
                        <div class="sep20"></div>

                        <div class="sep20"></div>
                        <div class="box">
                            <div class="inner">
                                <span class="gray">你的帖子</span>
                            </div>
                            <div class="inner">
                                <span class="chevron">»</span>
                                <div class="box" v-for="(item, i) in mypost" :key="i">
                                    <article class="media">
                                        <div class="media-content">
                                            <div class="content">
                                                <p>
                                                    <strong>{{item.artTitle}}</strong>
                                                    <br />
                                                    {{item.artContent}}
                                                </p>
                                            </div>
                                            <nav class="level is-mobile">
                                                <div class="level-left">
                                                    <a class="level-item">
                          <span class="icon is-small">
                            <i class="fab fa-hotjar"></i>
                          </span>
                                                        {{item.artHotNum}}
                                                    </a>

                                                    <a class="level-item">
                          <span class="icon is-small">
                            <i class="fas fa-comment-dots"></i>
                          </span>
                                                        {{item.artComNum}}
                                                    </a>

                                                    <a class="level-item">
                          <span class="icon is-small">
                            <i class="fas fa-heart"></i>
                          </span>
                                                        {{item.artLikeNum}}
                                                    </a>
                                                </div>
                                            </nav>
                                        </div>
                                    </article>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="c"></div>
                <div class="sep20"></div>
            </div>
        </div>



    </div>
</template>