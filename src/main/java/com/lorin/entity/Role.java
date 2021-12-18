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
@TableName("sys_role")
@ApiModel(value = "Role对象", description = "")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @NotBlank(message = "角色名称不能为空")
    @ApiModelProperty("角色名称")
    @TableField("name")
    private String name;

    @NotBlank(message = "角色名称不能为空")
    @ApiModelProperty("角色唯一标识")
    @TableField("code")
    private String code;

    @ApiModelProperty("描述")
    @TableField("description")
    private String description;

    @ApiModelProperty("创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @TableField("update_time")
    private LocalDateTime updateTime;

    @ApiModelProperty("角色状态")
    @TableField("status")
    private Integer status;

    @TableField(exist = false)
    private List<Long> menuIds = new ArrayList<>();

}
