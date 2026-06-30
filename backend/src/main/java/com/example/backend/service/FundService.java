package com.example.backend.service;

import com.example.backend.dto.FundDto;
import org.springframework.stereotype.Service;
import com.example.backend.entity.Fund;
import com.example.backend.repository.FundRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FundService {
    private final FundRepository fundRepository;

    // AI
    public List<FundDto> getFundsByIds(List<Long> ids) {

        return fundRepository.findAllById(ids)
                .stream()
                .map(this::toDto)
                .toList();
    }


    // DB取得
    public List<FundDto> getFunds() {

        return fundRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    // score
    public List<FundDto> getFundsWithScore() {

        return fundRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    // 
    private FundDto toDto(Fund fund) {

        return new FundDto(
                fund.getId(),
                fund.getName(),
                fund.getCategory(),

                fund.getCurrentPrice(),
                fund.getPrice1YearAgo(),
                fund.getPrice3YearAgo(),
                fund.getPrice5YearAgo(),

                fund.getExpenseRatio(),
                fund.getNetAssets(),

                calc1YearReturn(fund),
                calc3YearReturn(fund),
                calc5YearReturn(fund),

                totalScore(fund)
        );
    }

    // 年利
    // 1年リターン
    private double calc1YearReturn(Fund fund) {

        return (
                fund.getCurrentPrice().doubleValue()
                        - fund.getPrice1YearAgo().doubleValue()
        )
                / fund.getPrice1YearAgo().doubleValue()
                * 100;
    }

    // 3年平均年利
    private double calc3YearReturn(Fund fund) {

        return (
                Math.pow(
                        fund.getCurrentPrice().doubleValue()
                                /
                                fund.getPrice3YearAgo().doubleValue(),
                        1.0 / 3.0
                ) - 1
        ) * 100;
    }

    // 5年平均年利
    private double calc5YearReturn(Fund fund) {

        return (
                Math.pow(
                        fund.getCurrentPrice().doubleValue()
                                /
                                fund.getPrice5YearAgo().doubleValue(),
                        1.0 / 5.0
                ) - 1
        ) * 100;
    }

    // 信託報酬スコア
    private double expenseScore(double expenseRatio) {

        if(expenseRatio < 0.1) return 100;

        if(expenseRatio < 0.3) return 80;

        if(expenseRatio < 0.5) return 60;

        return 40;
    }

    // 純資産スコア
    private double assetScore(double asset) {

        if(asset > 1_000_000_000_000L)
            return 100;

        if(asset > 100_000_000_000L)
            return 80;

        return 60;
    }

    // トータルスコア
    private double totalScore(Fund fund) {

        double return5Y = calc5YearReturn(fund);

        double return3Y = calc3YearReturn(fund);

        double expenseScore =
                expenseScore(
                        fund.getExpenseRatio().doubleValue()
                );

        double assetScore =
                assetScore(
                        fund.getNetAssets().doubleValue()
                );

        return
                return5Y * 0.5
                +
                return3Y * 0.2
                +
                expenseScore * 0.15
                +
                assetScore * 0.15;
    }

}