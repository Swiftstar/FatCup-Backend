package com.fatcup.backend;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fatcup.backend.response.GeneralResponse;
import com.fatcup.backend.response.TestResponse;
import com.fatcup.backend.response.ReturnCode;

import io.swagger.annotations.Api;

@RestController
@Api(description = "首頁")
public class IndexController {
	
	@RequestMapping(value = "/", method = {RequestMethod.GET})
	public String index() {
		return "Hello";
	}

	@RequestMapping(value = "/test", method = {RequestMethod.POST})
	public GeneralResponse register(){
		TestResponse register = new TestResponse();
		register.setReturnCode(ReturnCode.OK);
		ArrayList<String> testL = new ArrayList<>();
		testL.add("abc");
		testL.add("def");
		register.Set("Number", 123);
		register.Set("String", "Test");
		register.Set("List", testL);
		register.Set("Register", new TestResponse());
		return register;
	}
}
