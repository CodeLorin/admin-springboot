package com.lorin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lorin.entity.Role;
import com.lorin.mapper.RoleMapper;
import com.lorin.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author lorin
 * @since 2021-12-17
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Override
    public List<Role> listRolesByUserId(Long id) {
        List<Role> roles = this.list(new QueryWrapper<Role>().inSql("id", "select role_id from sys_user_role where user_id=" + id));
        return roles;
    }
}
