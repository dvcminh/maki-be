package com.miki.animestylebackend.controller;

import com.miki.animestylebackend.model.Position;
import com.miki.animestylebackend.model.RoomMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class WebSocketController {

    private final Map<String, String> roomCreators = new HashMap<>();

    @MessageMapping("/createRoom")
    @SendTo("/topic/roomCreated")
    public Map<String, Object> createRoom(Position message) {
        String roomId = UUID.randomUUID().toString().substring(0, 5);
//        roomCreators.put(roomId, message.getSocketId());

        Map<String, Object> response = new HashMap<>();
        response.put("roomId", roomId);
        response.put("position", message);
        response.put("totalConnectedUsers", new ArrayList<>());
        return response;
    }

    @MessageMapping("/joinRoom")
    @SendTo("/topic/roomJoined")
    public Map<String, Object> joinRoom(RoomMessage message) {
        Map<String, Object> response = new HashMap<>();
        if (roomCreators.containsKey(message.getRoomId())) {
            response.put("status", "OK");
            response.put("userId", message.getSocketId());
        } else {
            response.put("status", "ERROR");
        }
        return response;
    }

    @MessageMapping("/updateLocation")
    @SendTo("/topic/updateLocationResponse")
    public Map<String, Object> updateLocation(Map<String, Object> locationData) {
        return locationData;
    }
}
