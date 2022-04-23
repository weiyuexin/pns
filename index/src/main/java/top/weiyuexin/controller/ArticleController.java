package top.weiyuexin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ArticleController {
    @RequestMapping("/article/write")
    public String writePage(){
        return "article/write";
    }

    @RequestMapping("/article")
    public String articlePage(){
        return "article/article";
    }
}
