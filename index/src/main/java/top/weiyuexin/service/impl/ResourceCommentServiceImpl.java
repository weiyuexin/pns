package top.weiyuexin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
        lqw.orderByDesc(ResourceComment::getId);
        resourceComments = resourceCommentMapper.selectList(lqw);
        return resourceComments;
    }
}
