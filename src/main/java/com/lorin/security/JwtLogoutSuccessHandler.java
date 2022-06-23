package com.lorin.security;

import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lorin.common.Result;
import com.lorin.entity.User;
import com.lorin.service.UserService;
import com.lorin.utils.JwtUtils;
import com.lorin.utils.RedisUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 退出登录
 *
 * @author lorin
 * @date 2021/12/17 15:58
 * @return null
 */
@Component
public class JwtLogoutSuccessHandler implements LogoutSuccessHandler {

    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    UserService userService;

    // 清楚登录jwt和权限路由
    public void clearAuthentication(String jwt) {
        redisUtil.del("TOKEN_" + jwt);
        Claims claim = jwtUtils.getClaimByToken(jwt);
        String username = claim.getSubject();
        /**
         * 1.通过用户名获取id
         */
        User user = userService.getUserByUsername(username);
        Long userId = user.getId();
        redisUtil.del("GrantedAuthority:" + userId.toString());
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        String jwt = request.getHeader(jwtUtils.getHeader());

        clearAuthentication(jwt);

        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream outputStream = response.getOutputStream();
        Result result = Result.success("", "退出登录成功");
        ObjectMapper mapper = new ObjectMapper();
        outputStream.write(mapper.writeValueAsString(result).getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();
    }
}
