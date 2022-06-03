
<<<<<<< HEAD
package top.weiyuexin.service.impl;
=======
package top.weiyuexin.service.Impl;
>>>>>>> 57b3c4dfe01c7da45003400b0ff8f302b59e465a

import cn.hutool.core.util.RandomUtil;
import cn.hutool.extra.mail.MailUtil;
import org.springframework.stereotype.Service;
import top.weiyuexin.entity.vo.R;
import top.weiyuexin.service.EmailService;

@Service
public class EmailServiceImpl implements EmailService {

    @Override
    public R send(String email) {
        R r = new R();
        //随机产生验证码
        Integer emailCode = RandomUtil.randomInt(10000,99999);
        //信息标题
        String title = "验证你的邮箱";
        //信息内容
        String emailCodeContent = "您的验证码是 "+ emailCode + ", 请在五分钟内完成验证。";
        //调用HuTool中的发送验证码的方法，发送验证码
        try {
            MailUtil.send(email,title,emailCodeContent,false);
            //发送成功
            r.setFlag(true);
            //将验证码保存到data中
            r.setData(emailCode);
            r.setMsg("验证码发送成功，请前往邮箱查看!");
        }catch (Exception e){
            //发送失败
            r.setFlag(false);
            r.setMsg("发送失败，请检查邮箱是否填写正确后重试!");
        }
        //返回消息模型
        return r;
    }
}
