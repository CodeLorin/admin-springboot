package com.lorin.service;

import com.lorin.common.dto.MenuDto;
import com.lorin.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * The interface Menu service.
 */
public interface MenuService extends IService<Menu> {
    /**
     * Gets user nav by id.
     *
     * @param id the id
     * @return the user nav by id
     */
    List<MenuDto> getUserNavById(Long id);

    /**
     * Menu tree list list.
     *
     * @return the list
     */
    List<Menu> menuTreeList();
}
