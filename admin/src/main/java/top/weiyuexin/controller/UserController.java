package top.weiyuexin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.weiyuexin.entity.User;
import top.weiyuexin.service.UserService;

@Controller
public class UserController {

    //注入服 务
    @Autowired
    private UserService userService;

    @RequestMapping("/user/{id}")
    @ResponseBody
    public Object queryUserById(@PathVariable("id") Integer id){

        User user = userService.queryUserById(id);

        return user;
    }
}
