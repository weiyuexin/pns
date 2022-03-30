package top.weiyuexin.Mapper;

import org.apache.ibatis.annotations.Mapper;
import top.weiyuexin.Entity.Article;
//@Mapper  //扫描DAO接口到spring容器
public interface ArticleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Article record);

    int insertSelective(Article record);

    Article selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Article record);

    int updateByPrimaryKeyWithBLOBs(Article record);

    int updateByPrimaryKey(Article record);
}