package com.fatcup.backend;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fatcup.backend.data.OrderRepository;
import com.fatcup.backend.data.Orders;
import com.fatcup.backend.data.OrdersStatus;
import com.fatcup.backend.net.ResponseBase;

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
}
