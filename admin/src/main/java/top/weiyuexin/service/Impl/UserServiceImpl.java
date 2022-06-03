package top.weiyuexin.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.weiyuexin.entity.User;
import top.weiyuexin.mapper.UserMapper;
import top.weiyuexin.service.UserService;

import java.util.List;

/**
 * 使用mp自动生成业务层
 * */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    //注入数据层
    @Autowired
    private UserMapper userMapper;

    /**
     * 获取用户积分排行实现
     * @param num
     * @return
     */
    @Override
    public List<User> getTopUser(Integer num) {
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.orderByDesc(User::getPoints);
        lqw.last("limit "+num);
        List<User> users = userMapper.selectList(lqw);
        return users;
    }


}
