package com.example.mybatisplusdemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mybatisplusdemo.model.domain.Favorite;
import com.example.mybatisplusdemo.model.dto.FavoriteSearchDTO;
import com.example.mybatisplusdemo.model.vo.FavoriteSearchVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * <p>
 * 用户收藏表 Mapper 接口
 * </p>
 *
 * @author lyc
 * @since 2025-07-04
 */
public interface FavoriteMapper extends BaseMapper<Favorite> {
    Page<FavoriteSearchVO> searchFavoriteVO(Page<FavoriteSearchVO> page, FavoriteSearchDTO favoriteSearchDTO);
}
