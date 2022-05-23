package com.example.airlines.repository;

import com.example.airlines.model.Pilot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PilotRepository extends JpaRepository<Pilot, Integer> {
}
