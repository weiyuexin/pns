package top.weiyuexin.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {
    @RequestMapping("/springboot/say")
    @ResponseBody
    public String say(){
        return "Hello,SpringBoot!";
    }
}
