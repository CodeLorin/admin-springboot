package com.lorin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lorin.aop.LogAnnotation;
import com.lorin.common.Const;
import com.lorin.common.Result;
import com.lorin.common.dto.OnlineUserDto;
import com.lorin.utils.PageUtil;
import com.lorin.utils.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;

/**
 * TODO
 *
 * @author lorin
 * @date 2021/12/19 22:45
 */


@Api(value = "在线用户Controller", tags = {"在线用户路由接口"})
@RestController
@RequestMapping("/sys/onlineuser")
public class OnlineUserController {
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    PageUtil pageUtil;
    @Autowired
    RedisUtil redisUtil;

    @ApiOperation(value = "获取所有在线用户信息")
    @LogAnnotation(module = "在线用户", operation = "获取在线用户信息")
    @PreAuthorize("hasAuthority('sys:online:list')")
    @GetMapping("/list")
    public Result getOnlineUser() {
        ArrayList<OnlineUserDto> onlineUserList = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        Collection<String> keys = redisTemplate.keys(Const.REDIS_HEADER + "*");
        for (String key : keys) {
            Object onlineUser = redisTemplate.opsForValue().get(key);
            if (onlineUser != null) {
                OnlineUserDto onlineUserDto = mapper.convertValue(onlineUser, OnlineUserDto.class);
                onlineUserList.add(onlineUserDto);
            }

        }
        return Result.success(onlineUserList, "获取在线用户信息成功");
    }

    @ApiOperation(value = "下线用户")
    @LogAnnotation(module = "在线用户", operation = "下线在线用户")
    @PreAuthorize("hasAuthority('sys:online:logout')")
    @DeleteMapping("/logout")
    public Result forceLogout(@RequestBody String[] tokenId) {
        if (tokenId != null) {
            for (String s : tokenId) {
                redisUtil.del(Const.REDIS_HEADER + s);
            }
        }

        return Result.success("", "下线用户成功");
    }
}
