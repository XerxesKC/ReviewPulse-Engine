package com.example.mybatisplusdemo.web.controller;

import com.example.mybatisplusdemo.common.utls.SessionUtils;
import com.example.mybatisplusdemo.mapper.MerchantMapper;
import com.example.mybatisplusdemo.model.domain.Category;
import com.example.mybatisplusdemo.model.domain.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.mybatisplusdemo.common.JsonResponse;
import com.example.mybatisplusdemo.service.IMerchantService;
import com.example.mybatisplusdemo.model.domain.Merchant;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatisplusdemo.model.dto.MerchantSearchDTO;
import com.example.mybatisplusdemo.service.ICategoryService;

import java.util.List;


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
@RequestMapping("/api/merchant")
public class MerchantController {

    private final Logger logger = LoggerFactory.getLogger( MerchantController.class );
    @Autowired
    private IMerchantService merchantService;

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private MerchantMapper merchantMapper;

    /**
    * 描述：根据Id 查询
    *
    */
    @RequestMapping(value = "/{id:\\d+}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse<Merchant> getById(@PathVariable("id") Long id)throws Exception {
        Merchant merchant = merchantService.getById(id);
        return JsonResponse.success(merchantService.getById(merchant));
    }

    //根据id查询商家
    @GetMapping("selectById")
    public  JsonResponse<Merchant> selectMy(Long id)throws Exception {
        return JsonResponse.success(merchantService.getById(id));
    }

    //插入商家
    @PostMapping("insert")
    public JsonResponse<Merchant> insertMy(@RequestBody Merchant merchant)throws Exception{
        merchantService.insertMy(merchant);
        return JsonResponse.success(merchantService.getById(merchant.getMerchantId()));
    }

    //删除商家
    @DeleteMapping("delete")
    public JsonResponse<Merchant> deleteMy(Merchant merchant)throws Exception {
        merchantService.deleteMy(merchant.getMerchantId());
        return JsonResponse.success(merchantService.getById(merchant));
    }

    @PostMapping("/deleteById")
    public JsonResponse deleteById(@RequestParam String merchantId) {
        boolean b = merchantService.removeById(merchantId);
        if (b) {
            return JsonResponse.successMessage("商家删除成功");
        } else {
            return JsonResponse.failure("商家删除失败");
        }
    }

    //修改商家
    @PostMapping("update")
    public JsonResponse<Merchant> updateMy(@RequestBody Merchant merchant)throws Exception{
        merchantService.updateMy(merchant);
        return JsonResponse.success(merchantService.getById(merchant));
    }

    //根据关键词查询商家
    @GetMapping("selectBySearch")
    public  JsonResponse<List<Merchant>> selectBySearch(String keyword)throws Exception {
        return JsonResponse.success(merchantService.selectBySearch(keyword));
    }

    //动态sql
    @PostMapping("selectByDynamicSql")
    public JsonResponse<List<Merchant>> selectByDynamicSql(@RequestBody Merchant merchant) throws Exception {
        return JsonResponse.success(merchantService.selectByDynamicSql(merchant));
    }

    /**
     * 商家多条件搜索接口
     */
    @PostMapping("/search")
    public JsonResponse<Page<Merchant>> search(@RequestBody MerchantSearchDTO dto) {
        //递归查找所有子类别id
        java.util.List<String> categoryIds = null;
        if (dto.getCategoryId() != null) {
            categoryIds = categoryService.getAllSubCategoryIds(dto.getCategoryId());
        }
        Page<Merchant> page = merchantService.searchMerchants(dto, categoryIds);
        return JsonResponse.success(page);
    }

    //登录
    @PostMapping("/login")
    public JsonResponse<Merchant> login(@RequestBody Merchant merchant) {
        return merchantService.login(merchant);
    }

    //获取当前登录商家
    @GetMapping("/getInfo")
    public JsonResponse<Merchant> getInfo() {
        Merchant currentMerchant = SessionUtils.getCurrentMerchantInfo();
        if (currentMerchant != null) {
            return JsonResponse.success(currentMerchant);
        } else {
            return JsonResponse.failure("未登录或用户信息不存在");
        }
    }

    /**
     * 列出所有商家（分页，返回所有记录）
     */
    @PostMapping("/list")
    public JsonResponse<Page<Merchant>> listAll(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize) {
        Page<Merchant> page = merchantService.listAllAsPage(pageNum, pageSize);
        return JsonResponse.success(page);
    }

    /**
     * 计算两点间距离（单位：千米）
     */
    @GetMapping("/calDistance")
    public JsonResponse<Double> calcDistance(@RequestParam double lat1, @RequestParam double lng1, @RequestParam double lat2, @RequestParam double lng2) {
        double distance = this.calcDis(lat1, lng1, lat2, lng2);
        return JsonResponse.success(distance);
    }

    /**
     * Haversine公式计算两点距离（单位：千米）
     */
    private double calcDis(double lat1, double lng1, double lat2, double lng2) {
        double R = 6371.0;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return R * c;
    }

    /**
     * 获取商家列表（统一搜索）
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @param searchQuery 搜索关键词（同时匹配名称、电话、邮箱）
     */
    @GetMapping("/getList")
    public JsonResponse<Page<Merchant>> getMerchantList(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String searchQuery) {

        Page<Merchant> page = new Page<>(pageNum, pageSize);
        return JsonResponse.success(merchantService.searchMerchantsLike(page, searchQuery));
    }

    @PostMapping("/verify")
    public JsonResponse<Merchant> verifyMerchant(
            @RequestParam String merchantId,
            @RequestParam Byte verificationStatus,
            @RequestParam(required = false) String review) {

        try {
            // 参数验证
            if (merchantId == null || merchantId.isEmpty()) {
                return JsonResponse.failure("商家ID不能为空");
            }
            if (verificationStatus == null || (verificationStatus != 0 && verificationStatus != 1 && verificationStatus != 2)) {
                return JsonResponse.failure("审核状态必须是0(不通过)、1(审核中)或2(通过)");
            }

            // 查找商家
            Merchant merchant = merchantService.getById(merchantId);
            if (merchant == null) {
                return JsonResponse.failure("商家不存在");
            }

            // 更新状态和审核信息
            merchant.setVerificationStatus(verificationStatus);
            merchant.setReview(review);
            merchantService.updateMy(merchant);

            return JsonResponse.success(merchant);

        } catch (Exception e) {
            return JsonResponse.failure(e.getMessage());
        }
    }

    @PostMapping("/status")
    public JsonResponse<String> toggleMerchantStatus(
            @RequestParam String merchantId,
            @RequestParam String status) {

        try {
            // 1. 参数校验
            if (merchantId == null) {
                return JsonResponse.failure("商家ID不能为空");
            }
            if (!"active".equals(status) && !"frozen".equals(status)) {
                return JsonResponse.failure("状态值必须是active或frozen");
            }

            // 2. 执行状态切换
            Merchant merchant = merchantService.getById(merchantId);
            if (merchant == null) {
                return JsonResponse.failure("商家不存在");
            }

            merchant.setMerchantStatus(status);
            merchantService.updateMy(merchant);

            // 3. 返回更新后的商家信息
            return JsonResponse.successMessage("操作成功");

        } catch (Exception e) {
            return JsonResponse.failure("操作失败: " + e.getMessage());
        }
    }

}
