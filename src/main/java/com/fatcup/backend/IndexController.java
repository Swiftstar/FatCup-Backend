package com.fatcup.backend;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fatcup.backend.data.DrinkRepository;
import com.fatcup.backend.data.OrderDetail;
import com.fatcup.backend.data.OrderDetailRepository;
import com.fatcup.backend.data.OrderRepository;
import com.fatcup.backend.data.Orders;
import com.fatcup.backend.data.OrdersStatus;
import com.fatcup.backend.data.Customer;
import com.fatcup.backend.data.CustomerRepository;
import com.fatcup.backend.net.ResponseBase;
import com.fatcup.backend.net.ReturnCode;

import io.swagger.annotations.Api;

@Controller
@Api(description = "首頁")
public class IndexController {
	
	Logger logger = LoggerFactory.getLogger(IndexController.class);
	
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	OrderRepository orderRepository;
	@Autowired
	OrderDetailRepository orderDetailRepository;
	@Autowired
	DrinkRepository drinkRepository;
	
	@RequestMapping(value = "/", method = {RequestMethod.GET})
	public String index(ModelAndView mv) {
		return "main";
	}
	
	@RequestMapping(value = "/quickorder", method = {RequestMethod.GET})
	public String QuickOrder(ModelAndView mv) {
		return "quickorder";
	}
	
	@RequestMapping(value = "/new", method = {RequestMethod.GET})
	public String n(Model m) {
		m.addAttribute("name","Henry");
		return "newfile";
	}
	
	@RequestMapping(value = "/banners", method = {RequestMethod.GET})
	public @ResponseBody ResponseBase Banner(HttpServletRequest requestHttp) {
		ResponseBase response = new ResponseBase();
		
		String scheme = requestHttp.getScheme();
		String serverName = requestHttp.getServerName();
		int port = requestHttp.getServerPort();
	
		ArrayList<String> banners = new ArrayList<String>();
		banners.add(String.format("%s://%s:%s/%s", scheme,serverName,port,"banner/b1.jpg"));
		banners.add(String.format("%s://%s:%s/%s", scheme,serverName,port,"banner/b2.jpg"));
		banners.add(String.format("%s://%s:%s/%s", scheme,serverName,port,"banner/b3.jpg"));
		response.Set("banners", banners);
		return response;
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
		
		Customer user = new Customer();
		user.setUid("a001");
		customerRepository.save(user);
		
//		Drink drink = new Drink();
//		drink.setName("apple juice");	
//		drinkRepository.save(drink);
//		
//		Drink drink1 = new Drink();
//		drink1.setName("orange juice");	
//		drinkRepository.save(drink1);
		
		Set<OrderDetail> set = new HashSet<OrderDetail>();
		OrderDetail oDetail = new OrderDetail(
				drinkRepository.findById(Integer.valueOf(1)).get());
		oDetail.setNum(1);
		set.add(oDetail);
		oDetail = new OrderDetail(
				drinkRepository.findById(Integer.valueOf(4)).get());
		oDetail.setNum(2);
		set.add(oDetail);
		orderDetailRepository.saveAll(set);

		Orders orders = new Orders();
		orders.setCustomer(user);
		orders.setDetails(set);
		orders.setOrderDateTime( LocalDateTime.now());
		orders.setStatus(OrdersStatus.WATING);
		orderRepository.save(orders);
		
		return register;
	}
}
