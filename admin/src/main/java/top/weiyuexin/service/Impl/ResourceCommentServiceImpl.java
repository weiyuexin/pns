package top.weiyuexin.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.weiyuexin.entity.ArticleComment;
import top.weiyuexin.entity.ResourceComment;
import top.weiyuexin.mapper.ResourceCommentMapper;
import top.weiyuexin.service.ResourceCommentService;

import java.util.ArrayList;
import java.util.List;

@Service
public class ResourceCommentServiceImpl extends ServiceImpl<ResourceCommentMapper, ResourceComment>
        implements ResourceCommentService {

    @Autowired
    private ResourceCommentMapper resourceCommentMapper;

    /**
     * 实现根据资源id查询评论
     * @param resourceId
     * @return
     */
    @Override
    public List<ResourceComment> getResourceCommentsByResourceId(Integer resourceId) {
        List<ResourceComment> resourceComments = new ArrayList<>();
        LambdaQueryWrapper<ResourceComment> lqw = new LambdaQueryWrapper<>();
        lqw.eq(ResourceComment::getResourceId,resourceId);
        resourceComments = resourceCommentMapper.selectList(lqw);
        return resourceComments;
    }

    /**
     * 分页查询资源评论实现
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public IPage<ResourceComment> getPage(Integer currentPage, Integer pageSize) {
        LambdaQueryWrapper<ResourceComment> lqw = new LambdaQueryWrapper<>();
        lqw.orderByDesc(ResourceComment::getId);
        IPage<ResourceComment> page = new Page<>(currentPage,pageSize);
        resourceCommentMapper.selectPage(page,lqw);
        return page;
    }
}
