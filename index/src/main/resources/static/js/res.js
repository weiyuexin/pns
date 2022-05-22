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
                        $(".comment_author_logo img").attr('src', data.data.photo);
                    } else {
                        $(".account img").attr('src', 'https://wyx-1303917755.cos.ap-beijing.myqcloud.com/img/2022/5/18/2022518f0f23093-5b30-4c45-8e54-cdd71683020a.png');
                        $(".comment_author_logo img").attr('src', 'https://wyx-1303917755.cos.ap-beijing.myqcloud.com/img/2022/5/18/2022518f0f23093-5b30-4c45-8e54-cdd71683020a.png');
                    }
                }else {
                    $(".account img").attr('src', 'https://wyx-1303917755.cos.ap-beijing.myqcloud.com/img/2022/5/18/2022518f0f23093-5b30-4c45-8e54-cdd71683020a.png');
                    $(".comment_author_logo img").attr('src', 'https://wyx-1303917755.cos.ap-beijing.myqcloud.com/img/2022/5/18/2022518f0f23093-5b30-4c45-8e54-cdd71683020a.png');
                }
            }else {  //没有处于登录状态
                $(".account img").attr('src', 'https://wyx-1303917755.cos.ap-beijing.myqcloud.com/img/2022/5/18/2022518f0f23093-5b30-4c45-8e54-cdd71683020a.png');
                $(".comment_author_logo img").attr('src', 'https://wyx-1303917755.cos.ap-beijing.myqcloud.com/img/2022/5/18/2022518f0f23093-5b30-4c45-8e54-cdd71683020a.png');
                $(".account").css("display","none");
                $(".message").css("display","none");
                $(".create").css("display","none");
            }
        },
        error:function (data) {
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
                var num=i+1;
                var articleUrl = "/article/" + data.data[i].id;
                html+="<li class=\"layui-col-md12\">\n" +
                    "                            <span class=\"hot-list-id layui-col-md1\">"+num+".</span>\n" +
                    "                            <a href=\""+articleUrl+"\" target='_blank' class=\"hot-list-title layui-col-md10\">\n" +
                    "                                "+data.data[i].title+"\n" +
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
                if(data.data[i].photo==""){
                    data.data[i].photo="https://cdn.jsdelivr.net/gh/weiyuexin/blogimg@latest/img/202112222148419.png";
                }
                html += "<li class=\"layui-col-md12\">\n" +
                    "                            <div class=\"mark-list-logo layui-col-md3\">\n" +
                    "                                <a target='_blank' href=\"/user/"+data.data[i].id+"\"><img\n" +
                    "                                        src=\""+data.data[i].photo+"\"\n" +
                    "                                        alt=\"\"></a>\n" +
                    "                            </div>\n" +
                    "                            <div class=\"mark-list-desc layui-col-md9\">\n" +
                    "                                <a target='_blank' href=\"/user/"+data.data[i].id+"\">\n" +
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
                var num=i+1;
                var articleUrl = "/resource/" + data.data[i].id;
                html+="<li class=\"layui-col-md12\">\n" +
                    "                            <span class=\"hot-list-id layui-col-md1\">"+num+".</span>\n" +
                    "                            <a href=\""+articleUrl+"\" target='_blank' class=\"hot-list-title layui-col-md10\">\n" +
                    "                                "+data.data[i].title+"\n" +
                    "                            </a><br>\n" +
                    "                        </li>";
            }
            $(".ResRank").html(html);
        },
        error: function (data) {
            layer.msg("服务器异常，请联系管理员!");
        }
    });

    getComment();
    addComment();

});
/*评论*/
function addComment() {
    layui.use('layedit', function () {
        var layedit = layui.layedit;
        var index = layedit.build('comment', {
            tool: [
                'strong' //加粗
                , 'italic' //斜体
                , 'underline' //下划线
                , 'del' //删除线
                , '|' //分割线
                , 'left' //左对齐
                , 'center' //居中对齐
                , 'right' //右对齐
                , 'link' //超链接
                , 'unlink' //清除链接
                , 'face' //表情
            ]
            , height: 120 //设置编辑器高度
        });
        //点击提交评论按钮
        $(".comment_submit_btn").click(function () {
            //获取评论内容
            var comment = layedit.getContent(index);
            //获取文章的id
            var resourceId = $("#resourceId").val();

            if (comment == "") {
                layer.msg("请输入评论内容");
            } else {
                $.ajax({
                    url: "/resource/comment/add",//请求地址
                    dataType: "json",//数据格式
                    type: "POST",//请求方式
                    async: false,//是否异步请求
                    data: {
                        "content": comment,
                        "resourceId": resourceId,
                    },
                    success: function (data) {   //如何发送成功
                        if (data.flag) {//发布成功
                            layer.msg(data.msg);
                            $("#comment").val("");
                            getComment($(".comment-num").html());
                            $(".commentNumber").text(parseInt($(".commentNumber").html()) + 1);
                            layedit.setContent(index, "");
                        } else if (data.flag==false) {
                            layer.msg(data.msg);
                        }
                    },
                    error: function (data) {
                        layer.msg("服务器异常，请联系管理员!");
                    }
                });
            }
        });
    });

};
//获取当前资源的评论
function getComment() {
    //获取文章的id
    var resourceId = $("#resourceId").val();
    // alert(articleId)
    $.ajax({
        url: "/resource/comment/" + resourceId,//请求地址
        dataType: "json",//数据格式
        type: "GET",//请求方式
        async: false,//是否异步请求
        success: function (data) {   //如何发送成功
            $(".comment_number").html("(" + data.data.comments.length + ")");
            if (data.data.comments.length > 0) {
                //展示评论
                var html = "";
                for (var i = 0; i < data.data.comments.length; i++) {
                    html += "<li>\n" +
                        "                            <div class=\"author_message layui-col-md12\">\n" +
                        "                                <div class=\"comment_list_author_logo\">\n" +
                        "                                    <a href=\"" +"/user/" + data.data.authors[i].id + "\" target='_blank'><img src=\"" + data.data.authors[i].photo + "\" alt=\"\"></a>\n" +
                        "                                </div>\n" +
                        "                                <div class=\"author_mess layui-col-md10\">\n" +
                        "                                    <div class=\"author_name\"> <a href=\"" +"/user/" + data.data.authors[i].id + "\" target='_blank'>" + data.data.authors[i].username + "</a></div>\n" +
                        "                                    <div class=\"comment_time\">" + data.data.comments[i].time + "</div>\n" +
                        "                                </div>\n" +
                        "                                <div class=\"comment_star\" onclick=\"starComment(" + data.data.comments[i].id + ")\">\n" +
                        "                                    <i class=\"layui-icon layui-icon-praise\"></i><span id=\"comment_star_num\">" + data.data.comments[i].star + "</span>\n" +
                        "                                </div>\n" +
                        "                            </div>\n" +
                        "                            <div class=\"comment_body layui-col-md12\">\n" +
                        "                                <span>" + data.data.comments[i].content + "</span>\n" +
                        "                            </div>\n" +
                        "                        </li>";
                }
                $(".comment_list ul").html(html);
            }
        },
        error: function (data) {
            layer.msg("服务器异常，请联系管理员!");
        }
    });
};