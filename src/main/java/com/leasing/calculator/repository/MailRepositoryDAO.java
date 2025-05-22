package com.leasing.calculator.repository;

import com.leasing.calculator.domain.aggregates.response.MailResponseDO;
import com.leasing.calculator.domain.aggregates.request.MailRequestDO;

import java.util.List;

public interface MailRepositoryDAO {
    List<MailResponseDO> selectMailByApplicationId(String applicationId);

    void createMail(MailRequestDO note);
}
