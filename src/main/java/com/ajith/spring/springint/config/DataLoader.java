package com.ajith.spring.springint.config;

import com.ajith.spring.springint.entities.Person;
import com.ajith.spring.springint.repository.PersonRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
public class DataLoader implements ApplicationRunner {

    @Autowired
    private PersonRepo personRepo;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("Loading default data:: ");
        Stream.iterate(0, i -> i+1)
                .limit(100)
                .forEach(x -> {
                    Person person = new Person();
                    person.setName("user"+x );
                    personRepo.save(person);
                });
    }
}
