package top.weiyuexin.controller;

import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import top.weiyuexin.entity.User;
import top.weiyuexin.entity.vo.R;
import top.weiyuexin.mapper.UserMapper;
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
    @Autowired
    private UserMapper userMapper;

    /**
     * 登录页面
     * @return
     */
    @GetMapping("/login")
    public String loginPage(){
        return "user/login";
    }

    /**
     * 执行登录操作
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
        password = DigestUtil.md5Hex(password);
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.eq(User::getUsername,username).eq(User::getPassword,password);
        User user = userMapper.selectOne(lqw);
        //判断时候查到用户
        if(user!=null){ //查询到了用户
            //更新状态码
            r.setFlag(true);
            r.setMsg("登录成功");
            //登录成功，讲用户信息保存到session
            session.setAttribute("user",user);
        }else {  //没有查询到用户
            r.setFlag(false);
            r.setMsg("账号或密码错误，请重试!");
        }
        return r;
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
