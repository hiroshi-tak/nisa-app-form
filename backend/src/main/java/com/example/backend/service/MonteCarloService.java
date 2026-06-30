package com.example.backend.service;

import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class MonteCarloService {

    public Map<String, Long> calculateStats(List<Long> assets) {

        List<Long> sorted = new ArrayList<>(assets);
        Collections.sort(sorted);

        int n = sorted.size();

        long min = sorted.get(0);
        long max = sorted.get(sorted.size() - 1);
        long median;
        if (n % 2 == 0) {
            median = (sorted.get(n / 2 - 1) + sorted.get(n / 2)) / 2;
        } else {
            median = sorted.get(n / 2);
        }

        return Map.of(
                "min", min,
                "max", max,
                "median", median
        );
    }

    public double calculateAvg(List<Long> assets) {
        return assets.stream()
                .mapToLong(Long::longValue)
                .average()
                .orElse(0);
    }
}