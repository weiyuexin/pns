package top.weiyuexin.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.weiyuexin.entity.LoginLog;
import top.weiyuexin.entity.User;
import top.weiyuexin.mapper.LoginLogMapper;
import top.weiyuexin.service.LoginLogService;

@Service
public class LoginLogServiceImpl extends ServiceImpl<LoginLogMapper, LoginLog> implements LoginLogService {
    @Autowired
    private LoginLogMapper loginLogMapper;

    @Override
    public IPage<LoginLog> getPage(Integer currentPage, Integer pageSize) {
        LambdaQueryWrapper<LoginLog> lqw = new LambdaQueryWrapper<>();
        lqw.orderByDesc(LoginLog::getId);
        IPage<LoginLog> page = new Page<>(currentPage,pageSize);
        loginLogMapper.selectPage(page,lqw);
        return page;
    }
}
