package top.weiyuexin.mapper;

import top.weiyuexin.entity.User;
//@Mapper  //扫描DAO接口到spring容器
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}