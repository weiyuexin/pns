package top.weiyuexin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.weiyuexin.entity.Article;

@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
}
