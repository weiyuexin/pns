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
});

$(function () {
    <!-- 实例化编辑器 -->
    var ue = UE.getEditor('container', {
        initialFrameWidth: 1300,
        initialFrameHeight: 550,
        maximumWords: 100000
    });
    //上传图片附件
    UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;
    UE.Editor.prototype.getActionUrl = function(action) {
        if (action == 'uploadimage') {
            return '/uploadimage';//上传路径
        } else if (action == 'uploadfile') {
            return '${pageContext.request.contextPath}/upload/uploadfile';//上传路径
        } else {
            return this._bkGetActionUrl.call(this, action);
        }
    }
    ue.ready(function () {
        //保存文章
        $(".btn-publish").on('click', function () {
            //获取文章id
            var id = $("#id").val();
            //获取文章正文
            var content = ue.getContent();
            //获取文章标题
            var title = $(".title").val();
            console.log("标题"+title);
            //获取文章类型
            var type = $("#type").val();
            console.log(type)
            if(title==""){
                layer.msg("请输入文章标题");
            }else if(content==""){
                layer.msg("请输入文章正文")
            }else if(type==""){
                layer.msg("请选择文章类型")
            }else {
                layer.confirm('确认发布修改后的文章吗吗?', {icon: 3, title:'提示'}, function(index){
                    //do something
                    /*发送保存文章请求*/
                    $.ajax({
                        url: "/article/edit.do",//请求地址
                        dataType: "json",//数据格式
                        type: "PUT",//请求方式
                        async: false,//是否异步请求
                        data: {
                            "id":id,
                            "title": title,
                            "type": type,
                            "content": content
                        },
                        success: function (data) {   //如何发送成功
                            layer.msg(data.msg);
                        },
                        error: function (data) {
                            layer.msg("服务器异常，请联系管理员!");
                        }
                    });
                    layer.close(index);
                });
            }
        });
    });

})