package top.weiyuexin.controller;

import cn.hutool.core.date.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import top.weiyuexin.entity.Resource;
import top.weiyuexin.entity.User;
import top.weiyuexin.entity.vo.R;
import top.weiyuexin.service.ResourceServer;
import top.weiyuexin.service.UserService;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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

    /**
     * 保存资源接口
     * @param resource
     * @param session
     * @return
     * @throws ParseException
     */
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

    /**
     * 根据id查询显示资源
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ModelAndView getResById(@PathVariable("id") Integer id){
        ModelAndView modelAndView = new ModelAndView();
        Resource resource = resourceServer.getById(id);
        //根据作者id查询作者信息
        User user = userService.getById(resource.getAuthorId());
        //格式化时间
        SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = time.format(resource.getTime());

        modelAndView.addObject("res",resource);
        modelAndView.addObject("author",user);
        modelAndView.addObject("date",date);
        modelAndView.setViewName("resource/resource");
        return modelAndView;
    }

    @GetMapping("/topResource/{num}")
    @ResponseBody
    public R getTopResource(@PathVariable("num") Integer num){
        R r = new R();
        List<Resource> resources = resourceServer.getTopResource(num);
        if(resources!=null){
            r.setFlag(true);
            r.setData(resources);
            r.setMsg("资源查询成功!");
        }else {
            r.setFlag(false);
            r.setMsg("资源查询失败，请稍后再试!");
        }
        return r;
    }

}
