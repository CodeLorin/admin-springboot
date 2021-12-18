package com.lorin.mapper;

import com.lorin.entity.User;
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
public interface UserMapper extends BaseMapper<User> {

    List<Long> getNavMenu(Long userId);

    List<User> listByMenuId(Long menuId);
}
