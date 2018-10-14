package com.fatcup.backend;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fatcup.backend.data.User;
import com.fatcup.backend.data.UserRepository;
import com.fatcup.backend.response.Check;
import com.fatcup.backend.response.Register;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/user")
@Api(description = "用户管理")
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	@RequestMapping(value = "/register", method = {RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Register register(){
		return new Register("200");
	}
	
	@RequestMapping(value = "/check", method = {RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Check check(@RequestHeader(value="Authorization") String token){
		
		System.out.println("request token:"+token);

		FirebaseToken decodedToken;
		
		try {
			decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
		}
		catch(Exception e) {
			System.out.println(e);
			return new Check("error", false);
		}
		
		String uid = decodedToken.getUid();
		
		User u = userRepository.findByUid(uid);
		
		System.out.println("uid:"+uid);
		return new Check(uid, u!=null);
	}
	
	@ApiOperation(value = "新增用户", notes = "新增用戶")
	@RequestMapping(value = "/add", method = {RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Register add(@RequestParam Map<String,String> requestParams) {
		
		User user = new User();
		user.setUid(requestParams.get("uid"));
		user.setCreateTime(LocalDateTime.now());
		userRepository.save(user);
		return new Register("200");
	}
}
