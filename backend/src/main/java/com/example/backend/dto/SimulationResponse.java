package com.example.backend.dto;

import java.util.List;

public class SimulationResponse {

    private long totalInvestment;
    private long finalAmount;
    private long profit;
    private List<YearlyData> yearlyData;

    public SimulationResponse(
            long totalInvestment,
            long finalAmount,
            long profit,
            List<YearlyData> yearlyData) {

        this.totalInvestment = totalInvestment;
        this.finalAmount = finalAmount;
        this.profit = profit;
        this.yearlyData = yearlyData;
    }

    public long getTotalInvestment() {
        return totalInvestment;
    }

    public long getFinalAmount() {
        return finalAmount;
    }

    public long getProfit() {
        return profit;
    }

    public List<YearlyData> getYearlyData() {
        return yearlyData;
    }
}