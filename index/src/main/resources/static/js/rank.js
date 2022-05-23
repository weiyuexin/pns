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
    /*发送获取积分排行请求*/
    $.ajax({
        url: "/user/topUser/10",//请求地址
        dataType: "json",//数据格式
        type: "GET",//请求方式
        async: false,//是否异步请求
        success: function (data) {   //如何发送成功
            var html = "";
            for (i = 0; i < data.data.length; i++) {
                if(data.data[i].photo==""){
                    data.data[i].photo="https://wyx-1303917755.cos.ap-beijing.myqcloud.com/img/2022/5/18/2022518f0f23093-5b30-4c45-8e54-cdd71683020a.png";
                }
                html += "<li class=\"layui-col-md12\">\n" +
                    "                                <div class=\"mark-list-logo layui-col-md3\">\n" +
                    "                                  <a target='_blank' href=\"/user/"+data.data[i].id+"\"><img\n" +
                    "                                        src=\""+data.data[i].photo+"\"\n" +
                    "                                        alt=\"\"></a>\n" +
                    "                                </div>\n" +
                    "                                <div class=\"mark-list-desc layui-col-md9\">\n" +
                    "                                     <a target='_blank' href=\"/user/"+data.data[i].id+"\">\n" +
                    "                                    <div class=\"mark-list-desc-name layui-col-md12\">" + data.data[i].username + "</div>\n" +
                    "                                </a>\n" +
                    "                                    <div class=\"mark-list-desc-score layui-col-md12\">总积分：<span>"+data.data[i].points+"</span></div>\n" +
                    "                                </div>\n" +
                    "                            </li>";
            }
            $(".userRank ul").html(html);
        },
        error: function (data) {
            layer.msg("服务器异常，请联系管理员!");
        }
    });

    /*发送获取热门文章请求*/
    $.ajax({
        url: "/article/topArticle/10",//请求地址
        dataType: "json",//数据格式
        type: "GET",//请求方式
        async: false,//是否异步请求
        success: function (data) {   //如果发送成功
            var html = "";
            for (i = 0; i < data.data.length; i++) {
                var num=i+1;
                var articleUrl = "/article/" + data.data[i].id;
                html += "<li class=\"layui-col-md12\">\n" +
                    "                                <span class=\"hot-list-id layui-col-md1\">"+num+".</span>\n" +
                    "                                <a target='_blank' href=\""+articleUrl+"\" class=\"hot-list-title layui-col-md10\">\n" +
                    "                                    "+data.data[i].title+"" +
                    "                                </a><br>\n" +
                    "                            </li>";
            }
            $(".articleRank ul").html(html);
        },
        error: function (data) {
            layer.msg("服务器异常，请联系管理员!");
        }
    });
    /*发送获取热门资源请求*/
    $.ajax({
        url: "/resource/topResource/10",//请求地址
        dataType: "json",//数据格式
        type: "GET",//请求方式
        async: false,//是否异步请求
        success: function (data) {   //如果发送成功
            var html = "";
            for (i = 0; i < data.data.length; i++) {
                var num=i+1;
                var resUrl = "/resource/"+data.data[i].id;
                html += "<li class=\"layui-col-md12\">\n" +
                    "                                <span class=\"hot-list-id layui-col-md1\">"+num+".</span>\n" +
                    "                                <a target='_blank' href=\""+resUrl+"\" class=\"hot-list-title layui-col-md10\">\n" +
                    "                                    "+data.data[i].title+"" +
                    "                                </a><br>\n" +
                    "                            </li>";
            }
            $(".ResRank").html(html);
        },
        error: function (data) {
            layer.msg("服务器异常，请联系管理员!");
        }
    });
})
