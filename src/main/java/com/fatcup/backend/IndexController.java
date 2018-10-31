package com.fatcup.backend;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fatcup.backend.data.Drink;
import com.fatcup.backend.data.DrinkRepository;
import com.fatcup.backend.data.OrderDetail;
import com.fatcup.backend.data.OrderDetailRepository;
import com.fatcup.backend.data.OrderRepository;
import com.fatcup.backend.data.Orders;
import com.fatcup.backend.data.User;
import com.fatcup.backend.data.UserRepository;
import com.fatcup.backend.net.ResponseBase;
import com.fatcup.backend.net.ReturnCode;

import io.swagger.annotations.Api;

@Controller
@Api(description = "首頁")
public class IndexController {
	
	@Autowired
	UserRepository userRepository;
	@Autowired
	OrderRepository orderRepository;
	@Autowired
	OrderDetailRepository orderDetailRepository;
	@Autowired
	DrinkRepository drinkRepository;
	
	@RequestMapping(value = "/", method = {RequestMethod.GET})
	public @ResponseBody String index() {
		
		User user = new User();
		user.setUid("a001");
		userRepository.save(user);
		
		Drink drink = new Drink();
		drink.setName("apple juice");	
		drinkRepository.save(drink);
		
		Drink drink1 = new Drink();
		drink1.setName("orange juice");	
		drinkRepository.save(drink1);
		
		Set<OrderDetail> set = new HashSet<OrderDetail>();
		set.add(new OrderDetail(drink));
		set.add(new OrderDetail(drink1));
		orderDetailRepository.saveAll(set);

		Orders orders = new Orders();
		orders.setUser(user);
		orders.setDetails(set);
		orders.setOrderDateTime( LocalDateTime.now());
		orderRepository.save(orders);

		return "Hello";
	}
	
	@RequestMapping(value = "/new", method = {RequestMethod.GET})
	public String n(Model m) {
		m.addAttribute("name","Henry");
		return "newfile";
	}

	@RequestMapping(value = "/test", method = {RequestMethod.POST, RequestMethod.GET})
	public @ResponseBody ResponseBase register(){
		ResponseBase register = new ResponseBase();
		register.setReturnCode(ReturnCode.OK);
		ArrayList<String> testL = new ArrayList<>();
		testL.add("abc");
		testL.add("def");
		register.Set("Number", 123);
		register.Set("String", "Test");
		register.Set("List", testL);
		register.Set("Register", new ResponseBase());
		return register;
	}
}
