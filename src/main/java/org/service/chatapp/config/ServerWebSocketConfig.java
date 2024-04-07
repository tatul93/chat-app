package org.service.chatapp.config;

import org.service.chatapp.config.security.BasicAuthHandshakeInterceptor;
import org.service.chatapp.controller.ChatWS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * Configuration class for WebSocket server.
 * It enables WebSocket and provides configuration for WebSocket handlers.
 */
@Configuration
@EnableWebSocket
public class ServerWebSocketConfig implements WebSocketConfigurer {

	/**
	 * Interceptor for handling basic authentication during WebSocket handshake.
	 */
	private final BasicAuthHandshakeInterceptor basicAuthHandshakeInterceptor;

	/**
	 * WebSocket handler for chat.
	 */
	private final ChatWS chatWS;

	/**
	 * Constructor for ServerWebSocketConfig, initializes chatWS and basicAuthHandshakeInterceptor.
	 *
	 * @param chatWS WebSocket handler for chat.
	 * @param basicAuthHandshakeInterceptor Interceptor for handling basic authentication during WebSocket handshake.
	 */
	@Autowired
	public ServerWebSocketConfig(ChatWS chatWS,
								 BasicAuthHandshakeInterceptor basicAuthHandshakeInterceptor) {
		this.chatWS = chatWS;
		this.basicAuthHandshakeInterceptor = basicAuthHandshakeInterceptor;
	}

	/**
	 * Registers WebSocket handlers.
	 * It sets the handshake handler and allowed origin patterns for the chat WebSocket handler.
	 *
	 * @param registry Registry for WebSocket handlers.
	 */
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(chatWS, "/ws")
				.setHandshakeHandler(basicAuthHandshakeInterceptor)
				.setAllowedOriginPatterns("*");
	}

}
