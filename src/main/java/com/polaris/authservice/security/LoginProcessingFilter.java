package com.polaris.authservice.security;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class LoginProcessingFilter extends UsernamePasswordAuthenticationFilter{
	

	public LoginProcessingFilter(String defaultProcessUrl, AuthenticationSuccessHandler successHandler, 
			AuthenticationFailureHandler failureHandler) {
		this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(defaultProcessUrl, HttpMethod.POST.toString()));
		this.setAuthenticationSuccessHandler(successHandler);
		this.setAuthenticationFailureHandler(failureHandler);
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,
			HttpServletResponse response) throws AuthenticationException
	{
		if (!HttpMethod.POST.name().equals(request.getMethod())) {
			if (logger.isDebugEnabled()) {
				logger.debug("Authentication method not supported. Request method: " + request.getMethod());
			}
			throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
		}

		try {
			LoginRequest loginRequest = new ObjectMapper().readValue(request.getInputStream(), LoginRequest.class);
			
			if (!StringUtils.hasText(loginRequest.getUsername())) {
				throw new AuthenticationServiceException("Username not provided");
			}
			else if (!StringUtils.hasText(loginRequest.getPassword())) {
				throw new AuthenticationServiceException("Password not provided");	
			}

			return getAuthenticationManager().authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		} catch (IOException e) {
			throw new RuntimeException();
		}
	}

}
