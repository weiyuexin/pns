package top.weiyuexin.Service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.weiyuexin.Entity.User;
import top.weiyuexin.Mapper.UserMapper;
import top.weiyuexin.Service.UserService;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    /*
    * 调用DAO层接口，根据id查询用户信息
    * */
    @Override
    public User queryUserById(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }
}
