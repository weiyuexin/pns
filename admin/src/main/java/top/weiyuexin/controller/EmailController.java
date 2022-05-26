
package top.weiyuexin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.weiyuexin.entity.vo.R;
import top.weiyuexin.service.EmailService;

import javax.servlet.http.HttpSession;

@Controller
public class EmailController {
    /**
     * 注入邮箱验证码服务
     */
    @Autowired
    private EmailService emailService;

    /**
     * 发送邮箱验证码接口
     * @param email
     * @param session
     * @return
     */
    @PostMapping("/email/send/{email}")
    @ResponseBody
    public Object send(@PathVariable("email") String email, HttpSession session){
        R r = emailService.send(email);
        System.out.println(r.toString());
        //判断验证码是否发送成功
        if(r.getFlag()){
            //将验证码保存到session
            session.setAttribute("code",r.getData());
            System.out.println("code:"+r.getData());
        }
        return r;
    }
}
