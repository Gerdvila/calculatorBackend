package com.leasing.calculator.repository;

import com.leasing.calculator.domain.aggregates.response.AcceptedApplicationResponseDO;
import com.leasing.calculator.domain.aggregates.response.ApplicationDailyCountResponseDO;
import com.leasing.calculator.domain.aggregates.response.ApplicationMonthlyCountResponseDO;
import com.leasing.calculator.domain.aggregates.response.HighRiskResponseDO;
import com.leasing.calculator.domain.aggregates.response.StatusCountResponseDO;

import java.util.List;

public interface StatisticRepositoryDAO {
    StatusCountResponseDO countStatus();
    List<ApplicationDailyCountResponseDO> countApplicationsCurrentMonthDaily();
    HighRiskResponseDO countHighRiskApplications();
    AcceptedApplicationResponseDO countAcceptedApplicationsTotalSum();
    ApplicationMonthlyCountResponseDO getApplicationMonthlyCount();
}
