package com.example.airlines.command;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


@Getter
@Setter
public class RegisterCommand {

    @NotEmpty
    @Size(min = 3, max = 30)
    private String email;

    @NotEmpty
    @Size(min = 3, max = 30)
    private String firstName;

    @NotEmpty
    @Size(min = 3, max = 30)
    private String lastName;

    @NotEmpty
    @Size(min = 3, max = 30)
    private String password;

    @NotEmpty
    @Size(min = 3, max = 50)
    private String country;

    @NotEmpty
    @Size(min = 3, max = 50)
    private String city;

    @NotEmpty
    @Size(min = 3, max = 50)
    private String street;

    @NotEmpty
    @Size(min = 5, max = 12)
    private String telephoneNumber;
}
