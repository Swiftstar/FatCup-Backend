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

import com.fatcup.backend.net.OrderDTO;
import com.fatcup.backend.net.ResponseBase;
import com.fatcup.backend.net.UserDTO;

@RestController
@RequestMapping("/customer")
@Api(description = "用户管理")
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	Logger logger = LoggerFactory.getLogger(CustomerController.class);
	
	@RequestMapping(value = "/user/check", method = {RequestMethod.POST})
	public ResponseEntity<ResponseBase> Check(
			@RequestHeader(value = "Authorization") String token) {
		logger.debug("request token:" + token);	
		return customerService.Check(token);
	}
	
	@ApiOperation(value = "新增用户", notes = "新增用戶")
	@RequestMapping(value = "/user/add", method = {RequestMethod.POST})
	public ResponseEntity<ResponseBase> Add(
			@RequestHeader(value = "Authorization") String token,
			@RequestBody UserDTO request) {
		return customerService.Add(token, request);
	}
	
	@RequestMapping(value = "/order/add", method = {RequestMethod.POST})
	public ResponseEntity<ResponseBase> OrderAdd(@RequestBody OrderDTO request) {
		
		return customerService.OrderAdd(request);
	}
}
