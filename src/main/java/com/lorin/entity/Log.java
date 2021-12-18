package com.lorin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;

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
 * @since 2021-12-17
 */
@Getter
@Setter
@TableName("sys_log")
@ApiModel(value = "Log对象", description = "")
public class Log implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("模块")
    @TableField("module")
    private String module;

    @ApiModelProperty("操作")
    @TableField("operation")
    private String operation;

    @ApiModelProperty("请求类和方法")
    @TableField("class_method")
    private String classMethod;

    @ApiModelProperty("参数")
    @TableField("prams")
    private String prams;

    @ApiModelProperty("请求用户")
    @TableField("username")
    private String username;

    @ApiModelProperty("请求方式")
    @TableField("request_method")
    private String requestMethod;

    @ApiModelProperty("ip地址")
    @TableField("ip")
    private String ip;

    @ApiModelProperty("ip位置")
    @TableField("location")
    private String location;

    @ApiModelProperty("useragent")
    @TableField("ua")
    private String ua;

    @ApiModelProperty("返回值")
    @TableField("res")
    private String res;

    @ApiModelProperty("请求耗时")
    @TableField("excute_time")
    private String excuteTime;

    @ApiModelProperty("创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;
}
