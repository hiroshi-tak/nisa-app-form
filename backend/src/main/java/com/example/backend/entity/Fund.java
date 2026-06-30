package com.example.backend.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "funds")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Fund {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private String category;

    private BigDecimal currentPrice;

    private BigDecimal price1YearAgo;

    private BigDecimal price3YearAgo;

    private BigDecimal price5YearAgo;

    private BigDecimal expenseRatio;

    private BigDecimal netAssets;
}