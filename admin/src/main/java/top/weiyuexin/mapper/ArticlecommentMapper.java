package top.weiyuexin.mapper;

<<<<<<< HEAD
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.weiyuexin.entity.ArticleComment;

@Mapper
public interface ArticleCommentMapper extends BaseMapper<ArticleComment> {
}
=======
import org.apache.ibatis.annotations.Mapper;
import top.weiyuexin.entity.ArticleComment;
@Mapper  //扫描DAO接口到spring容器
public interface ArticlecommentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ArticleComment record);

    int insertSelective(ArticleComment record);

    ArticleComment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ArticleComment record);

    int updateByPrimaryKey(ArticleComment record);
}
>>>>>>> refs/remotes/origin/master
