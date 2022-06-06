package top.weiyuexin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
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

    User getByUserName(String username);

    /**
     * 分页查询
     * @param currentPage
     * @param pageSize
     * @return
     */
    IPage<User> getPage(Integer currentPage, Integer pageSize,User user);
}
