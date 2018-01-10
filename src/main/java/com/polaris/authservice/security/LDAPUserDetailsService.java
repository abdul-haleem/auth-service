package com.polaris.authservice.security;

import javax.naming.directory.DirContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AuthenticatedLdapEntryContextMapper;
import org.springframework.ldap.core.LdapEntryIdentification;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.ldap.query.LdapQueryBuilder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


public class LDAPUserDetailsService implements UserDetailsService{

	@Autowired
	private LdapTemplate ldapService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		//LDAPUser user = new 
		return null;
	}
	
	
	private UserDetails retrieveUser(String username)
	{
		LdapQuery query = LdapQueryBuilder.query().where("uid").is(username);
		
		return ldapService.authenticate(query, "", new AuthenticatedLdapEntryContextMapper<UserDetails>() {

			@Override
			public User mapWithContext(DirContext ctx, LdapEntryIdentification ldapEntryIdentification) {
				
				return null;
			}
		});
	}

}
