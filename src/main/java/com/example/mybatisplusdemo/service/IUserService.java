package com.example.mybatisplusdemo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatisplusdemo.common.JsonResponse;
import com.example.mybatisplusdemo.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.mybatisplusdemo.model.dto.UserInsertDTO;
import com.example.mybatisplusdemo.model.dto.UserSearchDTO;
import com.example.mybatisplusdemo.model.dto.UserUpdateDTO;

import java.util.List;

/**
 * <p>
 * 用户信息表 服务类
 * </p>
 *
 * @author lyc
 * @since 2025-07-04
 */
public interface IUserService extends IService<User> {

    Page<User> searchUser(Page<User> page, UserSearchDTO userSearchDTO);

    /**
     * 用户登录，支持手机号或邮箱+密码
     */
    JsonResponse login(User user);

    /**
     * 新增用户，返回插入后的User对象
     */
    JsonResponse insertUser(UserInsertDTO userInsertDTO);

    JsonResponse updateUser(UserUpdateDTO userUpdateDTO);

    JsonResponse updateCurrentUser(UserUpdateDTO userUpdateDTO);

    User deleteUser(String userId);

    Page<User> searchUserLike(Page<User> page, String query);

    List<User> getUsers();

//    List<Map<String, Object>> getUserGrowthData();

}
