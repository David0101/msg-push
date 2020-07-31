package com.jim.msg.push.center;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @program: msg-push
 * @author: jim
 * @create: 2020-07-30 18:46
 */
@EnableEurekaServer
@SpringBootApplication
public class CenterBootstrapApplication {
    public static void main(String[] args) {
        SpringApplication.run(CenterBootstrapApplication.class, args);
    }
    /**
     * 加上账号密码，需要禁止csrf，并开启httpbasic认证
     */
    @EnableWebSecurity
    static class WebSecurityConfig extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
                    .anyRequest()
                    .authenticated()
                    .and()
                    .httpBasic()
                    .and()
                    .csrf().disable();
        }
    }
}
