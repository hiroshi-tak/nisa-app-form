package com.example.backend.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public class SimulationRequest {

    @Min(value = 0, message = "毎月積立額は0以上にしてください")
    private int monthlyAmount;

    @DecimalMin(value = "-100.0", message = "年利は-100%以上にしてください")
    @DecimalMax(value = "100.0", message = "年利は100%以下にしてください")
    private double annualReturn;

    @Min(value = 0, message = "運用年数は0以上にしてください")
    @Max(value = 100, message = "運用年数は100以下にしてください")
    private int years;

    public int getMonthlyAmount() {
        return monthlyAmount;
    }

    public void setMonthlyAmount(int monthlyAmount) {
        this.monthlyAmount = monthlyAmount;
    }

    public double getAnnualReturn() {
        return annualReturn;
    }

    public void setAnnualReturn(double annualReturn) {
        this.annualReturn = annualReturn;
    }

    public int getYears() {
        return years;
    }

    public void setYears(int years) {
        this.years = years;
    }
}