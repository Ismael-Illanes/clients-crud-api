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

    public ClientDTO() {
    }

    public ClientDTO(ClientModel client) {
        this.id = client.getId();
        this.name = client.getName();
        this.email = client.getEmail();
        this.phone = client.getPhone();
        this.address = client.getAddress();
        this.balance = client.getBalance();
        this.lastRecharge = client.getLastRecharge();
        this.totalRecharge = client.getTotalRecharge();
        this.lastExpense = client.getLastExpense();
        this.totalExpense = client.getTotalExpense();
        this.createdAt = client.getCreatedAt();
        this.updatedAt = client.getUpdatedAt();
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Double getLastRecharge() {
        return lastRecharge;
    }

    public void setLastRecharge(Double lastRecharge) {
        this.lastRecharge = lastRecharge;
    }

    public Double getTotalRecharge() {
        return totalRecharge;
    }

    public void setTotalRecharge(Double totalRecharge) {
        this.totalRecharge = totalRecharge;
    }

    public Double getLastExpense() {
        return lastExpense;
    }

    public void setLastExpense(Double lastExpense) {
        this.lastExpense = lastExpense;
    }

    public Double getTotalExpense() {
        return totalExpense;
    }

    public void setTotalExpense(Double totalExpense) {
        this.totalExpense = totalExpense;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
