package top.weiyuexin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.weiyuexin.dao.UserDao;
import top.weiyuexin.entity.User;
import top.weiyuexin.service.IUserService;
/*使用mp自动生成业务层*/
@Service
public class IUserServiceImpl extends ServiceImpl<UserDao, User> implements IUserService {
}
