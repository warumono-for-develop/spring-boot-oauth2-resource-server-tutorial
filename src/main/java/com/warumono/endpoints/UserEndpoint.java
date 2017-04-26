package com.warumono.endpoints;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/")
public class UserEndpoint
{
	@RequestMapping(value = "principal")
	public ResponseEntity<Map<String, String>> principal(Principal principal)
	{
		Map<String, String> user = new HashMap<>();
		user.put("email", principal.getName());
		user.put("picture", "/pictures/".concat(principal.getName().concat("/avatar.png")));
		
		return ResponseEntity.ok(user);
	}
}
