package com.lorin.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lorin.common.ErrorCode;
import com.lorin.common.Result;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * TODO
 *
 * @author lorin
 * @date 2021/12/17 2:23
 */

@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setContentType("application/json; charset=UTF-8");
        ServletOutputStream outputStream = response.getOutputStream();
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        Result result = null;
        if ("Bad credentials".equals(exception.getMessage())) {
            result = Result.fail(ErrorCode.LOGIN_ERROR.getCode(), "用户名或者密码错误");
        } else {
            result = Result.fail(ErrorCode.LOGIN_ERROR.getCode(), exception.getMessage());
        }
        ObjectMapper mapper = new ObjectMapper();
        outputStream.write(mapper.writeValueAsString(result).getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();
    }
}
