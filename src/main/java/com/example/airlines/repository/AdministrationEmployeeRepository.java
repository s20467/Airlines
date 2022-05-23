package com.example.airlines.repository;

import com.example.airlines.model.AdministrationEmployee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdministrationEmployeeRepository extends JpaRepository<AdministrationEmployee, Integer> {
}
