package com.lorin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lorin.aop.LogAnnotation;
import com.lorin.common.Const;
import com.lorin.common.Result;
import com.lorin.common.dto.PasswordDto;
import com.lorin.entity.Role;
import com.lorin.entity.User;
import com.lorin.entity.UserRole;
import com.lorin.service.RoleService;
import com.lorin.service.UserRoleService;
import com.lorin.service.UserService;
import com.lorin.utils.PageUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author lorin
 * @since 2021-12-17
 */
@RestController
@RequestMapping("/sys/user")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;
    @Autowired
    PageUtil pageUtil;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    UserRoleService userRoleService;

    @LogAnnotation(module = "用户", operation = "获取选中用户信息")
    @GetMapping("/info/{id}")
    @PreAuthorize("hasAuthority('sys:user:list')")
    public Result info(@PathVariable("id") Long id) {
        User user = userService.getById(id);
        Assert.notNull(user, "找不到该用户");
        List<Role> roleList = roleService.listRolesByUserId(id);
        user.setRoles(roleList);
        return Result.success(user, "获取用户成功");
    }

    @LogAnnotation(module = "用户", operation = "获取全部用户信息")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('sys:user:list')")
    public Result list(String query) {
        Page<User> pageData = userService.page(pageUtil.getPage(), new QueryWrapper<User>().like(StringUtils.isNotBlank(query), "username", query));
        pageData.getRecords().forEach(user -> {
            user.setRoles(roleService.listRolesByUserId(user.getId()));
        });
        return Result.success(pageData, "获取全部用户信息成功");
    }

    @LogAnnotation(module = "用户", operation = "增加用户")
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('sys:user:save')")
    public Result add(@Validated @RequestBody User user) {
        user.setCreateTime(LocalDateTime.now());
        user.setPassword(bCryptPasswordEncoder.encode(Const.DEFAULT_PASSWORD));
        user.setAvatar(Const.DEFAULT_AVATAR);
        userService.save(user);
        return Result.success("", "增加用户成功");
    }

    @LogAnnotation(module = "用户", operation = "更新用户")
    @PutMapping("/update")
    @PreAuthorize("hasAuthority('sys:user:update')")
    public Result update(@Validated @RequestBody User user) {
        user.setUpdateTime(LocalDateTime.now());
        userService.updateById(user);
        return Result.success("", "更新用户成功");
    }

    @LogAnnotation(module = "用户", operation = "删除用户")
    @Transactional
    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('sys:user:delete')")
    public Result delete(@RequestBody Long[] ids) {
        userService.removeByIds(Arrays.asList(ids));
        userRoleService.remove(new QueryWrapper<UserRole>().in("user_id", ids));
        return Result.success("", "删除用户成功");
    }

    @LogAnnotation(module = "用户", operation = "分配角色")
    @Transactional
    @PostMapping("/role/{id}")
    @PreAuthorize("hasAuthority('sys:user:role')")
    public Result rolePerm(@PathVariable("id") Long userId, @RequestBody Long[] roleIds) {

        List<UserRole> userRoles = new ArrayList<>();

        Arrays.stream(roleIds).forEach(r -> {
            UserRole userRole = new UserRole();
            userRole.setRoleId(r);
            userRole.setUserId(userId);

            userRoles.add(userRole);
        });
        userRoleService.remove(new QueryWrapper<UserRole>().eq("user_id", userId));
        userRoleService.saveBatch(userRoles);


        userService.clearUserAuthority(userId);

        return Result.success("", "分配角色成功");
    }

    @LogAnnotation(module = "用户", operation = "修改密码")
    @Transactional
    @PreAuthorize("hasAuthority('sys:user:repass')")
    @PostMapping("repass")
    public Result rePassword(@RequestBody PasswordDto passwordDto) {
        User user = userService.getById(passwordDto.getId());
        user.setPassword(bCryptPasswordEncoder.encode(passwordDto.getPassword()));
        user.setUpdateTime(LocalDateTime.now());
        userService.updateById(user);
        return Result.success("", "修改用户密码成功");
    }

}
