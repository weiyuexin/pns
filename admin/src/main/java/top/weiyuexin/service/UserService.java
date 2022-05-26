package top.weiyuexin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.weiyuexin.entity.User;

import java.util.List;

/*使用mp自动生成业务层*/
public interface UserService extends IService<User> {
    //自定义方法

    /**
     * 获取用户积分排行
     * @param num
     * @return
     */
    List<User> getTopUser(Integer num);

}
