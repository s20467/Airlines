package com.example.airlines.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@MappedSuperclass
public abstract class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter protected Integer id;

    @Getter @Setter protected String firstName;
    @Getter @Setter protected String lastName;
    @Getter @Setter protected String telephoneNumber;
    @Getter @Setter protected String username;
    @Getter @Setter protected String password;

    @Embedded
    @Getter @Setter private Address address;
}
