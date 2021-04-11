package com.jim.msg.push.auth.controller;

import com.jim.msg.push.auth.dto.LoginSecretRequest;
import com.jim.msg.push.commons.bean.AjaxResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RequestMapping("/oauth")
@RestController
public class OauthController {

    @RequestMapping(value = "/secret" )
    public AjaxResult secret(@Valid @RequestBody LoginSecretRequest secretReqest){

        return AjaxResult.success();
    }
}
