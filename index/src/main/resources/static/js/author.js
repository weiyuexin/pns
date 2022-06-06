$(document).ready(function () {
    /*
      * 判断用户是否登录
      * */
    $.ajax({
        url: "/user/check",  //请求地址
        dataType: "json",  //数据格式
        type:"GET",  //请求方式
        async: false,
        success:function (data){
            if(data.flag){ //处于登录状态
                //$(".account").css("display","block");
                $(".LoginAndRegister").css("display","none");

                //显示用户头像
                if(data.flag){
                    if (!data.data.photo =="") {
                        $(".account img").attr('src', data.data.photo);
                    } else {
                        $(".account img").attr('src', 'https://wyx-1303917755.cos.ap-beijing.myqcloud.com/img/2022/5/18/2022518f0f23093-5b30-4c45-8e54-cdd71683020a.png');
                    }
                }else {
                    $(".account img").attr('src', 'https://wyx-1303917755.cos.ap-beijing.myqcloud.com/img/2022/5/18/2022518f0f23093-5b30-4c45-8e54-cdd71683020a.png');

                }
            }else {  //没有处于登录状态
                $(".account").css("display","none");
                $(".message").css("display","none");
                $(".create").css("display","none");
                // $(".LoginAndRegister").css("display","block");
            }
        },
        error:function (data) {
            layer.msg("服务异常，请联系管理员");
        }

    });

    $("#searchArticle").click(function () {
        var keyword = $("#Search").val();
        if (!keyword) {
            $("#Search").css('borderColor', 'red');
        } else {
            $("#Search").css('borderColor', '');
            $.ajax({
                url: "/article/search",//请求地址
                data: {"keyword": keyword},//搜索值
                dataType: "json",//数据格式
                type: "GET",//请求方式
                async: true,//是否异步请求
                success: function (data) {

                }, error: function (data) {
                    layer.msg("服务器异常，请联系管理员!");
                }
            })
        }
    });

    $(".accountMessage").css("display", "inline");
    $(".articleList").css("display", "none");
    $(".changePassword").css("display", "none");

    //点击显示个人信息页面
    $(".menu ul .li1").click(function () {
        //添加选中样式
        $(".menu ul li").removeClass("active");
        $(this).addClass("active");
        //显示对应页面
        $(".accountMessage").css("display", "inline");
        $(".articleList").css("display", "none");
        $(".changePassword").css("display", "none");
        $(".editAccountMessage").css("display", "none");
    });
    //点击显示文章列表页面
    $(".menu ul .li2").click(function () {
        //添加选中样式
        $(".menu ul li").removeClass("active");
        $(this).addClass("active");
        //显示对应页面
        $(".accountMessage").css("display", "none");
        $(".articleList").css("display", "inline");
        $(".changePassword").css("display", "none");
        $(".editAccountMessage").css("display", "none");
        //调用查询文章的函数
        getArticlesByAuthorRankByTime();

    });
    //点击显示资源列表页面
    $(".menu ul .li3").click(function () {
        //添加选中样式
        $(".menu ul li").removeClass("active");
        $(this).addClass("active");
        //显示对应页面
        $(".accountMessage").css("display", "none");
        $(".articleList").css("display", "inline");
        $(".changePassword").css("display", "none");
        $(".editAccountMessage").css("display", "none");
    });

    //选择文章排序规则
    //按时间
    $("#RankByTime").click(function () {
        $("#RankByTime").addClass("RankActive");
        $("#RankByReadNum").removeClass("RankActive");
        getArticlesByAuthorRankByTime();
    });
    //按阅读量
    $("#RankByReadNum").click(function () {
        $("#RankByReadNum").addClass("RankActive");
        $("#RankByTime").removeClass("RankActive");
        getArticlesByAuthorRankByReadNum();
    });


});



