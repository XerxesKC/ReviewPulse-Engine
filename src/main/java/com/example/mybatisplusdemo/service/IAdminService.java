package com.example.mybatisplusdemo.service;

import com.example.mybatisplusdemo.model.domain.Admin;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 管理员账户表 服务类
 * </p>
 *
 * @author xh
 * @since 2025-07-04
 */
public interface IAdminService extends IService<Admin> {

    Admin login(Admin admin);
}
