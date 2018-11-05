package com.fatcup.backend;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fatcup.backend.data.Customer;
import com.fatcup.backend.data.CustomerRepository;
import com.fatcup.backend.data.Drink;
import com.fatcup.backend.data.DrinkRepository;
import com.fatcup.backend.data.OrderDetail;
import com.fatcup.backend.data.OrderDetailRepository;
import com.fatcup.backend.data.OrderRepository;
import com.fatcup.backend.data.Orders;
import com.fatcup.backend.data.OrdersStatus;
import com.fatcup.backend.net.OrderDTO;

@Service
public class OrderSevice {

	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private DrinkRepository drinkRepository;
	@Autowired
	private OrderDetailRepository orderDetailRepository;
	@Autowired
	private OrderRepository orderRepository;

	public int AddOrder(OrderDTO request) {

		Customer customer = customerRepository.findByUid(request.uid);
		
		if (customer == null)
			return -1;

		
		Set<OrderDetail> set = new HashSet<OrderDetail>();
		try {
			for (Map<String, Object> order : request.orderslist) {

				Drink drink;
				drink = drinkRepository
						.findById(Integer
								.parseInt(order.get("drink_id").toString()))
						.get();

				OrderDetail orderDetail = new OrderDetail(drink);
				orderDetail
						.setNum(Integer.parseInt(order.get("num").toString()));

				set.add(orderDetail);

			}
		} catch (Exception e) {
			return -2;
		}
		
		orderDetailRepository.saveAll(set);

		Orders orders = new Orders();
		orders.setCustomer(customer);
		orders.setStatus(OrdersStatus.WATING);
		orders.setDetails(set);
		orders.setCustomerLat(request.latitude);
		orders.setCustomerLong(request.longitude);
		orders.setRemark(request.remark);
		orders.setOrderDateTime(LocalDateTime.now());
		orderRepository.save(orders);
		
		return orders.getId();
	}
	
	public List<Orders> OrderHistory(OrderDTO request) {
		String uid = request.uid;
		Customer customer = customerRepository.findByUid(uid);
		if ( customer == null )
			return null;
		return orderRepository.findByCustomer(customer);
	}
}
