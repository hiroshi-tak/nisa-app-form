package com.example.backend.controller;

import com.example.backend.dto.FundDto;
import com.example.backend.dto.FundAnalysisRequest;
import com.example.backend.service.FundService;
import com.example.backend.service.GeminiService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/funds")
public class FundController {

    private final FundService fundService;
    private final GeminiService geminiService;

    public FundController(
            FundService fundService,
            GeminiService geminiService
    ) {
        this.fundService = fundService;
        this.geminiService = geminiService;
    }

    @GetMapping
    public List<FundDto> getFunds() {
        return fundService.getFunds();
    }

    @GetMapping("/score")
    public List<FundDto> getFundsWithScore() {
        return fundService.getFundsWithScore();
    }

    @PostMapping("/analysis")
    public String analyze(
            @RequestBody FundAnalysisRequest request
    ) {

        List<FundDto> funds =
            fundService.getFundsByIds(
                    request.fundIds()
            );

        if (funds.size() != 2) {
            throw new IllegalArgumentException(
                    "2ファンド選択してください"
            );
        }

        FundDto fundA = funds.get(0);
        FundDto fundB = funds.get(1);

        String prompt = String.format(
            """
            以下の2ファンドを比較してください。

            名称: %s
            1年年利: %.2f%%
            5年年利: %.2f%%
            信託報酬: %s%%
            純資産: %s

            名称: %s
            1年年利: %.2f%%
            5年年利: %.2f%%
            信託報酬: %s%%
            純資産: %s

            初心者向けに200文字以内で説明してください。
            """,
            fundA.name(),
            fundA.return1Year(),
            fundA.return5Year(),
            fundA.expenseRatio(),
            fundA.netAssets(),

            fundB.name(),
            fundB.return1Year(),
            fundB.return5Year(),
            fundB.expenseRatio(),
            fundB.netAssets()
        );

            //System.out.println("=== Gemini Prompt ===");
            //System.out.println(prompt);

        return geminiService.explainMonteCarlo(prompt);
    }
}