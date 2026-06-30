package com.example.backend.service;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.example.backend.dto.YearlyData;


@Service
public class SimulationService {

    public long calculateFutureValue(
            int monthlyAmount,
            double annualReturn,
            int years
    ) {

        double baseMonthlyRate =
                annualReturn / 100 / 12;

        int months = years * 12;

        double futureValue =
                monthlyAmount *
                ((Math.pow(1 + baseMonthlyRate, months) - 1)
                        / baseMonthlyRate)
                * (1 + baseMonthlyRate);

        return Math.round(futureValue);
    }

    public List<YearlyData> calculateYearlyData(
            int monthlyAmount,
            double annualReturn,
            int years
    ) {

        List<YearlyData> result = new ArrayList<>();

        double asset = 0;

        // 暴落年をランダムで1年決める
        int crashYear = (int)(Math.random() * years) + 1;

        for (int year = 1; year <= years; year++) {

            // 年ごとのリターン調整
            double annualRate;

            if (year == crashYear) {
                annualRate = -0.20; // -20%暴落
            } else {
                annualRate = annualReturn / 100;
            }

            double monthlyRate = annualRate / 12;

            for (int month = 0; month < 12; month++) {
                asset = (asset + monthlyAmount) * (1 + monthlyRate);
            }

            result.add(
                new YearlyData(
                    year,
                    Math.round(asset)
                )
            );
        }

        return result;
    }

    public List<Long> runMonteCarlo(
            int monthlyAmount,
            double annualReturn,
            int years,
            int simulations
    ) {

        List<Long> results = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < simulations; i++) {

            double asset = 0;

            for (int year = 1; year <= years; year++) {

                // 暴落判定（2%）
                boolean crash = random.nextDouble() < 0.02;

                double annualRate;

                if (crash) {
                    annualRate = -0.20; // 暴落
                } else {
                    // 通常年（ランダム性）
                    annualRate = (annualReturn / 100)
                            + (random.nextGaussian() * 0.05);
                }

                double monthlyRate = annualRate / 12;

                for (int month = 0; month < 12; month++) {
                    asset = (asset + monthlyAmount) * (1 + monthlyRate);
                }
            }

            results.add(Math.round(asset));
        }

        return results;
    }
}