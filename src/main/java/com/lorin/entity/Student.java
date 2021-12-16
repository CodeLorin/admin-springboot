package com.lorin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 *
 * </p>
 *
 * @author lorin
 * @since 2021-12-15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_student")
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;


    private String name;


    private Integer classes;


    private String num;


    private String gender;


    private LocalDateTime birth;


    private Integer teacherId;


    private String homeAddress;


    private String parentName;


    private String face;


}
