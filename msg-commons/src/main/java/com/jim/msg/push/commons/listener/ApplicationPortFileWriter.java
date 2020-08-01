package com.jim.msg.push.commons.listener;

import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;

public class ApplicationPortFileWriter implements ApplicationListener<ApplicationStartedEvent>{
    @Override
    public void onApplicationEvent(ApplicationStartedEvent applicationStartedEvent) {
        System.out.println("Listen :ApplicationPortFileWriter");

    }
}
