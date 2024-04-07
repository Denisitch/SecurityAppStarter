package com.denisitch.securityapp.controller;

import com.denisitch.securityapp.dto.AuthDTO;
import com.denisitch.securityapp.dto.PersonDTO;
import com.denisitch.securityapp.model.Person;
import com.denisitch.securityapp.security.JwtUtil;
import com.denisitch.securityapp.service.RegistrationService;
import com.denisitch.securityapp.util.PersonValidator;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final PersonValidator personValidator;
    private final RegistrationService registrationService;
    private final JwtUtil jwtUtil;
    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(
            PersonValidator personValidator,
            RegistrationService registrationService,
            JwtUtil jwtUtil,
            ModelMapper modelMapper,
            AuthenticationManager authenticationManager)
    {
        this.personValidator = personValidator;
        this.registrationService = registrationService;
        this.jwtUtil = jwtUtil;
        this.modelMapper = modelMapper;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/registration")
    public Map<String, String> performRegistration(
            @RequestBody @Valid PersonDTO personDTO,
            BindingResult bindingResult
            ) {
        Person person = convertToPerson(personDTO);
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            return Map.of("message", "Ошибка!");
            //TODO: add exception handler
        }
        registrationService.register(person);
        String token = jwtUtil.generateToken(person.getUsername());
        return Map.of("JWT-token", token);
    }

    @PostMapping("/login")
    public Map<String, String> performLogin(@RequestBody AuthDTO authDTO) {
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(authDTO.getUsername(), authDTO.getPassword());
        try {
            authenticationManager.authenticate(authToken);
        } catch (BadCredentialsException e) {
            return Map.of("message", "Incorrect credentials!");
        }
        String token = jwtUtil.generateToken(authDTO.getUsername());
        return Map.of("JWT-token", token);
    }

    public Person convertToPerson(PersonDTO personDTO) {
        return modelMapper.map(personDTO, Person.class);
    }
}
