package com.berk2s.omsapi.infra.sorting;

import org.springframework.data.domain.Sort;

public final class SortingUtils {

    public static Sort generateSort(String sortBy, String order) {
        Sort sorting = Sort.by(sortBy);

        if (order.equals("desc"))
            sorting = sorting.descending();
        else
            sorting = sorting.ascending();

        return sorting;
    }
}
