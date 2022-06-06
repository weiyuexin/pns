package top.weiyuexin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import top.weiyuexin.entity.ArticleComment;

import java.util.List;

public interface ArticleCommentService extends IService<ArticleComment> {
    /**
     * 根据文章id查询评论
     * @param articleId
     * @return
     */
    List<ArticleComment> getCommentById(Integer articleId);

    /**
     * 分页查询
     * @param currentPage
     * @param pageSize
     * @return
     */
    IPage<ArticleComment> getPage(Integer currentPage,Integer pageSize,ArticleComment articleComment);
}
