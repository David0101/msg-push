package com.jim.msg.push.auth.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginSecretRequest {
    @NotEmpty(message = "用户名不能为空")
    private String username;

    @NotEmpty(message = "clientId不能为空")
    private String clientId;

    @NotNull(message = "移动端类型不能为空")
    private Integer appType;

    //    @NotNull(message = "机构ID不能为空")
    private String orgId;
}
