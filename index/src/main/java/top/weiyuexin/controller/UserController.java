package top.weiyuexin.controller;

import cn.hutool.crypto.digest.DigestUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import top.weiyuexin.entity.User;
import top.weiyuexin.entity.vo.R;
import top.weiyuexin.service.UserService;

import javax.servlet.http.HttpSession;

/**
 * @author wyx
 */
@Controller
@RequestMapping("/user")
public class UserController {
    //注入服务
    @Autowired
    private UserService userService;

    /**
     * 登录页面
     * @return
     */
    @GetMapping("/login")
    public String loginPage(){
        return "user/login";
    }



    /**
     * 注册页面
     * @return
     */
    @GetMapping("/register")
    public String registerPage(){
        return "user/register";
    }

    /**
     * 执行注册操作
     * @param email
     * @param username
     * @param password
     * @return
     */
    @PostMapping("/register.do/{email}/{username}/{password}")
    @ResponseBody
    public Object register(@PathVariable("email") String email,
                           @PathVariable("username") String username,
                           @PathVariable("password") String password){
        R r = new R();
        //对密码进行md5加密处理
        password =DigestUtil.md5Hex(password);
        //保存到数据库
        User user = new User();
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(password);
        if(userService.save(user)){
            r.setFlag(true);
            r.setMsg("注册成功!");
        }else {
            r.setFlag(true);
            r.setMsg("注册失败,请重试!");
        }
        return r;
    }

    /**
     * 检查用户登录状态
     * @param session
     * @return
     */
    @GetMapping("/check")
    @ResponseBody
    public Object checkIsNotLogin(HttpSession session){
        R r = new R();
        //获取session中保存的用户信息
        User user = (User) session.getAttribute("user");
        if (user == null) {
            //未登录
            r.setFlag(false);
            r.setMsg("您还没有登录，请前往登录");
        } else {
            //已登录
            r.setFlag(true);
            r.setMsg("您处于登录状态");
            r.setData(user);
        }

        return r;
    }

    /**
     * 退出登录
     * @param session
     * @return
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        //销毁session
        session.invalidate();
        //重新加载首页
        return "redirect:/";
    }


}
