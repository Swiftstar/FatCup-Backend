package com.fatcup.backend;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

@RestController
@Api(description = "首頁")
public class IndexController {
	
	@RequestMapping(value = "/", method = {RequestMethod.POST,RequestMethod.GET})
	public String index() {
		return "Hello";
	}

}
