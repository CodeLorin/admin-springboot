package com.lorin.controller;

import com.lorin.aop.LogAnnotation;
import com.lorin.common.Result;
import com.lorin.common.dto.UserInfoDto;
import com.lorin.entity.User;
import com.lorin.service.UserService;
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
public class UserInfoController {
    @Autowired
    UserService userService;

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
