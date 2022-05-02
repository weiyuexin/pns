package top.weiyuexin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ResourceController {

    /**
     * 发布资源页面
     * @return
     */
    @GetMapping("resource/add")
    public String addResourcePage(){
        return "resource/addres";
    }
}
