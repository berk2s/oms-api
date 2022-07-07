package com.berk2s.omsapi.infra.swagger;

public class SwaggerExample {
    public static final String CUSTOMER_CREATED_RESPONSE = "{\"customerId\":\"9c3167d3-6c2b-4264-aa86-a5f544b65461\",\"fullName\":\"qXpYo\",\"createdAt\":\"2022-07-04T20:36:08.354335\",\"lastModifiedAt\":\"2022-07-04T20:36:08.354335\"}";
    public static final String CREATE_CUSTOMER_REQ_INVALID = "{\"error\":\"invalid_request\",\"error_description\":\"request.invalid\",\"details\":[\"fullName.empty\"]}";
    public static final String CREATE_CUSTOMER_FULL_NAME_NOT_IN_RAGE = "{\"error\":\"invalid_request\",\"error_description\":\"request.invalid\",\"details\":[\"fullName.notInRange\"]}";

    public static final String INVENTORY_CREATED_RESPONSE = "{\"inventoryId\":\"b4966085-a58f-4e6c-8cf9-3ebbaddddc12\",\"barcode\":\"yWEcCiMsPl\",\"description\":\"OwzEQjrcCt\",\"totalQuantity\":10,\"price\":10,\"createdAt\":\"2022-07-04T22:29:59.92712\",\"lastModifiedAt\":\"2022-07-04T22:29:59.92712\"}";
    public static final String CREATE_INVENTORY_REQ_INVALID = "{\"error\":\"invalid_request\",\"error_description\":\"request.invalid\",\"details\":[\"totalQuantity.empty\",\"description.empty\",\"barcode.empty\",\"price.empty\"]}";
    public static final String INVENTORY_EXISTS_ERROR = "{\"error\":\"invalid_request\",\"error_description\":\"inventory.exists\",\"details\":[]}";
    public static final String LIST_INVENTORIES = "{\"content\":[{\"inventoryId\":\"395fac6a-1eb9-4f9b-adc1-bc18541cb7ac\",\"barcode\":\"HsEmdQYmPh\",\"description\":\"AHWpaNyXhf\",\"totalQuantity\":10,\"price\":10.00,\"createdAt\":\"2022-07-07T10:27:03.108074\",\"lastModifiedAt\":\"2022-07-07T10:27:03.108074\"}],\"pageable\":{\"sort\":{\"unsorted\":false,\"sorted\":true,\"empty\":false},\"pageNumber\":0,\"pageSize\":10,\"offset\":0,\"paged\":true,\"unpaged\":false},\"totalElements\":1,\"totalPages\":1,\"last\":true,\"numberOfElements\":1,\"number\":0,\"size\":10,\"first\":true,\"sort\":{\"unsorted\":false,\"sorted\":true,\"empty\":false},\"empty\":false}\n";
    public static final String ORDER_CREATED_RESPONSE = "{\"orderId\":\"b199d05d-0ffd-4fa5-bde3-31a604989f45\",\"customer\":{\"customerId\":\"58aaa5e1-f751-40ac-89ab-56bb79cf9bf3\",\"fullName\":\"xpJGIZZDai\"},\"products\":[{\"productId\":\"5472a9d8-3f89-47ef-b0d8-36adba0f9933\",\"barcode\":\"KXnpouiw\",\"description\":\"cXcFoIOWEC\",\"quantity\":1,\"price\":10.00}],\"address\":{\"countryCode\":\"Wx\",\"city\":\"xE\",\"district\":\"Ql\",\"postalCode\":35125,\"phoneNumber\":\"EtehrJzHUB\"},\"totalPrice\":10.00}\n";
    public static final String CREATE_ORDER_REQ_INVALID = "{\"error\":\"invalid_request\",\"error_description\":\"request.invalid\",\"details\":[\"products.empty\",\"deliveryAddress.empty\"]}";
    public static final String CREATE_ORDER_OUT_OF_BOUNDS_ERROR = "{\"error\":\"invalid_request\",\"error_description\":\"quantity.outOfBounds\",\"details\":[]}";

    public static final String PRODUCT_NOT_FOUND = "{\"error\":\"not_found\",\"error_description\":\"inventory.notFound\",\"details\":[]}";
    public static final String CUSTOMER_NOT_FOUND = "{\"error\":\"not_found\",\"error_description\":\"customer.notFound\",\"details\":[]}";
    public static final String ORDER_NOT_FOUND = "{\"error\":\"not_found\",\"error_description\":\"order.notFound\",\"details\":[]}";

    public static final String ORDER_UPDATED_RESPONSE = "{\"orderId\":\"cfecd3ae-2c85-46b6-a0d9-1c3fdff8cef5\",\"customer\":{\"customerId\":\"3f4abb40-ff25-45e2-ae9f-2e3da5a77451\",\"fullName\":\"ctNJNDQwET\"},\"products\":[{\"productId\":\"da6c9092-7015-4263-be95-bdc2455a3003\",\"barcode\":\"SDFysyGadF\",\"description\":\"PTIzSLmoeX\",\"quantity\":10,\"price\":10.00}],\"address\":{\"countryCode\":\"TR\",\"city\":\"Istanbul\",\"district\":\"Besiktas\",\"postalCode\":34210,\"phoneNumber\":\"5554442211\"},\"totalPrice\":10.00}\n";
    public static final String UPDATE_ORDER_ADDRESS_REQUEST_INVALID = "{\"error\":\"invalid_request\",\"error_description\":\"request.invalid\",\"details\":[\"city.empty\",\"phoneNumber.empty\",\"postalCode.empty\",\"district.empty\",\"countryCode.empty\"]}\n";
    public static final String ADD_ORDER_REQUEST_INVALID = "{\"error\":\"invalid_request\",\"error_description\":\"request.invalid\",\"details\":[\"quantity.empty\",\"barcode.empty\"]}";
    public static final String UPDATE_ORDER_REQUEST_INVALID = "{\"error\":\"invalid_request\",\"error_description\":\"request.invalid\",\"details\":[\"quantity.empty\"]}";
}
