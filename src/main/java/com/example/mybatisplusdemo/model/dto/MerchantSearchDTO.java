package com.example.mybatisplusdemo.model.dto;

import lombok.Data;

@Data
public class MerchantSearchDTO {
    // 主/子类别ID
    private String categoryId;
    // 最小评分
    private String minScore;
    // 最大评分
    private String maxScore;
    // 最低价格
    private String minPrice;
    // 最高价格
    private String maxPrice;
    // 用户当前经度
    private String userLng;
    // 用户当前纬度
    private String userLat;
    // 排序方式：distance/score/price
    private String sortBy;
    // 分页
    private Integer pageNum = 1;
    private Integer pageSize = 10;
    // 餐馆名
    private String merchantName;
}
