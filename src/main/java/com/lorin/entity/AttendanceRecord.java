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
@TableName("sys_attendance_record")
@ApiModel(value = "AttendanceRecord对象", description = "")
public class AttendanceRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("student_name")
    private String studentName;

    @TableField("attendance_id")
    private Integer attendanceId;

    @TableField("attendance_time")
    private LocalDateTime attendanceTime;
    @TableField(exist = false)
    private String attendanceName;

}
