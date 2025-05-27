package com.clientscrud.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.clientscrud.models.ClientModel;
import com.clientscrud.models.ExpenseModel;
import com.clientscrud.models.WalletModel;
import com.clientscrud.repository.ClientRepository;
import com.clientscrud.repository.ExpenseRepository;

import dto.ClientDTO;
import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
public class ClientServicesTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ExpenseRepository expenseRepository;

    @InjectMocks
    private ClientServices clientServices;

    private ClientModel testClient;
    private WalletModel testWallet;
    private ExpenseModel testExpense;

    @BeforeEach
    void setUp() {
        // Setup test client
        testClient = new ClientModel();
        testClient.setId(1L);
        testClient.setName("Test Client");
        testClient.setEmail("test@example.com");
        testClient.setPhone("1234567890");
        testClient.setAddress("Test Address");
        testClient.setBalance(100.0);
        testClient.setLastRecharge(50.0);
        testClient.setTotalRecharge(150.0);
        testClient.setLastExpense(25.0);
        testClient.setTotalExpense(75.0);
        testClient.setCreatedAt("01/01/2023 - 12:00:00:000");
        testClient.setUpdatedAt("01/01/2023 - 12:00:00:000");

        // Setup test wallet
        testWallet = new WalletModel();
        testWallet.setId(1L);
        testWallet.setClient(testClient);

        // Setup test expense
        testExpense = new ExpenseModel();
        testExpense.setId(1L);
        testExpense.setAmount(25.0);
        testExpense.setDescription("Test Expense");
        testExpense.setDate("01/01/2023 - 12:00:00:000");
        testExpense.setWallet(testWallet);

        // Setup relationships
        List<ExpenseModel> expenses = new ArrayList<>();
        expenses.add(testExpense);
        testWallet.setExpenses(expenses);
        testClient.setWallet(testWallet);
    }

    @Test
    void testFindAll() {
        // Arrange
        List<ClientModel> clients = new ArrayList<>();
        clients.add(testClient);
        when(clientRepository.findAll()).thenReturn(clients);

        // Act
        List<ClientDTO> result = clientServices.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testClient.getId(), result.get(0).getId());
        assertEquals(testClient.getName(), result.get(0).getName());
        assertEquals(testClient.getEmail(), result.get(0).getEmail());
        assertEquals(25.0, result.get(0).getTotalExpense()); // Sum of expenses

        // Verify
        verify(clientRepository).findAll();
    }

    @Test
    void testFindById() {
        // Arrange
        when(clientRepository.findById(1L)).thenReturn(Optional.of(testClient));

        // Act
        ClientDTO result = clientServices.findById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(testClient.getId(), result.getId());
        assertEquals(testClient.getName(), result.getName());
        assertEquals(testClient.getEmail(), result.getEmail());
        assertEquals(25.0, result.getTotalExpense()); // Sum of expenses

        // Verify
        verify(clientRepository).findById(1L);
    }

    @Test
    void testFindById_WithNullId() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> clientServices.findById(null));

        // Verify
        verify(clientRepository, never()).findById(any());
    }

    @Test
    void testFindById_WithNonExistentId() {
        // Arrange
        when(clientRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> clientServices.findById(999L));

        // Verify
        verify(clientRepository).findById(999L);
    }

    @Test
    void testGetBalance() {
        // Arrange
        when(clientRepository.findById(1L)).thenReturn(Optional.of(testClient));

        // Act
        String result = clientServices.getBalance(1L);

        // Assert
        assertEquals("Test Client's balance: 100.0 $", result);

        // Verify
        verify(clientRepository).findById(1L);
    }

    @Test
    void testGetBalance_WithNullId() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> clientServices.getBalance(null));

        // Verify
        verify(clientRepository, never()).findById(any());
    }

    @Test
    void testGetBalance_WithNonExistentId() {
        // Arrange
        when(clientRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> clientServices.getBalance(999L));

        // Verify
        verify(clientRepository).findById(999L);
    }

    @Test
    void testDeleteClientById() {
        // Arrange
        when(clientRepository.existsById(1L)).thenReturn(true);
        doNothing().when(clientRepository).deleteById(1L);

        // Act
        clientServices.deleteClientById(1L);

        // Verify
        verify(clientRepository).existsById(1L);
        verify(clientRepository).deleteById(1L);
    }

    @Test
    void testDeleteClientById_WithNullId() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> clientServices.deleteClientById(null));

        // Verify
        verify(clientRepository, never()).existsById(any());
        verify(clientRepository, never()).deleteById(any());
    }

    @Test
    void testDeleteClientById_WithNonExistentId() {
        // Arrange
        when(clientRepository.existsById(999L)).thenReturn(false);

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> clientServices.deleteClientById(999L));

        // Verify
        verify(clientRepository).existsById(999L);
        verify(clientRepository, never()).deleteById(any());
    }

    @Test
    void testInsertClient() {
        // Arrange
        ClientModel newClient = new ClientModel();
        newClient.setName("New Client");
        newClient.setEmail("new@example.com");
        newClient.setPhone("9876543210");
        newClient.setAddress("New Address");

        when(clientRepository.save(any(ClientModel.class))).thenReturn(newClient);

        // Act
        clientServices.insertClient(newClient);

        // Assert
        assertNotNull(newClient.getCreatedAt());
        assertNotNull(newClient.getUpdatedAt());
        assertEquals(0.0, newClient.getBalance());
        assertEquals(0.0, newClient.getLastRecharge());
        assertEquals(0.0, newClient.getTotalRecharge());
        assertEquals(0.0, newClient.getLastExpense());
        assertEquals(0.0, newClient.getTotalExpense());
        assertNotNull(newClient.getWallet());
        assertEquals(newClient, newClient.getWallet().getClient());

        // Verify
        verify(clientRepository).save(newClient);
    }

    @Test
    void testUpdateClient() {
        // Arrange
        ClientModel updatedClient = new ClientModel();
        updatedClient.setId(1L);
        updatedClient.setName("Updated Client");
        updatedClient.setEmail("updated@example.com");
        updatedClient.setPhone("5555555555");
        updatedClient.setAddress("Updated Address");

        when(clientRepository.findById(1L)).thenReturn(Optional.of(testClient));
        when(clientRepository.save(any(ClientModel.class))).thenReturn(testClient);

        // Act
        clientServices.updateClient(updatedClient);

        // Assert
        assertEquals("Updated Client", testClient.getName());
        assertEquals("updated@example.com", testClient.getEmail());
        assertEquals("5555555555", testClient.getPhone());
        assertEquals("Updated Address", testClient.getAddress());
        assertNotNull(testClient.getUpdatedAt());

        // Verify
        verify(clientRepository).findById(1L);
        verify(clientRepository).save(testClient);
    }

    @Test
    void testUpdateClient_WithNullClient() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> clientServices.updateClient(null));

        // Verify
        verify(clientRepository, never()).findById(any());
        verify(clientRepository, never()).save(any());
    }

    @Test
    void testUpdateClient_WithNullId() {
        // Arrange
        ClientModel clientWithNullId = new ClientModel();

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> clientServices.updateClient(clientWithNullId));

        // Verify
        verify(clientRepository, never()).findById(any());
        verify(clientRepository, never()).save(any());
    }

    @Test
    void testUpdateClient_WithNonExistentId() {
        // Arrange
        ClientModel nonExistentClient = new ClientModel();
        nonExistentClient.setId(999L);

        when(clientRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> clientServices.updateClient(nonExistentClient));

        // Verify
        verify(clientRepository).findById(999L);
        verify(clientRepository, never()).save(any());
    }

    @Test
    void testRechargeBalance() {
        // Arrange
        when(clientRepository.findById(1L)).thenReturn(Optional.of(testClient));
        when(clientRepository.save(any(ClientModel.class))).thenReturn(testClient);

        // Act
        clientServices.rechargeBalance(1L, 50.0);

        // Assert
        assertEquals(150.0, testClient.getBalance()); // 100 + 50
        assertEquals(50.0, testClient.getLastRecharge());
        assertEquals(200.0, testClient.getTotalRecharge()); // 150 + 50
        assertNotNull(testClient.getUpdatedAt());

        // Verify
        verify(clientRepository).findById(1L);
        verify(clientRepository).save(testClient);
    }

    @Test
    void testRechargeBalance_WithNullId() {
        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> clientServices.rechargeBalance(null, 50.0));

        // Verify
        verify(clientRepository).findById(null);
        verify(clientRepository, never()).save(any());
    }

    @Test
    void testRechargeBalance_WithNonExistentId() {
        // Arrange
        when(clientRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> clientServices.rechargeBalance(999L, 50.0));

        // Verify
        verify(clientRepository).findById(999L);
        verify(clientRepository, never()).save(any());
    }

    @Test
    void testSpendBalance() {
        // Arrange
        when(clientRepository.findById(1L)).thenReturn(Optional.of(testClient));
        when(clientRepository.save(any(ClientModel.class))).thenReturn(testClient);
        when(expenseRepository.save(any(ExpenseModel.class))).thenReturn(testExpense);

        // Act
        clientServices.spendBalance(1L, 50.0, "Test Spend");

        // Assert
        assertEquals(50.0, testClient.getBalance()); // 100 - 50
        assertEquals(50.0, testClient.getLastExpense());
        assertEquals(125.0, testClient.getTotalExpense()); // 75 + 50
        assertNotNull(testClient.getUpdatedAt());

        // Verify
        verify(clientRepository).findById(1L);
        verify(expenseRepository).save(any(ExpenseModel.class));
        verify(clientRepository).save(testClient);
    }

    @Test
    void testSpendBalance_WithNullId() {
        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> clientServices.spendBalance(null, 50.0, "Test Spend"));

        // Verify
        verify(clientRepository).findById(null);
        verify(expenseRepository, never()).save(any());
        verify(clientRepository, never()).save(any());
    }

    @Test
    void testSpendBalance_WithNonExistentId() {
        // Arrange
        when(clientRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> clientServices.spendBalance(999L, 50.0, "Test Spend"));

        // Verify
        verify(clientRepository).findById(999L);
        verify(expenseRepository, never()).save(any());
        verify(clientRepository, never()).save(any());
    }

    @Test
    void testSpendBalance_WithInsufficientBalance() {
        // Arrange
        when(clientRepository.findById(1L)).thenReturn(Optional.of(testClient));

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> clientServices.spendBalance(1L, 150.0, "Test Spend"));

        // Verify
        verify(clientRepository).findById(1L);
        verify(expenseRepository, never()).save(any());
        verify(clientRepository, never()).save(any());
    }
}
