package top.weiyuexin.service;


import com.baomidou.mybatisplus.extension.service.IService;
import top.weiyuexin.entity.Resource;

import java.util.List;

public interface ResourceServer extends IService<Resource> {
    /**
     * 查询热门资源
     * @param num
     * @return
     */
    List<Resource> getTopResource(Integer num);
}
