package com.lorin.common.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * TODO
 *
 * @author lorin
 * @date 2021/12/18 1:04
 */


@Data
public class PasswordDto implements Serializable {

    @NotNull(message = "id不能为空")
    private Integer id;
    @NotBlank(message = "新密码不能为空")
    private String password;
    @NotBlank(message = "旧密码不能为空")
    private String currentPass;
}
