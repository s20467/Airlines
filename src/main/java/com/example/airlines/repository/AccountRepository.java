package com.example.airlines.repository;

import com.example.airlines.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    Optional<Account> findAccountByEmail(String email);
    boolean existsAccountByEmail(String email);
}
