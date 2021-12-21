package com.lorin.security;

import cn.hutool.core.util.StrUtil;
import com.lorin.common.ErrorCode;
import com.lorin.entity.User;
import com.lorin.service.UserService;
import com.lorin.utils.JwtUtils;
import com.lorin.utils.RedisUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * TODO
 *
 * @author lorin
 * @date 2021/12/17 2:56
 */

public class JwtAuthenticationFilter extends BasicAuthenticationFilter {
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    UserDetailServiceImpl userDetailService;
    @Autowired
    UserService userService;
    @Autowired
    RedisUtil redisUtil;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String jwt = request.getHeader(jwtUtils.getHeader());
        /**
         * 没有jwt,去登录
         */
        if (StrUtil.isBlankOrUndefined(jwt)) {
            chain.doFilter(request, response);
            return;
        }
        Claims claim = jwtUtils.getClaimByToken(jwt);
        /**
         * jwt非法或者过期
         */

//        if (claim == null) {
//            throw new JwtException(ErrorCode.TOKEN_ERROR.getMsg());
//        }
//        if (jwtUtils.isTokenExpired(claim)) {
//            throw new JwtException(ErrorCode.TOKEN_TIMEOUT.getMsg());
//        }
        /**
         * 去redis里面查找
         */
        boolean flag = redisUtil.hasKey("TOKEN_" + jwt);
        if (!flag) {
            throw new JwtException(ErrorCode.TOKEN_TIMEOUT.getMsg());
        }

        String username = claim.getSubject();
        /**
         * 1.通过用户名获取id
         * 2.通过id获取权限
         */
        User user = userService.getUserByUsername(username);
        Long userId = user.getId();
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, null, userDetailService.getUserAuthority(userId));
        SecurityContextHolder.getContext().setAuthentication(token);

        chain.doFilter(request, response);
    }
}
