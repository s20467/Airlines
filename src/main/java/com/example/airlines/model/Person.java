package com.example.airlines.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@MappedSuperclass
@Getter
@Setter
public abstract class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    protected String firstName;
    protected String lastName;
    protected String telephoneNumber;
    protected String username;
    protected String password;

    @Embedded
    private Address address;
}
