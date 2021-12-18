package com.lorin.security;

import cn.hutool.core.map.MapUtil;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lorin.common.Result;
import com.lorin.entity.User;
import com.lorin.service.UserService;
import com.lorin.utils.JwtUtils;
import com.lorin.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import java.util.HashMap;
import java.util.Map;

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

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json; charset=UTF-8");
        ServletOutputStream outputStream = response.getOutputStream();
        /**
         * 生成jwt
         */
        String jwt = jwtUtils.generateToken(authentication.getName());
        redisUtil.set("TOKEN_" + jwt, authentication.getName(), expire);

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
