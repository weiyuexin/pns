package top.weiyuexin.controller;

import cn.hutool.core.date.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.weiyuexin.entity.Article;
import top.weiyuexin.entity.User;
import top.weiyuexin.entity.vo.R;
import top.weiyuexin.service.ArticleService;
import top.weiyuexin.service.UserService;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class ArticleController {
    /**
     * 注入服务
     */
    @Autowired
    private ArticleService articleService;
    @Autowired
    private UserService userService;

    /**
     * 写文章页面
     * @return
     */
    @RequestMapping("/article/write")
    public String writePage(){
        return "article/write";
    }

    /**
     * 保存文章接口
     * @param article
     * @return
     * @throws ParseException
     */
    @PostMapping("/article/write.do")
    @ResponseBody
    public Object write(Article article, HttpSession session) throws ParseException {
        R r = new R();
        User user = (User) session.getAttribute("user");
        //判断用户是否登录
        if(user!=null){
            //设置作者的id
            article.setAuthorId(user.getId());
            //设置发布时间
            java.text.SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd");
            String s= DateUtil.now();
            Date date =  formatter.parse(s);
            article.setTime(date);
            r.setFlag(articleService.save(article));
            r.setMsg("文章发表成功!");
        }else{
            r.setFlag(false);
            r.setMsg("当前未登录，请先前往登录!");
        }

        return r;
    }

    @RequestMapping("/article")
    public String articlePage(){
        return "article/article";
    }
}
