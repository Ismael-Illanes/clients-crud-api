package repository;

import org.springframework.data.jpa.repository.JpaRepository;

import models.ClientModel;

public interface ClientRepository extends JpaRepository<ClientModel, Long> {

}
