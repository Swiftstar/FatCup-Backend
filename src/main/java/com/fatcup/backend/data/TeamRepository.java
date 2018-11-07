package com.fatcup.backend.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface TeamRepository extends CrudRepository<Team, String> {

}
