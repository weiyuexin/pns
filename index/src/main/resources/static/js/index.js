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
                        $(".account img").attr('src', 'https://cdn.jsdelivr.net/gh/weiyuexin/blogimg@latest/img/202112222148419.png');
                    }
                }else {
                    $(".account img").attr('src', 'https://cdn.jsdelivr.net/gh/weiyuexin/blogimg@latest/img/202112222148419.png');

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


    /*
    * 记载页面时显示推荐内容
    * */
    /*使用分页实现流加载*/
    layui.use('flow', function(){
        var $ = layui.jquery; //不用额外加载jQuery，flow模块本身是有依赖jQuery的，直接用即可。
        var flow = layui.flow;
        flow.load({
            elem: '#tuijian' //指定列表容器
            , isAuto: false      //到底页面底端自动加载下一页，设为false则点击'加载更多'才会加载
            ,done: function(page, next){ //到达临界点（默认滚动触发），触发下一页
                var lis = [];
                //以jQuery的Ajax请求为例，请求下一页数据（注意：page是从2开始返回）
                $.get('/article/'+page+'/10', function(res){
                    //假设你的列表返回在data集合中
                    layui.each(res.data.records, function(index, item){
                        //定义文章链接
                        var articleUrl = window.location.href + "article/" + item.id;
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
                            "                                                    <a href=\"/user/"+item.authorId+"\" target='_blank'>" + item.authorName + "</a>\n" +
                            "                                                </div>\n" +
                            "                                                <div class=\"star layui-col-md2\">\n" +
                            "                                                    <a href=\"\">\n" +
                            "                                                        <img src=\"https://cdn.jsdelivr.net/gh/weiyuexin/blogimg@latest/img/202205091201606.png\">\n" +
                            "                                                        <p><span>" + item.star + "</span>赞</p>\n" +
                            "                                                    </a>\n" +
                            "                                                </div>\n" +
                            "                                                <div class=\"unstar layui-col-md1\">\n" +
                            "                                                    <a href=\"\"><img\n" +
                            "                                                            src=\"https://cdn.jsdelivr.net/gh/weiyuexin/blogimg@latest/img/202205091206240.png\">\n" +
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


});
