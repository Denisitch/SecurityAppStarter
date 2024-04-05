package com.denisitch.securityapp.service;

import com.denisitch.securityapp.model.Person;
import com.denisitch.securityapp.repository.PeopleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistrationService {
    private final PasswordEncoder passwordEncoder;
    private final PeopleRepository peopleRepository;

    public RegistrationService(PasswordEncoder passwordEncoder, PeopleRepository peopleRepository) {
        this.passwordEncoder = passwordEncoder;
        this.peopleRepository = peopleRepository;
    }

    @Transactional
    public void register(Person person) {
        String encodePass = passwordEncoder.encode(person.getPassword());
        person.setPassword(encodePass);
        person.setRole("ROLE_USER");
        peopleRepository.save(person);
    }
}
