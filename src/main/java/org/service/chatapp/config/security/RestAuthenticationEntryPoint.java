package org.service.chatapp.config.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This class is a custom implementation of the AuthenticationEntryPoint interface.
 * It is used to commence an authentication scheme.
 */
@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

	/**
	 * This method is called whenever an exception is thrown due to an unauthenticated user trying to access a resource
	 * that requires authentication.
	 *
	 * @param request The request that resulted in an AuthenticationException.
	 * @param response The response.
	 * @param authException The exception that was thrown.
	 * @throws IOException If an input or output exception occurred.
	 * @throws ServletException If a servlet exception occurred.
	 */
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
						 AuthenticationException authException) throws IOException, ServletException {
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
	}
}
