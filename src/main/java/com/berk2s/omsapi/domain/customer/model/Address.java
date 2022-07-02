package com.berk2s.omsapi.domain.customer.model;

import com.berk2s.omsapi.domain.customer.exception.FakePhoneNumber;
import com.berk2s.omsapi.domain.customer.exception.InvalidCountryCode;
import com.berk2s.omsapi.domain.customer.exception.InvalidPostalCode;
import lombok.AllArgsConstructor;
import lombok.Data;

import static com.berk2s.omsapi.domain.validation.NullValidator.checkNonNull;

@AllArgsConstructor
@Data
public class Address {

    private String countryCode;
    private String city;
    private String district;
    private Long postalCode;
    private String phoneNumber;

    public static Address create(String countryCode, String city, String district,
                                 Long postalCode, String phoneNumber) {
        var address = new Address(countryCode, city, district, postalCode, phoneNumber);
        address.validate();

        return address;
    }

    public void validate() {
        checkNonNull(countryCode, city, district, postalCode, phoneNumber);

        if (countryCode.length() < 2 || countryCode.length() > 3) {
            throw new InvalidCountryCode("Country code length must be in 2 and 3");
        }

        if (postalCode > 10000 && postalCode < 99999) {
            throw new InvalidPostalCode("Postal code must be between 10000 and 99999");
        }

        if (phoneNumber.length() < 10 || phoneNumber.length() > 11) {
            throw new FakePhoneNumber("Phone number must be between in 10 and 11");
        }
    }
}
