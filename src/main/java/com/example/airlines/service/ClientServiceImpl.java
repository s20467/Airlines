package com.example.airlines.service;

import com.example.airlines.model.Client;
import com.example.airlines.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    @Override
    public List<Client> getClients() {
        return clientRepository.findAll();
    }

    @Override
    public Client getById(Integer clientId) {
        return clientRepository.findById(clientId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client with id: " + clientId + " not found"));
    }
}
