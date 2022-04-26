package top.weiyuexin.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.weiyuexin.entity.ArticleComment;
@Mapper
public interface ArticleCommentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ArticleComment record);

    int insertSelective(ArticleComment record);

    ArticleComment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ArticleComment record);

    int updateByPrimaryKey(ArticleComment record);
}