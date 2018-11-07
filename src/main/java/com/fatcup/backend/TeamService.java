package com.fatcup.backend;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fatcup.backend.data.OrderRepository;
import com.fatcup.backend.data.Orders;
import com.fatcup.backend.data.OrdersStatus;
import com.fatcup.backend.net.ResponseBase;
import com.fatcup.backend.net.TeamOrderDTO;

@Service
public class TeamService {

	@Autowired
	OrderRepository orderRepository;
	
	
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
		orderRepository.save(orders);
		
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, Object> data = objectMapper.convertValue(orders, HashMap.class);
		response.Set(data);
		return ResponseEntity.ok(response);
	}
}
