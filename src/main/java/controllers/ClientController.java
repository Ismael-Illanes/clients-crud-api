package controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import models.ClientModel;
import services.ClientServices;

@RequestMapping("/api")
@RestController
public class ClientController {
    @Autowired
    private ClientServices clientServices;

    @GetMapping("/find/clients")
    public List<ClientModel> getClients() {
        return clientServices.findAll();

    }

    @PostMapping("/insert/client")

    public String insertClient() {
        clientServices.insertClient();
        return "Client inserted successfully";
    }
}
