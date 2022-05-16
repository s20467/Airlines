package com.example.airlines.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;


@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Address {
    @Getter @Setter private String Country;
    @Getter @Setter private String City;
    @Getter @Setter private String Street;
}
