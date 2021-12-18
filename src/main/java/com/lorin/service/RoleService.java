package com.lorin.service;

import com.lorin.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lorin
 * @since 2021-12-17
 */
public interface RoleService extends IService<Role> {
    /**
     * TODO
     * @param id
     * @return java.util.List<com.lorin.entity.Role>
     */
    List<Role> listRolesByUserId(Long id);
}
