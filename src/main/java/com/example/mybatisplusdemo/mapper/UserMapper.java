package com.example.mybatisplusdemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatisplusdemo.model.domain.User;
import com.example.mybatisplusdemo.model.dto.UserSearchDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户信息表 Mapper 接口
 * </p>
 *
 * @author lyc
 * @since 2025-07-04
 */
public interface UserMapper extends BaseMapper<User> {

    Page<User> selectUserPage(@Param("page") Page<User> page, @Param("dto") UserSearchDTO userDTO);

    List<User> getUsers();

//    List<Map<String, Object>> selectUserGrowthData();

}
