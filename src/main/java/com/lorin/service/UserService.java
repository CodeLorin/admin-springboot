package com.lorin.service;

import com.lorin.common.Result;
import com.lorin.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author lorin
 * @since 2021-12-17
 */
public interface UserService extends IService<User> {
    /**
     * 通过用户名获取用户
     *
     * @param username
     * @return com.lorin.entity.User
     * @author lorin
     * @date 2021/12/17 10:29
     */
    User getUserByUsername(String username);

    /**
     * 获取用户权限
     *
     * @param userId
     * @return java.lang.String
     * @author lorin
     */
    String getUserAuthority(Long userId);
    /**
     * 清除权限缓存
     * @author lorin
     * @param userId
     */
    void clearUserAuthority(Long userId);
    /**
     * TODO
     * @author lorin
     * @param roleId
     */
    void clearUserAuthorityByRoleId(Long roleId);
    /**
     * TODO
     * @author lorin
     * @param menuId
     */
    void clearUserAuthorityByMenuId(Long menuId);

}
