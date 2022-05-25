package top.weiyuexin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.weiyuexin.entity.Article;
import top.weiyuexin.entity.Resource;
import top.weiyuexin.mapper.ResourceMapper;
import top.weiyuexin.service.ResourceServer;

import java.util.List;

@Service
public class ResourceServerImpl extends ServiceImpl<ResourceMapper, Resource> implements ResourceServer {
    @Autowired
    private ResourceMapper resourceMapper;

    @Override
    public List<Resource> getTopResource(Integer num) {
        LambdaQueryWrapper<Resource> lqw = new LambdaQueryWrapper<>();
        //查询条件
        lqw.orderByDesc(Resource::getStar);
        lqw.orderByDesc(Resource::getReadNum);
        lqw.last("limit "+num);
        List<Resource> resources = resourceMapper.selectList(lqw);
        return resources;
    }

    @Override
    public IPage<Resource> getPageByType(Integer currentPage, Integer pageSize, String type, Resource resource) {
        LambdaQueryWrapper<Resource> lqw = new LambdaQueryWrapper<>();
        //查询条件:文章显示标志为1
        lqw.eq(Resource::getType,type);
        lqw.orderByDesc(Resource::getTime);
        IPage<Resource> page = new Page<>(currentPage,pageSize);
        resourceMapper.selectPage(page,lqw);
        return page;
    }

    @Override
    public IPage<Resource> getPageByAuthorId(Integer currentPage, Integer pageSize, Integer authorId, Resource resource) {
        LambdaQueryWrapper<Resource> lqw = new LambdaQueryWrapper<>();
        //查询条件:文章显示标志为1
        lqw.eq(Resource::getAuthorId,authorId);
        lqw.orderByDesc(Resource::getTime);
        IPage<Resource> page = new Page<>(currentPage,pageSize);
        resourceMapper.selectPage(page,lqw);
        return page;
    }
}
