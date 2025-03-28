package com.leasing.calculator.service.helper;

import com.leasing.calculator.api.model.response.carApi.CarMakeResponse;
import com.leasing.calculator.api.model.response.carApi.CarModelInfoResponse;
import com.leasing.calculator.api.model.response.carApi.CarModelResponse;
import com.leasing.calculator.api.model.response.carApi.CarVariantInfoResponse;
import com.leasing.calculator.domain.aggregates.response.carApi.CarMakeResponseDO;
import com.leasing.calculator.domain.aggregates.response.carApi.CarModelResponseDO;
import com.leasing.calculator.domain.aggregates.response.carApi.EngineDataResponseDO;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class CarDetailResponseHelper {

    private CarDetailResponseHelper() {}

    public static CarVariantInfoResponse mapCarVariantInfoResponse(EngineDataResponseDO engineDataResponse) {

        List<Integer> years = engineDataResponse.data().stream()
                .map(engineData -> engineData.make_model_trim().year())
                .distinct()
                .collect(Collectors.toList());

        List<String> fuelTypes = engineDataResponse.data().stream()
                .map(EngineDataResponseDO.EngineData::engine_type)
                .distinct()
                .collect(Collectors.toList());

        List<Integer> enginePowers = engineDataResponse.data().stream()
                .map(EngineDataResponseDO.EngineData::horsepower_hp)
                .distinct()
                .collect(Collectors.toList());

        List<String> engineSizes = engineDataResponse.data().stream()
                .map(EngineDataResponseDO.EngineData::size)
                .distinct()
                .collect(Collectors.toList());

        return new CarVariantInfoResponse(
                years,
                fuelTypes,
                enginePowers,
                engineSizes);
    }

    public static CarModelInfoResponse mapCarModelInfoResponse(EngineDataResponseDO engineDataResponse) {

        Set<String> names = new HashSet<>();
        List<CarModelInfoResponse.Variant> variants = engineDataResponse.data().stream()
                .map(engineData -> new CarModelInfoResponse.Variant(
                        engineData.make_model_trim().id(),
                        engineData.make_model_trim().name()))
                .filter(variant -> names.add(variant.name()))
                .collect(Collectors.toList());

        List<Integer> years = engineDataResponse.data().stream()
                .map(engineData -> engineData.make_model_trim().year())
                .distinct()
                .collect(Collectors.toList());

        List<String> fuelTypes = engineDataResponse.data().stream()
                .map(EngineDataResponseDO.EngineData::engine_type)
                .distinct()
                .collect(Collectors.toList());

        List<Integer> enginePowers = engineDataResponse.data().stream()
                .map(EngineDataResponseDO.EngineData::horsepower_hp)
                .distinct()
                .collect(Collectors.toList());

        List<String> engineSizes = engineDataResponse.data().stream()
                .map(EngineDataResponseDO.EngineData::size)
                .distinct()
                .collect(Collectors.toList());

        return new CarModelInfoResponse(
                variants,
                years,
                fuelTypes,
                enginePowers,
                engineSizes);
    }

    public static CarModelResponse mapCarModelResponse(CarModelResponseDO carModelResponseDO){
        return new CarModelResponse(Objects.requireNonNull(carModelResponseDO).data().stream()
                .map(modelData -> new CarModelResponse.CarModel(modelData.id(), modelData.name()))
                .collect(Collectors.toList()));
    }

    public static CarMakeResponse mapCarMakeResponse(CarMakeResponseDO carMakeResponseDO){
        return new CarMakeResponse(Objects.requireNonNull(carMakeResponseDO).data().stream()
                .map(carMake -> (String) carMake.get("name"))
                .collect(Collectors.toList()));
    }
}