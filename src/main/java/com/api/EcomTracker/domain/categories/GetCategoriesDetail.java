package com.api.EcomTracker.domain.categories;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetCategoriesDetail {
    private final Long id;
    private  final String name;
    private final Boolean active;

    public GetCategoriesDetail(Categories categories) {
        this(categories.getId(), categories.getName(), categories.getActive());
    }
}
