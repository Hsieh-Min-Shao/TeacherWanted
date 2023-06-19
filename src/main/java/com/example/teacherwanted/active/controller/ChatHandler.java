package com.example.teacherwanted.active.controller;

import com.example.teacherwanted.active.service.ActiveOrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.util.*;

@Component
public class ChatHandler extends TextWebSocketHandler {

    @Autowired
    private ActiveOrderDetailService activeOrderDetailService;

    @Autowired
    private Jedis jedis;

    private Map<String, Set<WebSocketSession>> chatRooms = new HashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String chatRoomId = extractChatRoomId(session);
        Integer chatRoomActivityId = Integer.parseInt(chatRoomId);
        jedis.select(15);
        System.out.println("聊天室ID：" + chatRoomActivityId);
        //        將list轉為json放進broadcast裡xxx的位置

        List<String> chatHistory = jedis.lrange("chat:" + chatRoomId, 0, jedis.llen(chatRoomId) - 1);
//        用br換行
//        String result = String.join("<br>", chatHistory);
//        用p標籤包住
//        String result = String.join("</p><p>", chatHistory);
//        result = "<p>" + result + "</p>";
        String result = String.join("",chatHistory);

//        for (String chatOne : chatHistory) {
//            System.out.println("資料:" + chatOne);
//        }

        if (true) {




            // 將訊息貼在聊天室中
            Set<WebSocketSession> sessions = chatRooms.getOrDefault(chatRoomId, new HashSet<>());
            sessions.add(session);
            chatRooms.put(chatRoomId, sessions);

            //發送給當前使用者
            Set<WebSocketSession> set = new HashSet<>();
            set.add(session);

            broadcast(set, result);
        } else {

            //發送錯誤當前使用者
//            Set<WebSocketSession> sessions = chatRooms.getOrDefault(chatRoomId, new HashSet<>());
//            sessions.add(session);
//            chatRooms.put(chatRoomId, sessions);
            Set<WebSocketSession> set = new HashSet<>();
            set.add(session);

            broadcast(set, "無報名活動");
            session.close();
        }
    }


    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String chatRoomId = extractChatRoomId(session);
        Set<WebSocketSession> sessions = chatRooms.get(chatRoomId);
        if (sessions != null) {
            String payload = message.getPayload();
            jedis.select(15);
            String chatKey = "chat:" + chatRoomId;

            String modifiedPayload = payload;
//            儲存資料，用list
            jedis.rpush(chatKey, modifiedPayload);
            System.out.println("接收到消息: " + payload);
            System.out.println("聊天室ID: " + chatRoomId);
            broadcast(sessions, modifiedPayload);
        }
    }


    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String chatRoomId = extractChatRoomId(session);
        Set<WebSocketSession> sessions = chatRooms.get(chatRoomId);
        if (sessions != null) {
            sessions.remove(session);
            if (sessions.isEmpty()) {
                chatRooms.remove(chatRoomId);
            }
        }
    }

    private void broadcast(Set<WebSocketSession> sessions, String message) throws IOException {
        for (WebSocketSession session : sessions) {
            session.sendMessage(new TextMessage(message));
        }
    }

    private String extractChatRoomId(WebSocketSession session) {
        String[] pathSegments = session.getUri().getPath().split("/");
        return pathSegments[pathSegments.length - 1];
    }


}
