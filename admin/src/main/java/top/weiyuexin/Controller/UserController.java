package top.weiyuexin.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.weiyuexin.Entity.User;
import top.weiyuexin.Service.UserService;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/user")
    @ResponseBody
    public Object queryUserById(Integer id){

        User user = userService.queryUserById(id);

        return user;
    }
}
