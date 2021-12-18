package com.lorin.controller;


import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lorin.aop.LogAnnotation;
import com.lorin.common.Result;
import com.lorin.common.dto.MenuDto;
import com.lorin.entity.Menu;
import com.lorin.entity.RoleMenu;
import com.lorin.entity.User;
import com.lorin.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author lorin
 * @since 2021-12-17
 */
@Api(value = "菜单Controller", tags = {"菜单路由接口"})
@RestController
@RequestMapping("/sys/menu")

public class MenuController {
    @Autowired
    UserService userService;
    @Autowired
    MenuService menuService;
    @Autowired
    RoleMenuService roleMenuService;

    @ApiOperation(value = "获取菜单和权限信息")
    @LogAnnotation(module = "菜单", operation = "获取菜单和权限信息")
    @GetMapping("/nav")
    public Result nav(Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        // 获取权限信息
        String authority = userService.getUserAuthority(user.getId());
        String[] authoritiesArray = StringUtils.tokenizeToStringArray(authority, ",");

        //获取导航信息
        List<MenuDto> navs = menuService.getUserNavById(user.getId());
        if (navs == null) {
            return Result.success("");
        }
        return Result.success(MapUtil
                .builder()
                .put("authorities", authoritiesArray)
                .put("nav", navs)
                .build());
    }

    @ApiOperation(value = "获取选中菜单信息")
    @LogAnnotation(module = "菜单", operation = "获取选中菜单信息")
    @PreAuthorize("hasAuthority('sys:menu:list')")
    @GetMapping("/info/{id}")
    public Result info(@PathVariable("id") Long id) {
        return Result.success(menuService.getById(id), "获取选中菜单信息成功");
    }

    @ApiOperation(value = "获取所有菜单信息")
    @LogAnnotation(module = "菜单", operation = "获取所有菜单信息")
    @PreAuthorize("hasAuthority('sys:menu:list')")
    @GetMapping("/list")
    public Result list() {
        List<Menu> menus = menuService.menuTreeList();
        return Result.success(menus, "获取所有菜单信息成功");
    }

    @ApiOperation(value = "添加菜单信息")
    @LogAnnotation(module = "菜单", operation = "添加菜单信息")
    @PreAuthorize("hasAuthority('sys:menu:save')")
    @PostMapping("/add")
    public Result add(@Validated @RequestBody Menu menu) {
        menu.setCreateTime(LocalDateTime.now());
        menuService.save(menu);
        return Result.success(menu, "添加菜单信息成功");
    }
    @ApiOperation(value = "更新菜单信息")
    @LogAnnotation(module = "菜单", operation = "更新菜单信息")
    @PreAuthorize("hasAuthority('sys:menu:update')")
    @PutMapping("/update")
    public Result update(@Validated @RequestBody Menu menu) {
        menu.setUpdateTime(LocalDateTime.now());
        menuService.updateById(menu);
        userService.clearUserAuthorityByMenuId(menu.getId());
        return Result.success(menu, "更新菜单信息成功");
    }
    @ApiOperation(value = "删除菜单信息")
    @LogAnnotation(module = "菜单", operation = "删除菜单信息")
    @PreAuthorize("hasAuthority('sys:menu:delete')")
    @DeleteMapping("/del/{id}")
    @Transactional
    public Result delete(@PathVariable("id") Long id) {
        Long count = menuService.count(new QueryWrapper<Menu>().eq("parent_id", id));
        if (count > 0) {
            return Result.fail("请先删除子菜单");
        }
        // 清除缓存
        userService.clearUserAuthorityByMenuId(id);
        menuService.removeById(id);
        // 删除中间表
        roleMenuService.remove(new QueryWrapper<RoleMenu>().eq("menu_id", id));
        return Result.success("", "删除菜单信息成功");
    }
}
