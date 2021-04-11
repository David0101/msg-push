package com.jim.msg.push.auth.service;

import com.jim.msg.push.auth.dto.LoginSecretRequest;
import com.jim.msg.push.auth.entity.LoginKeyPair;
import com.jim.msg.push.auth.entity.PublicKey;
import com.jim.msg.push.auth.entity.SecretKey;
import com.jim.msg.push.auth.utils.CryptoUtils;
import com.jim.msg.push.auth.utils.TokenKeyUtil;
import org.springframework.stereotype.Service;




@Service
public class OauthService {

    public PublicKey getPublicKey(LoginSecretRequest loginSecretRequest){

        String secretKeyStr = TokenKeyUtil.getScretKeyWithOrgId(loginSecretRequest.getClientId(),loginSecretRequest.getAppType(), loginSecretRequest.getOrgId(), loginSecretRequest.getUsername());

        LoginKeyPair loginKeyPair = CryptoUtils.getKeyPair();

        SecretKey secretKey = new SecretKey(secretKeyStr);

        //redisCacheService.cache(secretKey,loginKeyPair.getPrivateKey());

        return new PublicKey(loginKeyPair.getPublicKey());
    }
}
