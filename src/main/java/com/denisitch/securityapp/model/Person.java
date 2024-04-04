package com.denisitch.securityapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @NotEmpty(message = "Имя не должно быть пустым")
    @Size(min = 2, max = 100, message = "Имя должно быть от 2 до 100 символов длиной")
    @Column(name = "username", nullable = false)
    private String username;
    @Min(value = 1900, message = "Год рождения должен быть больше, чем 1900")
    @Column(name = "years_of_birth", nullable = false)
    private Integer yearsOfBirth;
    @Column(name = "password", nullable = false)
    private String password;
}