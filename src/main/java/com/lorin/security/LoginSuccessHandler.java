package com.lorin.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.lorin.common.Const;
import com.lorin.common.Result;
import com.lorin.common.dto.OnlineUserDto;
import com.lorin.entity.User;
import com.lorin.service.UserService;
import com.lorin.utils.IpUtils;
import com.lorin.utils.JwtUtils;
import com.lorin.utils.RedisUtil;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * TODO
 *
 * @author lorin
 * @date 2021/12/17 2:27
 */


@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    RedisUtil redisUtil;
    @Value("${lorin.jwt.expire}")
    public Long expire;
    @Autowired
    UserService userService;
    @Autowired
    RedisTemplate redisTemplate;

    public void logoutOnlineUser(String name) {
        ObjectMapper mapper = new ObjectMapper();
        Collection<String> keys = redisTemplate.keys(Const.REDIS_HEADER + "*");
        if (keys == null) {
            return;
        }
        for (String key : keys) {
            Object onlineUser = redisTemplate.opsForValue().get(key);
            if (onlineUser != null) {
                OnlineUserDto onlineUserDto = mapper.convertValue(onlineUser, OnlineUserDto.class);
                String username = onlineUserDto.getUsername();
                if (username.equals(name)) {
                    redisUtil.del(Const.REDIS_HEADER + onlineUserDto.getTokenId());
                    return;
                }


            }

        }
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json; charset=UTF-8");
        ServletOutputStream outputStream = response.getOutputStream();
        /**
         * 当前用户是否在线
         */
        logoutOnlineUser(authentication.getName());
        /**
         /**
         * 生成jwt
         */
        String jwt = jwtUtils.generateToken(authentication.getName());
        /**
         * redis存储jwt和登录信息
         */
        OnlineUserDto onlineUserDto = new OnlineUserDto();
        onlineUserDto.setUsername(authentication.getName());
        String ip = IpUtils.getIpAddress(request);
        onlineUserDto.setIp(ip);
        String location = IpUtils.getIpSource(ip);
        if ("".equals(location)) {
            location = "局域网地址";
        }
        onlineUserDto.setLocation(location);
        UserAgent userAgent = IpUtils.getUserAgent(request);
        onlineUserDto.setBrowser(String.valueOf(userAgent.getBrowser()));
        onlineUserDto.setOs(String.valueOf(userAgent.getOperatingSystem()));
        onlineUserDto.setLoginTime(String.valueOf(LocalDateTime.now()));
        onlineUserDto.setTokenId(jwt);
        redisUtil.set(Const.REDIS_HEADER + jwt, onlineUserDto, expire);
        /**
         * 更新用户登录时间
         */
        User user = userService.getUserByUsername(authentication.getName());
        user.setLastLogin(LocalDateTime.now());
        userService.updateById(user);

        Result result = Result.success(jwt, "登录成功");
        ObjectMapper mapper = new ObjectMapper();
        outputStream.write(mapper.writeValueAsString(result).getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();
    }
}
