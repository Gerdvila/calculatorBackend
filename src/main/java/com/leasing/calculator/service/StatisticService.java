package com.leasing.calculator.service;

import com.leasing.calculator.api.model.response.AcceptedApplicationResponse;
import com.leasing.calculator.api.model.response.ApplicationDailyCountResponse;
import com.leasing.calculator.api.model.response.ApplicationMonthlyCountResponse;
import com.leasing.calculator.api.model.response.HighRiskResponse;
import com.leasing.calculator.api.model.response.StatusCountResponse;
import com.leasing.calculator.domain.aggregates.response.AcceptedApplicationResponseDO;
import com.leasing.calculator.domain.aggregates.response.ApplicationDailyCountResponseDO;
import com.leasing.calculator.domain.aggregates.response.ApplicationMonthlyCountResponseDO;
import com.leasing.calculator.domain.aggregates.response.HighRiskResponseDO;
import com.leasing.calculator.domain.aggregates.response.StatusCountResponseDO;
import com.leasing.calculator.repository.StatisticRepositoryDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticService {

    private final StatisticRepositoryDAO statisticsRepository;

    public StatisticService(StatisticRepositoryDAO statisticsRepository) {
        this.statisticsRepository = statisticsRepository;
    }

    public StatusCountResponse getApplicationStatusCount() {
        return this.statisticCountResponseDOToResponse(statisticsRepository.countStatus());
    }

    public List<ApplicationDailyCountResponse> getApplicationCurrentMonthDailyCount() {
        return statisticsRepository.countApplicationsCurrentMonthDaily().stream()
                .map(this::applicationDailyCountResponseDOToResponse)
                .toList();
    }

    public HighRiskResponse getHighRiskApplicationCount() {
        return this.highRiskResponseDOToResponse(statisticsRepository.countHighRiskApplications());
    }

    public AcceptedApplicationResponse getAcceptedApplicationsTotalSum() {
        return this.acceptedApplicationResponseDOToResponse(statisticsRepository.countAcceptedApplicationsTotalSum());
    }

    public ApplicationMonthlyCountResponse getApplicationCurrentMonth() {
        return this.applicationMonthlyCountResponseDOToResponse(statisticsRepository.getApplicationMonthlyCount());
    }

    private StatusCountResponse statisticCountResponseDOToResponse(StatusCountResponseDO statisticCountResponseDO) {
        return new StatusCountResponse(
                statisticCountResponseDO.newCount(),
                statisticCountResponseDO.acceptedCount(),
                statisticCountResponseDO.rejectedCount(),
                statisticCountResponseDO.pendingCount()
        );
    }

    private ApplicationDailyCountResponse applicationDailyCountResponseDOToResponse(ApplicationDailyCountResponseDO applicationDailyCountResponseDO) {
        return new ApplicationDailyCountResponse(
                applicationDailyCountResponseDO.day(),
                applicationDailyCountResponseDO.applicationCount()
        );
    }

    private HighRiskResponse highRiskResponseDOToResponse(HighRiskResponseDO highRiskResponseDO) {
        return new HighRiskResponse(
                highRiskResponseDO.currentMonthCount(),
                highRiskResponseDO.lastMonthCount()
        );
    }

    private AcceptedApplicationResponse acceptedApplicationResponseDOToResponse(AcceptedApplicationResponseDO acceptedApplicationResponseDO){
        return new AcceptedApplicationResponse(
                acceptedApplicationResponseDO.thisYearSum(),
                acceptedApplicationResponseDO.lastYearSum()
        );
    }

    private ApplicationMonthlyCountResponse applicationMonthlyCountResponseDOToResponse(ApplicationMonthlyCountResponseDO applicationMonthlyCountResponseDO) {
        return new ApplicationMonthlyCountResponse(
                applicationMonthlyCountResponseDO.thisMonthCount(),
                applicationMonthlyCountResponseDO.previousMonthCount()
        );
    }
}