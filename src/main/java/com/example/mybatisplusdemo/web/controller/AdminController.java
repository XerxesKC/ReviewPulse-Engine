package com.example.mybatisplusdemo.web.controller;

import com.example.mybatisplusdemo.model.domain.Merchant;
import com.example.mybatisplusdemo.model.domain.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.mybatisplusdemo.common.JsonResponse;
import com.example.mybatisplusdemo.service.IAdminService;
import com.example.mybatisplusdemo.service.IMerchantService;
import com.example.mybatisplusdemo.service.ICommentService;
import com.example.mybatisplusdemo.service.IUserService;
import com.example.mybatisplusdemo.model.domain.Admin;

import java.util.List;
import java.util.Map;

/**
 * 前端控制器
 *
 * @author lyc
 * @since 2025-07-04
 * @version v1.0
 */
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private IAdminService adminService;

    @Autowired
    private IMerchantService merchantService;

    @Autowired
    private ICommentService commentService;

    @Autowired
    private IUserService userService;

    /**
     * 描述：根据Id 查询
     */
    @GetMapping("/{id}")
    public JsonResponse<Admin> getById(@PathVariable("id") Long id) throws Exception {
        Admin admin = adminService.getById(id);
        return JsonResponse.success(admin);
    }

    // 登录
    @PostMapping("/login")
    public JsonResponse<Admin> login(@RequestBody Admin admin) {
        return JsonResponse.success(adminService.login(admin));
    }

    // 获取五星好评商户TOP20
    @GetMapping("/five-star-merchants")
    public JsonResponse<List<Map<String, Object>>> getFiveStarMerchants() {
        List<Map<String, Object>> merchants = merchantService.getFiveStarMerchants();
        return JsonResponse.success(merchants);
    }

    // 获取评论数最多商户TOP20
    @GetMapping("/most-commented-merchants")
    public JsonResponse<List<Map<String, Object>>> getMostCommentedMerchants() {
        List<Map<String, Object>> merchants = merchantService.getMostCommentedMerchants();
        return JsonResponse.success(merchants);
    }

    // 获取评分最高商户TOP10
    @GetMapping("/highest-rated-merchants")
    public JsonResponse<List<Map<String, Object>>> getHighestRatedMerchants() {
        List<Map<String, Object>> merchants = merchantService.getHighestRatedMerchants();
        return JsonResponse.success(merchants);
    }

    // 获取商户评分分布
    @GetMapping("/rating-distribution")
    public JsonResponse<List<Map<String, Object>>> getRatingDistribution() {
        List<Map<String, Object>> distribution = merchantService.getRatingDistribution();
        return JsonResponse.success(distribution);
    }

    // 获取用户增长数据
//    @GetMapping("/user-growth")
//    public JsonResponse<List<Map<String, Object>>> getUserGrowthData() {
//        List<Map<String, Object>> growthData = userService.getUserGrowthData();
//        return JsonResponse.success(growthData);
//    }

}