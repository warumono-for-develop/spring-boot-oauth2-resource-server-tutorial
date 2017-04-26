package com.warumono.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class UserController
{
	/*
	 * @PreAuthorize
	 * http://docs.spring.io/spring-security/site/docs/current/reference/html/el-access.html
	 * 
	 * hasRole([role])
	 * hasAnyRole([role1,role2])
	 * 
	 * hasAuthority([authority])
	 * hasAnyAuthority([authority1,authority2])
	 * 
	 * */
	
	@PreAuthorize(value = "hasRole('USER') and #oauth2.hasScope('read')")
//	@GetMapping(value = "me")
	@RequestMapping(value = "me")
	public ResponseEntity<Map<String, Object>> me(@AuthenticationPrincipal OAuth2Authentication authentication)
	{
		String username = authentication.getUserAuthentication().getPrincipal().toString();
		Set<String> scopes = authentication.getOAuth2Request().getScope();

		Map<String, Object> me = new HashMap<>();
		me.put("message", "Hello, Authenticator");
		me.put("username", username);
		me.put("scopes", scopes);
		
		return ResponseEntity.ok(me);
	}
	
	@PreAuthorize(value = "hasAuthority('ADMIN')")
	@RequestMapping(value = "accessable/by/authorities/admin")
	public ResponseEntity<Map<String, String>> accessableByAuthoritiesAdmin()
	{
		Map<String, String> admin = new HashMap<>();
		admin.put("message", "Hello, Admin");
		
		return ResponseEntity.ok(admin);
	}
	
	@PreAuthorize(value = "hasAuthority('USER')")
	@RequestMapping(value = "accessable/by/authorities/user")
	public ResponseEntity<Map<String, String>> accessableByAuthoritiesUser()
	{
		Map<String, String> admin = new HashMap<>();
		admin.put("message", "Hello, User");
		
		return ResponseEntity.ok(admin);
	}
	
	@PreAuthorize(value = "#oauth2.hasScope('read')")
	@RequestMapping(value = "accessable/by/scopes/read")
	public ResponseEntity<Map<String, String>> accessableByScopesRead()
	{
		Map<String, String> admin = new HashMap<>();
		admin.put("message", "Hello, Reader");
		
		return ResponseEntity.ok(admin);
	}
	
	@PreAuthorize(value = "#oauth2.hasScope('write')")
	@RequestMapping(value = "accessable/by/scopes/write")
	public ResponseEntity<Map<String, String>> accessableByScopesWrite(@RequestParam(value = "text") String text)
	{
		Map<String, String> admin = new HashMap<>();
		admin.put("message", "Hello, Writer");
		admin.put("param", text);
		
		return ResponseEntity.ok(admin);
	}
}
