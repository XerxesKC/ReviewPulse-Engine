package com.example.mybatisplusdemo.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mybatisplusdemo.model.domain.Merchant;
import com.example.mybatisplusdemo.model.domain.Merchantpost;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 商家动态表 Mapper 接口
 * </p>
 *
 * @author lyc
 * @since 2025-07-04
 */
public interface MerchantpostMapper extends BaseMapper<Merchantpost> {

    List<Merchantpost> getContents(@Param("merchantId") String merchantId);

    boolean deleteContent(@Param("postId") String postId);

    Integer addLikeCount(@Param("merchantpost") Merchantpost merchantpost);
}
