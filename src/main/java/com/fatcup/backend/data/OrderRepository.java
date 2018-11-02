package com.fatcup.backend.data;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface OrderRepository extends CrudRepository<Orders, Long> {

	public List<Orders> findByCustomer(Customer customer);
}
