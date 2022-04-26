package top.weiyuexin.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.weiyuexin.entity.Resource;
@Mapper
public interface ResourceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Resource record);

    int insertSelective(Resource record);

    Resource selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Resource record);

    int updateByPrimaryKey(Resource record);
}