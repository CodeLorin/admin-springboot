package com.lorin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <p>
 *
 * </p>
 *
 * @author lorin
 * @since 2021-12-17
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_menu")
@ApiModel(value = "Menu对象", description = "")
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @NotNull(message = "上级菜单不能为空")
    @ApiModelProperty("父菜单ID，一级菜单为0")
    @TableField("parent_id")
    private Long parentId;

    @NotBlank(message = "菜单名称不能为空")
    @ApiModelProperty("菜单名称")
    @TableField("name")
    private String name;

    @ApiModelProperty("菜单URL")
    @TableField("path")
    private String path;

    @NotBlank(message = "授权码不能为空")
    @ApiModelProperty("授权(多个用逗号分隔，如：user:list,user:create)")
    @TableField("perms")
    private String perms;

    @ApiModelProperty("组件名称")
    @TableField("component")
    private String component;

    @NotNull(message = "类型不能为空")
    @ApiModelProperty("类型     0：目录   1：菜单   2：按钮")
    @TableField("type")
    private Integer type;

    @ApiModelProperty("菜单图标")
    @TableField("icon")
    private String icon;

    @ApiModelProperty("排序")
    @TableField("orderNum")
    private Integer orderNum;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("update_time")
    private LocalDateTime updateTime;

    @TableField("status")
    private Integer status;

    @TableField(exist = false)
    private List<Menu> children = new ArrayList<>();
}
