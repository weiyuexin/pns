package top.weiyuexin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    /**
     * 首页
     * @return
     */
    @RequestMapping("/")
    public String index(){
        return "index";
    }

    /**
     * 资源列表
     * @return
     */
    @RequestMapping("/resources")
    public String resourcesPage(){
        return "resource/resources";
    }
}
