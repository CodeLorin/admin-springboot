package com.lorin.common.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * TODO
 *
 * @author lorin
 * @date 2021/12/19 22:41
 */


@Data
public class OnlineUserDto implements Serializable {
    private String username;
    private String ip;
    private String location;
    private String browser;
    private String os;
    private String loginTime;
}
