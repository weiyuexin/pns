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
                getAccount();
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

    $(".accountMessage").css("display", "none");
    $(".articleList").css("display", "none");
    $(".changePassword").css("display", "none");
    $(".editAccountMessage").css("display", "none");
    //点击修改个人信息后，显示修改个人信息页面
    $(".changeAccountMessage").click(function () {
        $(".accountMessage").css("display", "none");
        $(".articleList").css("display", "none");
        $(".resList").css("display", "none");
        $(".changePassword").css("display", "none");
        $(".editAccountMessage").css("display", "inline");

    });
    //点击显示个人信息页面
    $(".menu ul .li1").click(function () {
        //查询用户信息
        getAccount();
        //添加选中样式
        $(".menu ul li").removeClass("active");
        $(this).addClass("active");
        //显示对应页面
        $(".accountMessage").css("display", "inline");
        $(".articleList").css("display", "none");
        $(".resList").css("display", "none");
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
        $(".resList").css("display", "none");
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
        $(".articleList").css("display", "none");
        $(".resList").css("display", "inline");
        $(".changePassword").css("display", "none");
        $(".editAccountMessage").css("display", "none");
        getResByAuthor();
    });
    //点击显示修改密码页面
    $(".menu ul .li4").click(function () {
        //添加选中样式
        $(".menu ul li").removeClass("active");
        $(this).addClass("active");
        //显示对应页面
        $(".accountMessage").css("display", "none");
        $(".articleList").css("display", "none");
        $(".resList").css("display", "none");
        $(".changePassword").css("display", "inline");
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

    getResByAuthor();

    /*修改密码 */
    changePassword();
    /*修改个人信息*/
    changeAccount();

});
/*获取用户信息*/
function getAccount() {
    //获取用户信息
    $.ajax({
        url: "/user/info",  //请求地址
        dataType: "json",  //数据格式
        type: "GET",  //请求方式
        async: false,
        success: function (data) {
            //判断数据库中可以为空的字段是否有值
            if (data.data.sex == null) {
                data.data.sex = "暂未选择";
            }
            if (data.data.phone == null) {
                data.data.phone = "暂未填写";
            }
            if (data.data.signature == null) {
                data.data.signature = "暂无";
            }
            if (data.data.address == null) {
                data.data.address = "暂无";
            }
            //打印个人信息
            var logoname = $(".home .logo-name");
            logoname.text(data.data.username);

            if (!data.data.photo == "") {
                $("#accountLogo").attr('src', data.data.photo);
            } else {
                $("#accountLogo").attr('src', 'https://wyx-1303917755.cos.ap-beijing.myqcloud.com/img/2022/5/18/2022518f0f23093-5b30-4c45-8e54-cdd71683020a.png');

            }
            var html = "";
            html += "<li>\n" +
                "                        <span class=\"account_name\">昵&emsp;&emsp;称:</span>\n" +
                "                        <ss>" + data.data.username + "</ss>\n" +
                "                    </li>\n" +
                "                    <li>\n" +
                "                        <span class=\"account_name\">邮&emsp;&emsp;箱:</span>\n" +
                "                        <ss>" + data.data.email + "</ss>\n" +
                "                    </li>\n" +
                "                    <li>\n" +
                "                        <span class=\"account_name\">性&emsp;&emsp;别:</span>\n" +
                "                        <ss>" + data.data.sex + "</ss>\n" +
                "                    </li>\n" +
                "                    <li>\n" +
                "                        <span class=\"account_name\">个人简介:</span>\n" +
                "                        <ss>" + data.data.signature + "</ss>\n" +
                "                    </li>\n" +
                "                    <li>\n" +
                "                        <span class=\"account_name\">注册时间:</span>\n" +
                "                        <ss>" + data.data.time + "</ss>\n" +
                "                    </li>\n" +
                "                    <li>\n" +
                "                        <span class=\"account_name\">积&emsp;&emsp;分:</span>\n" +
                "                        <ss>" + data.data.points + "</ss>\n" +
                "                    </li>\n" +
                "                    <li>\n" +
                "                        <span class=\"account_name\">电&emsp;&emsp;话:</span>\n" +
                "                        <ss>" + data.data.phone + "</ss>\n" +
                "                    </li>\n" +
                "                    <li>\n" +
                "                        <span class=\"account_name\">现&nbsp;&nbsp;居&nbsp;&nbsp;地:</span>\n" +
                "                        <ss>" + data.data.address + "</ss>\n" +
                "                    </li>";
            $(".home .content .accountMessage ul").html(html);
        },
        error: function (data) {
            layer.msg("服务异常，请联系管理员");
        }
    })
}

