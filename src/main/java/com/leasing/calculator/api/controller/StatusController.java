package com.leasing.calculator.api.controller;


import com.leasing.calculator.api.model.exceptions.StatusNotFoundException;
import com.leasing.calculator.api.model.request.aggregates.StatusRequest;
import com.leasing.calculator.api.model.response.LeaseStatusResponse;
import com.leasing.calculator.service.StatusService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@Tag(name = "Status endpoints", description = "Manage lease statuses.")
public class StatusController {

    private final StatusService statusService;

    public StatusController(StatusService statusService) {
        this.statusService = statusService;
    }

    @PatchMapping("/admin/status/update")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateStatus(@RequestBody StatusRequest statusRequest) {
        statusService.updateStatusById(statusRequest);
    }

    @GetMapping("/admin/status/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LeaseStatusResponse getStatusByID(@PathVariable String id) throws StatusNotFoundException {
        return statusService.getStatusById(id);
    }

    @PatchMapping("/admin/status/update/is-read")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateStatusById(@RequestParam String id) {
        statusService.updateStatusIsRead(id);
    }
}
