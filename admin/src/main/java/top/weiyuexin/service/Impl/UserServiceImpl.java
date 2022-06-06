package top.weiyuexin.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.logging.log4j.util.Strings;
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

    @Override
    public User getByUserName(String username) {
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.like(User::getUsername,username);
        User user = userMapper.selectOne(lqw);
        return user;
    }


    /**
     * 分页查询实现
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public IPage<User> getPage(Integer currentPage, Integer pageSize,User user) {
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.orderByDesc(User::getId);
        //如果username不为空，则查询
        lqw.like(Strings.isNotEmpty(user.getUsername()),User::getUsername,user.getUsername());
        lqw.like(Strings.isNotEmpty(user.getEmail()),User::getEmail,user.getEmail());
        lqw.like(Strings.isNotEmpty(user.getSex()),User::getSex,user.getSex());
        lqw.like(Strings.isNotEmpty(user.getPhone()),User::getPhone,user.getPhone());
        lqw.like(Strings.isNotEmpty(user.getAddress()),User::getAddress,user.getAddress());
        IPage<User> page = new Page<>(currentPage,pageSize);
        userMapper.selectPage(page,lqw);
        return page;
    }
}
