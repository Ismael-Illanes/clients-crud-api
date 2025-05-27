package com.clientscrud.controllers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.clientscrud.models.ClientModel;
import com.clientscrud.services.ClientServices;
import com.fasterxml.jackson.databind.ObjectMapper;

import dto.ClientDTO;
import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
public class ClientControllerTest {

    @Mock
    private ClientServices clientServices;

    @InjectMocks
    private ClientController clientController;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private ClientModel testClient;
    private ClientDTO testClientDTO;
    private List<ClientDTO> clientDTOList;

    @BeforeEach
    void setUp() {
        // Setup MockMvc with exception handler
        mockMvc = MockMvcBuilders.standaloneSetup(clientController)
                .setControllerAdvice(new RestExceptionHandler())
                .build();

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

        // Setup test clientDTO
        testClientDTO = new ClientDTO(testClient, 25.0);

        // Setup clientDTO list
        clientDTOList = new ArrayList<>();
        clientDTOList.add(testClientDTO);
    }

    @Test
    void testGetClients() throws Exception {
        // Arrange
        when(clientServices.findAll()).thenReturn(clientDTOList);

        // Act & Assert
        mockMvc.perform(get("/clients/findAll"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(testClientDTO.getId()))
                .andExpect(jsonPath("$[0].name").value(testClientDTO.getName()))
                .andExpect(jsonPath("$[0].email").value(testClientDTO.getEmail()));

        // Verify
        verify(clientServices).findAll();
    }

    @Test
    void testGetClientById() throws Exception {
        // Arrange
        when(clientServices.findById(1L)).thenReturn(testClientDTO);

        // Act & Assert
        mockMvc.perform(get("/clients/find")
                .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(testClientDTO.getId()))
                .andExpect(jsonPath("$.name").value(testClientDTO.getName()))
                .andExpect(jsonPath("$.email").value(testClientDTO.getEmail()));

        // Verify
        verify(clientServices).findById(1L);
    }

    @Test
    void testGetClientById_WithNonExistentId() throws Exception {
        // Arrange
        when(clientServices.findById(999L)).thenThrow(new EntityNotFoundException("Client with ID 999 not found."));

        // Act & Assert
        mockMvc.perform(get("/clients/find")
                .param("id", "999"))
                .andExpect(status().isNotFound());

        // Verify
        verify(clientServices).findById(999L);
    }

    @Test
    void testDeleteClient() throws Exception {
        // Arrange
        doNothing().when(clientServices).deleteClientById(1L);

        // Act & Assert
        mockMvc.perform(delete("/clients/delete")
                .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Client deleted successfully"));

        // Verify
        verify(clientServices).deleteClientById(1L);
    }

    @Test
    void testDeleteClient_WithNonExistentId() throws Exception {
        // Arrange
        doThrow(new EntityNotFoundException("Client with ID 999 not found."))
                .when(clientServices).deleteClientById(999L);

        // Act & Assert
        mockMvc.perform(delete("/clients/delete")
                .param("id", "999"))
                .andExpect(status().isNotFound());

        // Verify
        verify(clientServices).deleteClientById(999L);
    }

    @Test
    void testInsertClient() throws Exception {
        // Arrange
        doNothing().when(clientServices).insertClient(any(ClientModel.class));

        // Act & Assert
        mockMvc.perform(post("/clients/insert")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testClient)))
                .andExpect(status().isOk())
                .andExpect(content().string("Client inserted successfully"));

        // Verify
        verify(clientServices).insertClient(any(ClientModel.class));
    }

