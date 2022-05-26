package top.weiyuexin.service;

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
}
