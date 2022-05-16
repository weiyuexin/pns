package top.weiyuexin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.weiyuexin.entity.Article;
import top.weiyuexin.mapper.ArticleMapper;
import top.weiyuexin.service.ArticleService;

import java.util.List;

//文章服务实现类
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    /**
     * 注入数据层
     */
    @Autowired
    private ArticleMapper articleMapper;

    //自定义方法的实现

    /**
     * 分页实现
     * @param currentPage
     * @param pageSize
     * @param article
     * @return
     */
    @Override
    public IPage<Article> getPage(Integer currentPage, Integer pageSize, Article article) {
        LambdaQueryWrapper<Article> lqw = new LambdaQueryWrapper<>();
        //查询条件:文章显示标志为1
        lqw.like(Article::getIsShow,1);
        lqw.orderByDesc(Article::getTime);
        IPage<Article> page = new Page<>(currentPage,pageSize);
        articleMapper.selectPage(page,lqw);
        return page;
    }
    /**
     * 查询热门文章实现
     * @param num
     * @return
     */
    @Override
    public List<Article> getTopArticle(Integer num) {
        LambdaQueryWrapper<Article> lqw = new LambdaQueryWrapper<>();
        //查询条件
        lqw.orderByDesc(Article::getReadNum);
        lqw.orderByDesc(Article::getCommentNum);
        lqw.last("limit "+num);
        List<Article> articles = articleMapper.selectList(lqw);
        return articles;
    }

    @Override
    public IPage<Article> getPageByType(Integer currentPage,
                                        Integer pageSize,
                                        String type,
                                        Article article) {
        LambdaQueryWrapper<Article> lqw = new LambdaQueryWrapper<>();
        //查询条件:文章显示标志为1
        lqw.like(Article::getIsShow,1);
        lqw.eq(Article::getType,type);
        lqw.orderByDesc(Article::getTime);
        IPage<Article> page = new Page<>(currentPage,pageSize);
        articleMapper.selectPage(page,lqw);
        return page;
    }

}
