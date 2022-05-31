package top.weiyuexin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.weiyuexin.entity.vo.R;
import top.weiyuexin.service.*;
import top.weiyuexin.utils.IpdbUtil;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


/**
 * @author wyx
 */
@Controller
public class IndexController {
    @Autowired
    private UserService userService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ResourceServer resourceServer;
    @Autowired
    private ArticleCommentService articleCommentService;
    @Autowired
    private ResourceCommentService resourceCommentService;
    @Autowired
    private ImageService imageService;

    @GetMapping("/count")
    @ResponseBody
    public R getCount() {
        R r = new R();
        Integer userCount = Math.toIntExact(userService.count());
        Integer ArticleCount = Math.toIntExact(articleService.count());
        Integer ResourceCount = Math.toIntExact(resourceServer.count());
        Integer ArticleCommentCount = Math.toIntExact(articleCommentService.count());
        Integer ResourceCommentCount = Math.toIntExact(resourceCommentService.count());
        Integer ImageCount = Math.toIntExact(imageService.count());

        Map<String,Integer> map = new HashMap<>();
        map.put("UserCount",userCount);
        map.put("ArticleCount",ArticleCount);
        map.put("ResourceCount",ResourceCount);
        map.put("ArticleCommentCount",ArticleCommentCount);
        map.put("ResourceCommentCount",ResourceCommentCount);
        map.put("ImageCount",ImageCount);

        r.setFlag(true);
        r.setData(map);
        r.setMsg("ok");

        return r;
    }

}
