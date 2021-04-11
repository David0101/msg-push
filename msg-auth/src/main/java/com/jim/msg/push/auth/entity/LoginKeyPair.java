package com.jim.msg.push.auth.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginKeyPair {
    private String privateKey;

    private String publicKey;
}
