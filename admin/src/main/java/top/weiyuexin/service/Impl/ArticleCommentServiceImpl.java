package top.weiyuexin.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.weiyuexin.entity.Article;
import top.weiyuexin.entity.ArticleComment;
import top.weiyuexin.mapper.ArticleCommentMapper;
import top.weiyuexin.service.ArticleCommentService;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleCommentServiceImpl extends ServiceImpl<ArticleCommentMapper, ArticleComment>
        implements ArticleCommentService {
    @Autowired
    private ArticleCommentMapper articleCommentMapper;
    @Override
    public List<ArticleComment> getCommentById(Integer articleId) {
        List<ArticleComment> articleComments = new ArrayList<>();
        LambdaQueryWrapper<ArticleComment> lqw = new LambdaQueryWrapper<>();
        lqw.eq(ArticleComment::getArticleId,articleId);
        articleComments=articleCommentMapper.selectList(lqw);
        return articleComments;
    }

    /**
     * 分页实现
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public IPage<ArticleComment> getPage(Integer currentPage, Integer pageSize) {
        LambdaQueryWrapper<ArticleComment> lqw = new LambdaQueryWrapper<>();
        lqw.orderByDesc(ArticleComment::getId);
        IPage<ArticleComment> page = new Page<>(currentPage,pageSize);
        articleCommentMapper.selectPage(page,lqw);
        return page;
    }
}
