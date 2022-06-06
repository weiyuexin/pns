package top.weiyuexin.controller;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import top.weiyuexin.entity.ArticleComment;
import top.weiyuexin.entity.Resource;
import top.weiyuexin.entity.ResourceComment;
import top.weiyuexin.entity.User;
import top.weiyuexin.entity.vo.R;
import top.weiyuexin.entity.vo.W;
import top.weiyuexin.service.ResourceCommentService;
import top.weiyuexin.service.ResourceServer;
import top.weiyuexin.service.UserService;
import top.weiyuexin.utils.OutHtml;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/resource")
public class ResourceController {

    /**
     * 注入服务
     */
    @Autowired
    private ResourceServer resourceServer;
    @Autowired
    private UserService userService;
    @Autowired
    private ResourceCommentService resourceCommentService;

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
            SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd");
            String s= DateUtil.now();
            Date date =  formatter.parse(s);
            resource.setTime(date);
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
     * 分页查询
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("/resources")
    @ResponseBody
    public W getPage(@RequestParam("page") Integer page,
                     @RequestParam("limit") Integer limit,
                     Resource resource){
        if(!resource.getAuthorName().equals("")){
            //查询要查询的作者id
            System.out.println("author:"+resource.getAuthorName());
            User user1 = userService.getByUserName(resource.getAuthorName());
            if(user1!=null){
                resource.setAuthorId(user1.getId());
            }else {
                resource.setAuthorId(-1);
            }
        }
        IPage<Resource> Ipage = resourceServer.getPage(page,limit,resource);
        //如果当前页码值大于当前页码值，那么重新执行查询操作，使用最大页码值作为当前页码值
        if(page>Ipage.getPages()){
            Ipage = resourceServer.getPage(page,limit,resource);
        }
        List<Resource> resources = Ipage.getRecords();
        for(int i=0;i<resources.size();i++){
            //根据作者id查询作者
            User user = userService.getById(resources.get(i).getAuthorId());
            if(user!=null){
                System.out.println(user.getUsername());
                resources.get(i).setAuthorName(user.getUsername());
            }
            //修改时间格式
            //格式化时间
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = sdf.format(resources.get(i).getTime());
            resources.get(i).setDate(date);
        }

        Ipage.setRecords(resources);
        return new W(0,(int)Ipage.getTotal(),Ipage.getRecords());
    }

    /**
     * 删除资源接口
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
            r.setFlag(resourceServer.removeById(id));
            r.setMsg("文章删除成功");
        }else {
            r.setData("当前登录状态丢失，请重新登录后再试!");
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
        R r = new R();
        User user = (User) session.getAttribute("user");
        if(user!=null){
            resourceComment.setAuthorId(user.getId());
            //设置发布时间
            SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
            String s= DateUtil.now();
            Date date =  formatter.parse(s);
            resourceComment.setTime(date);
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

    /**
     * 分页查询资源评论接口
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("/comments")
    @ResponseBody
    public W getPageComment(@RequestParam("page") Integer page,
                            @RequestParam("limit") Integer limit,
                            ResourceComment resourceComment){
        if(!resourceComment.getAuthorName().equals("")){
            //查询要查询的作者id
            System.out.println("author:"+resourceComment.getAuthorName());
            User user1 = userService.getByUserName(resourceComment.getAuthorName());
            if(user1!=null){
                resourceComment.setAuthorId(user1.getId());
            }else {
                resourceComment.setAuthorId(-1);
            }
        }
        IPage<ResourceComment> Ipage = resourceCommentService.getPage(page,limit,resourceComment);
        //如果当前页码值大于当前页码值，那么重新执行查询操作，使用最大页码值作为当前页码值
        if(page>Ipage.getPages()){
            Ipage = resourceCommentService.getPage(page,limit,resourceComment);
        }
        //过滤html标签
        List<ResourceComment> resourceComments = Ipage.getRecords();
        for(int i=0;i<resourceComments.size();i++){

            //根据作者id查询作者
            User user = userService.getById(resourceComments.get(i).getAuthorId());
            if(user!=null){
                System.out.println(user.getUsername());
                resourceComments.get(i).setAuthorName(user.getUsername());
            }
            //修改时间格式
            //格式化时间
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = sdf.format(resourceComments.get(i).getTime());
            resourceComments.get(i).setDate(date);
        }
        Ipage.setRecords(resourceComments);
        return new W(0,(int)Ipage.getTotal(),Ipage.getRecords());
    }

    /**
     * 删除评论
     * @param id
     * @param session
     * @return
     */
    @DeleteMapping("/comment/del/{id}")
    @ResponseBody
    public Object deleteComment(@PathVariable("id") Integer id, HttpSession session){
        R r = new R();
        //判断用户是否登录
        if(session.getAttribute("user")!=null){
            r.setFlag(resourceCommentService.removeById(id));
            r.setMsg("评论删除成功");
        }else {
            r.setData("当前登录状态丢失，请重新登录后再试!");
        }
        return r;
    }
}
