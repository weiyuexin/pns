package top.weiyuexin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import top.weiyuexin.entity.ArticleComment;
import top.weiyuexin.entity.ResourceComment;

import java.util.List;

public interface ResourceCommentService extends IService<ResourceComment> {
    /**
     * 根据资源id查询资源的评论
     * @param resourceId
     * @return
     */
    List<ResourceComment> getResourceCommentsByResourceId(Integer resourceId);

    /**
     * 分页查询
     * @param currentPage
     * @param pageSize
     * @return
     */
    IPage<ResourceComment> getPage(Integer currentPage, Integer pageSize);
}
