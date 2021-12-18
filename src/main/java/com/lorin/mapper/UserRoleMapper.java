package com.lorin.mapper;

import com.lorin.entity.UserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lorin
 * @since 2021-12-17
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {

    List<UserRole> listByMenuId(Long menuId);
}
