package com.leasing.calculator.domain.aggregates.request;

import java.math.BigDecimal;

public class CreateLeaseAndRatesRequestDO {

    private String make;
    private String model;
    private String modelVariant;
    private String year;
    private String fuelType;
    private Double enginePower;
    private Double engineSize;
    private String url;
    private String offer;
    private Boolean terms;
    private Boolean confirmation;
    private BigDecimal carValue;
    private int period;
    private BigDecimal downPayment;
    private int residualValuePercentage;
    private Boolean isEcoFriendly;
    private BigDecimal monthlyPayment;

    private CreateLeaseAndRatesRequestDO(Builder builder) {
        this.make = builder.make;
        this.model = builder.model;
        this.modelVariant = builder.modelVariant;
        this.year = builder.year;
        this.fuelType = builder.fuelType;
        this.enginePower = builder.enginePower;
        this.engineSize = builder.engineSize;
        this.url = builder.url;
        this.offer = builder.offer;
        this.terms = builder.terms;
        this.confirmation = builder.confirmation;
        this.carValue = builder.carValue;
        this.period = builder.period;
        this.downPayment = builder.downPayment;
        this.residualValuePercentage = builder.residualValuePercentage;
        this.isEcoFriendly = builder.isEcoFriendly;
        this.monthlyPayment = builder.monthlyPayment;
    }

    public static class Builder {
        private String make;
        private String model;
        private String modelVariant;
        private String year;
        private String fuelType;
        private Double enginePower;
        private Double engineSize;
        private String url;
        private String offer;
        private Boolean terms;
        private Boolean confirmation;
        private BigDecimal carValue;
        private int period;
        private BigDecimal downPayment;
        private int residualValuePercentage;
        private Boolean isEcoFriendly;
        private BigDecimal monthlyPayment;

        public Builder withMake(String make) {
            this.make = make;
            return this;
        }

        public Builder withModel(String model) {
            this.model = model;
            return this;
        }

        public Builder withModelVariant(String modelVariant) {
            this.modelVariant = modelVariant;
            return this;
        }

        public Builder withYear(String year) {
            this.year = year;
            return this;
        }

        public Builder withFuelType(String fuelType) {
            this.fuelType = fuelType;
            return this;
        }

        public Builder withEnginePower(Double enginePower) {
            this.enginePower = enginePower;
            return this;
        }

        public Builder withEngineSize(Double engineSize) {
            this.engineSize = engineSize;
            return this;
        }

        public Builder withUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder withOffer(String offer) {
            this.offer = offer;
            return this;
        }

        public Builder withTerms(Boolean terms) {
            this.terms = terms;
            return this;
        }

        public Builder withConfirmation(Boolean confirmation) {
            this.confirmation = confirmation;
            return this;
        }

        public Builder withCarValue(BigDecimal carValue) {
            this.carValue = carValue;
            return this;
        }

        public Builder withPeriod(int period) {
            this.period = period;
            return this;
        }

        public Builder withDownPayment(BigDecimal downPayment) {
            this.downPayment = downPayment;
            return this;
        }

        public Builder withResidualValuePercentage(int residualValuePercentage) {
            this.residualValuePercentage = residualValuePercentage;
            return this;
        }

        public Builder withIsEcoFriendly(Boolean isEcoFriendly) {
            this.isEcoFriendly = isEcoFriendly;
            return this;
        }

        public Builder withMonthlyPayment(BigDecimal monthlyPayment) {
            this.monthlyPayment = monthlyPayment;
            return this;
        }

        public CreateLeaseAndRatesRequestDO build() {
            return new CreateLeaseAndRatesRequestDO(this);
        }
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public String getModelVariant() {
        return modelVariant;
    }

    public String getYear() {
        return year;
    }

    public String getFuelType() {
        return fuelType;
    }

    public Double getEnginePower() {
        return enginePower;
    }

    public Double getEngineSize() {
        return engineSize;
    }

    public String getUrl() {
        return url;
    }

    public String getOffer() {
        return offer;
    }

    public Boolean getTerms() {
        return terms;
    }

    public Boolean getConfirmation() {
        return confirmation;
    }

    public BigDecimal getCarValue() {
        return carValue;
    }

    public int getPeriod() {
        return period;
    }

    public BigDecimal getDownPayment() {
        return downPayment;
    }

    public int getResidualValuePercentage() {
        return residualValuePercentage;
    }

    public Boolean getIsEcoFriendly() {
        return isEcoFriendly;
    }

    public BigDecimal getMonthlyPayment() {
        return monthlyPayment;
    }
}
