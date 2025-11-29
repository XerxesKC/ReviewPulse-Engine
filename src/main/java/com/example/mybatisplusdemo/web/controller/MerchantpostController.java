package com.example.mybatisplusdemo.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatisplusdemo.model.domain.Comment;
import com.example.mybatisplusdemo.model.domain.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.mybatisplusdemo.common.JsonResponse;
import com.example.mybatisplusdemo.service.IMerchantpostService;
import com.example.mybatisplusdemo.model.domain.Merchantpost;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


/**
 *
 *  前端控制器
 *
 *
 * @author lyc
 * @since 2025-07-04
 * @version v1.0
 */
@RestController
@RequestMapping("/api/merchantpost")
public class MerchantpostController {

    private final Logger logger = LoggerFactory.getLogger( MerchantpostController.class );

    @Autowired
    private IMerchantpostService merchantpostService;

    /**
    * 描述：根据Id 查询
    *
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse<Merchantpost> getById(@PathVariable("id") Long id)throws Exception {
        Merchantpost merchantpost = merchantpostService.getById(id);
        return JsonResponse.success(merchantpost);
    }

    @PostMapping("postDelete")
    public JsonResponse postDelete(@RequestBody Map<String, Integer> req) {
        boolean b = merchantpostService.removeById(req.get("id"));
        return JsonResponse.success(b);
    }

    @PostMapping("postInsert")
    public JsonResponse postInsert(@RequestBody Merchantpost merchantpost){
        // 自动填充时间
        merchantpost.setCreateTime(LocalDateTime.now());
        merchantpost.setUpdateTime(LocalDateTime.now());
        merchantpost.setIsDeleted("F");
        boolean b = merchantpostService.save(merchantpost);
        return JsonResponse.success(b);
    }

    @PostMapping("postUpdate")
    public JsonResponse postUpdate(@RequestBody Merchantpost merchantpost) {
        // 1. 参数校验：确保要更新的记录ID存在
        if (merchantpost.getPostId() == null) {
            return JsonResponse.failure("更新失败：必须提供postId");
        }

        // 2. 设置更新时间（createTime不需要更新）
        merchantpost.setUpdateTime(LocalDateTime.now());

        // 3. 执行更新（根据主键postId更新）
        boolean isUpdated = merchantpostService.updateById(merchantpost);

        // 4. 返回结果
        return isUpdated ?
                JsonResponse.successMessage("更新成功") :
                JsonResponse.failure("更新失败：记录不存在或数据未变化");
    }

    //获取所有动态
    @GetMapping("getContents")
    public JsonResponse getContents(String merchantId) {
        List<Merchantpost> list = merchantpostService.getContents(merchantId);
        return JsonResponse.success(list);
    }

    //删除动态
    @PostMapping("deleteContent")
    public JsonResponse deleteContent(@RequestBody Merchantpost merchantpost) {
        boolean b = merchantpostService.removeById(merchantpost.getPostId());
        return JsonResponse.success(b);
    }

    //增加点赞量
    @PostMapping("addLikeCount")
    public JsonResponse<Merchantpost> updateMy(@RequestBody Merchantpost merchantpost)throws Exception{
        merchantpostService.addLikeCount(merchantpost);
        return JsonResponse.success(merchantpostService.getById(merchantpost));
    }

}

