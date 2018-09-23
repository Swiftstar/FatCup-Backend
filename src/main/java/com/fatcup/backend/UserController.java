package com.fatcup.backend;

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
}
