package com.leasing.calculator.repository;

import com.leasing.calculator.domain.aggregates.request.FetchLeaseBySearchQueryRequestDO;
import com.leasing.calculator.domain.aggregates.response.ApplicationListResponseDO;

import java.util.List;

public interface ApplicationListRepositoryDAO {
    List<ApplicationListResponseDO> sortAndFilterByStatus(FetchLeaseBySearchQueryRequestDO fetchLeaseBySearchQueryRequestDO);
    List<ApplicationListResponseDO> sortApplicationsByTimestamp(FetchLeaseBySearchQueryRequestDO applicationListRequest);
    List<ApplicationListResponseDO> sortAndFilterBySearchQuery(FetchLeaseBySearchQueryRequestDO applicationListRequest);
    List<ApplicationListResponseDO> sortAndFilterByStatusAndSearchQuery(FetchLeaseBySearchQueryRequestDO applicationListRequest);
}