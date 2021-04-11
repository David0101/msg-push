package com.jim.msg.push.auth.controller;

import com.alibaba.fastjson.JSON;
import com.jim.msg.push.auth.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(value = "/v1/test")
public class TestContoller {

    @GetMapping(value = "/login")
    public String login()  {
        //设置令牌信息
        Map<String,Object> info = new HashMap<String,Object>();
        info.put("role","USER");
        info.put("success","SUCCESS");
        info.put("username","jim");
        //生成令牌
        String jwt = JwtUtil.createJWT(UUID.randomUUID().toString(), JSON.toJSONString(info),null);
        try {
            Claims claims = JwtUtil.parseJWT(jwt);
            int a = 0 ;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jwt;
    }
}
