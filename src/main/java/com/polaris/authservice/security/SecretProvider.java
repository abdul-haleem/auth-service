package com.polaris.authservice.security;

import java.security.KeyPair;

import javax.crypto.SecretKey;

import io.jsonwebtoken.impl.crypto.MacProvider;

public class SecretProvider {
	private static final SecretKey secretPair = MacProvider.generateKey();
	
	public static byte[] getSigningKey() {
		return secretPair.getEncoded();
	}
}
