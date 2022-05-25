package top.weiyuexin.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.weiyuexin.entity.User;
import top.weiyuexin.mapper.UserMapper;
import top.weiyuexin.service.UserService;
@Service  //标记为服务层组件
public class UserServiceImpl implements UserService {
    //注入持久层
    @Autowired
    private UserMapper userMapper;


    @Override
    public User queryUserById(Integer id) {
        return null;
    }
}
