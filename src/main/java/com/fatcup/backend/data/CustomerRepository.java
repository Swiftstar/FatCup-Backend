package com.fatcup.backend.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.fatcup.backend.data.Customer;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

@Transactional
public interface CustomerRepository extends CrudRepository<Customer, Integer> {

	 public Customer findByUid(String uid);
}
