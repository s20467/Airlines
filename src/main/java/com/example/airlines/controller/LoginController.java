package com.example.airlines.controller;


import com.example.airlines.command.RegisterCommand;
import com.example.airlines.model.Account;
import com.example.airlines.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final AccountService accountService;

    @PermitAll
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PermitAll
    @GetMapping("/register")
    public String registerAccount(Model model) {
        model.addAttribute("registerCommand", new RegisterCommand());
        return "register";
    }

    @PermitAll
    @PostMapping("/register")
    public String processRegisterAccount(@Valid RegisterCommand registerCommand, BindingResult bindingResult, Model model) {
        if(accountService.emailExists(registerCommand.getEmail())) {
            bindingResult.addError(new FieldError("registerCommand", "email", "Użytkownik o podanym emailu już istnieje"));
        }
        if(bindingResult.hasErrors()) {
            return "register";
        }
        accountService.createClientAccount(registerCommand);
        return "registration_success";
    }

}
