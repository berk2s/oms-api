package com.berk2s.omsapi.domain.order.model;

import com.berk2s.omsapi.domain.order.exception.FakePhoneNumber;
import com.berk2s.omsapi.domain.order.exception.InvalidCountryCode;
import com.berk2s.omsapi.domain.order.exception.InvalidPostalCode;
import com.berk2s.omsapi.domain.order.usecase.CreateOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import static com.berk2s.omsapi.domain.validation.NullValidator.checkNonNull;

@Slf4j
@AllArgsConstructor
@Data
public class OrderAddress {

    private String countryCode;
    private String city;
    private String district;
    private Integer postalCode;
    private String phoneNumber;

    public static OrderAddress newAddress(String countryCode, String city, String district,
                                          Integer postalCode, String phoneNumber) {
        var address = new OrderAddress(countryCode, city, district, postalCode, phoneNumber);
        address.validate();

        return address;
    }

    public static OrderAddress from(CreateOrder.DeliveryAddress deliveryAddress) {
        return OrderAddress.newAddress(deliveryAddress.getCountryCode(),
                deliveryAddress.getCity(),
                deliveryAddress.getDistrict(),
                deliveryAddress.getPostalCode(),
                deliveryAddress.getPhoneNumber());
    }

    public void validate() {
        checkNonNull(countryCode, city, district, postalCode, phoneNumber);

        if (countryCode.length() < 2 || countryCode.length() > 3) {
            log.warn("Given country code is invalid [countryCode: {}]", countryCode);
            throw new InvalidCountryCode("countryCode.invalid");
        }

        if (postalCode.compareTo(10000) < 0 || postalCode.compareTo(99999) > 0) {
            log.warn("Given postal code is invalid [postalCode: {}]", postalCode);
            throw new InvalidPostalCode("postalCode.invalid");
        }

        if (phoneNumber.length() < 10 || phoneNumber.length() > 11) {
            log.warn("Given phone number is invalid [phoneNumber: {}]", phoneNumber);
            throw new FakePhoneNumber("phoneNumber.invalid");
        }
    }
}
