package com.example.backend.dto;

import java.util.List;

public class MonteCarloResult {

    private String simulationId;
    private List<Long> finalAssets;

    public MonteCarloResult(
        String simulationId,
        List<Long> finalAssets) {
        
        this.simulationId = simulationId;    
        this.finalAssets = finalAssets;
    }

    public String getSimulationId() {
        return simulationId;
    }

    public List<Long> getFinalAssets() {
        return finalAssets;
    }
}