package org.dci.walletapp;

import androidx.annotation.NonNull;

import java.util.Objects;

public class ContactDetails {
private String companyPhoneNumber;
private String companyEmail;
private String companyFaxNumbers;

    public ContactDetails(String companyPhoneNumber, String companyEmail, String companyFaxNumbers) {
        this.companyPhoneNumber = companyPhoneNumber;
        this.companyEmail = companyEmail;
        this.companyFaxNumbers = companyFaxNumbers;
    }

    public String getCompanyPhoneNumber() {
        return companyPhoneNumber;
    }


    public String getCompanyEmail() {
        return companyEmail;
    }

    public String getCompanyFaxNumbers() {
        return companyFaxNumbers;
    }

    @NonNull
    @Override
    public String toString() {
        return "ContactDetails{" +
                "companyPhoneNumber='" + companyPhoneNumber + '\'' +
                ", companyEmail='" + companyEmail + '\'' +
                ", companyFaxNumbers='" + companyFaxNumbers + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContactDetails that = (ContactDetails) o;
        return Objects.equals(companyPhoneNumber, that.companyPhoneNumber) && Objects.equals(companyEmail, that.companyEmail) && Objects.equals(companyFaxNumbers, that.companyFaxNumbers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyPhoneNumber, companyEmail, companyFaxNumbers);
    }
}
