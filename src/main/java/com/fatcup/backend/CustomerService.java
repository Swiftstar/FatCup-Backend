package com.fatcup.backend;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fatcup.backend.data.Customer;
import com.fatcup.backend.data.CustomerRepository;
import com.fatcup.backend.data.Orders;
import com.fatcup.backend.net.OrderDTO;
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
	@Autowired
	private OrderSevice orderSevice;
	
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
	
	public ResponseEntity<ResponseBase> OrderAdd(OrderDTO request) {
		ResponseBase response = new ResponseBase();	
		int result = orderSevice.AddOrder(request);	
		switch(result) {
			case -1:
				response.setReturnCode(HttpStatus.BAD_REQUEST.value());
				response.setReturnMessage("user not exist");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			case -2:
				response.setReturnCode(HttpStatus.BAD_REQUEST.value());
				response.setReturnMessage("drink id not exist");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		
		response.Set("order_id", result);	
		return ResponseEntity.ok(response);
	}
	
	public ResponseEntity<ResponseBase> OrderHistory(OrderDTO request) {
		ResponseBase response = new ResponseBase();	
		List<Orders> orders = orderSevice.OrderHistory(request);
		if (orders == null) {
			response.setReturnCode(HttpStatus.BAD_REQUEST.value());
			response.setReturnMessage("request error");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		
		List<Map<String, Object>> ordersList = new ArrayList<Map<String, Object>>();
		for (Orders o : orders) {
			Map<String, Object> singeOrder = new HashMap<String, Object>();
			singeOrder.put("items", o.getDetails());
			singeOrder.put("order_id", o.getId());
			ordersList.add(singeOrder);
		}
		response.Set("orders", orders);
		return ResponseEntity.ok(response);
	}
	
	@SuppressWarnings("unchecked")
	public ResponseEntity<ResponseBase> OrderFinish(OrderDTO request) {
		ResponseBase response = new ResponseBase();
		Orders orders = orderSevice.OrderFinish(request.orderId);
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, Object> data = objectMapper.convertValue(orders, HashMap.class);
		response.Set(data);
		return ResponseEntity.ok(response);
	}
	
	@SuppressWarnings("unchecked")
	public ResponseEntity<ResponseBase> OrderDelete(OrderDTO request) {
		ResponseBase response = new ResponseBase();
		Orders orders = orderSevice.OrderDelete(request.orderId);
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, Object> data = objectMapper.convertValue(orders, HashMap.class);
		response.Set(data);
		return ResponseEntity.ok(response);
	}
}
