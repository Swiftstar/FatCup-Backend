package com.fatcup.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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
	
	@RequestMapping(path="/user/check", method= { RequestMethod.POST })
	public ResponseEntity<ResponseBase> UserCheck(
			@RequestHeader(value = "Authorization") String token) {
		return teamService.UserCheck(token);
	}

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
	
	@RequestMapping(path="/user/task", method = {RequestMethod.POST})
	public ResponseEntity<ResponseBase> UserTask(@RequestBody TeamOrderDTO request) {
		return teamService.UserTask(request);
	}
	
	@RequestMapping(path="/task/finish", method = {RequestMethod.POST})
	public ResponseEntity<ResponseBase> TaskFinish(@RequestBody TeamOrderDTO request) {
		return teamService.TaskFinish(request);
	}
	
	@RequestMapping(path="/task/delete", method = {RequestMethod.POST})
	public ResponseEntity<ResponseBase> TaskDelete(@RequestBody TeamOrderDTO request) {
		return teamService.TaskDelete(request);
	}
	
	@RequestMapping(path="/task/history/{uid}", method = {RequestMethod.GET})
	public ResponseEntity<ResponseBase> TaskHistory(@PathVariable String uid) {
		TeamOrderDTO request = new TeamOrderDTO();
		request.uid = uid;
		return teamService.TaskHistory(request);
	}
}