//修改密码函数
function changePassword() {
    //点击确认修改按钮
    $("#changePasswordBtn").click(function () {
        //原密码
        var oldPassword = $("#oldPassword").val();
        //新密码
        var newPassword = $("#newPassword").val();
        //确认密码
        var checkPassword = $("#checkPassword").val();

        //判断原密码
        if (oldPassword == "") {
            layer.msg("请输入原密码");
        } else if (newPassword == "") {
            layer.msg("请输入新密码");
        } else if (checkPassword == "") {
            layer.msg("请重新输入确认密码");
        } else if (newPassword == checkPassword) {
            //使用Ajax提交修改
            $.ajax({
                url: "/user/update",  //请求地址
                dataType: "json",  //数据格式
                type: "PUT",  //请求方式
                async: false,
                data: {"password": newPassword},
                success: function (data) {
                    if (data.flag) {//删除成功
                        layer.msg("密码修改成功,请重新登录。。。");
                        window.setTimeout(function () {
                            location.href = "/user/login";
                        }, 2000);
                    } else {
                        layer.msg(data.msg)
                    }
                },
                error: function (data) {
                    layer.msg("服务异常，请联系管理员");
                }

            })
        } else {
            //两次输入的密码不同
            layer.msg("两次输入的密码不一致");
        }
    })
}


//修改个人信息函数
function changeAccount() {
    layui.use('upload', function () {
        var upload = layui.upload;

        //执行实例
        var uploadInst = upload.render({
            elem: '#fileBtn', //绑定元素
            url: '/upload/', //上传接口
            accept: "images",
            done: function (res) {
                //上传完毕回调
                $("#accountHead").attr('src', res.data);
            },
            error: function () {
                //请求异常回调
            }
        });
    });
    $(".uploadChangeAccount").click(function () {
        var photo = $("#accountHead").attr('src');
        var sex = $('input[name="sex"]:checked').val();
        var signature = $("textarea[name='signature']").val();
        var phone = $("input[name='phone']").val();
        var address = $("#city").val();
        if (photo == "") {
            layer.msg("请选择头像");
        } else if (sex == "") {
            layer.msg("请选择性别")
        } else if (signature == "") {
            layer.msg("请填写简介")
        } else if (phone == "") {
            layer.msg("请填写联系方式")
        } else if (address == "") {
            layer.msg("请填写地址")
        } else {
            //使用Ajax提交修改
            $.ajax({
                url: "/user/update",  //请求地址
                dataType: "json",  //数据格式
                type: "PUT",  //请求方式
                async: false,
                data: {"photo": photo, "sex": sex, "signature": signature, "phone": phone, "address": address},
                success: function (data) {
                    console.log(data);
                    if (data.flag) {
                        layer.msg(data.msg);
                    }else {
                        layer.msg(data.msg);
                    }
                },
                error: function (data) {
                    layer.msg("服务异常，请联系管理员");
                }

            })
        }

    });
}

