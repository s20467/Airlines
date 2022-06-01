package com.example.airlines.service;

import com.example.airlines.command.RegisterCommand;
import com.example.airlines.model.Account;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AccountService extends UserDetailsService {
    Account createClientAccount(RegisterCommand registerCommand);
    boolean emailExists(String email);
}
