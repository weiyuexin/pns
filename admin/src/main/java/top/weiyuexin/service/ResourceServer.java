package top.weiyuexin.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import top.weiyuexin.entity.Resource;
import top.weiyuexin.entity.User;

import java.util.List;

public interface ResourceServer extends IService<Resource> {
    /**
     * 查询热门资源
     * @param num
     * @return
     */
    List<Resource> getTopResource(Integer num);

    /**
     * 根据类型查看资源
     * @param currentPage
     * @param pageSize
     * @param type
     * @param resource
     * @return
     */
    IPage<Resource> getPageByType(Integer currentPage, Integer pageSize, String type, Resource resource);

    IPage<Resource> getPageByAuthorId(Integer currentPage, Integer pageSize, Integer authorId, Resource resource);

    /**
     * 分页查询
     * @param currentPage
     * @param pageSize
     * @return
     */
    IPage<Resource> getPage(Integer currentPage, Integer pageSize,Resource resource);
}
