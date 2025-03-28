package com.leasing.calculator.repository;

import com.leasing.calculator.util.CarAPIJwt;

public interface CarApiJwtRepositoryDAO {
    CarAPIJwt getJwtToken();

    void updateJwtToken(CarAPIJwt jwtToken);
}
