package top.weiyuexin.controller;

import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import top.weiyuexin.entity.Admin;
import top.weiyuexin.entity.User;
import top.weiyuexin.entity.vo.R;
import top.weiyuexin.mapper.AdminMapper;
import top.weiyuexin.service.*;
import top.weiyuexin.utils.IpdbUtil;

import javax.servlet.http.HttpSession;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


/**
 * @author wyx
 */
@Controller
public class IndexController {
    @Autowired
    private UserService userService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ResourceServer resourceServer;
    @Autowired
    private ArticleCommentService articleCommentService;
    @Autowired
    private ResourceCommentService resourceCommentService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private AdminMapper  adminMapper;

    @GetMapping("/count")
    @ResponseBody
    public R getCount() {
        R r = new R();
        Integer userCount = Math.toIntExact(userService.count());
        Integer ArticleCount = Math.toIntExact(articleService.count());
        Integer ResourceCount = Math.toIntExact(resourceServer.count());
        Integer ArticleCommentCount = Math.toIntExact(articleCommentService.count());
        Integer ResourceCommentCount = Math.toIntExact(resourceCommentService.count());
        Integer ImageCount = Math.toIntExact(imageService.count());

        Map<String,Integer> map = new HashMap<>();
        map.put("UserCount",userCount);
        map.put("ArticleCount",ArticleCount);
        map.put("ResourceCount",ResourceCount);
        map.put("ArticleCommentCount",ArticleCommentCount);
        map.put("ResourceCommentCount",ResourceCommentCount);
        map.put("ImageCount",ImageCount);

        r.setFlag(true);
        r.setData(map);
        r.setMsg("ok");

        return r;
    }

    @GetMapping("/login")
    public String login(){
        return "user/login";
    }

    /**
     * 执行管理员登录操作
     * @param username
     * @param password
     * @param session
     * @return
     */
    @GetMapping("/login.do/{username}/{password}")
    @ResponseBody
    public Object login(@PathVariable("username") String username,
                        @PathVariable("password") String password,
                        HttpSession session){
        R r = new R();
        //对密码进行md5加密处理
        //password = DigestUtil.md5Hex(password);
        LambdaQueryWrapper<Admin> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Admin::getUsername,username);
        lqw.eq(Admin::getPassword,password);
        Admin admin = adminMapper.selectOne(lqw);
        //判断时候查到用户
        if(admin!=null){ //查询到了用户
            //更新状态码
            r.setFlag(true);
            r.setMsg("登录成功");
            //登录成功，讲用户信息保存到session
            session.setAttribute("user",admin);
        }else {  //没有查询到用户
            r.setFlag(false);
            r.setMsg("账号或密码错误，请重试!");
        }
        return r;
    }

}
