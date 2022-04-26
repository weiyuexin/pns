package top.weiyuexin.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.weiyuexin.entity.User;
@Mapper
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}