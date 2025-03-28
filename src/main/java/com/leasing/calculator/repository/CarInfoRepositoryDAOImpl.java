package com.leasing.calculator.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.leasing.calculator.domain.aggregates.response.carApi.CarMakeResponseDO;
import com.leasing.calculator.domain.aggregates.response.carApi.CarModelResponseDO;
import com.leasing.calculator.domain.aggregates.response.carApi.EngineDataResponseDO;
import com.leasing.calculator.service.CarApiLoginService;
import com.leasing.calculator.util.CarAPIJwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
public class CarInfoRepositoryDAOImpl implements CarInfoRepositoryDAO {

    private final RestTemplate restTemplate;
    private final CarApiJwtRepositoryDAO carApiJwtRepositoryDAO;
    private final CarApiLoginService carAPILoginService;

    @Autowired
    public CarInfoRepositoryDAOImpl(RestTemplateBuilder restTemplateBuilder, CarApiJwtRepositoryDAO carApiJwtRepositoryDAO, CarApiLoginService carAPILoginService) {
        this.restTemplate = restTemplateBuilder.build();
        this.carApiJwtRepositoryDAO = carApiJwtRepositoryDAO;
        this.carAPILoginService = carAPILoginService;
    }

    public CarMakeResponseDO getCarMakes() throws JsonProcessingException {
        HttpHeaders headers = getHttpHeaders();
        ResponseEntity<CarMakeResponseDO> makesResponse = restTemplate.exchange(
                "https://carapi.app/api/makes?year=2019",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                CarMakeResponseDO.class
        );
        return makesResponse.getBody();
    }

    public CarModelResponseDO getCarModels(String make) throws JsonProcessingException {
        make = make.trim();
        String url = String.format("https://carapi.app/api/models?year=2019&make=%s", make);
        HttpHeaders headers = getHttpHeaders();
        ResponseEntity<CarModelResponseDO> modelResponse = restTemplate.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                CarModelResponseDO.class
        );
        return modelResponse.getBody();
    }

    public EngineDataResponseDO getModelEngineData(int modelID) throws JsonProcessingException {
        String url = String.format("https://carapi.app/api/engines?verbose=yes&make_model_id=%d&year=2019", modelID);
        HttpHeaders headers = getHttpHeaders();
        ResponseEntity<EngineDataResponseDO> modelInfoResponse = restTemplate.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                EngineDataResponseDO.class
        );
        return modelInfoResponse.getBody();
    }

    public EngineDataResponseDO getVariantEngineData(int variantID) throws JsonProcessingException {
        String url = String.format("https://carapi.app/api/engines?verbose=yes&make_model_trim_id=%d&year=2019", variantID);
        HttpHeaders headers = getHttpHeaders();
        ResponseEntity<EngineDataResponseDO> variantInfoResponse = restTemplate.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                EngineDataResponseDO.class
        );
        return variantInfoResponse.getBody();
    }

    private CarAPIJwt getCarAPIJwt() throws JsonProcessingException {
        CarAPIJwt jwtToken = carApiJwtRepositoryDAO.getJwtToken();
        if (jwtToken.isExpired()) {
            return carAPILoginService.loginAndSetJwt();
        }
        return jwtToken;
    }

    private HttpHeaders getHttpHeaders() throws JsonProcessingException {
        CarAPIJwt jwtToken = getCarAPIJwt();
        HttpHeaders headers = new HttpHeaders();

        headers.setBearerAuth(jwtToken.jwt());
        return headers;
    }
}