package com.lorin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDate;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 *
 * </p>
 *
 * @author lorin
 * @since 2021-12-18
 */
@Getter
@Setter
@TableName("sys_teacher")
@ApiModel(value = "Teacher对象", description = "")
public class Teacher implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @NotBlank(message = "工号不能为空")
    @ApiModelProperty("工号")
    @TableField("work_id")
    private String workId;

    @NotBlank(message = "名字不能为空")
    @ApiModelProperty("名字")
    @TableField("name")
    private String name;

    @NotBlank(message = "性别不能为空")
    @ApiModelProperty("性别")
    @TableField("gender")
    private String gender;

    @NotBlank(message = "班级不能为空")
    @ApiModelProperty("班级")
    @TableField("classes")
    private String classes;

    @ApiModelProperty("出生年月日")
    @TableField("birth")
    private LocalDate birth;


}
