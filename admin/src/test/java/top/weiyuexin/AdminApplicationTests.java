package top.weiyuexin;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.weiyuexin.service.ArticleCommentService;
import top.weiyuexin.service.ArticleService;
import top.weiyuexin.service.ResourceServer;
import top.weiyuexin.service.UserService;

@SpringBootTest
class AdminApplicationTests {

    @Autowired
    private UserService userService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ResourceServer resourceServer;
    @Autowired
    private ArticleCommentService articleCommentService;
    @Test
    void contextLoads() {
        System.out.println(userService.getById(44).toString());
    }
    @Test
    void articleTset(){
        System.out.println(articleService.getById(84).toString());
    }
    @Test
    void resourceTset(){
        System.out.println(resourceServer.getById(108).toString());
    }
    @Test
    void articleCommentTset(){
        System.out.println(articleCommentService.getById(40).toString());
    }
}
