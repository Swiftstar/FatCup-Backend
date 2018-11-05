package com.fatcup.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fatcup.backend.net.ResponseBase;

@RestController
@RequestMapping("/team")
public class TeamController {
	
	@Autowired
	TeamService teamService;

	@RequestMapping(path="/map/orders")
	public ResponseEntity<ResponseBase> MapOrders() {	
		return teamService.MapOrders();
	}
}