//查询作者的文章，按照时间排序
function getArticlesByAuthorRankByTime() {
    var userId;
    //1、获取session中的用户信息
    $.ajax({
        url: "/user/check",  //请求地址
        dataType: "json",  //数据格式
        type: "GET",  //请求方式
        async: false,
        success: function (data) {
            userId = data.data.id;
        },
        error: function (data) {
            layer.msg("服务异常，请联系管理员");
        }
    });
    //查询文章
    $.ajax({
        url: "/article/user/" + userId + "/1/10/time",  //请求地址
        dataType: "json",  //数据格式
        type: "GET",  //请求方式
        async: false,
        success: function (data) {
            console.log(data);
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
                    "                                <a href=\"" + articleUrl + "\" target='_blank'>浏览</a>\n" +
                    "                                <a onclick=\"deleteArticleById(" + data.data.records[i].id + ")\">删除</a>\n" +
                    "                                <a href=\"" + articleEditUrl + "\" target='_blank'>编辑</a>\n" +
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
    var userId;
    //1、获取session中的用户信息
    $.ajax({
        url: "/user/check",  //请求地址
        dataType: "json",  //数据格式
        type: "GET",  //请求方式
        async: false,
        success: function (data) {
            userId = data.data.id;
        },
        error: function (data) {
            layer.msg("服务异常，请联系管理员");
        }
    });
    //查询文章
    $.ajax({
        url: "/article/user/" + userId +"/1/10/readNum",  //请求地址
        dataType: "json",  //数据格式
        type: "GET",  //请求方式
        async: false,
        success: function (data) {
            console.log(data);
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
                    "                                <a href=\"" + articleUrl + "\" target='_blank'>浏览</a>\n" +
                    "                                <a onclick=\"deleteArticleById(" + data.data.records[i].id + ")\">删除</a>\n" +
                    "                                <a href=\"" + articleEditUrl + "\" target='_blank'>编辑</a>\n" +
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
    });

}

//删除指定id的文章
function deleteArticleById(id) {
    layer.confirm("删除后文章不可恢复，确认删除吗?", {icon: 3, title: '提示'}, function () {
        $.ajax({
            url: "/article/del/" + id,//请求地址
            dataType: "json",//数据格式
            type: "DELETE",//请求方式
            async: false,//是否异步请求
            success: function (data) {   //如何删除成功
                if (data.flag) {//删除成功
                    layer.msg(data.msg);
                    //重新获取文章
                    getArticlesByAuthorRankByTime();
                } else {
                    layer.msg(data.msg);
                }
            },
            error: function (data) {
                layer.msg("服务器异常，请联系管理员!");
            }
        });
        layer.close(index);
    });
}
//删除指定id的资源
function deleteResById(id) {
    layer.confirm("资源后文章不可恢复，确认删除吗?", {icon: 3, title: '提示'}, function () {
        $.ajax({
            url: "/resource/del",//请求地址
            dataType: "json",//数据格式
            type: "DELETE",//请求方式
            async: false,//是否异步请求
            data:{
                "id":id
            },
            success: function (data) {   //如何删除成功
                if (data.flag) {//删除成功
                    layer.msg(data.msg);
                    //重新获取文章
                    getResByAuthor();
                } else {
                    layer.msg(data.msg);
                }
            },
            error: function (data) {
                layer.msg("服务器异常，请联系管理员!");
            }
        });
        layer.close(index);
    });
}
//查询作者的资源
function getResByAuthor() {
    //查询文章
    $.ajax({
        url: "/resource/1/10/",  //请求地址
        dataType: "json",  //数据格式
        type: "GET",  //请求方式
        async: false,
        success: function (data) {
            console.log(data);
            var html = "";
            for (var i = 0; i < data.data.records.length; i++) {
                //定义资源链接
                var resUrl = "/resource/" + data.data.records[i].id;
                html += " <li>\n" +
                    "                    <div class=\"layui-panel article\">\n" +
                    "                        <div class=\"article-left layui-col-md9\">\n" +
                    "                            <div class=\"article-title layui-col-md12\">\n" +
                    "                                <a href=\"" + resUrl + "\" target='_blank'>" + data.data.records[i].title + "</a>\n" +
                    "                            </div>\n" +
                    "                            <div class=\"article-content\">\n" +
                    "                                简介：\n" +
                    "                                <span>" + data.data.records[i].content + "</span>\n" +
                    "                            </div>\n" +
                    "                            \n" +
                    "                        </div>\n" +
                    "                        <div class=\"article-right layui-col-md3\">\n" +
                    "                            <div class=\"article-time layui-col-md12\">" + data.data.records[i].time + "</div>\n" +
                    "                            <div class=\"article-operation layui-col-md12\">\n" +
                    "                                <a href=\"" + resUrl + "\" target='_blank'>浏览</a>\n" +
                    "                                <a onclick=\"deleteResById(" + data.data.records[i].id + ")\">删除</a>\n" +
                    "                              \n" +
                    "                            </div>\n" +
                    "                        </div>\n" +
                    "                    </div>\n" +
                    "                </li>";
            }
            $("#myRes").html(html);
            if (data.data.records.length > 0) {
                $(".el-empty").hide();
            } else {
                $(".el-empty").show();
            }
        },
        error: function (data) {
            layer.msg("服务异常，请联系管理员");
        }
    })

}