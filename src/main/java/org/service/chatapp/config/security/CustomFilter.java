package org.service.chatapp.config.security;

import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * Custom filter class that extends the GenericFilterBean class of Spring Framework.
 * This class is used to apply custom filtering rules to the incoming requests.
 */
public class CustomFilter extends GenericFilterBean {

	/**
	 * This method is used to apply the custom filter to the incoming requests.
	 * It is an overridden method from the GenericFilterBean class.
	 *
	 * @param servletRequest The incoming request.
	 * @param servletResponse The response object.
	 * @param filterChain The chain of filters to be applied.
	 * @throws IOException If an input or output exception occurred.
	 * @throws ServletException If a servlet exception occurred.
	 */
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		filterChain.doFilter(servletRequest, servletResponse);
	}
}
