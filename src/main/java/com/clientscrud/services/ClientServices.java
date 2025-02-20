package com.clientscrud.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clientscrud.models.ClientModel;

import jakarta.transaction.Transactional;
import com.clientscrud.repository.*;;

@Service
public class ClientServices {

    @Autowired
    private ClientRepository clientRepository;

    public List<ClientModel> findAll() {
        return clientRepository.findAll();
    }

    @Transactional
    public ClientModel insertClient(ClientModel client) {
        System.out.println("Client inserted successfully ( " + client.toString() + " )");
        client.setUpdatedAt(java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss:SSS")));
        client.setCreatedAt(java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss:SSS")));
        return clientRepository.save(client);
    }
}
