package com.fatcup.backend;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import com.fatcup.backend.net.OrderDTO;
import com.fatcup.backend.net.ResponseBase;
import com.fatcup.backend.net.UserDTO;

@CrossOrigin(origins="*")
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
	
	@RequestMapping(value = "/order/history/{uid}", method = {RequestMethod.GET})
	public ResponseEntity<ResponseBase> OrderHistory(@PathVariable String uid  /*@RequestBody OrderDTO request*/) {
		
		OrderDTO request = new OrderDTO();
		request.uid = uid;
		return customerService.OrderHistory(request);
	}
	
	@RequestMapping(path="/order/finish", method = {RequestMethod.POST})
	public ResponseEntity<ResponseBase> OrderFinish(@RequestBody OrderDTO request) {
		return customerService.OrderFinish(request);
	} 
	
	@RequestMapping(path="/order/delete", method = {RequestMethod.POST})
	public ResponseEntity<ResponseBase> OrderDelete(@RequestBody OrderDTO request) {
		return customerService.OrderDelete(request);
	} 
	
	@RequestMapping(value="/preloading", method = {RequestMethod.GET})
	public @ResponseBody ResponseBase Banner(HttpServletRequest requestHttp) {
		ResponseBase response = new ResponseBase();
		
		String scheme = requestHttp.getScheme();
		String serverName = requestHttp.getServerName();
		int port = requestHttp.getServerPort();
	
		ArrayList<String> banners = new ArrayList<String>();
		banners.add(String.format("%s://%s:%s/%s", scheme,serverName,port,"banner/b1.jpg"));
		banners.add(String.format("%s://%s:%s/%s", scheme,serverName,port,"banner/b2.jpg"));
		banners.add(String.format("%s://%s:%s/%s", scheme,serverName,port,"banner/b3.jpg"));
//		banners.add(String.format("%s://%s:%s/%s", scheme,serverName,port,"banner/b3.jpg"));
		response.Set("banners", banners);
		return response;
	}
}
