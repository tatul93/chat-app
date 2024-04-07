package org.service.chatapp.config.security;

import org.service.chatapp.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Base64;
import java.util.Map;

/**
 * Interceptor for handling basic authentication during WebSocket handshake.
 */
@Component
public class BasicAuthHandshakeInterceptor extends DefaultHandshakeHandler {

	/**
	 * Service for loading user details.
	 */
	private final UserDetailsServiceImpl userDetailsService;

	/**
	 * Constructor for BasicAuthHandshakeInterceptor, initializes userDetailsService.
	 *
	 * @param userDetailsService Service for loading user details.
	 */
	@Autowired
	public BasicAuthHandshakeInterceptor(UserDetailsServiceImpl userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	/**
	 * Determines the user associated with a WebSocket handshake request.
	 *
	 * @param request The handshake request.
	 * @param wsHandler The WebSocket handler.
	 * @param attributes Attributes associated with the handshake.
	 * @return The user associated with the request, or null if not authenticated.
	 */
	@Override
	protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler,
									  Map<String, Object> attributes) {
		UserDetails userDetails = authenticate(request);
		if (userDetails != null) {
			return userDetails::getUsername;
		}
		return null;
	}

	/**
	 * Authenticates a user based on a handshake request.
	 *
	 * @param request The handshake request.
	 * @return The authenticated user's details, or null if not authenticated.
	 */
	private UserDetails authenticate(ServerHttpRequest request) {
		String authorization = request.getHeaders().getFirst("Authorization");
		if (authorization != null && authorization.startsWith("Basic ")) {
			String base64Credentials = authorization.substring("Basic ".length()).trim();

			byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
			String credentials = new String(credDecoded);

			final String[] values = credentials.split(":", 2);
			return userDetailsService.loadUserByUsername(values[0]);
		}
		return null;
	}
}
