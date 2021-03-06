package top.weiyuexin.controller;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;
import top.weiyuexin.entity.*;
import top.weiyuexin.entity.vo.R;
import top.weiyuexin.service.ResourceCommentService;
import top.weiyuexin.service.ResourceServer;
import top.weiyuexin.service.UserService;
import top.weiyuexin.utils.IpUtil;
import top.weiyuexin.utils.IpdbUtil;
import top.weiyuexin.utils.getClentIp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/resource")
public class ResourceController {

    @Autowired
    private ResourceServer resourceServer;
    @Autowired
    private UserService userService;
    @Autowired
    private ResourceCommentService resourceCommentService;

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
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

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
            String province = Arrays.toString(new String[]{IpdbUtil.find(getClentIp.getIpAddr(request), "CN")[1]});
            province = province.substring(1,province.length()-1);
            resource.setIpAddr(province);
            r.setFlag(resourceServer.save(resource));
            r.setMsg("文章发表成功!");
            //发表成功，积分加5
            user.setPoints(user.getPoints()+5);
            userService.updateById(user);
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

        //资源浏览量加一
        resource.setReadNum(resource.getReadNum()+1);
        resourceServer.updateById(resource);

        return modelAndView;
    }

    /**
     * 热门资源
     * @param num
     * @return
     */
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

    /**
     * 根据类型查询资源
     * @param type
     * @param currentPage
     * @param pageSize
     * @param resource
     * @return
     */
    @GetMapping("/{type}/{currentPage}/{pageSize}")
    @ResponseBody
    public R getPageByType(@PathVariable("type") String type,
                           @PathVariable("currentPage") Integer currentPage,
                           @PathVariable("pageSize") Integer pageSize,Resource resource){
        IPage<Resource> page = resourceServer.getPageByType(currentPage,pageSize,type,resource);
        if(currentPage>page.getPages()){
            page = resourceServer.getPageByType(currentPage,pageSize,type,resource);
        }
        return new R(true,page);
    }

    /**
     * 评论资源
     * @param resourceComment
     * @param session
     * @return
     * @throws ParseException
     */
    @PostMapping("/comment/add")
    @ResponseBody
    public R addComment(ResourceComment resourceComment, HttpSession session) throws ParseException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        R r = new R();
        User user = (User) session.getAttribute("user");
        if(user!=null){
            resourceComment.setAuthorId(user.getId());
            //设置发布时间
            java.text.SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
            String s= DateUtil.now();
            Date date =  formatter.parse(s);
            resourceComment.setTime(date);
            String province = Arrays.toString(new String[]{IpdbUtil.find(getClentIp.getIpAddr(request), "CN")[1]});
            province = province.substring(1,province.length()-1);
            resourceComment.setIpAddr(province);
            r.setFlag(resourceCommentService.save(resourceComment));
            //设置评论数加一
            Resource resource = resourceServer.getById(resourceComment.getResourceId());
            resource.setCommentNum(resource.getCommentNum()+1);
            resourceServer.updateById(resource);

            //发表成功，积分加2
            user.setPoints(user.getPoints()+2);
            userService.updateById(user);

            if(r.getFlag()){
                r.setMsg("评论成功!");
            }else {
                r.setMsg("评论失败，请稍后重试!");
            }
        }else {
            r.setFlag(false);
            r.setMsg("请登录后再来评论");
        }
        return r;
    }

    /**
     * 根据资源id查询评论
     * @param resourceId
     * @return
     */
    @GetMapping("/comment/{resourceId}")
    @ResponseBody
    public R getComments(@PathVariable("resourceId") Integer resourceId){
        List<ResourceComment> resourceComments = resourceCommentService.getResourceCommentsByResourceId(resourceId);
        List<User> authors = new ArrayList<>();
        List<String> dates = new ArrayList<>();
        for(int i=0;i<resourceComments.size();i++){
            authors.add(userService.getById(resourceComments.get(i).getAuthorId()));
            //格式化时间
            SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dates.add((String)time.format(resourceComments.get(i).getTime()));
        }

        Map<String,Object> map = new HashMap<>();
        map.put("comments",resourceComments);
        map.put("authors",authors);
        map.put("dates",dates);
        return new R(true,map);
    }

    /**
     * 点赞资源评论
     * @param resourceComment
     * @param session
     * @return
     */
    @PostMapping("/comment/star")
    @ResponseBody
    public R starComment(ResourceComment resourceComment,HttpSession session){
        R r = new R();
        User user = (User)session.getAttribute("user");
        if(user!=null){
            //点赞数加一
            resourceComment=resourceCommentService.getById(resourceComment.getId());
            resourceComment.setStar(resourceComment.getStar()+1);
            //保存
            r.setFlag(resourceCommentService.updateById(resourceComment));
            r.setMsg("点赞成功!");
            //点赞成功，积分加1
            user.setPoints(user.getPoints()+1);
            userService.updateById(user);
        }else {
            r.setFlag(false);
            r.setMsg("请登录后再来点赞!");
        }
        return r;
    }

    /**
     * 根据资源作者查询资源
     * @param currentPage
     * @param pageSize
     * @param resource
     * @return
     */
    @GetMapping("/{currentPage}/{pageSize}")
    @ResponseBody
    public R getPageByAuthorId(@PathVariable("currentPage") Integer currentPage,
                               @PathVariable("pageSize") Integer pageSize,
                               Resource resource,HttpSession session){
        R r = new R();
        User user = (User) session.getAttribute("user");
        if(user!=null){
            IPage<Resource> iPage = resourceServer.getPageByAuthorId(currentPage,pageSize,user.getId(),resource);
            if(iPage.getPages()>0){
                r.setFlag(true);
                r.setData(iPage);
                r.setMsg("查询成功");
            }else {
                r.setFlag(false);
                r.setMsg("查询失败");
            }
        }else {
            r.setFlag(false);
            r.setMsg("没有登陆，请先前往登录");
        }

        return r;
    }

    /**
     * 删除资源
     * @param resource
     * @param session
     * @return
     */
    @DeleteMapping("/del")
    @ResponseBody
    public R deleteResource(Resource resource,HttpSession session){
        R r = new R();
        User user = (User) session.getAttribute("user");

        if(user!=null){
            r.setFlag(resourceServer.removeById(resource));
            if(r.getFlag()){
                r.setMsg("删除成功!");
            }else {
                r.setMsg("删除失败！");
            }
        }else {
            r.setFlag(false);
            r.setMsg("请重新登录后再试");
        }
        return r;
    }
}
