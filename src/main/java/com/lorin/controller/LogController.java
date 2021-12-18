package com.lorin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lorin.aop.LogAnnotation;
import com.lorin.common.Result;
import com.lorin.entity.Log;
import com.lorin.service.LogService;
import com.lorin.utils.PageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author lorin
 * @since 2021-12-17
 */
@Api(value = "日志Controller", tags = {"日志路由接口"})
@RestController
@RequestMapping("/sys/log")
public class LogController {
    @Autowired
    LogService logService;
    @Autowired
    PageUtil pageUtil;

    @PreAuthorize("hasAuthority('sys:log:list')")
    @ApiOperation(value = "获取日志信息")
    @GetMapping("/list")
    public Result logList(String query) {
        Page<Log> logData = logService.page(pageUtil.getPage(), new QueryWrapper<Log>().like(StringUtils.isNotBlank(query), "username", query));
        return Result.success(logData, "获取日志信息成功");
    }

    @PreAuthorize("hasAuthority('sys:log:delete')")
    @ApiOperation(value = "删除日志信息")
    @LogAnnotation(module = "日志", operation = "删除日志")
    @DeleteMapping("/delete")
    public Result delete(@RequestBody Long[] ids) {
        logService.removeByIds(Arrays.asList(ids));
        return Result.success("", "删除日志成功");
    }
}
