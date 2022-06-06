$(document).ready(function (){

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

    $.ajax({
        url: "/resource/书籍/1/8",  //请求地址
        dataType: "json",  //数据格式
        type: "GET",  //请求方式
        async: false,
        success: function (data) {
            var html="";
            for (i = 0; i < data.data.records.length; i++) {
                var resUrl = "/resource/"+data.data.records[i].id;
                html+="<a href=\""+resUrl+"\" target='_blank'>\n" +
                    "                <div class=\"layui-col-md4 box layui-col-md3\">\n" +
                    "                    <div class=\"layui-col-md12 logo\">\n" +
                    "                        <img src=\""+data.data.records[i].icon+"\">\n" +
                    "                    </div>\n" +
                    "                    <div class=\"layui-col-md12 title\">"+data.data.records[i].title+"</div>\n" +
                    "                    <div class=\"layui-col-md12 jianjie\">"+data.data.records[i].content+"</div>\n" +
                    "                </div>\n" +
                    "            </a>";
                $(".reslist").html(html);
            }
        },
        error: function (data) {
            layer.msg("服务异常，请联系管理员");
        }

    });
    //搜索
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

    $("#book").click(function (){
        $.ajax({
            url: "/resource/书籍/1/8",  //请求地址
            dataType: "json",  //数据格式
            type: "GET",  //请求方式
            async: false,
            success: function (data) {
                var html="";
                for (i = 0; i < data.data.records.length; i++) {
                    var resUrl = "/resource/"+data.data.records[i].id;
                    html+="<a href=\""+resUrl+"\" target='_blank'>\n" +
                        "                <div class=\"layui-col-md4 box layui-col-md3\">\n" +
                        "                    <div class=\"layui-col-md12 logo\">\n" +
                        "                        <img src=\""+data.data.records[i].icon+"\">\n" +
                        "                    </div>\n" +
                        "                    <div class=\"layui-col-md12 title\">"+data.data.records[i].title+"</div>\n" +
                        "                    <div class=\"layui-col-md12 jianjie\">"+data.data.records[i].content+"</div>\n" +
                        "                </div>\n" +
                        "            </a>";
                    $(".reslist").html(html);
                }
            },
            error: function (data) {
                layer.msg("服务异常，请联系管理员");
            }

        });
    });
    $("#course").click(function (){
        $.ajax({
            url: "/resource/教程/1/8",  //请求地址
            dataType: "json",  //数据格式
            type: "GET",  //请求方式
            async: false,
            success: function (data) {
                var html="";
                for (i = 0; i < data.data.records.length; i++) {
                    var resUrl = "/resource/"+data.data.records[i].id;
                    html+="<a href=\""+resUrl+"\" target='_blank'>\n" +
                        "                <div class=\"layui-col-md4 box layui-col-md3\">\n" +
                        "                    <div class=\"layui-col-md12 logo\">\n" +
                        "                        <img src=\""+data.data.records[i].icon+"\">\n" +
                        "                    </div>\n" +
                        "                    <div class=\"layui-col-md12 title\">"+data.data.records[i].title+"</div>\n" +
                        "                    <div class=\"layui-col-md12 jianjie\">"+data.data.records[i].content+"</div>\n" +
                        "                </div>\n" +
                        "            </a>";
                    $(".reslist").html(html);
                }
            },
            error: function (data) {
                layer.msg("服务异常，请联系管理员");
            }

        });
    });
    $("#tool").click(function (){
        $.ajax({
            url: "/resource/工具/1/8",  //请求地址
            dataType: "json",  //数据格式
            type: "GET",  //请求方式
            async: false,
            success: function (data) {
                var html="";
                for (i = 0; i < data.data.records.length; i++) {
                    var resUrl = "/resource/"+data.data.records[i].id;
                    html+="<a href=\""+resUrl+"\" target='_blank'>\n" +
                        "                <div class=\"layui-col-md4 box layui-col-md3\">\n" +
                        "                    <div class=\"layui-col-md12 logo\">\n" +
                        "                        <img src=\""+data.data.records[i].icon+"\">\n" +
                        "                    </div>\n" +
                        "                    <div class=\"layui-col-md12 title\">"+data.data.records[i].title+"</div>\n" +
                        "                    <div class=\"layui-col-md12 jianjie\">"+data.data.records[i].content+"</div>\n" +
                        "                </div>\n" +
                        "            </a>";
                    $(".reslist").html(html);
                }
            },
            error: function (data) {
                layer.msg("服务异常，请联系管理员");
            }

        });
    });
    $("#cpp").click(function (){
        $.ajax({
            url: "/resource/cpp/1/8",  //请求地址
            dataType: "json",  //数据格式
            type: "GET",  //请求方式
            async: false,
            success: function (data) {
                var html="";
                for (i = 0; i < data.data.records.length; i++) {
                    var resUrl = "/resource/"+data.data.records[i].id;
                    html+="<a href=\""+resUrl+"\" target='_blank'>\n" +
                        "                <div class=\"layui-col-md4 box layui-col-md3\">\n" +
                        "                    <div class=\"layui-col-md12 logo\">\n" +
                        "                        <img src=\""+data.data.records[i].icon+"\">\n" +
                        "                    </div>\n" +
                        "                    <div class=\"layui-col-md12 title\">"+data.data.records[i].title+"</div>\n" +
                        "                    <div class=\"layui-col-md12 jianjie\">"+data.data.records[i].content+"</div>\n" +
                        "                </div>\n" +
                        "            </a>";
                    $(".reslist").html(html);
                }
            },
            error: function (data) {
                layer.msg("服务异常，请联系管理员");
            }

        });
    });
    $("#Java").click(function (){
        $.ajax({
            url: "/resource/Java/1/8",  //请求地址
            dataType: "json",  //数据格式
            type: "GET",  //请求方式
            async: false,
            success: function (data) {
                var html="";
                for (i = 0; i < data.data.records.length; i++) {
                    var resUrl = "/resource/"+data.data.records[i].id;
                    html+="<a href=\""+resUrl+"\" target='_blank'>\n" +
                        "                <div class=\"layui-col-md4 box layui-col-md3\">\n" +
                        "                    <div class=\"layui-col-md12 logo\">\n" +
                        "                        <img src=\""+data.data.records[i].icon+"\">\n" +
                        "                    </div>\n" +
                        "                    <div class=\"layui-col-md12 title\">"+data.data.records[i].title+"</div>\n" +
                        "                    <div class=\"layui-col-md12 jianjie\">"+data.data.records[i].content+"</div>\n" +
                        "                </div>\n" +
                        "            </a>";
                    $(".reslist").html(html);
                }
            },
            error: function (data) {
                layer.msg("服务异常，请联系管理员");
            }

        });
    });
    $("#Python").click(function (){
        $.ajax({
            url: "/resource/Python/1/8",  //请求地址
            dataType: "json",  //数据格式
            type: "GET",  //请求方式
            async: false,
            success: function (data) {
                var html="";
                for (i = 0; i < data.data.records.length; i++) {
                    var resUrl = "/resource/"+data.data.records[i].id;
                    html+="<a href=\""+resUrl+"\" target='_blank'>\n" +
                        "                <div class=\"layui-col-md4 box layui-col-md3\">\n" +
                        "                    <div class=\"layui-col-md12 logo\">\n" +
                        "                        <img src=\""+data.data.records[i].icon+"\">\n" +
                        "                    </div>\n" +
                        "                    <div class=\"layui-col-md12 title\">"+data.data.records[i].title+"</div>\n" +
                        "                    <div class=\"layui-col-md12 jianjie\">"+data.data.records[i].content+"</div>\n" +
                        "                </div>\n" +
                        "            </a>";
                    $(".reslist").html(html);
                }
            },
            error: function (data) {
                layer.msg("服务异常，请联系管理员");
            }

        });
    });
    $("#JavaScript").click(function (){
        $.ajax({
            url: "/resource/JavaScript/1/8",  //请求地址
            dataType: "json",  //数据格式
            type: "GET",  //请求方式
            async: false,
            success: function (data) {
                var html="";
                for (i = 0; i < data.data.records.length; i++) {
                    var resUrl = "/resource/"+data.data.records[i].id;
                    html+="<a href=\""+resUrl+"\" target='_blank'>\n" +
                        "                <div class=\"layui-col-md4 box layui-col-md3\">\n" +
                        "                    <div class=\"layui-col-md12 logo\">\n" +
                        "                        <img src=\""+data.data.records[i].icon+"\">\n" +
                        "                    </div>\n" +
                        "                    <div class=\"layui-col-md12 title\">"+data.data.records[i].title+"</div>\n" +
                        "                    <div class=\"layui-col-md12 jianjie\">"+data.data.records[i].content+"</div>\n" +
                        "                </div>\n" +
                        "            </a>";
                    $(".reslist").html(html);
                }
            },
            error: function (data) {
                layer.msg("服务异常，请联系管理员");
            }

        });
    });
    $("#GO").click(function (){
        $.ajax({
            url: "/resource/GO/1/8",  //请求地址
            dataType: "json",  //数据格式
            type: "GET",  //请求方式
            async: false,
            success: function (data) {
                var html="";
                for (i = 0; i < data.data.records.length; i++) {
                    var resUrl = "/resource/"+data.data.records[i].id;
                    html+="<a href=\""+resUrl+"\" target='_blank'>\n" +
                        "                <div class=\"layui-col-md4 box layui-col-md3\">\n" +
                        "                    <div class=\"layui-col-md12 logo\">\n" +
                        "                        <img src=\""+data.data.records[i].icon+"\">\n" +
                        "                    </div>\n" +
                        "                    <div class=\"layui-col-md12 title\">"+data.data.records[i].title+"</div>\n" +
                        "                    <div class=\"layui-col-md12 jianjie\">"+data.data.records[i].content+"</div>\n" +
                        "                </div>\n" +
                        "            </a>";
                    $(".reslist").html(html);
                }
            },
            error: function (data) {
                layer.msg("服务异常，请联系管理员");
            }

        });
    });
    $("#PHP").click(function (){
        $.ajax({
            url: "/resource/PHP/1/8",  //请求地址
            dataType: "json",  //数据格式
            type: "GET",  //请求方式
            async: false,
            success: function (data) {
                var html="";
                for (i = 0; i < data.data.records.length; i++) {
                    var resUrl = "/resource/"+data.data.records[i].id;
                    html+="<a href=\""+resUrl+"\" target='_blank'>\n" +
                        "                <div class=\"layui-col-md4 box layui-col-md3\">\n" +
                        "                    <div class=\"layui-col-md12 logo\">\n" +
                        "                        <img src=\""+data.data.records[i].icon+"\">\n" +
                        "                    </div>\n" +
                        "                    <div class=\"layui-col-md12 title\">"+data.data.records[i].title+"</div>\n" +
                        "                    <div class=\"layui-col-md12 jianjie\">"+data.data.records[i].content+"</div>\n" +
                        "                </div>\n" +
                        "            </a>";
                    $(".reslist").html(html);
                }
            },
            error: function (data) {
                layer.msg("服务异常，请联系管理员");
            }

        });
    });
    $("#AI").click(function (){
        $.ajax({
            url: "/resource/人工智能/1/8",  //请求地址
            dataType: "json",  //数据格式
            type: "GET",  //请求方式
            async: false,
            success: function (data) {
                var html="";
                for (i = 0; i < data.data.records.length; i++) {
                    var resUrl = "/resource/"+data.data.records[i].id;
                    html+="<a href=\""+resUrl+"\" target='_blank'>\n" +
                        "                <div class=\"layui-col-md4 box layui-col-md3\">\n" +
                        "                    <div class=\"layui-col-md12 logo\">\n" +
                        "                        <img src=\""+data.data.records[i].icon+"\">\n" +
                        "                    </div>\n" +
                        "                    <div class=\"layui-col-md12 title\">"+data.data.records[i].title+"</div>\n" +
                        "                    <div class=\"layui-col-md12 jianjie\">"+data.data.records[i].content+"</div>\n" +
                        "                </div>\n" +
                        "            </a>";
                    $(".reslist").html(html);
                }
            },
            error: function (data) {
                layer.msg("服务异常，请联系管理员");
            }

        });
    });
    $("#HarmonyOS").click(function (){
        $.ajax({
            url: "/resource/HarmonyOS/1/8",  //请求地址
            dataType: "json",  //数据格式
            type: "GET",  //请求方式
            async: false,
            success: function (data) {
                var html="";
                for (i = 0; i < data.data.records.length; i++) {
                    var resUrl = "/resource/"+data.data.records[i].id;
                    html+="<a href=\""+resUrl+"\" target='_blank'>\n" +
                        "                <div class=\"layui-col-md4 box layui-col-md3\">\n" +
                        "                    <div class=\"layui-col-md12 logo\">\n" +
                        "                        <img src=\""+data.data.records[i].icon+"\">\n" +
                        "                    </div>\n" +
                        "                    <div class=\"layui-col-md12 title\">"+data.data.records[i].title+"</div>\n" +
                        "                    <div class=\"layui-col-md12 jianjie\">"+data.data.records[i].content+"</div>\n" +
                        "                </div>\n" +
                        "            </a>";
                    $(".reslist").html(html);
                }
            },
            error: function (data) {
                layer.msg("服务异常，请联系管理员");
            }

        });
    });
    $("#yun").click(function (){
        $.ajax({
            url: "/resource/云计算/1/8",  //请求地址
            dataType: "json",  //数据格式
            type: "GET",  //请求方式
            async: false,
            success: function (data) {
                var html="";
                for (i = 0; i < data.data.records.length; i++) {
                    var resUrl = "/resource/"+data.data.records[i].id;
                    html+="<a href=\""+resUrl+"\" target='_blank'>\n" +
                        "                <div class=\"layui-col-md4 box layui-col-md3\">\n" +
                        "                    <div class=\"layui-col-md12 logo\">\n" +
                        "                        <img src=\""+data.data.records[i].icon+"\">\n" +
                        "                    </div>\n" +
                        "                    <div class=\"layui-col-md12 title\">"+data.data.records[i].title+"</div>\n" +
                        "                    <div class=\"layui-col-md12 jianjie\">"+data.data.records[i].content+"</div>\n" +
                        "                </div>\n" +
                        "            </a>";
                    $(".reslist").html(html);
                }
            },
            error: function (data) {
                layer.msg("服务异常，请联系管理员");
            }

        });
    });
    $("#dataStructure").click(function (){
        $.ajax({
            url: "/resource/数据结构/1/8",  //请求地址
            dataType: "json",  //数据格式
            type: "GET",  //请求方式
            async: false,
            success: function (data) {
                var html="";
                for (i = 0; i < data.data.records.length; i++) {
                    var resUrl = "/resource/"+data.data.records[i].id;
                    html+="<a href=\""+resUrl+"\" target='_blank'>\n" +
                        "                <div class=\"layui-col-md4 box layui-col-md3\">\n" +
                        "                    <div class=\"layui-col-md12 logo\">\n" +
                        "                        <img src=\""+data.data.records[i].icon+"\">\n" +
                        "                    </div>\n" +
                        "                    <div class=\"layui-col-md12 title\">"+data.data.records[i].title+"</div>\n" +
                        "                    <div class=\"layui-col-md12 jianjie\">"+data.data.records[i].content+"</div>\n" +
                        "                </div>\n" +
                        "            </a>";
                    $(".reslist").html(html);
                }
            },
            error: function (data) {
                layer.msg("服务异常，请联系管理员");
            }

        });
    });
    $("#os").click(function (){
        $.ajax({
            url: "/resource/操作系统/1/8",  //请求地址
            dataType: "json",  //数据格式
            type: "GET",  //请求方式
            async: false,
            success: function (data) {
                var html="";
                for (i = 0; i < data.data.records.length; i++) {
                    var resUrl = "/resource/"+data.data.records[i].id;
                    html+="<a href=\""+resUrl+"\" target='_blank'>\n" +
                        "                <div class=\"layui-col-md4 box layui-col-md3\">\n" +
                        "                    <div class=\"layui-col-md12 logo\">\n" +
                        "                        <img src=\""+data.data.records[i].icon+"\">\n" +
                        "                    </div>\n" +
                        "                    <div class=\"layui-col-md12 title\">"+data.data.records[i].title+"</div>\n" +
                        "                    <div class=\"layui-col-md12 jianjie\">"+data.data.records[i].content+"</div>\n" +
                        "                </div>\n" +
                        "            </a>";
                    $(".reslist").html(html);
                }
            },
            error: function (data) {
                layer.msg("服务异常，请联系管理员");
            }

        });
    });
    $("#net").click(function (){
        $.ajax({
            url: "/resource/计算机网络/1/8",  //请求地址
            dataType: "json",  //数据格式
            type: "GET",  //请求方式
            async: false,
            success: function (data) {
                var html="";
                for (i = 0; i < data.data.records.length; i++) {
                    var resUrl = "/resource/"+data.data.records[i].id;
                    html+="<a href=\""+resUrl+"\" target='_blank'>\n" +
                        "                <div class=\"layui-col-md4 box layui-col-md3\">\n" +
                        "                    <div class=\"layui-col-md12 logo\">\n" +
                        "                        <img src=\""+data.data.records[i].icon+"\">\n" +
                        "                    </div>\n" +
                        "                    <div class=\"layui-col-md12 title\">"+data.data.records[i].title+"</div>\n" +
                        "                    <div class=\"layui-col-md12 jianjie\">"+data.data.records[i].content+"</div>\n" +
                        "                </div>\n" +
                        "            </a>";
                    $(".reslist").html(html);
                }
            },
            error: function (data) {
                layer.msg("服务异常，请联系管理员");
            }

        });
    });
    $("#jcjc").click(function (){
        $.ajax({
            url: "/resource/基础教程/1/8",  //请求地址
            dataType: "json",  //数据格式
            type: "GET",  //请求方式
            async: false,
            success: function (data) {
                var html="";
                for (i = 0; i < data.data.records.length; i++) {
                    var resUrl = "/resource/"+data.data.records[i].id;
                    html+="<a href=\""+resUrl+"\" target='_blank'>\n" +
                        "                <div class=\"layui-col-md4 box layui-col-md3\">\n" +
                        "                    <div class=\"layui-col-md12 logo\">\n" +
                        "                        <img src=\""+data.data.records[i].icon+"\">\n" +
                        "                    </div>\n" +
                        "                    <div class=\"layui-col-md12 title\">"+data.data.records[i].title+"</div>\n" +
                        "                    <div class=\"layui-col-md12 jianjie\">"+data.data.records[i].content+"</div>\n" +
                        "                </div>\n" +
                        "            </a>";
                    $(".reslist").html(html);
                }
            },
            error: function (data) {
                layer.msg("服务异常，请联系管理员");
            }

        });
    });
    $("#kuangjia").click(function (){
        $.ajax({
            url: "/resource/开发框架/1/8",  //请求地址
            dataType: "json",  //数据格式
            type: "GET",  //请求方式
            async: false,
            success: function (data) {
                var html="";
                for (i = 0; i < data.data.records.length; i++) {
                    var resUrl = "/resource/"+data.data.records[i].id;
                    html+="<a href=\""+resUrl+"\" target='_blank'>\n" +
                        "                <div class=\"layui-col-md4 box layui-col-md3\">\n" +
                        "                    <div class=\"layui-col-md12 logo\">\n" +
                        "                        <img src=\""+data.data.records[i].icon+"\">\n" +
                        "                    </div>\n" +
                        "                    <div class=\"layui-col-md12 title\">"+data.data.records[i].title+"</div>\n" +
                        "                    <div class=\"layui-col-md12 jianjie\">"+data.data.records[i].content+"</div>\n" +
                        "                </div>\n" +
                        "            </a>";
                    $(".reslist").html(html);
                }
            },
            error: function (data) {
                layer.msg("服务异常，请联系管理员");
            }

        });
    });
    $("#lib").click(function (){
        $.ajax({
            url: "/resource/常用库/1/8",  //请求地址
            dataType: "json",  //数据格式
            type: "GET",  //请求方式
            async: false,
            success: function (data) {
                var html="";
                for (i = 0; i < data.data.records.length; i++) {
                    var resUrl = "/resource/"+data.data.records[i].id;
                    html+="<a href=\""+resUrl+"\" target='_blank'>\n" +
                        "                <div class=\"layui-col-md4 box layui-col-md3\">\n" +
                        "                    <div class=\"layui-col-md12 logo\">\n" +
                        "                        <img src=\""+data.data.records[i].icon+"\">\n" +
                        "                    </div>\n" +
                        "                    <div class=\"layui-col-md12 title\">"+data.data.records[i].title+"</div>\n" +
                        "                    <div class=\"layui-col-md12 jianjie\">"+data.data.records[i].content+"</div>\n" +
                        "                </div>\n" +
                        "            </a>";
                    $(".reslist").html(html);
                }
            },
            error: function (data) {
                layer.msg("服务异常，请联系管理员");
            }

        });
    });
    $("#pack").click(function (){
        $.ajax({
            url: "/resource/打包工具/1/8",  //请求地址
            dataType: "json",  //数据格式
            type: "GET",  //请求方式
            async: false,
            success: function (data) {
                var html="";
                for (i = 0; i < data.data.records.length; i++) {
                    var resUrl = "/resource/"+data.data.records[i].id;
                    html+="<a href=\""+resUrl+"\" target='_blank'>\n" +
                        "                <div class=\"layui-col-md4 box layui-col-md3\">\n" +
                        "                    <div class=\"layui-col-md12 logo\">\n" +
                        "                        <img src=\""+data.data.records[i].icon+"\">\n" +
                        "                    </div>\n" +
                        "                    <div class=\"layui-col-md12 title\">"+data.data.records[i].title+"</div>\n" +
                        "                    <div class=\"layui-col-md12 jianjie\">"+data.data.records[i].content+"</div>\n" +
                        "                </div>\n" +
                        "            </a>";
                    $(".reslist").html(html);
                }
            },
            error: function (data) {
                layer.msg("服务异常，请联系管理员");
            }

        });
    });
    $("#fwq").click(function (){
        $.ajax({
            url: "/resource/云服务器/1/8",  //请求地址
            dataType: "json",  //数据格式
            type: "GET",  //请求方式
            async: false,
            success: function (data) {
                var html="";
                for (i = 0; i < data.data.records.length; i++) {
                    var resUrl = "/resource/"+data.data.records[i].id;
                    html+="<a href=\""+resUrl+"\" target='_blank'>\n" +
                        "                <div class=\"layui-col-md4 box layui-col-md3\">\n" +
                        "                    <div class=\"layui-col-md12 logo\">\n" +
                        "                        <img src=\""+data.data.records[i].icon+"\">\n" +
                        "                    </div>\n" +
                        "                    <div class=\"layui-col-md12 title\">"+data.data.records[i].title+"</div>\n" +
                        "                    <div class=\"layui-col-md12 jianjie\">"+data.data.records[i].content+"</div>\n" +
                        "                </div>\n" +
                        "            </a>";
                    $(".reslist").html(html);
                }
            },
            error: function (data) {
                layer.msg("服务异常，请联系管理员");
            }

        });
    });
    $("#sjk").click(function (){
        $.ajax({
            url: "/resource/数据库/1/8",  //请求地址
            dataType: "json",  //数据格式
            type: "GET",  //请求方式
            async: false,
            success: function (data) {
                var html="";
                for (i = 0; i < data.data.records.length; i++) {
                    var resUrl = "/resource/"+data.data.records[i].id;
                    html+="<a href=\""+resUrl+"\" target='_blank'>\n" +
                        "                <div class=\"layui-col-md4 box layui-col-md3\">\n" +
                        "                    <div class=\"layui-col-md12 logo\">\n" +
                        "                        <img src=\""+data.data.records[i].icon+"\">\n" +
                        "                    </div>\n" +
                        "                    <div class=\"layui-col-md12 title\">"+data.data.records[i].title+"</div>\n" +
                        "                    <div class=\"layui-col-md12 jianjie\">"+data.data.records[i].content+"</div>\n" +
                        "                </div>\n" +
                        "            </a>";
                    $(".reslist").html(html);
                }
            },
            error: function (data) {
                layer.msg("服务异常，请联系管理员");
            }

        });
    });
    $("#doc").click(function (){
        $.ajax({
            url: "/resource/开发文档/1/8",  //请求地址
            dataType: "json",  //数据格式
            type: "GET",  //请求方式
            async: false,
            success: function (data) {
                var html="";
                for (i = 0; i < data.data.records.length; i++) {
                    var resUrl = "/resource/"+data.data.records[i].id;
                    html+="<a href=\""+resUrl+"\" target='_blank'>\n" +
                        "                <div class=\"layui-col-md4 box layui-col-md3\">\n" +
                        "                    <div class=\"layui-col-md12 logo\">\n" +
                        "                        <img src=\""+data.data.records[i].icon+"\">\n" +
                        "                    </div>\n" +
                        "                    <div class=\"layui-col-md12 title\">"+data.data.records[i].title+"</div>\n" +
                        "                    <div class=\"layui-col-md12 jianjie\">"+data.data.records[i].content+"</div>\n" +
                        "                </div>\n" +
                        "            </a>";
                    $(".reslist").html(html);
                }
            },
            error: function (data) {
                layer.msg("服务异常，请联系管理员");
            }

        });
    });
    $("#data").click(function (){
        $.ajax({
            url: "/resource/大数据/1/8",  //请求地址
            dataType: "json",  //数据格式
            type: "GET",  //请求方式
            async: false,
            success: function (data) {
                var html="";
                for (i = 0; i < data.data.records.length; i++) {
                    var resUrl = "/resource/"+data.data.records[i].id;
                    html+="<a href=\""+resUrl+"\" target='_blank'>\n" +
                        "                <div class=\"layui-col-md4 box layui-col-md3\">\n" +
                        "                    <div class=\"layui-col-md12 logo\">\n" +
                        "                        <img src=\""+data.data.records[i].icon+"\">\n" +
                        "                    </div>\n" +
                        "                    <div class=\"layui-col-md12 title\">"+data.data.records[i].title+"</div>\n" +
                        "                    <div class=\"layui-col-md12 jianjie\">"+data.data.records[i].content+"</div>\n" +
                        "                </div>\n" +
                        "            </a>";
                    $(".reslist").html(html);
                }
            },
            error: function (data) {
                layer.msg("服务异常，请联系管理员");
            }

        });
    });
    $("#zcyl").click(function (){
        $.ajax({
            url: "/resource/计算机组成原理/1/8",  //请求地址
            dataType: "json",  //数据格式
            type: "GET",  //请求方式
            async: false,
            success: function (data) {
                var html="";
                for (i = 0; i < data.data.records.length; i++) {
                    var resUrl = "/resource/"+data.data.records[i].id;
                    html+="<a href=\""+resUrl+"\" target='_blank'>\n" +
                        "                <div class=\"layui-col-md4 box layui-col-md3\">\n" +
                        "                    <div class=\"layui-col-md12 logo\">\n" +
                        "                        <img src=\""+data.data.records[i].icon+"\">\n" +
                        "                    </div>\n" +
                        "                    <div class=\"layui-col-md12 title\">"+data.data.records[i].title+"</div>\n" +
                        "                    <div class=\"layui-col-md12 jianjie\">"+data.data.records[i].content+"</div>\n" +
                        "                </div>\n" +
                        "            </a>";
                    $(".reslist").html(html);
                }
            },
            error: function (data) {
                layer.msg("服务异常，请联系管理员");
            }

        });
    });

});