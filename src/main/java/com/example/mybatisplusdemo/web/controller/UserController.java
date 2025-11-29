package com.example.mybatisplusdemo.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatisplusdemo.common.utls.SessionUtils;
import com.example.mybatisplusdemo.model.domain.Merchantpost;
import com.example.mybatisplusdemo.model.dto.UserInsertDTO;
import com.example.mybatisplusdemo.model.dto.UserSearchDTO;
import com.example.mybatisplusdemo.model.dto.UserUpdateDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.mybatisplusdemo.common.JsonResponse;
import com.example.mybatisplusdemo.service.IUserService;
import com.example.mybatisplusdemo.model.domain.User;
import org.springframework.web.multipart.MultipartFile;

import javax.management.Query;
import java.util.List;
import java.util.Map;


/**
 *
 *  前端控制器
 *
 *
 * @author xh
 * @since 2025-07-04
 * @version v1.0
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger( UserController.class );

    @Autowired
    private IUserService userService;


    /**
    * 描述：根据Id 查询
    *
    */
    @GetMapping("/search")
    public JsonResponse searchUser(UserSearchDTO userSearchDTO,
                            @RequestParam(defaultValue = "1") Integer pageNum,
                            @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<User> page = new Page<>(pageNum, pageSize);
        page = userService.searchUser(page, userSearchDTO);
        return JsonResponse.success(page);
    }

    /**
     * 描述：新增用户
     */
    @PostMapping("/insert")
    public JsonResponse insertUser(@RequestBody UserInsertDTO userInsertDTO) {
        return userService.insertUser(userInsertDTO);
    }

    /**
     * 描述：更新用户
     */
    @PostMapping("/update")
    public JsonResponse updateUser(@RequestBody UserUpdateDTO userUpdateDTO) {
        return userService.updateUser(userUpdateDTO);
    }

    /**
     * 描述：更新当前已登录的用户（需要更新session）
     */
    @PostMapping("/updateCurrentUser")
    public JsonResponse updateCurrentUser(@RequestBody UserUpdateDTO userUpdateDTO) {
        return userService.updateCurrentUser(userUpdateDTO);
    }

    /**
     * 删除用户
     */
    @PostMapping("/delete")
    public JsonResponse deleteUser(@RequestParam String userId) {
        User user = userService.deleteUser(userId);
        if (user != null) {
            return JsonResponse.success(user);
        } else {
            return JsonResponse.failure("用户删除失败");
        }
    }

    @PostMapping("/deleteById")
    public JsonResponse deleteById(@RequestParam String userId) {
        boolean b = userService.removeById(userId);
        if (b) {
            return JsonResponse.successMessage("用户删除成功");
        } else {
            return JsonResponse.failure("用户删除失败");
        }
    }

    @RequestMapping(value = "/getById/{id}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse<User> getById(@PathVariable("id") String id)throws Exception {
        User user = userService.getById(id);
        return JsonResponse.success(user);
    }

    /**
     * 用户注册接口
     */
    @PostMapping("/register")
    public JsonResponse register(@RequestBody UserInsertDTO userInsertDTO) {
        return userService.insertUser(userInsertDTO);
    }

    /**
     * 用户登录接口
     */
    @PostMapping("/login")
    public JsonResponse<User> login(@RequestBody User user) {
        return userService.login(user);
    }

    /**
     * 获取当前登录用户
     */
    @GetMapping("/getInfo")
    public JsonResponse<User> getInfo() {
        User currentUser = SessionUtils.getCurrentUserInfo();
        if (currentUser != null) {
            return JsonResponse.success(currentUser);
        } else {
            return JsonResponse.failure("未登录或用户信息不存在");
        }
    }

    /**
     * 获取用户列表（统一搜索）
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @param query 搜索关键词（同时匹配昵称、电话、邮箱）
     */
    @GetMapping("/list")
    public JsonResponse<Page<User>> getUserList(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String query) {

        Page<User> page = new Page<>(pageNum, pageSize);
        return JsonResponse.success(userService.searchUserLike(page, query));
    }

    /**
     * 更新用户状态（冻结/解冻）
     */
    @PostMapping("/updateStatus")
    public JsonResponse<User> updateUserStatus(
            @RequestBody Map<String, String> request) {  // 改为接收JSON
        String userId = request.get("userId");
        String status = request.get("status");
        UserUpdateDTO dto = (UserUpdateDTO) new UserUpdateDTO().setUserId(userId).setUserStatus(status);
        return userService.updateUser(dto);
    }

    //获得所有用户
    @GetMapping("getUsers")
    public JsonResponse getUsers() {
        List<User> list = userService.getUsers();
        return JsonResponse.success(list);
    }
}
