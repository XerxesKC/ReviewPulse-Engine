package com.example.mybatisplusdemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatisplusdemo.common.JsonResponse;
import com.example.mybatisplusdemo.common.utls.SessionUtils;
import com.example.mybatisplusdemo.model.domain.User;
import com.example.mybatisplusdemo.mapper.UserMapper;
import com.example.mybatisplusdemo.model.dto.UserDTO;
import com.example.mybatisplusdemo.model.dto.UserInsertDTO;
import com.example.mybatisplusdemo.model.dto.UserSearchDTO;
import com.example.mybatisplusdemo.model.dto.UserUpdateDTO;
import com.example.mybatisplusdemo.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author lyc
 * @since 2025-07-04
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;

    private static final String NAME_REGEX = "^[\\u4E00-\\u9FA5\\w\\p{P}]{1,30}$";
    private static final String PHONE_REGEX = "^\\d{11}$"; // 11位数字
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"; // 邮箱格式
    private static final String PASSWORD_REGEX = "^[a-zA-Z0-9!@#$%^&*()_+\\-=\\[\\]{};':\",./<>?`~]{4,20}$"; // 要求密码长度在4到20个字符之间，允许数字、英文和常见标点符号

    private String generateUserId() {
        return java.util.UUID.randomUUID().toString().replace("-", "");
    }

    @Override
    public Page<User> searchUser(Page<User> page, UserSearchDTO userSearchDTO) {
        return userMapper.selectUserPage(page, userSearchDTO);
    }

