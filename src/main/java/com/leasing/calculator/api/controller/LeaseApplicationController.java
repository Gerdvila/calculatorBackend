package com.leasing.calculator.api.controller;

import com.leasing.calculator.api.model.exceptions.ApplicationNotFoundException;
import com.leasing.calculator.api.model.request.aggregates.CreateLeaseApplicationRequest;
import com.leasing.calculator.api.model.request.aggregates.FetchLeaseListBySearchQueryRequest;
import com.leasing.calculator.api.model.request.aggregates.LeaseStatusRequest;
import com.leasing.calculator.api.model.response.ApplicationListResponse;
import com.leasing.calculator.api.model.response.FetchLeaseByIdResponse;
import com.leasing.calculator.api.model.response.LeaseResponse;
import com.leasing.calculator.service.CreateLeaseService;
import com.leasing.calculator.service.FetchLeaseListBySearchQueryService;
import com.leasing.calculator.service.FetchLeaseService;
import com.leasing.calculator.service.LeasePaginationService;
import com.leasing.calculator.service.UpdateLeaseStatusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@Tag(name = "Lease Application", description = "Manage lease application related functionalities such as application creation and status update.")
public class LeaseApplicationController {

    private final CreateLeaseService createLeaseService;
    private final UpdateLeaseStatusService updateLeaseStatusService;
    private final LeasePaginationService leasePaginationService;
    private final FetchLeaseListBySearchQueryService fetchLeaseListBySearchQueryService;
    private final FetchLeaseService fetchLeaseService;

    public LeaseApplicationController(CreateLeaseService createLeaseService,
                                      UpdateLeaseStatusService updateLeaseStatusService,
                                      LeasePaginationService leasePaginationService,
                                      FetchLeaseService fetchLeaseService,
                                      FetchLeaseListBySearchQueryService fetchLeaseListBySearchQueryService) {
        this.leasePaginationService = leasePaginationService;
        this.createLeaseService = createLeaseService;
        this.updateLeaseStatusService = updateLeaseStatusService;
        this.fetchLeaseService = fetchLeaseService;
        this.fetchLeaseListBySearchQueryService = fetchLeaseListBySearchQueryService;
    }

    @PostMapping("/public/applications/create")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Create Lease Application",
            description = "Creates a new lease application using the provided details in the request payload."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Lease application created successfully",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid input provided",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    public void createApplication(
            @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Request payload containing lease application details",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = CreateLeaseApplicationRequest.class)
                    )
            ) CreateLeaseApplicationRequest createLeaseApplicationRequest) throws IllegalArgumentException {
        createLeaseService.createApplication(createLeaseApplicationRequest);
    }

    @PatchMapping("/public/status/update")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateStatus(@RequestBody LeaseStatusRequest statusRequest) {
        updateLeaseStatusService.updateLeaseStatus(statusRequest);
    }

    @GetMapping("/admin/applications/page/{pageNumber}")
    @ResponseStatus(HttpStatus.OK)
    public List<LeaseResponse> getAllApplicationsByPage(@PathVariable long pageNumber) {
        return leasePaginationService.selectAllApplicationsByPage(pageNumber);
    }


    @GetMapping("/admin/applications/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FetchLeaseByIdResponse getApplicationById(@PathVariable String id) throws ApplicationNotFoundException {
        return fetchLeaseService.getApplicationById(id);
    }


    @PostMapping("/admin/applications")
    @ResponseStatus(HttpStatus.OK)
    public List<ApplicationListResponse> sortApplications(@RequestBody FetchLeaseListBySearchQueryRequest applicationListBySearchQueryRequest) {
        return fetchLeaseListBySearchQueryService.sortApplications(applicationListBySearchQueryRequest);
    }
}