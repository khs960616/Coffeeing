package com.ssafy.coffeeing.modules.search.service;

import com.ssafy.coffeeing.modules.product.domain.Capsule;
import com.ssafy.coffeeing.modules.product.domain.Coffee;
import com.ssafy.coffeeing.modules.product.domain.ProductType;
import com.ssafy.coffeeing.modules.product.repository.CapsuleRepository;
import com.ssafy.coffeeing.modules.product.repository.CoffeeRepository;
import com.ssafy.coffeeing.modules.search.domain.Acidity;
import com.ssafy.coffeeing.modules.search.domain.Body;
import com.ssafy.coffeeing.modules.search.domain.Roast;
import com.ssafy.coffeeing.modules.search.domain.Tag;
import com.ssafy.coffeeing.modules.search.dto.BeanSearchElement;
import com.ssafy.coffeeing.modules.search.dto.CapsuleSearchElement;
import com.ssafy.coffeeing.modules.search.dto.SearchBeanResponse;
import com.ssafy.coffeeing.modules.search.dto.SearchCapsuleResponse;
import com.ssafy.coffeeing.modules.search.dto.SearchProductRequest;
import com.ssafy.coffeeing.modules.search.dto.SearchTagRequest;
import com.ssafy.coffeeing.modules.search.dto.TagsResponse;
import com.ssafy.coffeeing.modules.search.mapper.SearchMapper;
import com.ssafy.coffeeing.modules.search.repository.SearchQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class SearchService {

    private final CapsuleRepository capsuleRepository;

    private final CoffeeRepository coffeeRepository;

    private final SearchQueryRepository searchQueryRepository;
    private static final int AUTO_COMPLETE_SIZE = 10;

    @Transactional(readOnly = true)
    public TagsResponse getProductsBySuggestion(SearchTagRequest searchTagRequest) {
        List<Tag> tags = new ArrayList<>();
        String keyword = searchTagRequest.keyword();
        List<Capsule> capsules = capsuleRepository
                .findCapsulesByCapsuleNameKrContainingIgnoreCase(keyword, PageRequest.of(0, AUTO_COMPLETE_SIZE));
        List<Coffee> coffees = coffeeRepository
                .findCoffeesByCoffeeNameKrContainingIgnoreCase(keyword, PageRequest.of(0, AUTO_COMPLETE_SIZE));

        addCapsulesAndCoffeesToTagElement(tags, capsules, coffees);

        Collections.shuffle(tags);
        tags.sort(Comparator.comparing(Tag::name));

        if (tags.size() > AUTO_COMPLETE_SIZE) {
            tags = tags.subList(0, AUTO_COMPLETE_SIZE);
        }
        return SearchMapper.supplyTagsResponseFrom(tags);
    }

    @Transactional(readOnly = true)
    public SearchBeanResponse getProductsBySearchBean(SearchProductRequest searchProductRequest) {
        Page<BeanSearchElement> beanSearchElements;

        beanSearchElements = searchBeanByConditions(searchProductRequest);
        Boolean isLast = searchProductRequest.page() + 1 == beanSearchElements.getTotalPages();

        return SearchMapper.supplySearchBeanResponseOf(
                beanSearchElements.getContent(),
                searchProductRequest.page(),
                isLast,
                beanSearchElements.getTotalPages() - 1);
    }

    @Transactional(readOnly = true)
    public SearchCapsuleResponse getProductsBySearchCapsule(SearchProductRequest searchProductRequest) {
        Page<CapsuleSearchElement> capsuleSearchElements;

        capsuleSearchElements = searchCapsuleByConditions(searchProductRequest);
        Boolean isLast = searchProductRequest.page() + 1 == capsuleSearchElements.getTotalPages();

        return SearchMapper.supplySearchCapsuleResponseOf(
                capsuleSearchElements.getContent(),
                searchProductRequest.page(),
                isLast,
                capsuleSearchElements.getTotalPages() - 1);
    }

    private Page<BeanSearchElement> searchBeanByConditions(SearchProductRequest searchProductRequest) {
        return searchQueryRepository.searchByBeanConditions(
                searchProductRequest.keyword(),
                roastStringToList(searchProductRequest.roast()),
                acidityStringToList(searchProductRequest.acidity()),
                bodyStringToList(searchProductRequest.body()),
                flavorNoteToList(searchProductRequest.flavorNote()),
                PageRequest.of(searchProductRequest.page(), searchProductRequest.size()));
    }

    private Page<CapsuleSearchElement> searchCapsuleByConditions(SearchProductRequest searchProductRequest) {
        return searchQueryRepository.searchByCapsuleConditions(
                searchProductRequest.keyword(),
                roastStringToList(searchProductRequest.roast()),
                acidityStringToList(searchProductRequest.acidity()),
                bodyStringToList(searchProductRequest.body()),
                flavorNoteToList(searchProductRequest.flavorNote()),
                PageRequest.of(searchProductRequest.page(), searchProductRequest.size()));
    }

    private List<String> flavorNoteToList(String flavorNote) {
        if(Objects.isNull(flavorNote)) return new ArrayList<>();

        return Arrays.stream(flavorNote.split(","))
                .map(String::trim)
                .toList();
    }

    private List<Roast> roastStringToList(String roast) {
        if(Objects.isNull(roast)) return new ArrayList<>();

        return Arrays.stream(roast.split(","))
                .map(String::trim)
                .map(Roast::findRoast)
                .toList();
    }

    private List<Acidity> acidityStringToList(String acidity) {
        if(Objects.isNull(acidity)) return new ArrayList<>();

        return Arrays.stream(acidity.split(","))
                .map(String::trim)
                .map(Acidity::findAcidity)
                .toList();
    }

    private List<Body> bodyStringToList(String body) {
        if(Objects.isNull(body)) return new ArrayList<>();

        return Arrays.stream(body.split(","))
                .map(String::trim)
                .map(Body::findBody)
                .toList();
    }

    private void addCapsulesAndCoffeesToTagElement(
            List<Tag> tags,
            List<Capsule> capsules,
            List<Coffee> coffees) {
        tags.addAll(capsules.stream().map(capsule ->
                new Tag(capsule.getId(), ProductType.COFFEE_CAPSULE, capsule.getCapsuleNameKr())).toList());
        tags.addAll(coffees.stream().map(coffee ->
                new Tag(coffee.getId(), ProductType.COFFEE_BEAN, coffee.getCoffeeNameKr())).toList());
    }
}
