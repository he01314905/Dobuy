package com.chat.model;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service("chatService")
public class ChatService {
    private final SimpMessagingTemplate messagingTemplate;

    public ChatService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendToUser(String username, ChatMessage message) {
        messagingTemplate.convertAndSendToUser(username, "/queue/messages", message);
    }
}
