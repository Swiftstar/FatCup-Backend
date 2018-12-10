package com.fatcup.backend.data;

import java.util.Collection;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface OrderRepository extends CrudRepository<Orders, Long> {

	public List<Orders> findByCustomer(Customer customer);
	
	public List<Orders> findByStatusIn(Collection<OrdersStatus> status);
	
	public List<Orders> findByTeam(Team team);
	public List<Orders> findByTeamAndStatus(Team team, OrdersStatus status);
}
