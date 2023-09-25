package com.ssafy.coffeeing.modules.search.dto;

import java.util.List;

public record SearchProductResponse(
        List<ProductSearchElement> products,
        Integer currentPage,
        Boolean isLast,
        Integer totalPage
) {
}