    @Test
    void testUpdateClient() throws Exception {
        // Arrange
        doNothing().when(clientServices).updateClient(any(ClientModel.class));

        // Act & Assert
        mockMvc.perform(put("/clients/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testClient)))
                .andExpect(status().isOk())
                .andExpect(content().string("Client updated successfully"));

        // Verify
        verify(clientServices).updateClient(any(ClientModel.class));
    }

    @Test
    void testUpdateClient_WithNonExistentId() throws Exception {
        // Arrange
        doThrow(new EntityNotFoundException("Client with ID 999 not found."))
                .when(clientServices).updateClient(any(ClientModel.class));

        // Act & Assert
        mockMvc.perform(put("/clients/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testClient)))
                .andExpect(status().isNotFound());

        // Verify
        verify(clientServices).updateClient(any(ClientModel.class));
    }

    @Test
    void testRechargeBalance() throws Exception {
        // Arrange
        doNothing().when(clientServices).rechargeBalance(1L, 50.0);

        // Act & Assert
        mockMvc.perform(post("/clients/recharge")
                .param("id", "1")
                .param("amount", "50.0"))
                .andExpect(status().isOk())
                .andExpect(content().string("Balance recharged successfully by 50.0 $"));

        // Verify
        verify(clientServices).rechargeBalance(1L, 50.0);
    }

    @Test
    void testRechargeBalance_WithNonExistentId() throws Exception {
        // Arrange
        doThrow(new EntityNotFoundException("Client with ID 999 not found."))
                .when(clientServices).rechargeBalance(999L, 50.0);

        // Act & Assert
        mockMvc.perform(post("/clients/recharge")
                .param("id", "999")
                .param("amount", "50.0"))
                .andExpect(status().isNotFound());

        // Verify
        verify(clientServices).rechargeBalance(999L, 50.0);
    }

    @Test
    void testSpendBalance() throws Exception {
        // Arrange
        doNothing().when(clientServices).spendBalance(1L, 50.0, "Test Spend");

        // Act & Assert
        mockMvc.perform(post("/clients/spend")
                .param("id", "1")
                .param("amount", "50.0")
                .param("description", "Test Spend"))
                .andExpect(status().isOk())
                .andExpect(content().string("Balance spent successfully"));

        // Verify
        verify(clientServices).spendBalance(1L, 50.0, "Test Spend");
    }

    @Test
    void testSpendBalance_WithNonExistentId() throws Exception {
        // Arrange
        doThrow(new EntityNotFoundException("Client with ID 999 not found."))
                .when(clientServices).spendBalance(999L, 50.0, "Test Spend");

        // Act & Assert
        mockMvc.perform(post("/clients/spend")
                .param("id", "999")
                .param("amount", "50.0")
                .param("description", "Test Spend"))
                .andExpect(status().isNotFound());

        // Verify
        verify(clientServices).spendBalance(999L, 50.0, "Test Spend");
    }

    @Test
    void testSpendBalance_WithInsufficientBalance() throws Exception {
        // Arrange
        doThrow(new IllegalArgumentException("Insufficient balance for the expense."))
                .when(clientServices).spendBalance(1L, 150.0, "Test Spend");

        // Act & Assert
        mockMvc.perform(post("/clients/spend")
                .param("id", "1")
                .param("amount", "150.0")
                .param("description", "Test Spend"))
                .andExpect(status().isBadRequest());

        // Verify
        verify(clientServices).spendBalance(1L, 150.0, "Test Spend");
    }

    @Test
    void testGetBalance() throws Exception {
        // Arrange
        when(clientServices.getBalance(1L)).thenReturn("Test Client's balance: 100.0 $");

        // Act & Assert
        mockMvc.perform(get("/clients/balance")
                .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Test Client's balance: 100.0 $"));

        // Verify
        verify(clientServices).getBalance(1L);
    }

    @Test
    void testGetBalance_WithNonExistentId() throws Exception {
        // Arrange
        when(clientServices.getBalance(999L)).thenThrow(new EntityNotFoundException("Client with ID 999 not found."));

        // Act & Assert
        mockMvc.perform(get("/clients/balance")
                .param("id", "999"))
                .andExpect(status().isNotFound());

        // Verify
        verify(clientServices).getBalance(999L);
    }
}
