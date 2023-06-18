package com.ajith.spring.springint.repository;

import com.ajith.spring.springint.entities.Person;
import org.springframework.data.repository.CrudRepository;


public interface PersonRepo extends CrudRepository<Person, Long> {
}
