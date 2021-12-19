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
 * @since 2021-12-19
 */
@Getter
@Setter
@TableName("sys_attendance")
@ApiModel(value = "Attendance对象", description = "")
public class Attendance implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("考勤名称")
    @TableField("name")
    private String name;

    @ApiModelProperty("发布时间")
    @TableField("publish_time")
    private LocalDateTime publishTime;

    @ApiModelProperty("限制时间,分钟为单位")
    @TableField("limit_time")
    private Integer limitTime;


}
