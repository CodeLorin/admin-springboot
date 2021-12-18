package com.lorin.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lorin.common.dto.MenuDto;
import com.lorin.entity.Menu;
import com.lorin.mapper.MenuMapper;
import com.lorin.mapper.UserMapper;
import com.lorin.service.MenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    UserMapper userMapper;

    @Override
    public List<MenuDto> getUserNavById(Long id) {
        List<Long> menuIds = userMapper.getNavMenu(id);
        if (menuIds.size() == 0) {
            return null;
        }
        List<Menu> menus = this.listByIds(menuIds);

        //树状结构
        List<Menu> menuTree = buildTree(menus);
        //实体转dto
        return convert(menuTree);
    }

    @Override
    public List<Menu> menuTreeList() {
        // 获取所有菜单信息
        List<Menu> menuList = this.list(new QueryWrapper<Menu>().orderByAsc("orderNum"));
        // 转成树状结构
        return buildTree(menuList);
    }


    private List<Menu> buildTree(List<Menu> menus) {
        List<Menu> finalMenus = new ArrayList<>();
        // 找出自己的child
        for (Menu menu : menus) {
            for (Menu e : menus) {
                if (menu.getId().equals(e.getParentId())) {
                    menu.getChildren().add(e);
                }
            }
            if (menu.getParentId() == 0L) {
                finalMenus.add(menu);
            }
        }
        System.out.println(JSONUtil.toJsonStr(finalMenus));
        return finalMenus;
    }

    private List<MenuDto> convert(List<Menu> menuTree) {
        List<MenuDto> menuDto = new ArrayList<>();
        menuTree.forEach(m -> {
            MenuDto dto = new MenuDto();
            dto.setId(m.getId());
            dto.setName(m.getPerms());
            dto.setTitle(m.getName());
            dto.setIcon(m.getIcon());
            dto.setComponent(m.getComponent());
            dto.setPath(m.getPath());
            if (m.getChildren().size() > 0) {
                dto.setChildren(convert(m.getChildren()));
            }
            menuDto.add(dto);

        });
        return menuDto;
    }


}
