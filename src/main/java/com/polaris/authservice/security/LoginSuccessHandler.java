package com.polaris.authservice.security;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.query.LdapQueryBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.polaris.authservice.security.util.UserAttributesMapper;

public class LoginSuccessHandler implements AuthenticationSuccessHandler {

	private LdapTemplate ldapActions;
	
	@Autowired
	public LoginSuccessHandler(LdapTemplate ldapTemplate) {
		this.ldapActions = ldapTemplate;
	}
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		String username = authentication.getPrincipal().toString();
		
		List<LDAPUser> list = ldapActions.search(LdapQueryBuilder.query().base("").where("uid").is(username), new UserAttributesMapper());
		
		
	}

}