//查询作者的文章，按照时间排序
function getArticlesByAuthorRankByTime() {
    var userid;
    userid = $("#userid").html();
    //查询文章
    $.ajax({
        url: "/article/user/" + userid +"/1/10/time",  //请求地址
        dataType: "json",  //数据格式
        type: "GET",  //请求方式
        async: false,
        success: function (data) {
            var html = "";
            for (var i = 0; i < data.data.records.length; i++) {
                //定义文章链接
                var articleUrl = "/article/" + data.data.records[i].id;
                var articleEditUrl = "/article/edit/" + data.data.records[i].id;
                html += " <li>\n" +
                    "                    <div class=\"layui-panel article\">\n" +
                    "                        <div class=\"article-left layui-col-md9\">\n" +
                    "                            <div class=\"article-title layui-col-md12\">\n" +
                    "                                <a href=\"" + articleUrl + "\" target='_blank'>" + data.data.records[i].title + "</a>\n" +
                    "                            </div>\n" +
                    "                            <div class=\"article-content\">\n" +
                    "                                摘要：\n" +
                    "                                <span>" + data.data.records[i].content + "</span>\n" +
                    "                            </div>\n" +
                    "                            <div class=\"article-num layui-col-md12\">\n" +
                    "                                <i class=\"fa fa-file-text-o\"></i> 阅读\n" +
                    "                                <span>" + data.data.records[i].readNum + "</span>&emsp;<i class=\"fa fa-thumbs-o-up\"></i>点赞\n" +
                    "                                <span>" + data.data.records[i].star + "</span>&emsp;<i class=\"fa fa-commenting-o\"></i>评论\n" +
                    "                                <span>" + data.data.records[i].commentNum + "</span>\n" +
                    "                            </div>\n" +
                    "                        </div>\n" +
                    "                        <div class=\"article-right layui-col-md3\">\n" +
                    "                            <div class=\"article-time layui-col-md12\">" + data.data.records[i].time + "</div>\n" +
                    "                            <div class=\"article-operation layui-col-md12\">\n" +
                    "                            </div>\n" +
                    "                        </div>\n" +
                    "                    </div>\n" +
                    "                </li>";
            }
            $("#myArticles").html(html);
            if (data.data.records.length > 0) {
                $(".el-empty").hide();
                $(".articleRank").show();
            } else {
                $(".el-empty").show();
                $(".articleRank").hide();
            }
        },
        error: function (data) {
            layer.msg("服务异常，请联系管理员");
        }
    })

}

//查询作者的文章，按照阅读量排序
function getArticlesByAuthorRankByReadNum() {
    var userid;
    userid = $("#userid").html();
    //查询文章
    $.ajax({
        url: "/article/user/" + userid +"/1/10/readNum", //请求地址
        dataType: "json",  //数据格式
        type: "GET",  //请求方式
        async: false,
        success: function (data) {
            var html = "";
            for (var i = 0; i < data.data.records.length; i++) {
                //定义文章链接
                var articleUrl = "/article/" + data.data.records[i].id;
                var articleEditUrl = "/article/edit/" + data.data.records[i].id;
                html += " <li>\n" +
                    "                    <div class=\"layui-panel article\">\n" +
                    "                        <div class=\"article-left layui-col-md9\">\n" +
                    "                            <div class=\"article-title layui-col-md12\">\n" +
                    "                                <a href=\"" + articleUrl + "\" target='_blank'>" + data.data.records[i].title + "</a>\n" +
                    "                            </div>\n" +
                    "                            <div class=\"article-content\">\n" +
                    "                                摘要：\n" +
                    "                                <span>" + data.data.records[i].content + "</span>\n" +
                    "                            </div>\n" +
                    "                            <div class=\"article-num layui-col-md12\">\n" +
                    "                                <i class=\"fa fa-file-text-o\"></i> 阅读\n" +
                    "                                <span>" + data.data.records[i].readNum + "</span>&emsp;<i class=\"fa fa-thumbs-o-up\"></i>点赞\n" +
                    "                                <span>" + data.data.records[i].star + "</span>&emsp;<i class=\"fa fa-commenting-o\"></i>评论\n" +
                    "                                <span>" + data.data.records[i].commentNum + "</span>\n" +
                    "                            </div>\n" +
                    "                        </div>\n" +
                    "                        <div class=\"article-right layui-col-md3\">\n" +
                    "                            <div class=\"article-time layui-col-md12\">" + data.data.records[i].time + "</div>\n" +
                    "                            <div class=\"article-operation layui-col-md12\">\n" +
                    "                            </div>\n" +
                    "                        </div>\n" +
                    "                    </div>\n" +
                    "                </li>";
            }
            $("#myArticles").html(html);
            if (data.data.records.length > 0) {
                $(".el-empty").hide();
                $(".articleRank").show();
            } else {
                $(".el-empty").show();
                $(".articleRank").hide();
            }
        },
        error: function (data) {
            layer.msg("服务异常，请联系管理员");
        }
    })

}
