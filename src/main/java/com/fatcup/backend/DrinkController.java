package com.fatcup.backend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fatcup.backend.data.Drink;
import com.fatcup.backend.data.DrinkRepository;
import com.fatcup.backend.net.ResponseBase;
import com.fatcup.backend.net.ReturnCode;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/drink")
public class DrinkController {
	
	@Autowired
	DrinkRepository drinkRepository;
	
	ObjectMapper objectMapper = new ObjectMapper();
	
	@RequestMapping(value = "/list", method = {RequestMethod.GET})
	public ResponseBase List(
			@RequestParam(name = "language", required = false, defaultValue = "US") String language) {
		ResponseBase response = new ResponseBase();
		ArrayList<Drink> aList = (ArrayList<Drink>) drinkRepository.findAll();

		response.setReturnCode(ReturnCode.OK);
		response.Set("drinklist", aList);

		return response;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/information", method = {RequestMethod.GET})
	public ResponseEntity<ResponseBase> IndexInformation(
			@RequestParam(name = "language", required = false, defaultValue = "US") String language,
			@RequestParam(name="drinkid") Integer drinkId) {
		ResponseBase response = new ResponseBase();
		
		if ( drinkId != null ) {
			
			Optional<Drink> drink = drinkRepository.findById(drinkId.intValue());
			if ( drink.isPresent()) {
				Map<String, Object> data = objectMapper.convertValue(drink.get(), HashMap.class);
				response.Set(data);
			} else {
				response.setReturnMessage("drink not found");
			}
			return ResponseEntity.ok(response);
		}
		else {
			response.setReturnCode(HttpStatus.BAD_REQUEST.ordinal());
			response.setReturnMessage("drinkid not exist or parse error");;
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}
	
	@RequestMapping(value = "/test", method = {RequestMethod.GET})
	public ResponseBase Test() throws Exception{
		ResponseBase response = new ResponseBase();	
		ArrayList<Drink> aList = (ArrayList<Drink>) drinkRepository.findAll();
		
		response.setReturnCode(ReturnCode.OK);
		response.Set("drinklist", aList);
		
		return response;
	}

}
