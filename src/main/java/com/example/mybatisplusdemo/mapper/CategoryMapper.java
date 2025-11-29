package com.example.mybatisplusdemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mybatisplusdemo.model.domain.Category;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 商家分类表 Mapper 接口
 * </p>
 *
 * @author lyc
 * @since 2025-07-04
 */
public interface CategoryMapper extends BaseMapper<Category> {

    Integer insertMy(@Param("category") Category category);

    Integer deleteMy(@Param("categoryId") String categoryId);

    Integer updateMy(@Param("category") Category category);

    List<Category> getParentList();

    Category getCategoryName(String categoryId);
}
