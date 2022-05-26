package top.weiyuexin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.weiyuexin.entity.ResourceComment;

import java.util.List;

public interface ResourceCommentService extends IService<ResourceComment> {
    /**
     * 根据资源id查询资源的评论
     * @param resourceId
     * @return
     */
    List<ResourceComment> getResourceCommentsByResourceId(Integer resourceId);
}
