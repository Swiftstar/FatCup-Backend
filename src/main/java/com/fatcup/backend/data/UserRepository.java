package com.fatcup.backend.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.fatcup.backend.data.User;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

@Transactional
public interface UserRepository extends CrudRepository<User, Integer> {

	 public User findByUid(String uid);
}
