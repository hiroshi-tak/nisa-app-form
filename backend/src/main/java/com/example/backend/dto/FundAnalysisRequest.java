package com.example.backend.dto;

import java.util.List;

public record FundAnalysisRequest(
    List<Long> fundIds
) {}
