package com.clientscrud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.clientscrud.models.WalletModel;

@Repository
public interface WalletRepository extends JpaRepository<WalletModel, Long> {

}
