package top.weiyuexin.controller;

import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import top.weiyuexin.entity.Admin;
import top.weiyuexin.entity.Article;
import top.weiyuexin.entity.LoginLog;
import top.weiyuexin.entity.User;
import top.weiyuexin.entity.vo.R;
import top.weiyuexin.entity.vo.W;
import top.weiyuexin.mapper.UserMapper;
import top.weiyuexin.service.AdminService;
import top.weiyuexin.service.LoginLogService;
import top.weiyuexin.service.UserService;
import top.weiyuexin.utils.OutHtml;

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
    @Autowired
    private AdminService adminService;
    @Autowired
    private LoginLogService loginLogService;

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
        Admin admin = (Admin) session.getAttribute("user");
        if (admin == null) {
            //未登录
            r.setFlag(false);
            r.setMsg("您还没有登录，请前往登录");
        } else {
            //已登录
            r.setFlag(true);
            r.setMsg("您处于登录状态");
            r.setData(admin);
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
     * 修改管理员信息接口
     * @param admin
     * @param session
     * @return
     */
    @PutMapping("/update")
    @ResponseBody
    public R updata(Admin admin,HttpSession session){
        R r = new R();
        Admin admin1 =(Admin) session.getAttribute("user");
        if(session.getAttribute("user")!=null){
            admin.setId(admin1.getId());
           /* if(admin.getPassword()!=null){
                //加密
                admin.setPassword(DigestUtil.md5Hex(user.getPassword()));
            }*/
            r.setFlag(adminService.updateById(admin));
            if(r.getFlag()){
                r.setMsg("修改成功!");
                //修改成功，更新session中的信息
                admin = adminService.getById(admin.getId());
                session.setAttribute("user",admin);
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
     * 分页条件查询
     * @param page
     * @param limit
     * @param user
     * @return
     */
    @GetMapping("/users")
    @ResponseBody
    public W getPage(@RequestParam("page") Integer page,
                     @RequestParam("limit") Integer limit,
                     User user){

        System.out.println(user.toString());
        IPage<User> Ipage = userService.getPage(page,limit,user);
        //如果当前页码值大于当前页码值，那么重新执行查询操作，使用最大页码值作为当前页码值
        if(page>Ipage.getPages()){
            Ipage = userService.getPage(page,limit,user);
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

    /**
     * 分页查询登录日志
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("/logs")
    @ResponseBody
    public W getLoginLogByPage(@RequestParam("page") Integer page,
                     @RequestParam("limit") Integer limit, LoginLog loginLog){
        if(!loginLog.getUsername().equals("")){
            User user1 = userService.getByUserName(loginLog.getUsername());
            if(user1!=null){
                loginLog.setUserId(user1.getId());
            }else {
                loginLog.setUserId(-1);
            }
        }
        IPage<LoginLog> Ipage = loginLogService.getPage(page,limit,loginLog);
        //如果当前页码值大于当前页码值，那么重新执行查询操作，使用最大页码值作为当前页码值
        if(page>Ipage.getPages()){
            Ipage = loginLogService.getPage(page,limit,loginLog);
        }
        List<LoginLog> loginLogs = Ipage.getRecords();
        for(int i=0;i<loginLogs.size();i++){
            //根据作者id查询作者
            User user = userService.getById(loginLogs.get(i).getUserId());
            if(user!=null){
                System.out.println(user.getUsername());
                loginLogs.get(i).setUsername(user.getUsername());
            }
            //修改时间格式
            //格式化时间
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = sdf.format(loginLogs.get(i).getTime());
            loginLogs.get(i).setDate(date);
        }
        Ipage.setRecords(loginLogs);

        Ipage.setRecords(loginLogs);
        return new W(0,(int)Ipage.getTotal(),Ipage.getRecords());
    }

    /**
     * 删除登录日志接口
     * @param id
     * @param session
     * @return
     */
    @DeleteMapping("/log/del/{id}")
    @ResponseBody
    public R deleteLoginLog(@PathVariable("id") Integer id, HttpSession session){
        R r = new R();
        //判断用户是否登录
        if(session.getAttribute("user")!=null){
            r.setFlag(loginLogService.removeById(id));
            r.setMsg("日志删除成功");
        }else {
            r.setData("当前登录状态丢失，请重新登录后再试!");
        }
        return r;
    }

}
