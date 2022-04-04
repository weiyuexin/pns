package top.weiyuexin.service;

import top.weiyuexin.entity.User;

public interface UserService {
    /*
    * 根据用户id查询用户信息
    * */
    User queryUserById(Integer id);
}
