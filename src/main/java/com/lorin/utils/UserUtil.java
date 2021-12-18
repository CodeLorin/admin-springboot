package com.lorin.utils;

import com.lorin.entity.User;
import com.lorin.security.AccountUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * 用户工具类
 *
 * @author lorin
 */
@Component
public class UserUtil {

    /**
     * 获取当前登录用户
     *
     * @return 用户登录信息
     */
    public static String getLoginUser() {
        return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}