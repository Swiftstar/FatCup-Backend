package com.fatcup.backend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import com.fatcup.backend.net.ResponseBase;
import com.fatcup.backend.net.UserDTO;

@RestController
@RequestMapping("/user")
@Api(description = "用户管理")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@RequestMapping(value = "/check", method = {RequestMethod.POST})
	public ResponseEntity<ResponseBase> Check(
			@RequestHeader(value = "Authorization") String token) {
		logger.debug("request token:" + token);	
		return userService.Check(token);
	}
	
	@ApiOperation(value = "新增用户", notes = "新增用戶")
	@RequestMapping(value = "/add", method = {RequestMethod.POST})
	public ResponseEntity<ResponseBase> Add(
			@RequestHeader(value = "Authorization") String token,
			@RequestBody UserDTO request) {
		return userService.Add(token, request);
	}
}
