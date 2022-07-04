package com.berk2s.omsapi.infra.swagger;

public class SwaggerExample {
    public static final String CUSTOMER_CREATED_RESPONSE = "{\"customerId\":\"9c3167d3-6c2b-4264-aa86-a5f544b65461\",\"fullName\":\"qXpYo\",\"createdAt\":\"2022-07-04T20:36:08.354335\",\"lastModifiedAt\":\"2022-07-04T20:36:08.354335\"}";
    public static final String CREATE_CUSTOMER_REQ_INVALID = "{\"error\":\"invalid_request\",\"error_description\":\"request.invalid\",\"details\":[\"fullName.empty\"]}";
    public static final String CREATE_CUSTOMER_FULL_NAME_NOT_IN_RAGE = "{\"error\":\"invalid_request\",\"error_description\":\"request.invalid\",\"details\":[\"fullName.notInRange\"]}";

    public static final String INVENTORY_CREATED_RESPONSE = "{\"inventoryId\":\"b4966085-a58f-4e6c-8cf9-3ebbaddddc12\",\"barcode\":\"yWEcCiMsPl\",\"description\":\"OwzEQjrcCt\",\"totalQuantity\":10,\"price\":10,\"createdAt\":\"2022-07-04T22:29:59.92712\",\"lastModifiedAt\":\"2022-07-04T22:29:59.92712\"}";
    public static final String CREATE_INVENTORY_REQ_INVALID = "{\"error\":\"invalid_request\",\"error_description\":\"request.invalid\",\"details\":[\"totalQuantity.empty\",\"description.empty\",\"barcode.empty\",\"price.empty\"]}";
    public static final String INVENTORY_EXISTS_ERROR = "{\"error\":\"invalid_request\",\"error_description\":\"inventory.exists\",\"details\":[]}";
}
