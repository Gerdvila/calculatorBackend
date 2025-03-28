package com.leasing.calculator.util;

import com.leasing.calculator.domain.aggregates.request.CreateLeaseAndRatesRequestDO;
import com.leasing.calculator.domain.aggregates.request.PersonalInformationRequestDO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class LeaseValidationUtil {

    private static final ArrayList<Integer> PERIOD_VALUE = new ArrayList<>(Arrays.asList(3, 4, 5, 6, 12, 18, 24, 36, 48, 60, 72));
    private static final ArrayList<Integer> RESIDUAL_VALUE_PERCENTAGES = new ArrayList<>(Arrays.asList(0, 5, 10, 15, 20, 25, 30));

    private LeaseValidationUtil() {}

    public static void validatePersonalInformation(PersonalInformationRequestDO personalInformationDAORequest) {

        if (null == personalInformationDAORequest.getFirstName() || personalInformationDAORequest.getFirstName().isEmpty() || !personalInformationDAORequest.getFirstName().toLowerCase().matches("^[A-Za-zÀ-ÖØ-öø-ſƀ-ƺǍ-ǥǦ-ǳǴ-ǵǶ-ȟȠ-ȯȱ-ȳȴ-ɏ-]\\D*$")) {
            throw new IllegalArgumentException("Invalid first name.");
        }

        if (null == personalInformationDAORequest.getLastName() || personalInformationDAORequest.getLastName().isEmpty() || !personalInformationDAORequest.getLastName().toLowerCase().matches("^[A-Za-zÀ-ÖØ-öø-ſƀ-ƺǍ-ǥǦ-ǳǴ-ǵǶ-ȟȠ-ȯȱ-ȳȴ-ɏ-]\\D*$")) {
            throw new IllegalArgumentException("Invalid last name.");
        }

        if (null == personalInformationDAORequest.getEmail() || personalInformationDAORequest.getEmail().isEmpty() || !personalInformationDAORequest.getEmail().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            throw new IllegalArgumentException("Invalid email.");
        }

        if (null == personalInformationDAORequest.getPhoneNumber() || personalInformationDAORequest.getPhoneNumber().isEmpty() || !personalInformationDAORequest.getPhoneNumber().matches("^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\s\\./0-9]*$")) {
            throw new IllegalArgumentException("Invalid phone number.");
        }

        if (null == personalInformationDAORequest.getPid() || personalInformationDAORequest.getPid().isEmpty()) {
            throw new IllegalArgumentException("PID must not be empty.");
        }

        if (null == personalInformationDAORequest.getDateOfBirth() || personalInformationDAORequest.getDateOfBirth().isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("Invalid date of birth.");
        }

        List<String> validStatuses = List.of("Single", "Married", "Partnership");
        if (!validStatuses.contains(personalInformationDAORequest.getMaritalStatus())) {
            throw new IllegalArgumentException("Invalid marital status.");
        }


        if (personalInformationDAORequest.getNumberOfChildren() < 0) {
            throw new IllegalArgumentException("Number of children cannot be negative.");
        }


        List<String> availableSelectionOptions = List.of(
                "Austria", "Belgium", "Bulgaria", "Croatia", "Cyprus", "Czech Republic",
                "Denmark", "Estonia", "Finland", "France", "Germany", "Greece", "Hungary",
                "Ireland", "Italy", "Latvia", "Lithuania", "Luxembourg", "Malta", "Netherlands",
                "Poland", "Portugal", "Romania", "Slovakia", "Slovenia", "Spain", "Sweden", "Other"
        );

        if (!availableSelectionOptions.contains(personalInformationDAORequest.getCitizenship())) {
            throw new IllegalArgumentException("Invalid citizenship.");
        }


        if (null == personalInformationDAORequest.getMonthlyIncome() || personalInformationDAORequest.getMonthlyIncome().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Monthly income must be non-negative.");
        }
    }

    public static void validateLeaseAndRatesResponse(CreateLeaseAndRatesRequestDO createLeaseAndRatesRequestDO) {

        if (null == createLeaseAndRatesRequestDO.getMake() || createLeaseAndRatesRequestDO.getMake().isEmpty()) {
            throw new IllegalArgumentException("Make cannot be null or empty.");
        }

        if (null == createLeaseAndRatesRequestDO.getModel() || createLeaseAndRatesRequestDO.getModel().isEmpty()) {
            throw new IllegalArgumentException("Model cannot be null or empty.");
        }

        if (null == createLeaseAndRatesRequestDO.getYear() || createLeaseAndRatesRequestDO.getYear().isEmpty() || !createLeaseAndRatesRequestDO.getYear().matches("\\d{4}")) {
            throw new IllegalArgumentException("Invalid year format. Year must be 4 digits.");
        }

        if (null == createLeaseAndRatesRequestDO.getFuelType() || createLeaseAndRatesRequestDO.getFuelType().isEmpty()) {
            throw new IllegalArgumentException("Fuel type cannot be null or empty.");
        }

        if (null == createLeaseAndRatesRequestDO.getEnginePower() || createLeaseAndRatesRequestDO.getEnginePower() < 0) {
            throw new IllegalArgumentException("Engine power must be non-negative.");
        }

        if (null == createLeaseAndRatesRequestDO.getEngineSize() || createLeaseAndRatesRequestDO.getEngineSize() < 0) {
            throw new IllegalArgumentException("Engine size must be non-negative.");
        }

        if (!createLeaseAndRatesRequestDO.getUrl().isEmpty() && !createLeaseAndRatesRequestDO.getUrl().matches("^(http|https)://.*")) {
            throw new IllegalArgumentException("Invalid URL.");
        }
        int MAX_BASE64_SIZE = 3 * 1024 * 1024 * 4 / 3;
        if (null != createLeaseAndRatesRequestDO.getOffer() && createLeaseAndRatesRequestDO.getOffer().length() > MAX_BASE64_SIZE) {
            throw new IllegalArgumentException("File too large. Maximum size allowed is 3MB.");
        }

        if (!createLeaseAndRatesRequestDO.getTerms() || !createLeaseAndRatesRequestDO.getConfirmation()) {
            throw new IllegalArgumentException("Terms and confirmation must not be null.");
        }

        if (null == createLeaseAndRatesRequestDO.getCarValue() || createLeaseAndRatesRequestDO.getCarValue().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Car value must be greater than zero.");
        }

        if (!PERIOD_VALUE.contains((createLeaseAndRatesRequestDO.getPeriod()))) {
            throw new IllegalArgumentException("Lease period incorrect.");
        }

        if (null == createLeaseAndRatesRequestDO.getDownPayment() || createLeaseAndRatesRequestDO.getDownPayment().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Down payment must be non-negative.");
        }

        if (!RESIDUAL_VALUE_PERCENTAGES.contains(createLeaseAndRatesRequestDO.getResidualValuePercentage())) {
            throw new IllegalArgumentException("Residual value is incorrect");
        }

        if (null == createLeaseAndRatesRequestDO.getMonthlyPayment() || createLeaseAndRatesRequestDO.getMonthlyPayment().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Monthly payment must be greater than zero.");
        }

        if (null == createLeaseAndRatesRequestDO.getIsEcoFriendly()) {
            throw new IllegalArgumentException("Eco-friendly status must not be null.");
        }
    }

    public static boolean validateIfHighRisk(PersonalInformationRequestDO personalInformationRequest, CreateLeaseAndRatesRequestDO leaseAndRatesRequest) {
        List<String> europeanUnionCountries = List.of(
                "Austria", "Belgium", "Bulgaria", "Croatia", "Cyprus", "Czech Republic",
                "Denmark", "Estonia", "Finland", "France", "Germany", "Greece", "Hungary",
                "Ireland", "Italy", "Latvia", "Lithuania", "Luxembourg", "Malta", "Netherlands",
                "Poland", "Portugal", "Romania", "Slovakia", "Slovenia", "Spain", "Sweden"
        );

        if (!europeanUnionCountries.contains(personalInformationRequest.getCitizenship())) {
            return true;
        }

        if (isYoungerThan21(personalInformationRequest.getDateOfBirth()) && leaseAndRatesRequest.getCarValue().compareTo(BigDecimal.valueOf(50000)) > 0) {
            return true;
        }

        if (isYoungerThan21(personalInformationRequest.getDateOfBirth()) && leaseAndRatesRequest.getEngineSize() >= 2.5) {
            return true;
        }

        if (isYoungerThan21(personalInformationRequest.getDateOfBirth()) && leaseAndRatesRequest.getEnginePower() >= 300) {
            return !Objects.equals(leaseAndRatesRequest.getModel(), "Tesla");
        }

        return !isValidIncome(personalInformationRequest.getMonthlyIncome(), leaseAndRatesRequest.getMonthlyPayment(), personalInformationRequest.getNumberOfChildren());
    }

    public static boolean isYoungerThan21(LocalDateTime birthDate) {
        Period period = Period.between(birthDate.toLocalDate(), LocalDate.now());
        return period.getYears() < 21;
    }

    public static boolean isValidIncome(BigDecimal monthlyIncome, BigDecimal monthlyPayment, int children) {
        return monthlyIncome.subtract(monthlyPayment).compareTo(BigDecimal.valueOf(600L * (1 + children))) >= 0;
    }
}