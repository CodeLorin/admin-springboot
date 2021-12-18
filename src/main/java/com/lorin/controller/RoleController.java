package com.lorin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lorin.aop.LogAnnotation;
import com.lorin.common.Const;
import com.lorin.common.Result;
import com.lorin.entity.Role;
import com.lorin.entity.RoleMenu;
import com.lorin.entity.UserRole;
import com.lorin.service.RoleMenuService;
import com.lorin.service.RoleService;
import com.lorin.service.UserRoleService;
import com.lorin.service.UserService;
import com.lorin.utils.PageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author lorin
 * @since 2021-12-17
 */
@RestController
@RequestMapping("/sys/role")
@Api(value = "角色Controller", tags = {"角色路由接口"})
public class RoleController {
    @Autowired
    RoleService roleService;
    @Autowired
    RoleMenuService roleMenuService;
    @Autowired
    PageUtil pageUtil;
    @Autowired
    UserService userService;
    @Autowired
    UserRoleService userRoleService;

    @ApiOperation(value = "获取角色信息")
    @LogAnnotation(module = "角色", operation = "获取角色信息")
    @PreAuthorize("hasAuthority('sys:role:list')")
    @GetMapping("/info/{id}")
    public Result info(@PathVariable("id") Long id) {
        Role role = roleService.getById(id);
        // 获取角色关联的菜单id
        List<RoleMenu> roleMenus = roleMenuService.list(new QueryWrapper<RoleMenu>().eq("role_id", id));
        List<Long> menuIds = roleMenus.stream().map(p -> p.getMenuId()).collect(Collectors.toList());
        role.setMenuIds(menuIds);
        return Result.success(role, "获取角色信息成功");
    }

    @ApiOperation(value = "获取全部角色信息")
    @LogAnnotation(module = "角色", operation = "获取全部角色信息")
    @PreAuthorize("hasAuthority('sys:role:list')")
    @GetMapping("/list")
    public Result list(String query) {
        Page<Role> pageData = roleService.page(pageUtil.getPage(), new QueryWrapper<Role>().like(StringUtils.isNotBlank(query), "name", query));
        return Result.success(pageData, "获取全部角色信息成功");
    }

    @ApiOperation(value = "保存角色信息")
    @LogAnnotation(module = "角色", operation = "保存角色信息")
    @PreAuthorize("hasAuthority('sys:role:save')")
    @PostMapping("/add")
    public Result add(@Validated @RequestBody Role role) {
        System.out.println(role);
        role.setCreateTime(LocalDateTime.now());
        role.setStatus(Const.STATUS_ON);
        roleService.save(role);
        return Result.success(role, "添加角色成功");
    }

    @ApiOperation(value = "更新角色信息")
    @LogAnnotation(module = "角色", operation = "更新角色信息")
    @PreAuthorize("hasAuthority('sys:role:update')")
    @PutMapping("/update")
    public Result update(@Validated @RequestBody Role role) {
        role.setUpdateTime(LocalDateTime.now());
        roleService.updateById(role);
        // 删除缓存
        userService.clearUserAuthorityByRoleId(role.getId());
        return Result.success(role, "更新角色成功");
    }

    @ApiOperation(value = "删除角色信息")
    @LogAnnotation(module = "角色", operation = "删除角色信息")
    @PreAuthorize("hasAuthority('sys:role:delete')")
    @DeleteMapping("/delete")
    @Transactional
    public Result delete(@RequestBody Long[] ids) {
        roleService.removeByIds(Arrays.asList(ids));
        // 删除中间表
        userRoleService.remove(new QueryWrapper<UserRole>().in("role_id", ids));
        roleMenuService.remove(new QueryWrapper<RoleMenu>().in("role_id", ids));
        // 删除缓存
        for (Long id : ids) {
            userService.clearUserAuthorityByRoleId(id);
        }

        return Result.success("", "删除角色成功");
    }

    @ApiOperation(value = "分配角色权限")
    @LogAnnotation(module = "角色", operation = "分配角色权限")
    @PreAuthorize("hasAuthority('sys:role:perm')")
    @PostMapping("/perm/{id}")
    @Transactional
    public Result handlerPerm(@PathVariable("id") Long id, @RequestBody Long[] menuIds) {
        List<RoleMenu> roleMenus = new ArrayList<>();
        Arrays.stream(menuIds).forEach(menuId -> {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setMenuId(menuId);
            roleMenu.setRoleId(id);

            roleMenus.add(roleMenu);
        });
        // 先删除原来的记录,再保存新的
        roleMenuService.remove(new QueryWrapper<RoleMenu>().eq("role_id", id));
        roleMenuService.saveBatch(roleMenus);

        userService.clearUserAuthorityByRoleId(id);
        return Result.success(menuIds, "分配用户权限成功");
    }
}
