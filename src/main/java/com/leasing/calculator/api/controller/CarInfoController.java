package com.leasing.calculator.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.leasing.calculator.api.model.response.carApi.CarMakeResponse;
import com.leasing.calculator.api.model.response.carApi.CarModelInfoResponse;
import com.leasing.calculator.api.model.response.carApi.CarModelResponse;
import com.leasing.calculator.api.model.response.carApi.CarVariantInfoResponse;
import com.leasing.calculator.service.FetchCarInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/public/carApi")
@Tag(name = "Car Information", description = "Provides car-related information such as car makes, models, and variant details.")
public class CarInfoController {

    private final FetchCarInfoService fetchCarInfoService;

    public CarInfoController(FetchCarInfoService fetchCarInfoService) {
        this.fetchCarInfoService = fetchCarInfoService;
    }

    @Operation(
            summary = "Get Car Makes",
            description = "Fetches a list of available car makes."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Car makes fetched successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CarMakeResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    @GetMapping("/makes")
    @ResponseStatus(HttpStatus.OK)
    public CarMakeResponse getCarMakes() throws JsonProcessingException {
        return fetchCarInfoService.fetchCarMakes();
    }

    @Operation(
            summary = "Get Models for Make",
            description = "Fetches car models for a specific car make."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Car models fetched successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CarModelResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request parameter",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    @GetMapping("/models")
    @ResponseStatus(HttpStatus.OK)
    public CarModelResponse getMakeModels(@RequestParam @Parameter(description = "Car make name to filter models") String make)
            throws JsonProcessingException {
        return fetchCarInfoService.fetchModelsForMake(make);
    }

    @Operation(
            summary = "Get Model Info",
            description = "Fetches detailed information about a specific car model."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Car model information fetched successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CarModelInfoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request parameter",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    @GetMapping("/model_info")
    @ResponseStatus(HttpStatus.OK)
    public CarModelInfoResponse getModelInfo(@RequestParam @Parameter(description = "ID of the car model") int modelId)
            throws TypeMismatchException, JsonProcessingException {
        return fetchCarInfoService.fetchModelInfo(modelId);
    }

    @Operation(
            summary = "Get Variant Info",
            description = "Fetches detailed information about a specific car variant."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Car variant information fetched successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CarVariantInfoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request parameter",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    @GetMapping("/variant_info")
    @ResponseStatus(HttpStatus.OK)
    public CarVariantInfoResponse getVariantInfo(@RequestParam @Parameter(description = "ID of the car variant") int variantId)
            throws TypeMismatchException, JsonProcessingException {
        return fetchCarInfoService.fetchVariantInfo(variantId);
    }
}