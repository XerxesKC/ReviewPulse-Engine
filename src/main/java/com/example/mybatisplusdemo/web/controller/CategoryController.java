package com.example.mybatisplusdemo.web.controller;

import com.example.mybatisplusdemo.model.domain.Merchant;
import com.example.mybatisplusdemo.model.dto.MerchantCurrentDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.mybatisplusdemo.common.JsonResponse;
import com.example.mybatisplusdemo.service.ICategoryService;
import com.example.mybatisplusdemo.model.domain.Category;


/**
 *
 *  前端控制器
 *
 *
 * @author xys
 * @since 2025-07-04
 * @version v1.0
 */
@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private final Logger logger = LoggerFactory.getLogger( CategoryController.class );

    @Autowired
    private ICategoryService categoryService;


    /**
    * 描述：根据Id 查询
    *
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse<Category> getById(@PathVariable("id") Long id)throws Exception {
        Category category = categoryService.getById(id);
        return JsonResponse.success(category);
    }

    //根据id查询分类
    @GetMapping("selectById")
    public  JsonResponse<Category> selectById(Long id)throws Exception {
        Category category = categoryService.getById(id);
        return JsonResponse.success(category);
    }

    //插入分类
    @PostMapping("insert")
    public JsonResponse<Category> insert(@RequestBody Category category)throws Exception{
        long count = categoryService.count() + 1;
        category.setCategoryId(count + "");
        categoryService.insertMy(category);
        return JsonResponse.success(categoryService.getById(category.getCategoryId()));
    }

    //删除分类
    @DeleteMapping("delete")
    public JsonResponse<Category> deleteMy(Category category) throws Exception {
        categoryService.deleteMy(category.getCategoryId());
        return JsonResponse.success(categoryService.getById(category));
    }

    @PostMapping("update")
    public JsonResponse<Category> updateMy(@RequestBody Category category)throws Exception{
        categoryService.updateMy(category);
        return JsonResponse.success(categoryService.getById(category));
    }

    /**
     * 获取所有类别列表（主/子类别）
     */
    @GetMapping("/list")
    public JsonResponse<java.util.List<Category>> listAll() {
        java.util.List<Category> list = categoryService.list();
        return JsonResponse.success(list);
    }

    /**
     * 根据类别名称精确查询类别ID
     */
    @GetMapping("/getIdByName")
    public JsonResponse<String> getIdByName(@RequestParam String categoryName) {
        Category category = categoryService.lambdaQuery()
                .eq(Category::getCategoryName, categoryName)
                .one();
        if (category != null) {
            return JsonResponse.success(category.getCategoryId());
        } else {
            return JsonResponse.failure("未找到对应类别");
        }
    }

    //获得所有类别
    @GetMapping("/getParentList")
    public  JsonResponse getParentList()throws Exception {
        return JsonResponse.success(categoryService.getParentList());
    }

    //获得当前登录商家类别名称
    @GetMapping("/getMerchantCategoryName")
    public JsonResponse getMerchantCategoryName(@RequestParam String categoryId) throws Exception {
        Category category = categoryService.getMerchantCategoryName(categoryId);
        if (category != null) {
            return JsonResponse.success(category.getCategoryName());
        } else {
            return JsonResponse.failure("未找到对应商家类别");
        }
    }
}
