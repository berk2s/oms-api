package com.berk2s.omsapi.domain.usecase;

public interface UseCaseHandler<E, T extends UseCase> {

    E handle(T t);
}
