package com.example.backend.controller;

import com.example.backend.dto.*;
import com.example.backend.service.MonteCarloService;
import com.example.backend.service.SimulationService;
import org.springframework.web.bind.annotation.*;
import com.example.backend.service.GeminiService;

import java.util.Map;
import java.util.List;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/simulation")
public class SimulationController {

    private final SimulationService simulationService;
    private final GeminiService geminiService;
    private final MonteCarloService monteCarloService;

    private final Map<String, List<Long>> store = new ConcurrentHashMap<>();

    public SimulationController(
            SimulationService simulationService,
            GeminiService geminiService,
            MonteCarloService monteCarloService
    ) {
        this.simulationService = simulationService;
        this.geminiService = geminiService;
        this.monteCarloService = monteCarloService;
    }

    @PostMapping
    public SimulationResponse simulate(@Valid @RequestBody SimulationRequest request) {

        long finalAmount =
                simulationService.calculateFutureValue(
                        request.getMonthlyAmount(),
                        request.getAnnualReturn(),
                        request.getYears());

        long totalInvestment =
                (long) request.getMonthlyAmount()
                        * request.getYears()
                        * 12;

        long profit = finalAmount - totalInvestment;

        List<YearlyData> yearlyData =
                simulationService.calculateYearlyData(
                        request.getMonthlyAmount(),
                        request.getAnnualReturn(),
                        request.getYears());

        return new SimulationResponse(
                totalInvestment,
                finalAmount,
                profit,
                yearlyData);
    }

    // モンテカルAPI
    @PostMapping("/montecarlo")
    public MonteCarloResult monteCarlo(@Valid @RequestBody SimulationRequest request) {

        List<Long> data = simulationService.runMonteCarlo(
                request.getMonthlyAmount(),
                request.getAnnualReturn(),
                request.getYears(),
                1000
        );

        String id = UUID.randomUUID().toString();

        store.put(id, data);

        return new MonteCarloResult(id, data);
    }

    // AI 追加
    @PostMapping("/montecarlo/explain")
    public String explain(@RequestBody ExplainRequest request) {

        List<Long> assets = store.get(request.getSimulationId());

        Map<String, Long> stats = monteCarloService.calculateStats(assets);
        double avg = monteCarloService.calculateAvg(assets);

        String prompt =
            "あなたは資産運用アドバイザーです。\n" +
            "以下のルールを厳守してください。\n" +
            "・前置き禁止\n" +
            "・箇条書き禁止\n" +
            "・改行禁止\n" +
            "・120文字以内\n\n" +
            "モンテカルロ結果の要約：\n" +
            "最小:" + stats.get("min") +
            " 最大:" + stats.get("max") +
            " 中央値:" + stats.get("median") +
            " 平均:" + (long) avg;

        return geminiService.explainMonteCarlo(prompt);
    }

}