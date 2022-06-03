package com.example.airlines.service;

import com.example.airlines.model.Client;

import java.util.List;

public interface ClientService {
    List<Client> getClients();
    Client getById(Integer clientId);
}
