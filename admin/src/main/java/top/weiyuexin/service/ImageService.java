package top.weiyuexin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import top.weiyuexin.entity.Image;

public interface ImageService extends IService<Image> {

    /**
     * 分页查询
     * @param currentPage
     * @param pageSize
     * @param image
     * @return
     */
    IPage<Image> getPage(Integer currentPage, Integer pageSize,Image image);
}
