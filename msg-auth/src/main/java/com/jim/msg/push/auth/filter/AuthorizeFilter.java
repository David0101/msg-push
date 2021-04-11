package com.jim.msg.push.auth.filter;

import org.springframework.core.Ordered;

public class AuthorizeFilter implements  Ordered {
    @Override
    public int getOrder() {
        return 0;
    }
}
