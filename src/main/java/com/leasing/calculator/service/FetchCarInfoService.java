package com.leasing.calculator.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.leasing.calculator.api.model.response.carApi.aggregates.CarMakeResponse;
import com.leasing.calculator.api.model.response.carApi.aggregates.CarModelInfoResponse;
import com.leasing.calculator.api.model.response.carApi.aggregates.CarModelResponse;
import com.leasing.calculator.api.model.response.carApi.aggregates.CarVariantInfoResponse;
import com.leasing.calculator.domain.aggregates.response.carApi.CarMakeResponseDO;
import com.leasing.calculator.domain.aggregates.response.carApi.CarModelResponseDO;
import com.leasing.calculator.domain.aggregates.response.carApi.EngineDataResponseDO;
import com.leasing.calculator.repository.CarInfoRepositoryDAO;
import com.leasing.calculator.service.helper.CarDetailResponseHelper;
import org.springframework.stereotype.Service;

@Service
public class FetchCarInfoService {
    private final CarInfoRepositoryDAO carInfoRepositoryDAO;

    public FetchCarInfoService(CarInfoRepositoryDAO carInfoRepositoryDAO) {
        this.carInfoRepositoryDAO = carInfoRepositoryDAO;
    }

    public CarMakeResponse fetchCarMakes() throws JsonProcessingException {
        CarMakeResponseDO makesResponse = carInfoRepositoryDAO.getCarMakes();

        return CarDetailResponseHelper.mapCarMakeResponse(makesResponse);
    }

    public CarModelResponse fetchModelsForMake(String make) throws JsonProcessingException {
        CarModelResponseDO modelResponse = carInfoRepositoryDAO.getCarModels(make);
        return CarDetailResponseHelper.mapCarModelResponse(modelResponse);
    }

    public CarModelInfoResponse fetchModelInfo(int modelID) throws JsonProcessingException {
        EngineDataResponseDO modelEngineResponse = carInfoRepositoryDAO.getModelEngineData(modelID);

        return CarDetailResponseHelper.mapCarModelInfoResponse(modelEngineResponse);
    }

    public CarVariantInfoResponse fetchVariantInfo(int variantID) throws JsonProcessingException {
        EngineDataResponseDO variantEngineResponse = carInfoRepositoryDAO.getVariantEngineData(variantID);

        return CarDetailResponseHelper.mapCarVariantInfoResponse(variantEngineResponse);
    }
}