package top.weiyuexin.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.weiyuexin.entity.Image;
import top.weiyuexin.mapper.ImageMapper;
import top.weiyuexin.service.ImageService;

@Service
public class ImageServiceImpl extends ServiceImpl<ImageMapper, Image> implements ImageService {
}
