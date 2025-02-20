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
import dto.ClientDTO;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private ClientServices clientServices;

    @GetMapping("/findAll")
    public List<ClientDTO> getClients() {
        return clientServices.findAll();
    }

    @GetMapping("/find")
    public ClientDTO getClientById(@RequestParam("id") Long id) {
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
        return "Client inserted successfully";
    }

    @PutMapping("/update")
    public String updateClient(@RequestBody ClientModel client) {
        clientServices.updateClient(client);
        return "Client updated successfully";
    }

    @PostMapping("/recharge")
    public String rechargeBalance(@RequestParam("id") Long id, @RequestParam("amount") Double amount) {
        clientServices.rechargeBalance(id, amount);
        return "Balance recharged successfully by " + amount + " $";
    }

    @PostMapping("/spend")
    public String spendBalance(@RequestParam("id") Long id, @RequestParam("amount") Double amount,
            @RequestParam("description") String description) {
        clientServices.spendBalance(id, amount, description);
        return "Balance spent successfully";
    }

    @GetMapping("/balance")
    public String getBalance(@RequestParam("id") Long id) {
        return clientServices.getBalance(id);
    }
}
