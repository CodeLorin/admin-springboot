package com.lorin.controller;

import com.lorin.aop.LogAnnotation;
import com.lorin.common.Result;
import com.lorin.common.dto.UserInfoDto;
import com.lorin.entity.User;
import com.lorin.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;


/**
 * TODO
 *
 * @author lorin
 * @date 2021/12/17 19:13
 */


@RestController
@Api(value = "用户信息Controller", tags = {"用户信息路由接口"})
public class UserInfoController {
    @Autowired
    UserService userService;

    @ApiOperation(value = "获取全部用户信息")
    @GetMapping("/sys/userInfo")
    public Result userInfo(Principal principal) {
        UserInfoDto userInfoDto = new UserInfoDto();
        User user = userService.getUserByUsername(principal.getName());
        userInfoDto.setId(user.getId());
        userInfoDto.setUsername(user.getUsername());
        userInfoDto.setAvatar(user.getAvatar());
        return Result.success(userInfoDto, "获取用户信息成功");
    }
}
