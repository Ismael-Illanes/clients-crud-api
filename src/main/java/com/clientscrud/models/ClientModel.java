package com.clientscrud.models;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "clients")
public class ClientModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "phone", unique = true, nullable = false)
    private String phone;

    @Column(name = "address")
    private String address;

    @Column(name = "created_at")
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;

    @Column(name = "balance")
    private Double balance;

    @Column(name = "last_recharge")
    private Double lastRecharge;

    @Column(name = "total_recharge")
    private Double totalRecharge;

    @Column(name = "last_expense")
    private Double lastExpense;

    @Column(name = "total_expense")
    private Double totalExpense;

    @OneToOne(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private WalletModel wallet;
    
    @Override
    public String toString() {
        return "ClientModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", balance=" + balance +
                ", lastRecharge=" + lastRecharge +
                ", totalRecharge=" + totalRecharge +
                ", lastExpense=" + lastExpense +
                ", totalExpense=" + totalExpense +
                '}';
    }
}
