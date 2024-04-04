package com.denisitch.securityapp.service;

import com.denisitch.securityapp.model.Person;
import com.denisitch.securityapp.repository.PeopleRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PeopleService {
    private final PeopleRepository peopleRepository;

    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public Optional<Person> findUserByUsername(String username) {
        return peopleRepository.findByUsername(username);
    }
}
