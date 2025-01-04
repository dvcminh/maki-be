package com.miki.animestylebackend.config;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class WebSocketConfig {

    @Bean
    public SocketIOServer socketIOServer() {
        Configuration config = new Configuration();
        config.setHostname("localhost"); // Set the hostname
        config.setPort(4000); // Define the port for Socket.IO

        config.setOrigin("*"); // Allow all origins
        return new SocketIOServer(config);
    }
}
