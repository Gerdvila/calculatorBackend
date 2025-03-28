package com.leasing.calculator.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.leasing.calculator.domain.aggregates.response.carApi.CarMakeResponseDO;
import com.leasing.calculator.domain.aggregates.response.carApi.CarModelResponseDO;
import com.leasing.calculator.domain.aggregates.response.carApi.EngineDataResponseDO;

public interface CarInfoRepositoryDAO {
    CarMakeResponseDO getCarMakes() throws JsonProcessingException;

    CarModelResponseDO getCarModels(String make) throws JsonProcessingException;

    EngineDataResponseDO getModelEngineData(int modelID) throws JsonProcessingException;

    EngineDataResponseDO getVariantEngineData(int variantID) throws JsonProcessingException;
}