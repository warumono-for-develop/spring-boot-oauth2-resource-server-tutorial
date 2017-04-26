package com.warumono.configurations;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.util.FileCopyUtils;

@Configuration
public class JwtConfiguration
{
	@Autowired
	private JwtAccessTokenConverter accessTokenConverter;

	@Bean
	public TokenStore tokenStore()
	{
		return new JwtTokenStore(accessTokenConverter);
	}

	@Bean
	protected JwtAccessTokenConverter jwtTokenEnhancer()
	{
		//// keytool -list -rfc --keystore keystore.jks | openssl x509 -inform pem -pubkey
		//// copy from '-----BEGIN PUBLIC KEY-----' to '-----END PUBLIC KEY-----'
		////
		//// -----BEGIN PUBLIC KEY-----
		//// ...
		//// -----END PUBLIC KEY-----
		//// 
		//// create new file
		//// vi authpublickey.cert
		//// paste to authpublickey.cert
		//// esc -> :wq!
		
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		//*
		Resource resource = new ClassPathResource("authpublickey.cert");
		String publicKey;
		
		try
		{
			publicKey = new String(FileCopyUtils.copyToByteArray(resource.getInputStream()));
		}
		catch(IOException e)
		{
			throw new RuntimeException(e);
		}
		
		converter.setVerifierKey(publicKey);
		/*/
		converter.setVerifierKey(getVerifierKey());
		//*/
		return converter;
	}
	
	protected static final String getVerifierKey()
	{
		Resource resource = new ClassPathResource("authpublickey.cert");
		
		try
		{
			return new String(FileCopyUtils.copyToByteArray(resource.getInputStream()));
		}
		catch(IOException e)
		{
			throw new RuntimeException(e);
		}
	}
}
