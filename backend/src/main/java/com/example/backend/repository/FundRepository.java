package com.example.backend.repository;

import com.example.backend.entity.Fund;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FundRepository extends JpaRepository<Fund, Long> {
}