package com.clientscrud.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.clientscrud.models.ClientModel;
import com.clientscrud.services.ClientServices;

@RestController
@RequestMapping("/api/clients")
public class ClientController {
    @Autowired
    public ClientServices clientServices;

    @GetMapping("/findAll")
    public List<ClientModel> getClients() {
        return clientServices.findAll();

    }

    @GetMapping("/find/")
    public ClientModel getClientById(@RequestParam("id") Long id) {
        return clientServices.findById(id);
    }

    @DeleteMapping("/delete")
    public String deleteClient(@RequestParam("id") Long id) {
        clientServices.deleteClientById(id);
        return "Client deleted successfully";
    }

    @PostMapping("/insert")
    public String insertClient(@RequestBody ClientModel client) {
        clientServices.insertClient(client);
        return "Client inserted successfully added ( " + client.toString() + " )";
    }

    @PutMapping("/update")
    public String updateClient(@RequestBody ClientModel client) {
        clientServices.updateClient(client);
        return "Client updated successfully";
    }

}
