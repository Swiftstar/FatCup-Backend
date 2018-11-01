package com.fatcup.backend;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fatcup.backend.data.Customer;
import com.fatcup.backend.data.CustomerRepository;
import com.fatcup.backend.net.ResponseBase;
import com.fatcup.backend.net.ReturnCode;
import com.fatcup.backend.net.UserDTO;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private FirebaseAuth firebaseAuth;
	
	Logger logger = LoggerFactory.getLogger(CustomerService.class);

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

		Customer u = customerRepository.findByUid(uid);
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
		
		if ( customerRepository.findByUid(uid) != null ) {
			response.setReturnCode(ReturnCode.OK);
			response.setReturnMessage("exist customer");
			response.Set("status", false);
			return ResponseEntity.ok(response) ;
		}

		Customer customer = new Customer();
		customer.setUid(uid);
		customer.setName(request.name);
		customer.setBirthday(LocalDate.parse(request.birth));
		customer.setGender(Customer.Gender.valueOf(request.gender));
		customer.setLogintype(request.logintype);
		customer.setPhone(request.phone);
		customer.setCreateTime(LocalDateTime.now());
		customerRepository.save(customer);
		
		response.Set("status", true);

		return ResponseEntity.ok(response);
	}
}
