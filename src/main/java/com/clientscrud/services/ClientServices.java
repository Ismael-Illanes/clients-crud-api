package com.clientscrud.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clientscrud.models.ClientModel;
import com.clientscrud.models.ExpenseModel;
import com.clientscrud.models.WalletModel;
import com.clientscrud.repository.ClientRepository;
import com.clientscrud.repository.ExpenseRepository;

import dto.ClientDTO;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ClientServices {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    public List<ClientDTO> findAll() {
        return clientRepository.findAll().stream()
                .map(client -> {
                    Double totalExpenseAmount = client.getWallet().getExpenses().stream()
                            .mapToDouble(ExpenseModel::getAmount)
                            .sum();
                    return new ClientDTO(client, totalExpenseAmount);
                })
                .collect(Collectors.toList());
    }

    public ClientDTO findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Client ID cannot be null");
        }
        Optional<ClientModel> optionalClient = clientRepository.findById(id);
        if (optionalClient.isEmpty()) {
            throw new EntityNotFoundException("Client with ID " + id + " not found.");
        }
        ClientModel client = optionalClient.get();
        Double totalExpenseAmount = client.getWallet().getExpenses().stream()
                .mapToDouble(ExpenseModel::getAmount)
                .sum();
        return new ClientDTO(client, totalExpenseAmount);
    }

    public String getBalance(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Client ID cannot be null");
        }
        ClientModel client = clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Client with ID " + id + " not found."));
        return client.getName() + "'s balance: " + client.getBalance() + " $";
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
        LocalDateTime now = LocalDateTime.now();
        String formattedDateTime = now.format(DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss:SSS"));

        client.setCreatedAt(formattedDateTime);
        client.setUpdatedAt(formattedDateTime);
        client.setBalance(0.0);
        client.setLastRecharge(0.0);
        client.setTotalRecharge(0.0);
        client.setLastExpense(0.0);
        client.setTotalExpense(0.0);

        WalletModel wallet = new WalletModel();
        wallet.setClient(client);
        client.setWallet(wallet);

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
        existingClient.setUpdatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss:SSS")));

        clientRepository.save(existingClient);
    }

    @Transactional
    public void rechargeBalance(Long clientId, Double amount) {
        ClientModel client = clientRepository.findById(clientId)
                .orElseThrow(() -> new EntityNotFoundException("Client with ID " + clientId + " not found."));

        client.setBalance(client.getBalance() + amount);
        client.setLastRecharge(amount);
        client.setTotalRecharge(client.getTotalRecharge() + amount);
        client.setUpdatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss:SSS")));

        clientRepository.save(client);
    }

    @Transactional
    public void spendBalance(Long clientId, Double amount, String description) {
        ClientModel client = clientRepository.findById(clientId)
                .orElseThrow(() -> new EntityNotFoundException("Client with ID " + clientId + " not found."));

        if (client.getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient balance for the expense.");
        }

        client.setBalance(client.getBalance() - amount);
        client.setLastExpense(amount);
        client.setTotalExpense(client.getTotalExpense() + amount);
        client.setUpdatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss:SSS")));

        ExpenseModel expense = new ExpenseModel();
        expense.setAmount(amount);
        expense.setDescription(description);
        expense.setDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss:SSS")));
        expense.setWallet(client.getWallet());

        expenseRepository.save(expense);
        clientRepository.save(client);
    }
}
