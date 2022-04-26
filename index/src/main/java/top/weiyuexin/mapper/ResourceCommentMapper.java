package top.weiyuexin.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.weiyuexin.entity.ResourceComment;
@Mapper
public interface ResourceCommentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ResourceComment record);

    int insertSelective(ResourceComment record);

    ResourceComment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ResourceComment record);

    int updateByPrimaryKey(ResourceComment record);
}