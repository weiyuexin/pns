$(document).ready(function () {
    /*
     * 判断用户是否登录
     * */
    $.ajax({
        url: "/user/check",  //请求地址
        dataType: "json",  //数据格式
        type: "GET",  //请求方式
        async: false,
        success: function (data) {
            if (data.flag) { //处于登录状态
                //$(".account").css("display","block");
                $(".LoginAndRegister").css("display", "none");

                //显示用户头像
                if (data.flag) {
                    if (!data.data.photo == "") {
                        $(".account img").attr('src', data.data.photo);
                    } else {
                        $(".account img").attr('src', 'https://cdn.jsdelivr.net/gh/weiyuexin/blogimg@latest/img/202112222148419.png');
                    }
                } else {
                    $(".account img").attr('src', 'https://cdn.jsdelivr.net/gh/weiyuexin/blogimg@latest/img/202112222148419.png');

                }
            } else {  //没有处于登录状态
                $(".account").css("display", "none");
                $(".message").css("display", "none");
                $(".create").css("display", "none");
                // $(".LoginAndRegister").css("display","block");
            }
        },
        error: function (data) {
            layer.msg("服务异常，请联系管理员");
        }

    });
    /*发送获取热门文章请求*/
    $.ajax({
        url: "/article/topArticle/5",//请求地址
        dataType: "json",//数据格式
        type: "GET",//请求方式
        async: false,//是否异步请求
        success: function (data) {   //如果发送成功
            var html = "";
            for (i = 0; i < data.data.length; i++) {
                var num = i + 1;
                var articleUrl = "/article/" + data.data[i].id;
                html += "<li class=\"layui-col-md12\">\n" +
                    "                            <span class=\"hot-list-id layui-col-md1\">" + num + ".</span>\n" +
                    "                            <a href=\"" + articleUrl + "\" target='_blank' class=\"hot-list-title layui-col-md10\">\n" +
                    "                                " + data.data[i].title + "\n" +
                    "                            </a><br>\n" +
                    "                        </li>";
            }
            $(".articleRank").html(html);
        },
        error: function (data) {
            layer.msg("服务器异常，请联系管理员!");
        }
    });
    /*发送获取积分排行请求*/
    $.ajax({
        url: "/user/topUser/5",//请求地址
        dataType: "json",//数据格式
        type: "GET",//请求方式
        async: false,//是否异步请求
        success: function (data) {   //如何发送成功
            var html = "";
            for (i = 0; i < data.data.length; i++) {
                if (data.data[i].photo == "") {
                    data.data[i].photo = "https://cdn.jsdelivr.net/gh/weiyuexin/blogimg@latest/img/202112222148419.png";
                }
                html += "<li class=\"layui-col-md12\">\n" +
                    "                            <div class=\"mark-list-logo layui-col-md3\">\n" +
                    "                                <a target='_blank' href=\"/user/" + data.data[i].id + "\"><img\n" +
                    "                                        src=\"" + data.data[i].photo + "\"\n" +
                    "                                        alt=\"\"></a>\n" +
                    "                            </div>\n" +
                    "                            <div class=\"mark-list-desc layui-col-md9\">\n" +
                    "                                <a target='_blank' href=\"/user/" + data.data[i].id + "\">\n" +
                    "                                    <div class=\"mark-list-desc-name layui-col-md12\">" + data.data[i].username + "</div>\n" +
                    "                                </a>\n" +
                    "                                <div class=\"mark-list-desc-score layui-col-md12\">总积分：<span>" + data.data[i].points + "</span></div>\n" +
                    "                            </div>\n" +
                    "                        </li>";
            }
            $(".userRank").html(html);
        },
        error: function (data) {
            layer.msg("服务器异常，请联系管理员!");
        }
    });
    /*发送获取热门资源请求*/
    $.ajax({
        url: "/resource/topResource/5",//请求地址
        dataType: "json",//数据格式
        type: "GET",//请求方式
        async: false,//是否异步请求
        success: function (data) {   //如果发送成功
            var html = "";
            for (i = 0; i < data.data.length; i++) {
                var num = i + 1;
                var articleUrl = "/resource/" + data.data[i].id;
                html += "<li class=\"layui-col-md12\">\n" +
                    "                            <span class=\"hot-list-id layui-col-md1\">" + num + ".</span>\n" +
                    "                            <a href=\"" + articleUrl + "\" target='_blank' class=\"hot-list-title layui-col-md10\">\n" +
                    "                                " + data.data[i].title + "\n" +
                    "                            </a><br>\n" +
                    "                        </li>";
            }
            $(".ResRank").html(html);
        },
        error: function (data) {
            layer.msg("服务器异常，请联系管理员!");
        }
    });

    var keyWord = $("#keyWord").html();
    /*发送搜索请求*/
    layui.use(['element', 'flow'], function () {
        var element = layui.element;
        var $ = layui.jquery; //不用额外加载jQuery，flow模块本身是有依赖jQuery的，直接用即可。
        var flow = layui.flow;

        flow.load({
            elem: '#tuijian' //指定列表容器
            , isAuto: false      //到底页面底端自动加载下一页，设为false则点击'加载更多'才会加载
            , done: function (page, next) { //到达临界点（默认滚动触发），触发下一页
                var lis = [];
                var key = $("#keyWord").html();
                //以jQuery的Ajax请求为例，请求下一页数据（注意：page是从2开始返回）
                $.get('/article/search/'+key+"/" + page + '/5', function (res) {
                    //假设你的列表返回在data集合中
                    layui.each(res.data.records, function (index, item) {
                        //定义文章链接
                        var articleUrl = "/article/" + item.id;
                        lis.push(" <li>\n" +
                            "                                    <div class=\"article\">\n" +
                            "                                        <div class=\"articleTitle layui-col-md12\">\n" +
                            "                                            <a href=\"" + articleUrl + "\" target='_blank'>\n" +
                            "                                                <h2>" + item.title + "</h2>\n" +
                            "                                            </a>\n" +
                            "                                        </div>\n" +
                            "                                        <div class=\"articleContent layui-col-md12\">\n" +
                            "                                            <div class=\"articleDesc layui-col-md12\">\n" +
                            "                                                <a href=\"" + articleUrl + "\" target='_blank'>\n" +
                            "                                                    <p>" + item.content + "</p>\n" +
                            "                                                </a>\n" +
                            "                                            </div>\n" +
                            "                                            <div class=\"articleOperation layui-col-md12\">\n" +
                            "                                                <div class=\"author layui-col-md2\">\n" +
                            "                                                    <a href=\"/user/" + item.authorId + "\" target='_blank'>" + item.authorName + "</a>\n" +
                            "                                                </div>\n" +
                            "                                                <div class=\"star layui-col-md2\">\n" +
                            "                                                    <a href=\"\">\n" +
                            "                                                        <img src=\"https://wyx-1303917755.cos.ap-beijing.myqcloud.com/img/2022/5/16/202251685dc978a-452b-4d24-8a84-eea3baad667f.png\">\n" +
                            "                                                        <p><span>" + item.star + "</span>赞</p>\n" +
                            "                                                    </a>\n" +
                            "                                                </div>\n" +
                            "                                                <div class=\"unstar layui-col-md1\">\n" +
                            "                                                    <a href=\"\"><img\n" +
                            "                                                            src=\"https://wyx-1303917755.cos.ap-beijing.myqcloud.com/img/2022/5/16/202251675fa9c81-539b-4635-b532-69dcc4b83d34.png\">\n" +
                            "                                                        <p>评论</p>\n" +
                            "                                                    </a>\n" +
                            "                                                </div>\n" +
                            "                                                <div class=\"more layui-col-md1\">\n" +
                            "                                                    <i class=\"layui-icon layui-icon-more\"></i>\n" +
                            "                                                </div>\n" +
                            "                                            </div>\n" +
                            "                                        </div>\n" +
                            "                                    </div>\n" +
                            "                                    <hr>\n" +
                            "                                </li>");
                    });

                    //执行下一页渲染，第二参数为：满足“加载更多”的条件，即后面仍有分页
                    //pages为Ajax返回的总页数，只有当前页小于总页数的情况下，才会继续出现加载更多
                    next(lis.join(''), page < res.data.pages);
                });
            }
        });
    });


    gotoSearch();

});

function gotoSearch() {
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
};
