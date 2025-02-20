package com.clientscrud.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clientscrud.models.ClientModel;
import com.clientscrud.services.ClientServices;

@RestController
@RequestMapping("/api")
public class ClientController {
    @Autowired
    public ClientServices clientServices;

    @GetMapping("/find/clients")
    public List<ClientModel> getClients() {
        return clientServices.findAll();

    }

    @PostMapping("/insert")

    public String insertClient(@RequestBody ClientModel client) {
        clientServices.insertClient(client);
        return "Client inserted successfully";
    }

    @GetMapping("/test")  // Asegúrate de que este endpoint esté aquí
    public String test() {
        return "API is working!";
    }
}
