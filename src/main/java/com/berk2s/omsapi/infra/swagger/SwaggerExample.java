package com.berk2s.omsapi.infra.swagger;

public class SwaggerExample {
    public static final String CUSTOMER_CREATED_RESPONSE = "{\"customerId\":\"9c3167d3-6c2b-4264-aa86-a5f544b65461\",\"fullName\":\"qXpYo\",\"createdAt\":\"2022-07-04T20:36:08.354335\",\"lastModifiedAt\":\"2022-07-04T20:36:08.354335\"}";
    public static final String CREATE_CUSTOMER_REQ_INVALID = "{\"error\":\"invalid_request\",\"error_description\":\"request.invalid\",\"details\":[\"fullName.empty\"]}";
    public static final String CREATE_CUSTOMER_FULL_NAME_NOT_IN_RAGE = "{\"error\":\"invalid_request\",\"error_description\":\"request.invalid\",\"details\":[\"fullName.notInRange\"]}";
}
