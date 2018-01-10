package com.polaris.authservice.security.util;

import java.io.UnsupportedEncodingException;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

import org.springframework.ldap.core.AttributesMapper;

import com.polaris.authservice.security.LDAPUser;

public class UserAttributesMapper implements AttributesMapper<LDAPUser> {

    @Override
    public LDAPUser mapFromAttributes(Attributes attributes) throws NamingException {
    	LDAPUser user;
        if (attributes == null) {
            return null;
        }
        user = new LDAPUser();
        user.setName(attributes.get("cn").get().toString());
        user.setRoleUser();
        if (attributes.get("userPassword") != null) {
            String userPassword = null;
            try {
                userPassword = new String((byte[]) attributes.get("userPassword").get(), "UTF-8");
            } catch (UnsupportedEncodingException e) {

            }
            user.setPassword(userPassword);
        }
        if (attributes.get("uid") != null) {
         //   user.setUid(attributes.get("uid").get().toString());
        }
        if (attributes.get("sn") != null) {
           // user.setSn(attributes.get("sn").get().toString());
        }
        if (attributes.get("postalAddress") != null) {
           // user.setPostalAddress(attributes.get("postalAddress").get().toString());
        }
        if (attributes.get("telephoneNumber") != null) {
            //user.setTelephoneNumber(attributes.get("telephoneNumber").get().toString());
        }
        return user;
    }
}