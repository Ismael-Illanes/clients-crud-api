package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import models.ClientModel;
import repository.ClientRepository;

@Service
public class ClientServices {

    @Autowired
    private ClientRepository clientRepository;

    public List<ClientModel> findAll() {
        return clientRepository.findAll();
    }

    @Transactional
    public void insertClient() {
        ClientModel client = new ClientModel(null, "Ismael", "ismael@gmail.com", "123456789", "123 Main Street");

        clientRepository.save(client);
        System.out.println("Client inserted successfully ( " + client.toString() + " )");
    }
}
