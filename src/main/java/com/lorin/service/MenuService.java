package com.lorin.service;

import com.lorin.common.dto.MenuDto;
import com.lorin.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author lorin
 * @since 2021-12-17
 */
public interface MenuService extends IService<Menu> {
    /**
     * TODO
     * @author lorin
     * @param id
     * @return java.util.List<com.lorin.common.dto.MenuDto>
     */
    List<MenuDto> getUserNavById(Long id);
    /**
     * TODO
     * @author lorin
     * @return java.util.List<com.lorin.entity.Menu>
     */
    List<Menu> menuTreeList();
}
