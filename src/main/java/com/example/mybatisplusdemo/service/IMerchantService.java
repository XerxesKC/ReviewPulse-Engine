package com.example.mybatisplusdemo.service;

import com.example.mybatisplusdemo.common.JsonResponse;
import com.example.mybatisplusdemo.model.domain.Merchant;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatisplusdemo.model.dto.MerchantSearchDTO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商家信息表 服务类
 * </p>
 *
 * @author lyc
 * @since 2025-07-04
 */
public interface IMerchantService extends IService<Merchant> {
    /**
     * 商家多条件分页搜索，返回带距离的分页结果
     */
    Page<Merchant> searchMerchants(MerchantSearchDTO dto, List<String> categoryIds);

    List<Merchant> selectBySearch(String keyword);

    Integer insertMy(Merchant merchant);

    Integer deleteMy(String merchantId);

    Integer updateMy(Merchant merchant);

    List<Merchant> selectByDynamicSql(Merchant merchant);

    JsonResponse login(Merchant merchant);

    /**
     * 查询所有商家并以分页对象返回
     */
    Page<Merchant> listAllAsPage(int pageNum, int pageSize);

    Page<Merchant> searchMerchantsLike(Page<Merchant> page, String query);

    List<Map<String, Object>> getFiveStarMerchants();
    List<Map<String, Object>> getMostCommentedMerchants();
    List<Map<String, Object>> getHighestRatedMerchants();
    List<Map<String, Object>> getRatingDistribution();

}
