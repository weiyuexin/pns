package top.weiyuexin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.weiyuexin.entity.User;

@Mapper
public interface UserDao extends BaseMapper<User> {

}
