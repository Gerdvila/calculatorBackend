package com.leasing.calculator.service;

import com.leasing.calculator.api.model.request.aggregates.FetchLeaseListBySearchQueryRequest;
import com.leasing.calculator.api.model.response.ApplicationListResponse;
import com.leasing.calculator.domain.aggregates.request.FetchLeaseBySearchQueryRequestDO;
import com.leasing.calculator.repository.ApplicationListRepositoryDAO;
import com.leasing.calculator.util.MappingUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FetchLeaseListBySearchQueryService {

    private final ApplicationListRepositoryDAO applicationListRepository;

    public FetchLeaseListBySearchQueryService(ApplicationListRepositoryDAO applicationListRepository) {
        this.applicationListRepository = applicationListRepository;
    }

    public List<ApplicationListResponse> sortApplications(FetchLeaseListBySearchQueryRequest applicationListRequest) {
        FetchLeaseBySearchQueryRequestDO applicationListDAORequest = MappingUtil.convertRequestIntoDORequestFetchLeaseBySearchQuery(applicationListRequest);

        if (
                (applicationListRequest.STATUS() != null && !applicationListRequest.STATUS().isEmpty())
                && (applicationListRequest.searchQuery() != null && !applicationListRequest.searchQuery().isEmpty())
        ) {
            return applicationListRepository.sortAndFilterByStatusAndSearchQuery(applicationListDAORequest).stream()
                    .map(MappingUtil::convertApplicationListDAOResponseIntoResponse).toList();
        } else if (applicationListRequest.STATUS() != null && !applicationListRequest.STATUS().isEmpty()) {
            return applicationListRepository.sortAndFilterByStatus(applicationListDAORequest).stream()
                    .map(MappingUtil::convertApplicationListDAOResponseIntoResponse).toList();
        } else if (applicationListRequest.searchQuery() != null && !applicationListRequest.searchQuery().isEmpty()) {
            return applicationListRepository.sortAndFilterBySearchQuery(applicationListDAORequest).stream()
                    .map(MappingUtil::convertApplicationListDAOResponseIntoResponse).toList();
        } else {
            return applicationListRepository.sortApplicationsByTimestamp(applicationListDAORequest).stream()
                    .map(MappingUtil::convertApplicationListDAOResponseIntoResponse).toList();
        }
    }
}