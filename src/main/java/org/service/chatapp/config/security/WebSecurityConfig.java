package org.service.chatapp.config.security;

import org.service.chatapp.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuration class for web security.
 * It enables web security and provides beans for security filter chain and password encoder.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	/**
	 * Service for user details.
	 */
	private final UserDetailsServiceImpl loginService;

	/**
	 * Constructor for WebSecurityConfig, initializes loginService.
	 *
	 * @param loginService Service for user details.
	 */
	public WebSecurityConfig(UserDetailsServiceImpl loginService) {
		this.loginService = loginService;
	}

	/**
	 * Bean for security filter chain.
	 * It configures HTTP security for the application.
	 *
	 * @param http HTTP security object.
	 * @return The security filter chain.
	 * @throws Exception If an exception occurred.
	 */
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable();

		http.authorizeRequests()
				.antMatchers("/ws/**")
				.permitAll()
				.anyRequest()
				.authenticated()
				.and()
				.httpBasic(Customizer.withDefaults());
		return http.build();
	}

	/**
	 * Bean for password encoder.
	 * It provides a BCrypt password encoder.
	 *
	 * @return The password encoder.
	 */
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * Configures global authentication.
	 * It sets the user details service for authentication.
	 *
	 * @param auth Authentication manager builder.
	 * @throws Exception If an exception occurred.
	 */
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(loginService);
	}

}
