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

    //首先需要根据作者的id查询到作者的信息
    var authorName = $(".authorName").html();//用户名
    var authorLogo = "";//头像
    var signature = "";


});
