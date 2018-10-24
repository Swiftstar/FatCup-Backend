package com.fatcup.backend;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fatcup.backend.data.Drink;
import com.fatcup.backend.data.DrinkRepository;
import com.fatcup.backend.net.ResponseBase;
import com.fatcup.backend.net.ReturnCode;

@RestController
@RequestMapping("/drink")
public class DrinkController {
	
	@Autowired
	DrinkRepository drinkRepository;
	
	@RequestMapping(value = "/list", method = {RequestMethod.GET})
	public ResponseBase List() {
		ResponseBase response = new ResponseBase();	
		ArrayList<Drink> aList = (ArrayList<Drink>) drinkRepository.findAll();
		
		response.setReturnCode(ReturnCode.OK);
		response.Set("drinklist", aList);
		
		return response;
	}
	
	@RequestMapping(value = "/test", method = {RequestMethod.GET})
	public ResponseBase Test() throws Exception{
		throw new UnsupportedOperationException();
//		GeneralResponse response = new GeneralResponse();	
//		ArrayList<Drink> aList = (ArrayList<Drink>) drinkRepository.findAll();
//		
//		response.setReturnCode(ReturnCode.OK);
//		response.Set("drinklist", aList);
//		
//		return response;
	}

}
