package com.lorin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lorin.common.Result;
import com.lorin.entity.Menu;
import com.lorin.entity.Role;
import com.lorin.entity.User;
import com.lorin.entity.UserRole;
import com.lorin.mapper.UserMapper;
import com.lorin.mapper.UserRoleMapper;
import com.lorin.service.MenuService;
import com.lorin.service.RoleService;
import com.lorin.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lorin.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author lorin
 * @since 2021-12-17
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    RoleService roleService;
    @Autowired
    MenuService menuService;
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    UserRoleMapper userRoleMapper;

    /**
     * 根据用户名获取用户实体类
     *
     * @param username
     * @return com.lorin.entity.User
     * @author lorin
     */
    @Override
    public User getUserByUsername(String username) {
        return getOne(new QueryWrapper<User>().eq("username", username));
    }

    @Override
    public String getUserAuthority(Long userId) {
        String authority = "";
        String key = "GrantedAuthority:" + userId;
        if (redisUtil.hasKey(key)) {
            authority = (String) redisUtil.get(key);
        } else {
            // 获取角色 ROLE_admin,sys:user:list
            // select * from sys_role where id in (select role_id from sys_user_role where user_id =?)
            List<Role> roles = roleService.list(new QueryWrapper<Role>().inSql("id", "select role_id from sys_user_role where user_id = " + userId));
            if (roles.size() > 0) {
                String roleCodes = roles.stream().map(r -> "ROLE_" + r.getCode()).collect(Collectors.joining(","));
                authority = roleCodes.concat(",");
            }
            // 获取菜单权限
            List<Long> menuIds = userMapper.getNavMenu(userId);
            if (menuIds.size() > 0) {
                List<Menu> menus = menuService.listByIds(menuIds);
                String menuPerms = menus.stream().map(m -> m.getPerms()).collect(Collectors.joining(","));
                authority = authority.concat(menuPerms);
            }
            // 增加权限缓存
            redisUtil.set(key, authority, 60 * 60);
        }
        return authority;
    }

    @Override
    public void clearUserAuthority(Long userId) {
        redisUtil.del("GrantedAuthority:" + userId);
    }

    @Override
    public void clearUserAuthorityByRoleId(Long roleId) {
        List<UserRole> roles = userRoleMapper.selectList(new QueryWrapper<UserRole>().eq("role_id", roleId));
        roles.forEach(r -> {
            this.clearUserAuthority(r.getUserId());
        });
    }

    @Override
    public void clearUserAuthorityByMenuId(Long menuId) {
        List<UserRole> userRoles = userRoleMapper.listByMenuId(menuId);
        userRoles.forEach(r -> {
            this.clearUserAuthority(r.getUserId());
        });
    }




}
