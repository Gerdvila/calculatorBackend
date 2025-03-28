package com.leasing.calculator.domain.aggregates.request;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PersonalInformationRequestDO {

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String pid;
    private LocalDateTime dateOfBirth;
    private String maritalStatus;
    private int numberOfChildren;
    private String citizenship;
    private BigDecimal monthlyIncome;
    private String languagePref;

    private PersonalInformationRequestDO(Builder builder) {
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.email = builder.email;
        this.phoneNumber = builder.phoneNumber;
        this.pid = builder.pid;
        this.dateOfBirth = builder.dateOfBirth;
        this.maritalStatus = builder.maritalStatus;
        this.numberOfChildren = builder.numberOfChildren;
        this.citizenship = builder.citizenship;
        this.monthlyIncome = builder.monthlyIncome;
        this.languagePref = builder.languagePref;
    }

    public static class Builder {
        private String firstName;
        private String lastName;
        private String email;
        private String phoneNumber;
        private String pid;
        private LocalDateTime dateOfBirth;
        private String maritalStatus;
        private int numberOfChildren;
        private String citizenship;
        private BigDecimal monthlyIncome;
        private String languagePref;

        public Builder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder withPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder withPid(String pid) {
            this.pid = pid;
            return this;
        }

        public Builder withDateOfBirth(LocalDateTime dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
            return this;
        }

        public Builder withMaritalStatus(String maritalStatus) {
            this.maritalStatus = maritalStatus;
            return this;
        }

        public Builder withNumberOfChildren(int numberOfChildren) {
            this.numberOfChildren = numberOfChildren;
            return this;
        }

        public Builder withCitizenship(String citizenship) {
            this.citizenship = citizenship;
            return this;
        }

        public Builder withMonthlyIncome(BigDecimal monthlyIncome) {
            this.monthlyIncome = monthlyIncome;
            return this;
        }

        public Builder withLanguagePref(String languagePref) {
            this.languagePref = languagePref;
            return this;
        }

        public PersonalInformationRequestDO build() {
            return new PersonalInformationRequestDO(this);
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPid() {
        return pid;
    }

    public LocalDateTime getDateOfBirth() {
        return dateOfBirth;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public int getNumberOfChildren() {
        return numberOfChildren;
    }

    public String getCitizenship() {
        return citizenship;
    }

    public BigDecimal getMonthlyIncome() {
        return monthlyIncome;
    }

    public String getLanguagePref() {
        return languagePref;
    }
}