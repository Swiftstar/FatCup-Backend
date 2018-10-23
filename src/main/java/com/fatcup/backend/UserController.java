package com.fatcup.backend;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import com.fatcup.backend.data.User;
import com.fatcup.backend.data.UserRepository;
import com.fatcup.backend.response.GeneralResponse;
import com.fatcup.backend.response.ReturnCode;
import com.fatcup.backend.request.UserAddRequest;

@RestController
@RequestMapping("/user")
@Api(description = "用户管理")
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@RequestMapping(value = "/check", method = {RequestMethod.POST})
	public GeneralResponse check(
			@RequestHeader(value = "Authorization") String token) {
		GeneralResponse response = new GeneralResponse();
		logger.debug("request token:" + token);

		try {
			FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
			String uid = decodedToken.getUid();
			System.out.println("uid:" + uid);

			User u = userRepository.findByUid(uid);
			if (u!=null) {
				response.setReturnCode(ReturnCode.OK);
				response.Set("uid", u.getUid());
				response.Set("isuser", true);
			} else {
				response.setReturnCode(ReturnCode.OK);
				response.Set("uid", null);
				response.Set("isuser", false);
			}
			return response;

		} catch (Exception e) {
			System.out.println(e);
			response.setReturnCode(ReturnCode.ERROR);
			response.setReturnMessage(e.getMessage());
			return response;
		}
	}
	
	@ApiOperation(value = "新增用户", notes = "新增用戶")
	@RequestMapping(value = "/add", method = {RequestMethod.POST})
	public GeneralResponse add(
			@RequestHeader(value = "Authorization") String token,
			@RequestBody UserAddRequest request) {
		GeneralResponse response = new GeneralResponse();
		try {
			FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
			String uid = decodedToken.getUid();
			
			User user = new User();
			user.setUid(uid);
			user.setName(request.name);
			user.setBirthday(request.birth);
			user.setGender(request.gender);
			user.setLogintype(request.logintype);
			user.setPhone(request.phone);
			user.setCreateTime(LocalDateTime.now());
			userRepository.save(user);

			response.setReturnCode(ReturnCode.OK);
			response.Set("status", true);
			return response;
		} catch (Exception e) {
			System.out.println(e);
			response.setReturnCode(ReturnCode.ERROR);
			response.setReturnMessage(e.getMessage());
			return response;
		}	
	}
}
