package top.weiyuexin.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.weiyuexin.entity.Article;
import top.weiyuexin.entity.Image;
import top.weiyuexin.mapper.ImageMapper;
import top.weiyuexin.service.ImageService;

@Service
public class ImageServiceImpl extends ServiceImpl<ImageMapper, Image> implements ImageService {
    @Autowired
    private ImageMapper imageMapper;

    @Override
    public IPage<Image> getPage(Integer currentPage, Integer pageSize,Image image) {
        LambdaQueryWrapper<Image> lqw = new LambdaQueryWrapper<>();
        lqw.orderByDesc(Image::getId);
        lqw.like(image.getAuthorId()!=null,Image::getAuthorId,image.getAuthorId());
        lqw.like(Strings.isNotEmpty(image.getOriginName()),Image::getOriginName,image.getOriginName());
        IPage<Image> page = new Page<>(currentPage,pageSize);
        imageMapper.selectPage(page,lqw);
        return page;
    }
}
