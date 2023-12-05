package com.alibou.websocket.config;

import com.sun.security.auth.UserPrincipal;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;
import java.util.UUID;

public class HandshakeHandler extends DefaultHandshakeHandler {
    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
       String randomId= UUID.randomUUID().toString();
        ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
        HttpSession session = servletRequest.getServletRequest().getSession();
        System.out.println(session.getId()+"lol this was");
        System.out.println(randomId+"   yeah  I wrote something here");
return new UserPrincipal(session.getId());
    }
//    @Override
//    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler,)
}
