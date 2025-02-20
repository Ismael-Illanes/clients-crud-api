package com.clientscrud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.clientscrud.models.ExpenseModel;

@Repository
public interface ExpenseRepository extends JpaRepository<ExpenseModel, Long> {

}
