package com.api.EcomTracker.domain.categories;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetAllCategories {
    private final Long id;
    private  final String name;
    private final Boolean active;

    public GetAllCategories(Categories categories) {
        this(categories.getId(), categories.getName(), categories.getActive());
    }
}
