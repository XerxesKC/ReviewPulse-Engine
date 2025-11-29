package com.example.mybatisplusdemo.service;

import com.example.mybatisplusdemo.model.domain.Category;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.mybatisplusdemo.model.domain.Merchant;
import com.example.mybatisplusdemo.model.dto.MerchantCurrentDTO;

import java.util.List;

/**
 * <p>
 * 商家分类表 服务类
 * </p>
 *
 * @author lyc
 * @since 2025-07-04
 */
public interface ICategoryService extends IService<Category> {
    /**
     * 递归查找所有子类别ID（包含自身）
     */
    List<String> getAllSubCategoryIds(String categoryId);

    Integer insertMy(Category category);

    Integer deleteMy(String categoryId);

    Integer updateMy(Category category);

    List getParentList();

    Category getMerchantCategoryName(String category);
}
