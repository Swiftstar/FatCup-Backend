package com.fatcup.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fatcup.backend.net.ResponseBase;
import com.fatcup.backend.net.TeamOrderDTO;

@RestController
@RequestMapping("/team")
public class TeamController {
	
	@Autowired
	TeamService teamService;

	@RequestMapping(path="/map/orders", method= { RequestMethod.GET })
	public ResponseEntity<ResponseBase> MapOrders() {	
		return teamService.MapOrders();
	}
	
	@RequestMapping(path="/task/information", method= { RequestMethod.GET })
	public ResponseEntity<ResponseBase> TaskInformation(
			@RequestParam(name = "language", required = false, defaultValue = "US") String language,
			@RequestParam(name = "orderid") Long orderId) {
		return teamService.TaskInformation(language, orderId);
	}
	
	@RequestMapping(path="/task/add", method= { RequestMethod.POST })
	public ResponseEntity<ResponseBase> TaskAdd(@RequestBody TeamOrderDTO request) {
		return teamService.TaskAdd(request);
	}
}
