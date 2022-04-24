package top.weiyuexin.mapper;

import top.weiyuexin.entity.ResourceComment;

public interface ResourceCommentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ResourceComment record);

    int insertSelective(ResourceComment record);

    ResourceComment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ResourceComment record);

    int updateByPrimaryKey(ResourceComment record);
}