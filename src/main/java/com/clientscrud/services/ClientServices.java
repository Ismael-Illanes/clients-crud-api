package com.clientscrud.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clientscrud.models.ClientModel;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import com.clientscrud.repository.*;;

@Service
public class ClientServices {

    @Autowired
    private ClientRepository clientRepository;

    public List<ClientModel> findAll() {
        return clientRepository.findAll();
    }

    public ClientModel findById(Long id) {
        return clientRepository.findById(id).map(p -> p.getId() == id ? p : null).orElse(null);
    }

    @Transactional
    public void deleteClientById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Client ID cannot be null");
        }
        if (!clientRepository.existsById(id)) {
            throw new EntityNotFoundException("Client with ID " + id + " not found.");
        }
        clientRepository.deleteById(id);
    }

    @Transactional
    public String insertClient(ClientModel client) {
        System.out.println("Client inserted successfully ( " + client.toString() + " )");
        client.setUpdatedAt(java.time.LocalDateTime.now()
                .format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss:SSS")));
        client.setCreatedAt(java.time.LocalDateTime.now()
                .format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss:SSS")));
        clientRepository.save(client);
        return "Client inserted successfully";
    }

    @Transactional
    public void updateClient(ClientModel client) {
        if (client == null || client.getId() == null) {
            throw new IllegalArgumentException("Client and its ID cannot be null");
        }

        ClientModel existingClient = clientRepository.findById(client.getId())
                .orElseThrow(() -> new EntityNotFoundException("Client with ID " + client.getId() + " not found."));

        existingClient.setName(client.getName());
        existingClient.setEmail(client.getEmail());
        existingClient.setPhone(client.getPhone());
        existingClient.setAddress(client.getAddress());

        existingClient.setUpdatedAt(java.time.LocalDateTime.now()
                .format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss:SSS")));

        clientRepository.save(existingClient);
    }

}
