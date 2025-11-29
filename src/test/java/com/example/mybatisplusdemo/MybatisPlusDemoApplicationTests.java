package com.example.mybatisplusdemo;

import com.example.mybatisplusdemo.mapper.AdminMapper;
import com.example.mybatisplusdemo.mapper.MerchantMapper;
import com.example.mybatisplusdemo.model.domain.Admin;
import com.example.mybatisplusdemo.model.domain.Merchant;
import com.example.mybatisplusdemo.service.IAdminService;
import com.example.mybatisplusdemo.service.IMerchantService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest
class MybatisPlusDemoApplicationTests {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private IAdminService adminService;

    @Autowired
    private IMerchantService merchantService;

    @Test
    public void t1() {
        // 查id为1的管理员
        Admin admin = adminService.getById('1');
        System.out.println(admin);
    }

    @Test
    public void t2() {
        List<Map<String, Object>> list = merchantService.getFiveStarMerchants();
        //List<Map<String, Object>> list = merchantService.getHighestRatedMerchants();
        //List<Map<String, Object>> list = merchantService.getMostCommentedMerchants();
        //List<Map<String, Object>> list = merchantService.getRatingDistribution();
        System.out.println(list);
    }

}
