package com.example.mybatisplusdemo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatisplusdemo.model.domain.Favorite;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.mybatisplusdemo.model.dto.FavoriteDTO;
import com.example.mybatisplusdemo.model.dto.FavoriteInsertDTO;
import com.example.mybatisplusdemo.model.dto.FavoriteUpdateDTO;
import com.example.mybatisplusdemo.model.vo.FavoriteSearchVO;
import com.example.mybatisplusdemo.model.dto.FavoriteSearchDTO;

/**
 * <p>
 * 用户收藏表 服务类
 * </p>
 *
 * @author lyc
 * @since 2025-07-04
 */
public interface IFavoriteService extends IService<Favorite> {

    Page<Favorite> searchFavorite(Page<Favorite> page, FavoriteDTO favoriteDTO);

    Favorite insertFavorite(FavoriteInsertDTO favoriteInsertDTO);

    Favorite updateFavorite(FavoriteUpdateDTO favoriteUpdateDTO);

    Favorite deleteFavorite(String favoriteId);

    Page<FavoriteSearchVO> searchFavorite(Page<FavoriteSearchVO> page, FavoriteSearchDTO favoriteSearchDTO);
}
