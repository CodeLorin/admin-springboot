package com.lorin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lorin.aop.LogAnnotation;
import com.lorin.common.Result;
import com.lorin.entity.Role;
import com.lorin.entity.Teacher;
import com.lorin.entity.User;
import com.lorin.entity.UserRole;
import com.lorin.service.TeacherService;
import com.lorin.utils.PageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author lorin
 * @since 2021-12-18
 */
@Api(value = "老师Controller", tags = {"老师路由接口"})
@RestController
@RequestMapping("/sys/teacher")
public class TeacherController {
    @Autowired
    TeacherService teacherService;
    @Autowired
    PageUtil pageUtil;

    @ApiOperation(value = "获取全部老师信息")
    @LogAnnotation(module = "老师", operation = "获取全部老师信息")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('sys:teacher:list')")
    public Result list(String query) {
        Page<Teacher> teacherData = teacherService.page(pageUtil.getPage(), new QueryWrapper<Teacher>().like(StringUtils.isNotBlank(query), "name", query));
        return Result.success(teacherData, "获取全部老师信息成功");
    }

    @ApiOperation(value = "获取选中老师信息")
    @LogAnnotation(module = "老师", operation = "获取选中老师信息")
    @GetMapping("/info/{id}")
    @PreAuthorize("hasAuthority('sys:teacher:list')")
    public Result info(@PathVariable("id") Long id) {
        Teacher teacher = teacherService.getById(id);
        Assert.notNull(teacher, "找不到该老师");
        return Result.success(teacher, "获取老师信息成功");
    }

    @ApiOperation(value = "增加老师信息")
    @LogAnnotation(module = "老师", operation = "增加老师信息")
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('sys:teacher:save')")
    public Result add(@Validated @RequestBody Teacher teacher) {
        teacherService.save(teacher);
        return Result.success("", "增加老师成功");
    }

    @ApiOperation(value = "更新老师信息")
    @LogAnnotation(module = "老师", operation = "更新老师信息")
    @PutMapping("/update")
    @PreAuthorize("hasAuthority('sys:teacher:update')")
    public Result update(@Validated @RequestBody Teacher teacher) {
        teacherService.updateById(teacher);
        return Result.success("", "更新老师成功");
    }

    @ApiOperation(value = "删除老师信息")
    @LogAnnotation(module = "老师", operation = "删除老师")
    @Transactional
    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('sys:teacher:delete')")
    public Result delete(@RequestBody Long[] ids) {
        teacherService.removeByIds(Arrays.asList(ids));
        return Result.success("", "删除老师成功");
    }

}
