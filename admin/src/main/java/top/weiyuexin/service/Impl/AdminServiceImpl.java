package top.weiyuexin.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.weiyuexin.entity.Admin;
import top.weiyuexin.mapper.AdminMapper;
import top.weiyuexin.service.AdminService;

@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {
}
