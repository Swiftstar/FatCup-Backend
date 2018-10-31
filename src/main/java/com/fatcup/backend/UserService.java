package com.fatcup.backend;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fatcup.backend.data.OrderRepository;
import com.fatcup.backend.data.User;
import com.fatcup.backend.data.UserRepository;
import com.fatcup.backend.net.ResponseBase;
import com.fatcup.backend.net.ReturnCode;
import com.fatcup.backend.net.UserDTO;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private FirebaseAuth firebaseAuth;
	@Autowired
	private OrderRepository orderRepository;
	
	Logger logger = LoggerFactory.getLogger(UserService.class);

	public ResponseEntity<ResponseBase> Check(String token) {
		ResponseBase response = new ResponseBase();

		FirebaseToken decodedToken;
		try {
			decodedToken = firebaseAuth.verifyIdToken(token);
		} catch (FirebaseAuthException e) {
			response.setReturnCode(HttpStatus.FORBIDDEN.value());
			response.setReturnMessage(e.getErrorCode());
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
		}

		String uid = decodedToken.getUid();
		logger.debug("uid:" + uid);

		User u = userRepository.findByUid(uid);
		if (u != null) {
			response.setReturnCode(ReturnCode.OK);
			response.Set("uid", u.getUid());
			response.Set("isuser", true);
		} else {
			response.setReturnCode(ReturnCode.OK);
			response.Set("uid", null);
			response.Set("isuser", false);
		}
		return ResponseEntity.ok(response);
	}
	
	public ResponseEntity<ResponseBase> Add(String token, UserDTO request) {
		ResponseBase response = new ResponseBase();

		FirebaseToken decodedToken;
		try {
			decodedToken = firebaseAuth.verifyIdToken(token);
		} catch (FirebaseAuthException e) {
			response.setReturnCode(HttpStatus.FORBIDDEN.value());
			response.setReturnMessage(e.getErrorCode());
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
		}

		String uid = decodedToken.getUid();
		
		if ( userRepository.findByUid(uid) != null ) {
			response.setReturnCode(ReturnCode.OK);
			response.setReturnMessage("exist user");
			response.Set("status", false);
			return ResponseEntity.ok(response) ;
		}

		User user = new User();
		user.setUid(uid);
		user.setName(request.name);
		user.setBirthday(LocalDate.parse(request.birth));
		user.setGender(User.Gender.valueOf(request.gender));
		user.setLogintype(request.logintype);
		user.setPhone(request.phone);
		user.setCreateTime(LocalDateTime.now());
		userRepository.save(user);
		
		response.Set("status", true);

		return ResponseEntity.ok(response);
	}
}
