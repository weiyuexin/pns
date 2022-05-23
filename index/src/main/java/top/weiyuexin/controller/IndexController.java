package top.weiyuexin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    /**
     * 首页
     * @return
     */
    @GetMapping("/")
    public String index(){
        return "index";
    }

    /**
     * 资源列表
     * @return
     */
    @GetMapping("/resources")
    public String resourcesPage(){
        return "resource/resources";
    }

    /**
     * 热门排行列表
     * @return
     */
    @GetMapping("/rank")
    public String rankPage(){
        return "rank";
    }


}
