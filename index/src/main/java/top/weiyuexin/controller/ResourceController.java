package top.weiyuexin.controller;

import cn.hutool.core.date.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.weiyuexin.entity.Resource;
import top.weiyuexin.entity.User;
import top.weiyuexin.entity.vo.R;
import top.weiyuexin.service.ResourceServer;
import top.weiyuexin.service.UserService;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/resource")
public class ResourceController {

    @Autowired
    private ResourceServer resourceServer;
    @Autowired
    private UserService userService;

    /**
     * 发布资源页面
     * @return
     */
    @GetMapping("/add")
    public String addResourcePage(){
        return "resource/addres";
    }

    @PostMapping("/add.do")
    @ResponseBody
    public R addResources(Resource resource, HttpSession session) throws ParseException {
        R r = new R();
        User user = (User) session.getAttribute("user");
        System.out.println(resource.toString());
        //判断用户是否登录
        if(user!=null){
            //设置作者的id
            resource.setAuthorId(user.getId());
            //设置发布时间
            java.text.SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd");
            String s= DateUtil.now();
            Date date =  formatter.parse(s);
            resource.setTime(date);
            r.setFlag(resourceServer.save(resource));
            r.setMsg("文章发表成功!");
        }else{
            r.setFlag(false);
            r.setMsg("当前未登录，请先前往登录!");
        }

        return r;
    }

}
