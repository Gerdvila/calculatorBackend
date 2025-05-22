package com.leasing.calculator.api.controller;

import com.leasing.calculator.api.model.response.AcceptedApplicationResponse;
import com.leasing.calculator.api.model.response.ApplicationDailyCountResponse;
import com.leasing.calculator.api.model.response.ApplicationMonthlyCountResponse;
import com.leasing.calculator.api.model.response.HighRiskResponse;
import com.leasing.calculator.api.model.response.StatusCountResponse;
import com.leasing.calculator.service.StatisticService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/statistics")
public class StatisticsController {

    private final StatisticService statisticService;

    public StatisticsController(StatisticService statisticService) {
        this.statisticService = statisticService;
    }

    @GetMapping("/applications/status/count")
    @Operation(summary = "Retrieve a count of applications grouped by status for this year.")
    @ResponseStatus(HttpStatus.OK)
    public StatusCountResponse getApplicationStatusCount() {
        return statisticService.getApplicationStatusCount();
    }

    @GetMapping("/applications/daily/count")
    @Operation(summary = "Retrieve a count of applications grouped by day for current month.")
    @ResponseStatus(HttpStatus.OK)
    public List<ApplicationDailyCountResponse> getApplicationCurrentMonthCount() {
        return statisticService.getApplicationCurrentMonthDailyCount();
    }

    @GetMapping("/applications/high-risk/count")
    @Operation(summary = "Retrieve a count of high-risk applications for current month and previous month.")
    @ResponseStatus(HttpStatus.OK)
    public HighRiskResponse getHighRiskApplicationCount() {
        return statisticService.getHighRiskApplicationCount();
    }

    @GetMapping("/applications/accepted/total/sum")
    @Operation(summary = "Retrieve a total sum of accepted applications leases value for this year.")
    @ResponseStatus(HttpStatus.OK)
    public AcceptedApplicationResponse getAcceptedApplicationsTotalSum() {
        return statisticService.getAcceptedApplicationsTotalSum();
    }

    @GetMapping("/applications/monthly/count")
    @Operation(summary = "Retrieve a count of applications for current month and previous month.")
    @ResponseStatus(HttpStatus.OK)
    public ApplicationMonthlyCountResponse getApplicationCountMonth() {
        return statisticService.getApplicationCurrentMonth();
    }
}