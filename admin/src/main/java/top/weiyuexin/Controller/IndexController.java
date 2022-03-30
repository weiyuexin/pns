package top.weiyuexin.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class IndexController {
    @RequestMapping("/springboot/say")
    @ResponseBody
    public String say(){
        return "Hello,SpringBoot!";
    }
    @RequestMapping("/mapvalue")
    @ResponseBody
    public Map<String,Object> mapvalue(){
        Map<String,Object> map = new HashMap<String ,Object>();
        map.put("message","Spring Boot");
        return map;
    }
}
