package com.fatcup.backend;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fatcup.backend.response.Register;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@RequestMapping(value = "/register")
	public @ResponseBody Register register(){
		return new Register("200");
	}
	
	@RequestMapping(value = "/check")
	public @ResponseBody Register check(@RequestHeader(value="Authorization") String token) throws Throwable {
		
		System.out.println("request token:"+token);

		FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
		String uid = decodedToken.getUid();
		
		System.out.println("uid:"+uid);
		return new Register(token);
	}
	
	@RequestMapping(value = "/check2")
	public void check2() {
		
	}
}
