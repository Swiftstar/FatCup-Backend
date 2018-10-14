package com.fatcup.backend;

import java.io.Console;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fatcup.backend.response.register;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@RequestMapping(value = "/register")
	public @ResponseBody register register(){
		return new register("200");
	}
	
	@RequestMapping(value = "/check")
	public @ResponseBody register check(@RequestHeader(value="Authorization") String token) {
		
		System.out.println(token);
		return new register(token);
	}
	
	@RequestMapping(value = "/check2")
	public void check2() {
		
	}
}
