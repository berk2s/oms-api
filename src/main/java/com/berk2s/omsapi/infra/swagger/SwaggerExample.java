package com.berk2s.omsapi.infra.swagger;

public class SwaggerExample {
    public static final String CUSTOMER_CREATED_RESPONSE = "{\"customerId\":\"9c3167d3-6c2b-4264-aa86-a5f544b65461\",\"fullName\":\"qXpYo\",\"createdAt\":\"2022-07-04T20:36:08.354335\",\"lastModifiedAt\":\"2022-07-04T20:36:08.354335\"}";
    public static final String CREATE_CUSTOMER_REQ_INVALID = "{\"error\":\"invalid_request\",\"error_description\":\"request.invalid\",\"details\":[\"fullName.empty\"]}";
    public static final String CREATE_CUSTOMER_FULL_NAME_NOT_IN_RAGE = "{\"error\":\"invalid_request\",\"error_description\":\"request.invalid\",\"details\":[\"fullName.notInRange\"]}";

    public static final String INVENTORY_CREATED_RESPONSE = "{\"inventoryId\":\"b4966085-a58f-4e6c-8cf9-3ebbaddddc12\",\"barcode\":\"yWEcCiMsPl\",\"description\":\"OwzEQjrcCt\",\"totalQuantity\":10,\"price\":10,\"createdAt\":\"2022-07-04T22:29:59.92712\",\"lastModifiedAt\":\"2022-07-04T22:29:59.92712\"}";
    public static final String CREATE_INVENTORY_REQ_INVALID = "{\"error\":\"invalid_request\",\"error_description\":\"request.invalid\",\"details\":[\"totalQuantity.empty\",\"description.empty\",\"barcode.empty\",\"price.empty\"]}";
    public static final String INVENTORY_EXISTS_ERROR = "{\"error\":\"invalid_request\",\"error_description\":\"inventory.exists\",\"details\":[]}";

    public static final String ORDER_CREATED_RESPONSE = "{\"orderId\":\"b199d05d-0ffd-4fa5-bde3-31a604989f45\",\"customer\":{\"customerId\":\"58aaa5e1-f751-40ac-89ab-56bb79cf9bf3\",\"fullName\":\"xpJGIZZDai\"},\"products\":[{\"productId\":\"5472a9d8-3f89-47ef-b0d8-36adba0f9933\",\"barcode\":\"KXnpouiw\",\"description\":\"cXcFoIOWEC\",\"quantity\":1,\"price\":10.00}],\"address\":{\"countryCode\":\"Wx\",\"city\":\"xE\",\"district\":\"Ql\",\"postalCode\":35125,\"phoneNumber\":\"EtehrJzHUB\"},\"totalPrice\":10.00}\n";
    public static final String CREATE_ORDER_REQ_INVALID = "{\"error\":\"invalid_request\",\"error_description\":\"request.invalid\",\"details\":[\"products.empty\",\"deliveryAddress.empty\"]}";
    public static final String CREATE_ORDER_OUT_OF_BOUNDS_ERROR = "{\"error\":\"invalid_request\",\"error_description\":\"quantity.outOfBounds\",\"details\":[]}";
    public static final String PRODUCT_NOT_FOUND = "{\"error\":\"not_found\",\"error_description\":\"inventory.notFound\",\"details\":[]}";
    public static final String CUSTOMER_NOT_FOUND = "{\"error\":\"not_found\",\"error_description\":\"customer.notFound\",\"details\":[]}";
}
