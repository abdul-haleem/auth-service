package com.polaris.authservice.security;

import java.util.ArrayList;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.util.Assert;

public class LDAPAuthenticationProvider implements AuthenticationProvider {

	private LdapTemplate ldapActions;
	
	@Autowired
	public LDAPAuthenticationProvider(LdapTemplate ldapTemplate) {
		this.ldapActions = ldapTemplate;
	}
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		Assert.notNull(authentication, "No authentication data provided");
		
		String username = authentication.getPrincipal().toString();
		String password = authentication.getCredentials().toString();
		
		AndFilter filter = new AndFilter();
        filter.and(new EqualsFilter("uid", username));
        
		if (ldapActions.authenticate("", filter.toString(), password)) {
	        
			return new UsernamePasswordAuthenticationToken(username, null,Collections.emptyList());
		}
		else {
			throw new BadCredentialsException("Authentication Failed. Username or Password not valid.");
		}
		
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}

}
