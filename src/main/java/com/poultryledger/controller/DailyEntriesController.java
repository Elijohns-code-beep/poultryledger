package com.poultryledger.controller;

import com.poultryledger.entity.DailyLog;
import com.poultryledger.service.DailyLogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Controller
public class DailyEntriesController {
    private final DailyLogService dailyLogService;

    public DailyEntriesController(DailyLogService dailyLogService) {
        this.dailyLogService = dailyLogService;
    }

    @GetMapping("/daily-entries")
    public String page(Model model) {
        List<DailyLog> logs = dailyLogService.list();
        int totalKienyejiEggs = logs.stream()
                .mapToInt(log -> valueOrZero(log.getKienyejiEggs()))
                .sum();
        int totalImprovedKienyejiEggs = logs.stream()
                .mapToInt(log -> valueOrZero(log.getImprovedKienyejiEggs()))
                .sum();
        int totalEggs = totalKienyejiEggs + totalImprovedKienyejiEggs;
        BigDecimal totalFeedAmount = logs.stream()
                .map(dailyLogService::totalFeedAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(3, RoundingMode.HALF_UP);
        BigDecimal totalFeedCost = logs.stream()
                .map(dailyLogService::totalFeedCost)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);

        model.addAttribute("logs", logs);
        model.addAttribute("totalKienyejiEggs", totalKienyejiEggs);
        model.addAttribute("totalImprovedKienyejiEggs", totalImprovedKienyejiEggs);
        model.addAttribute("totalEggs", totalEggs);
        model.addAttribute("totalFeedAmount", totalFeedAmount);
        model.addAttribute("totalFeedCost", totalFeedCost);
        return "daily-entries/page";
    }

    private int valueOrZero(Integer value) {
        return value == null ? 0 : value;
    }
}
