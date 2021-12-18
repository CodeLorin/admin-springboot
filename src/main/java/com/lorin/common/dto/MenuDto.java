package com.lorin.common.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 *
 * @author lorin
 * @date 2021/12/17 16:52
 */


@Data
public class MenuDto implements Serializable {
    private Long id;
    private String name;
    private String title;
    private String icon;
    private String path;
    private String component;
    private List<MenuDto> children = new ArrayList<>();
}
