package top.weiyuexin.service;

import top.weiyuexin.entity.vo.R;

//邮箱服务类接口
public interface EmailService {
    /**
     * 发送邮箱验证码
     * @param email
     * @return
     */
    R send(String email);
}
