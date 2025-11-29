package com.example.mybatisplusdemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.mybatisplusdemo.model.domain.Admin;
import com.example.mybatisplusdemo.mapper.AdminMapper;
import com.example.mybatisplusdemo.model.domain.Merchant;
import com.example.mybatisplusdemo.service.IAdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 管理员账户表 服务实现类
 * </p>
 *
 * @author xh
 * @since 2025-07-04
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

    @Autowired AdminMapper adminMapper;

    @Override
    public Admin login(Admin admin) {
        LambdaQueryWrapper<Admin> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Admin::getAdminName, admin.getAdminName())
                .eq(Admin::getAdminPassword, admin.getAdminPassword());
        return adminMapper.selectOne(queryWrapper);
    }
}
