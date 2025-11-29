package com.example.mybatisplusdemo.service.impl;

import com.example.mybatisplusdemo.model.domain.Category;
import com.example.mybatisplusdemo.mapper.CategoryMapper;
import com.example.mybatisplusdemo.model.domain.Merchant;
import com.example.mybatisplusdemo.model.dto.MerchantCurrentDTO;
import com.example.mybatisplusdemo.service.ICategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 商家分类表 服务实现类
 * </p>
 *
 * @author lyc
 * @since 2025-07-04
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {

    //获取给定分类 ID 及其所有子分类的 ID 列表，并且是以树形结构进行递归查找的。
    @Override
    public List<String> getAllSubCategoryIds(String categoryId) {
        List<String> result = new ArrayList<>();
        result.add(categoryId);
        List<Category> all = this.list();
        addChildren(categoryId, all, result);
        return result;
    }

    //递归查找并添加子分类 ID
    private void addChildren(String parentId, List<Category> all, List<String> result) {
        for (Category c : all) {
            if (parentId.equals(c.getParentId())) {
                result.add(c.getCategoryId());
                addChildren(c.getCategoryId(), all, result);
            }
        }
    }
    @Autowired
    CategoryMapper categoryMapper;

    public Integer insertMy(Category category) {
        return categoryMapper.insertMy(category);
    }

    @Override
    public Integer deleteMy(String categoryId) {
        return categoryMapper.deleteMy(categoryId);
    }

    @Override
    public Integer updateMy(Category category) {
        return categoryMapper.updateMy(category);
    }

    @Override
    public List<Category> getParentList() {
        return categoryMapper.getParentList();
    }

    @Override
    public Category getMerchantCategoryName(String categoryId) {
        return categoryMapper.getCategoryName(categoryId);
    }
}
