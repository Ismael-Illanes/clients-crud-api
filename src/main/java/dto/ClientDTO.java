package dto;

import com.clientscrud.models.ClientModel;

public class ClientDTO {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private Double balance;
    private Double lastRecharge;
    private Double totalRecharge;
    private Double lastExpense;
    private Double totalExpense;
    private String createdAt;
    private String updatedAt;

    public ClientDTO(ClientModel client, Double totalExpenseAmount) {
        this.id = client.getId();
        this.name = client.getName();
        this.email = client.getEmail();
        this.phone = client.getPhone();
        this.address = client.getAddress();
        this.balance = client.getBalance();
        this.lastRecharge = client.getLastRecharge();
        this.totalRecharge = client.getTotalRecharge();
        this.lastExpense = client.getLastExpense();
        this.totalExpense = totalExpenseAmount;
        this.createdAt = client.getCreatedAt();
        this.updatedAt = client.getUpdatedAt();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public Double getBalance() {
        return balance;
    }

    public Double getLastRecharge() {
        return lastRecharge;
    }

    public Double getTotalRecharge() {
        return totalRecharge;
    }

    public Double getLastExpense() {
        return lastExpense;
    }

    public Double getTotalExpense() {
        return totalExpense;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }
}
