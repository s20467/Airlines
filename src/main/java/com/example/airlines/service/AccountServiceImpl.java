package com.example.airlines.service;

import com.example.airlines.command.RegisterCommand;
import com.example.airlines.model.Account;
import com.example.airlines.model.Address;
import com.example.airlines.model.Client;
import com.example.airlines.model.WrongAccountOwnerType;
import com.example.airlines.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return accountRepository.findAccountByEmail(username).orElseThrow(() ->
                new UsernameNotFoundException("User with username (email): " + username + " not found")
        );
    }

    @Override
    public Account createClientAccount(RegisterCommand registerCommand) {
        Account newAccount = new Account();
        Client newClient = new Client();
        newClient.setFirstName(registerCommand.getFirstName());
        newClient.setLastName(registerCommand.getLastName());
        newClient.setAddress(new Address(registerCommand.getCountry(), registerCommand.getCity(), registerCommand.getStreet()));
        newClient.setTelephoneNumber(registerCommand.getTelephoneNumber());
        newAccount.setEmail(registerCommand.getEmail());
        newAccount.setPassword(passwordEncoder.encode(registerCommand.getPassword()));
        try {
            newAccount.setClientOwner(newClient);
        } catch (WrongAccountOwnerType exc) {
            System.out.println(exc.getMessage());
        }
        return accountRepository.save(newAccount);
    }

    @Override
    public boolean emailExists(String email) {
        return accountRepository.existsAccountByEmail(email);
    }
}

