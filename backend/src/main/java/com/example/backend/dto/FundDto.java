package com.example.backend.dto;

import java.math.BigDecimal;

public record FundDto(

    Long id,

    String name,

    String category,

    BigDecimal currentPrice,

    BigDecimal price1YearAgo,

    BigDecimal price3YearAgo,

    BigDecimal price5YearAgo,

    BigDecimal expenseRatio,

    BigDecimal netAssets,

    Double return1Year,

    Double return3Year,

    Double return5Year,

    Double totalScore
) {}