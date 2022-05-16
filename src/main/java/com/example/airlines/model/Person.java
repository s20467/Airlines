package com.example.airlines.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@MappedSuperclass
public abstract class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter protected Integer id;

    @Getter @Setter protected String firstName;
    @Getter @Setter protected String lastName;
    @Getter @Setter protected String telephoneNumber;

    @Embedded
    @Getter @Setter private Address address;
}