//    @Override
//    public boolean register(User user) {
//        // 手机号或邮箱必须有一个
//        if ((user.getPhone() == null || user.getPhone().isEmpty()) && (user.getEmail() == null || user.getEmail().isEmpty())) {
//            return false;
//        }
//        // 校验手机号或邮箱唯一
//        QueryWrapper<User> query = new QueryWrapper<>();
//        if (user.getPhone() != null && !user.getPhone().isEmpty()) {
//            query.eq("phone", user.getPhone());
//        } else {
//            query.eq("email", user.getEmail());
//        }
//        if (this.count(query) > 0) {
//            return false;
//        }
//        // 分配递增userId
//        user.setUserId(generateUserId());
//        // 密码不加密，直接存储
//        // 保存用户
//        return this.save(user);
//    }

    @Override
    public JsonResponse login(User user) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName, user.getUserName())
                .eq(User::getPassword, user.getPassword());
        User currentUser = userMapper.selectOne(queryWrapper);

        if (currentUser == null) {
            return JsonResponse.failure("用户不存在或密码错误");
        }

        if ("active".equals(currentUser.getUserStatus())) {
            SessionUtils.saveCurrentUserInfo(currentUser);
            return JsonResponse.success(currentUser, "登录成功");
        } else if ("frozen".equals(currentUser.getUserStatus())) {
            return JsonResponse.failure("用户被冻结");
        } else {
            return JsonResponse.failure("未知用户状态");
        }
    }

    @Override
    public JsonResponse insertUser(UserInsertDTO userInsertDTO) {
        //1.非空校验
        if(userInsertDTO.getUserName()==null||userInsertDTO.getUserName().trim().isEmpty()){
            return JsonResponse.failure("用户名不能为空");
        }
        if(userInsertDTO.getPassword()==null||userInsertDTO.getPassword().trim().isEmpty()){
            return JsonResponse.failure("密码不能为空");
        }

        //2.唯一性校验
        if (isUserNameExist(userInsertDTO)) {
            return JsonResponse.failure("用户名已存在");
        }
        if(isPhoneExist(userInsertDTO)) {
            return JsonResponse.failure("手机号已存在");
        }

        //3.合法性校验
        if(!isUserNameLegal(userInsertDTO)) {
            return JsonResponse.failure("用户名不合法，长度1-30个字符");
        }
        if (!isPhoneLegal(userInsertDTO)){
            return JsonResponse.failure("手机号不合法");
        }
        if (!isEmailLegal(userInsertDTO)){
            return JsonResponse.failure("邮箱不合法");
        }
        if (!isPasswordLegal(userInsertDTO)){
            return JsonResponse.failure("密码不合法，长度4-20个字符");
        }

        //4.入库
        User user = new User();
        org.springframework.beans.BeanUtils.copyProperties(userInsertDTO, user);
        // 生成 userId
        user.setUserId(generateUserId());
        // 默认状态为 active
        user.setUserStatus("active");
        // 设置创建时间和更新时间为当前时间
        user.setCreateTime(java.time.LocalDateTime.now());
        user.setUpdateTime(java.time.LocalDateTime.now());
        // isDeleted 默认未删除
        user.setIsDeleted("F");
        boolean result = this.save(user);
        return result ? JsonResponse.success(user) : JsonResponse.failure("新增用户失败");
    }

    @Override
    public JsonResponse updateUser(UserUpdateDTO userUpdateDTO) {
        // 1.非空校验
        if (userUpdateDTO.getUserId() == null || userUpdateDTO.getUserId().trim().isEmpty()) {
            return JsonResponse.failure("用户ID不能为空");
        }

        User user = getById(userUpdateDTO.getUserId());
        if (user == null) {
            return JsonResponse.failure("用户不存在");
        }

        //2.唯一性校验
        if(userUpdateDTO.getUserName()!=null&&!userUpdateDTO.getUserName().trim().isEmpty()) {
            if (!user.getUserName().equals(userUpdateDTO.getUserName()) && isUserNameExist(userUpdateDTO)) {
                return JsonResponse.failure("用户名已存在");
            }
        }
        if(userUpdateDTO.getPhone()!=null&&!userUpdateDTO.getPhone().trim().isEmpty()) {
            if (!user.getPhone().equals(userUpdateDTO.getPhone()) && isPhoneExist(userUpdateDTO)) {
                return JsonResponse.failure("手机号已存在");
            }
        }

        //3.合法性校验
        if(userUpdateDTO.getUserName()!=null&&!userUpdateDTO.getUserName().trim().isEmpty()) {
            if (!isUserNameLegal(userUpdateDTO)) {
                return JsonResponse.failure("用户名不合法，长度1-30个字符");
            }
        }
        if(userUpdateDTO.getPhone()!=null&&!userUpdateDTO.getPhone().trim().isEmpty()){
            if (!isPhoneLegal(userUpdateDTO)){
                return JsonResponse.failure("手机号不合法");
            }
        }
        if(userUpdateDTO.getEmail()!=null&&!userUpdateDTO.getEmail().trim().isEmpty()) {
            if (!isEmailLegal(userUpdateDTO)) {
                return JsonResponse.failure("邮箱不合法");
            }
        }
        if(userUpdateDTO.getEmail()!=null&&!userUpdateDTO.getEmail().trim().isEmpty()) {
            if (!isPasswordLegal(userUpdateDTO)) {
                return JsonResponse.failure("密码不合法，长度4-20个字符");
            }
        }

        //4.入库

        // 字段一一对应赋值，只有非空/非空字符串才赋值
        if (userUpdateDTO.getUserName() != null && !userUpdateDTO.getUserName().isEmpty()) {
            user.setUserName(userUpdateDTO.getUserName());
        }
        if (userUpdateDTO.getPhone() != null && !userUpdateDTO.getPhone().isEmpty()) {
            user.setPhone(userUpdateDTO.getPhone());
        }
        if (userUpdateDTO.getEmail() != null && !userUpdateDTO.getEmail().isEmpty()) {
            user.setEmail(userUpdateDTO.getEmail());
        }
        if (userUpdateDTO.getPassword() != null && !userUpdateDTO.getPassword().isEmpty()) {
            user.setPassword(userUpdateDTO.getPassword());
        }
        if (userUpdateDTO.getAvatar() != null && !userUpdateDTO.getAvatar().isEmpty()) {
            user.setAvatar(userUpdateDTO.getAvatar());
        }
        if (userUpdateDTO.getGender() != null && !userUpdateDTO.getGender().isEmpty()) {
            user.setGender(userUpdateDTO.getGender());
        }
        if (userUpdateDTO.getBirthday() != null) {
            user.setBirthday(userUpdateDTO.getBirthday());
        }
        if (userUpdateDTO.getShowCollection() != null && !userUpdateDTO.getShowCollection().isEmpty()) {
            user.setShowCollection(userUpdateDTO.getShowCollection());
        }
        if (userUpdateDTO.getShowComment() != null && !userUpdateDTO.getShowComment().isEmpty()) {
            user.setShowComment(userUpdateDTO.getShowComment());
        }
        if (userUpdateDTO.getUserStatus() != null && !userUpdateDTO.getUserStatus().isEmpty()) {
            user.setUserStatus(userUpdateDTO.getUserStatus());
        }
        if (userUpdateDTO.getIsDeleted() != null && !userUpdateDTO.getIsDeleted().isEmpty()) {
            user.setIsDeleted(userUpdateDTO.getIsDeleted());
        }
        user.setUpdateTime(java.time.LocalDateTime.now()); // 更新时间
        // 其他字段如有补充
        boolean success = updateById(user);
        return success ? JsonResponse.success(user) : JsonResponse.failure("更新用户失败");
    }

    @Override
    public JsonResponse updateCurrentUser(UserUpdateDTO userUpdateDTO) {
        JsonResponse jsonResponse = updateUser(userUpdateDTO);
        if (jsonResponse.getData() != null) {
            // 更新成功后，更新当前用户信息到Session
            SessionUtils.saveCurrentUserInfo((User)jsonResponse.getData());
        }
        return jsonResponse;
    }


    @Override
    public User deleteUser(String userId) {
        // 逻辑删除，将isDeleted字段设为1
        User user = getById(userId);
        if (user == null) {
            return user;
        }
        user.setIsDeleted("T");
        user.setUpdateTime(java.time.LocalDateTime.now());
        updateById(user);
        return user;
    }

    @Override
    public Page<User> searchUserLike(Page<User> page, String query) {
        if (StringUtils.isBlank(query)) {
            return this.page(page);
        }

        return lambdaQuery()
                .and(wrapper -> wrapper
                        .like(User::getUserName, query)
                        .or()
                        .like(User::getPhone, query)
                        .or()
                        .like(User::getEmail, query)
                )
                .page(page);
    }

    @Override
    public List<User> getUsers() {
        List<User> list = new ArrayList<>();
        list = userMapper.getUsers();
        return list;
    }

    private boolean isPasswordLegal(UserDTO userDTO) {
        String password = userDTO.getPassword();
        if (password == null || password.trim().isEmpty()) {
            return true;
        }
        return password.matches(PASSWORD_REGEX) ;
    }

    private boolean isEmailLegal(UserDTO userDTO) {
        String email = userDTO.getEmail();
        if(email == null || email.trim().isEmpty()){
            return true;
        }
        return email.matches(EMAIL_REGEX);
    }

    private boolean isPhoneLegal(UserDTO userDTO) {
        String phone = userDTO.getPhone();
        if(phone == null || phone.trim().isEmpty()){
            return true;
        }
        return phone.matches(PHONE_REGEX);
    }

    private boolean isUserNameLegal(UserDTO userDTO) {
        String userName = userDTO.getUserName();
        if (userName == null || userName.trim().isEmpty()) {
            return true;
        }
        return userName.matches(NAME_REGEX);
    }

    private boolean isPhoneExist(UserDTO userDTO) {
        if (userDTO.getPhone() == null || userDTO.getPhone().trim().isEmpty()) {
            return false;
        }
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPhone, userDTO.getPhone());
        if (this.count(queryWrapper) > 0) {
            return true;
        }
        return false;
    }

    private boolean isUserNameExist(UserDTO userDTO) {
        if (userDTO.getUserName() == null || userDTO.getUserName().trim().isEmpty()) {
            return false; // 如果 userName 为空，则不检查重复
        }
        // 检查 userName 是否重复
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName, userDTO.getUserName());
        if (this.count(queryWrapper) > 0) {
            return true;
        }
        return false;
    }


}