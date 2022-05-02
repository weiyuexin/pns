package top.weiyuexin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.weiyuexin.mapper.UserMapper;
import top.weiyuexin.entity.User;
import top.weiyuexin.service.UserService;
/**
 * 使用mp自动生成业务层
 * */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
