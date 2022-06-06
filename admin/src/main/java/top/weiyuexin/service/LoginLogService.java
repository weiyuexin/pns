package top.weiyuexin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import top.weiyuexin.entity.LoginLog;
import top.weiyuexin.entity.User;

public interface LoginLogService extends IService<LoginLog> {
    /**
     * 分页查询日志
     * @param currentPage
     * @param pageSize
     * @return
     */
    IPage<LoginLog> getPage(Integer currentPage, Integer pageSize,LoginLog loginLog);
}
