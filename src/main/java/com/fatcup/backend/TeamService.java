package com.fatcup.backend;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fatcup.backend.data.OrderRepository;
import com.fatcup.backend.data.Orders;
import com.fatcup.backend.data.OrdersStatus;
import com.fatcup.backend.data.Team;
import com.fatcup.backend.data.TeamRepository;
import com.fatcup.backend.net.ResponseBase;
import com.fatcup.backend.net.ReturnCode;
import com.fatcup.backend.net.TeamOrderDTO;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;

@Service
public class TeamService {

	Logger logger = LoggerFactory.getLogger(TeamService.class);
	@Autowired
	OrderRepository orderRepository;
	@Autowired
	TeamRepository teamRepository;
	@Autowired
	FirebaseAuth firebaseAuth;
	
	public ResponseEntity<ResponseBase> UserCheck(String token) {
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

		Team u = teamRepository.findById(uid).get();
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
	
	public ResponseEntity<ResponseBase> MapOrders() {
		ResponseBase response = new ResponseBase();
		
		Collection<OrdersStatus> status = new HashSet<OrdersStatus>();		
		status.add(OrdersStatus.WATING);
		status.add(OrdersStatus.ACCEPT);	
		ArrayList<Orders> result = (ArrayList<Orders>) orderRepository.findByStatusIn(status);
		
		response.Set("taskList", result);
		return ResponseEntity.ok(response);
	}
	
	@SuppressWarnings("unchecked")
	public ResponseEntity<ResponseBase> TaskInformation(String language, Long orderId) {
		ResponseBase response = new ResponseBase();
		Orders orders = orderRepository.findById(orderId).get();
		
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, Object> data = objectMapper.convertValue(orders, HashMap.class);
		response.Set(data);
		return ResponseEntity.ok(response);
	}
	
	@SuppressWarnings("unchecked")
	public ResponseEntity<ResponseBase> TaskAdd(TeamOrderDTO request) {
		ResponseBase response = new ResponseBase();
		Orders orders = orderRepository.findById(request.orderId).get();
		orders.setStatus(OrdersStatus.ACCEPT);	
		orders.setTeamLat(request.latitude);
		orders.setTeamLong(request.longitude);
		orderRepository.save(orders);
		
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, Object> data = objectMapper.convertValue(orders, HashMap.class);
		response.Set(data);
		return ResponseEntity.ok(response);
	}
	
	public ResponseEntity<ResponseBase> UserTask(TeamOrderDTO request) {
		ResponseBase response = new ResponseBase();
		Team team = teamRepository.findById(request.uid).get();	
		List<Orders> orders = orderRepository.findByTeamAndStatus(team, OrdersStatus.ACCEPT);
		response.Set("orders", orders);
		return ResponseEntity.ok(response);
	}
	
	@SuppressWarnings("unchecked")
	public ResponseEntity<ResponseBase> TaskFinish(TeamOrderDTO request) {
		ResponseBase response = new ResponseBase();
		Orders orders = orderRepository.findById(request.orderId).get();
		orders.setStatus(OrdersStatus.FINISH);
		orderRepository.save(orders);
		
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, Object> data = objectMapper.convertValue(orders, HashMap.class);
		response.Set(data);
		return ResponseEntity.ok(response);
	}
	
	@SuppressWarnings("unchecked")
	public ResponseEntity<ResponseBase> TaskDelete(TeamOrderDTO request) {
		ResponseBase response = new ResponseBase();
		Orders orders = orderRepository.findById(request.orderId).get();
		orders.setStatus(OrdersStatus.CANCEL);
		orders.setRemark(request.reason);
		orderRepository.save(orders);
		
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, Object> data = objectMapper.convertValue(orders, HashMap.class);
		response.Set(data);
		return ResponseEntity.ok(response);
	}
	
	public ResponseEntity<ResponseBase> TaskHistory(TeamOrderDTO request) {
		ResponseBase response = new ResponseBase();
		Team team = teamRepository.findById(request.uid).get();	
		List<Orders> orders = orderRepository.findByTeam(team);
		response.Set("orders", orders);
		return ResponseEntity.ok(response);
	}
}
