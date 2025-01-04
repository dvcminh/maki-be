package com.miki.animestylebackend.service;

import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SocketIOService {

    private final SocketIOServer server;
    private final Map<String, String> roomCreator = new HashMap<>();

    public SocketIOService(SocketIOServer server) {
        this.server = server;
    }

    @PostConstruct
    public void startServer() {
        server.start();
        System.out.println("Socket.IO server started on port 9092");
    }

    @PreDestroy
    public void stopServer() {
        server.stop();
        System.out.println("Socket.IO server stopped");
    }

    @OnConnect
    public void onConnect(com.corundumstudio.socketio.SocketIOClient client) {
        System.out.println("User connected: " + client.getSessionId());
    }

    @OnDisconnect
    public void onDisconnect(com.corundumstudio.socketio.SocketIOClient client) {
        System.out.println("User disconnected: " + client.getSessionId());
    }

    @OnEvent("createRoom")
    public void createRoom(com.corundumstudio.socketio.SocketIOClient client, Map<String, Object> data) {
        String roomId = java.util.UUID.randomUUID().toString().substring(0, 5);
        client.joinRoom(roomId);

        // Save room creator
        roomCreator.put(roomId, client.getSessionId().toString());

        // Emit roomCreated event to the creator
        Map<String, Object> response = new HashMap<>();
        response.put("roomId", roomId);
        response.put("position", data.get("position"));
        response.put("totalConnectedUsers", roomId); // Send all connected users in the room

        client.sendEvent("roomCreated", response);
    }

    @OnEvent("joinRoom")
    public void joinRoom(com.corundumstudio.socketio.SocketIOClient client, Map<String, String> data) {
        String roomId = data.get("roomId");

        if (server.getRoomOperations(roomId).getClients().isEmpty()) {
            client.sendEvent("roomJoined", Map.of("status", "ERROR"));
            return;
        }

        client.joinRoom(roomId);
        client.sendEvent("roomJoined", Map.of("status", "OK"));

        // Notify room creator
        String creatorSessionId = roomCreator.get(roomId);
        if (creatorSessionId != null) {
            for (com.corundumstudio.socketio.SocketIOClient c : server.getRoomOperations(roomId).getClients()) {
                if (c.getSessionId().toString().equals(creatorSessionId)) {
                    c.sendEvent("userJoinedRoom", Map.of("userId", client.getSessionId()));
                }
            }
        }
    }

    @OnEvent("updateLocation")
    public void updateLocation(com.corundumstudio.socketio.SocketIOClient client, Map<String, Object> data) {
        server.getBroadcastOperations().sendEvent("updateLocationResponse", data);
    }
}
