package top.weiyuexin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.weiyuexin.entity.Resource;
import top.weiyuexin.mapper.ResourceMapper;
import top.weiyuexin.service.ResourceServer;
@Service
public class ResourceServerImpl extends ServiceImpl<ResourceMapper, Resource> implements ResourceServer {
}
