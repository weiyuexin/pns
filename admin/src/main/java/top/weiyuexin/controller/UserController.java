package top.weiyuexin.controller;

import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import top.weiyuexin.entity.User;
import top.weiyuexin.entity.vo.R;
import top.weiyuexin.entity.vo.W;
import top.weiyuexin.mapper.UserMapper;
import top.weiyuexin.service.UserService;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    //注入服务
    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;



    /**
     * 注册
     * @param user
     * @param code
     * @param session
     * @return
     */
    @PostMapping ("/register.do/{code}")
    @ResponseBody
    public Object register(User user,
                           @PathVariable("code") Integer code,
                           HttpSession session){
        R r = new R();
        //对密码进行md5加密处理
        user.setPassword(DigestUtil.md5Hex(user.getPassword()));
        //验证验证码是否输入正确
        System.out.println(code+" " +session.getAttribute("code"));
        if(!code.equals(session.getAttribute("code"))){
            r.setMsg("验证码错误，请重试!");
        }else{
            //保存到数据库
            user.setEmail(user.getEmail());
            user.setUsername(user.getUsername());
            user.setPassword(user.getPassword());
            if(userService.save(user)){
                r.setFlag(true);
                r.setMsg("注册成功!");
            }else {
                r.setFlag(true);
                r.setMsg("注册失败,请重试!");
            }
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
     * 根据id查询文章作者信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ModelAndView getById(@PathVariable("id") Integer id){
        ModelAndView modelAndView = new ModelAndView();
        //查询用户
        User user = userService.getById(id);

        //格式化时间
        SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = time.format(user.getTime());
        //设置试图
        modelAndView.setViewName("user/author");
        //设置内容
        modelAndView.addObject("user",user);
        modelAndView.addObject("date",date);

        return modelAndView;
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


    /**
     * 获取用户积分排行
     * @param num
     * @return
     */
    @GetMapping("/topUser/{num}")
    @ResponseBody
    public R getTopUser(@PathVariable("num") Integer num){
        R r = new R();
        List<User> users = userService.getTopUser(num);
        if(users!=null){
            r.setFlag(true);
            r.setData(users);
            r.setMsg("查询成功");
        }else {
            r.setFlag(false);
            r.setMsg("查询失败，请稍后重试!");
        }

        return r;
    }


    /**
     * 账号管理页面
     * @param t
     * @return
     */
    @GetMapping("/account/{t}")
    public String accountPage(@PathVariable("t") String t){
        if(t.equals("account")){
            return "user/account";
        }else if(t.equals("article")){
            return "article/myarticle";
        }else {
            return "resource/myres";
        }
    }

    /**
     * 查看session中的用户登录信息
     * @param session
     * @return
     */
    @GetMapping("/info")
    @ResponseBody
    public R getUserInfoFromSession(HttpSession session){
        R r = new R();
        User user = (User) session.getAttribute("user");
        if(user!=null){
            r.setFlag(true);
            r.setData(user);
            r.setMsg("已登录");
        }else {
            r.setFlag(false);
            r.setMsg("还没有登录，请前往登录!");
        }
        return r;
    }

    /**
     * 修改用户信息接口
     * @param user
     * @param session
     * @return
     */
    @PutMapping("/update")
    @ResponseBody
    public R updata(User user,HttpSession session){
        R r = new R();
        User user1 =(User) session.getAttribute("user");
        if(session.getAttribute("user")!=null){
            user.setId(user1.getId());
            if(user.getPassword()!=null){
                //加密
                user.setPassword(DigestUtil.md5Hex(user.getPassword()));
            }
            r.setFlag(userService.updateById(user));
            if(r.getFlag()){
                r.setMsg("修改成功!");
                //修改成功，更新session中的信息
                user = userService.getById(user.getId());
                session.setAttribute("user",user);
            }else {
                r.setMsg("修改失败，请稍后重试!");
            }
        }else {
            r.setFlag(false);
            r.setMsg("请登录后重试！");
        }
        return r;
    }



    /**
     * 分页查询
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("/users")
    @ResponseBody
    public W getPage(@RequestParam("page") Integer page,
                     @RequestParam("limit") Integer limit){

        IPage<User> Ipage = userService.getPage(page,limit);
        //如果当前页码值大于当前页码值，那么重新执行查询操作，使用最大页码值作为当前页码值
        if(page>Ipage.getPages()){
            Ipage = userService.getPage(page,limit);
        }
        List<User> users = Ipage.getRecords();

        Ipage.setRecords(users);
        return new W(0,(int)Ipage.getTotal(),Ipage.getRecords());
    }

    /**
     * 删除用户接口
     * @param id
     * @param session
     * @return
     */
    @DeleteMapping("/del/{id}")
    @ResponseBody
    public Object delete(@PathVariable("id") Integer id, HttpSession session){
        R r = new R();
        //判断用户是否登录
        if(session.getAttribute("user")!=null){
            r.setFlag(userService.removeById(id));
            r.setMsg("用户删除成功");
        }else {
            r.setData("当前登录状态丢失，请重新登录后再试!");
        }
        return r;
    }


}
