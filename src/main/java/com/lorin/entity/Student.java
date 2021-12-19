package com.lorin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

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
@TableName("sys_student")
@ApiModel(value = "Student对象", description = "")
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("学生姓名")
    @TableField("name")
    private String name;

    @ApiModelProperty("密码")
    @TableField("password")
    private String password;

    @ApiModelProperty("班级")
    @TableField("classes")
    private String classes;

    @ApiModelProperty("学号")
    @TableField("num")
    private String num;

    @ApiModelProperty("性别")
    @TableField("gender")
    private String gender;

    @ApiModelProperty("出生日期")
    @TableField("birth")
    private LocalDate birth;

    @ApiModelProperty("老师id")
    @TableField("teacher_id")
    private Integer teacherId;

    @TableField(exist = false)
    private String teacherName;

    @ApiModelProperty("家庭住址")
    @TableField("home_address")
    private String homeAddress;

    @ApiModelProperty("父母名称")
    @TableField("parent_name")
    private String parentName;

    @ApiModelProperty("人脸识别数据url")
    @TableField("face")
    private String face;


}
