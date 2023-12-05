package com.alibou.websocket.config;

import com.alibou.websocket.api.CustomEvent;
import com.alibou.websocket.chat.ChatMessage;
import com.alibou.websocket.chat.MessageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.config.WebSocketMessageBrokerStats;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;

@Component
@Slf4j
@RequiredArgsConstructor
@Service
public class WebSocketEventListener {

    private final SimpMessageSendingOperations messagingTemplate;


    @Autowired
    private SimpUserRegistry simpUserRegistry;

    @Autowired
    private WebSocketMessageBrokerStats webSocketMessageBrokerStats;




    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) headerAccessor.getSessionAttributes().get("username");

            log.info("user disconnected: {}", username);
            var chatMessage = ChatMessage.builder()
                    .type(MessageType.LEAVE)
                    .sender(username)
                    .build();
            messagingTemplate.convertAndSend("/topic/public", chatMessage);

    }
    @EventListener
    public void handleWebSocketListener(SessionSubscribeEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) headerAccessor.getSessionAttributes().get("username");
        log.info("user connected: {}", headerAccessor.getDestination());
            var chatMessage = ChatMessage.builder()
                    .type(MessageType.CHAT)
                    .sender("hola")
                    .content("hehe hasdele")
                    .build();
            messagingTemplate.convertAndSend("/topic/public", chatMessage);

    }

    @EventListener
    public void unsubscribe(SessionUnsubscribeEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) headerAccessor.getSessionAttributes().get("username");
        log.info("user connected: {}", headerAccessor.getDestination());
        var chatMessage = ChatMessage.builder()
                .type(MessageType.CHAT)
                .sender("hola")
                .content("hehe hasdele")
                .build();
        messagingTemplate.convertAndSend("/topic/public", chatMessage);
    }
    @EventListener
    public void cutsomEventHandler(CustomEvent event) {
        log.info("user connected: {}", event.getMessage());
        var chatMessage = ChatMessage.builder()
                .type(MessageType.CHAT)
                .sender("shreyansh")
                .content("this event is triggered when you hit an api on home as they publish any message")
                .build();
        messagingTemplate.convertAndSend("/topic/public", chatMessage);
    }
}
