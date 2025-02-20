package com.clientscrud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.clientscrud.models.ClientModel;

@Repository
public interface ClientRepository extends JpaRepository<ClientModel, Long> {

}
