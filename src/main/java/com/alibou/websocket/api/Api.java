package com.alibou.websocket.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Api {
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    @GetMapping("/home")
    public String basic(){
        fun();
        return "shreyansh said hi";
    }
    public void fun(){
        CustomEvent customEvent= new CustomEvent(this,"this is what i published from a rest api endpoint");
        applicationEventPublisher.publishEvent(customEvent);
    }
}
