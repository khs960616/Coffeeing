package com.ssafy.coffeeing.modules.search.controller;

import com.ssafy.coffeeing.modules.search.dto.*;
import com.ssafy.coffeeing.modules.search.service.SearchService;
import com.ssafy.coffeeing.modules.util.base.BaseResponse;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @ApiImplicitParams(
            {
                    @ApiImplicitParam(
                            name = "keyword"
                            , value = "검색 키워드"
                            , required = true
                            , dataType = "string"
                            , paramType = "query"
                            , defaultValue = "None"
                    )
            })
    @ApiOperation(value = "태그에 사용할 키워드 검색")
    @GetMapping("/tags")
    public BaseResponse<TagsResponse> getProductsBySuggestion(SearchTagRequest searchTagRequest) {
        return BaseResponse.<TagsResponse>builder()
                .data(searchService.getProductsBySuggestion(searchTagRequest))
                .build();
    }

    @ApiImplicitParams(
            {
                    @ApiImplicitParam(
                            name = "keyword"
                            , value = "검색 키워드"
                            , required = false
                            , dataType = "string"
                            , paramType = "query"
                            , defaultValue = "None"
                    )
                    ,
                    @ApiImplicitParam(
                            name = "roast"
                            , value = "로스팅 정도"
                            , required = false
                            , dataType = "string"
                            , paramType = "query"
                            , defaultValue = "LIGHT"
                    )
                    ,
                    @ApiImplicitParam(
                            name = "body"
                            , value = "바디감 정도"
                            , required = false
                            , dataType = "string"
                            , paramType = "query"
                            , defaultValue = "HEAVY"
                    ),
                    @ApiImplicitParam(
                            name = "acidity"
                            , value = "산미 정도"
                            , required = false
                            , dataType = "string"
                            , paramType = "query"
                            , defaultValue = "LOW"
                    )
                    ,
                    @ApiImplicitParam(
                            name = "flavorNote"
                            , value = "플레이버 노트"
                            , required = false
                            , dataType = "string"
                            , paramType = "query"
                            , defaultValue = "fruity, sweety"
                    ),
                    @ApiImplicitParam(
                            name = "page"
                            , value = "Integer"
                            , required = false
                            , dataType = "number"
                            , paramType = "query"
                            , defaultValue = "0"
                    ),
                    @ApiImplicitParam(
                            name = "size"
                            , value = "Integer"
                            , required = false
                            , dataType = "number"
                            , paramType = "query"
                            , defaultValue = "8"
                    )
            })
    @ApiOperation(value = "캡슐 검색")
    @GetMapping("/products/capsule")
    public BaseResponse<SearchCapsuleResponse> getProductsBySearchCapsule(SearchProductRequest searchProductRequest) {
        return BaseResponse.<SearchCapsuleResponse>builder()
                .data(searchService.getProductsBySearchCapsule(searchProductRequest))
                .build();
    }

    @ApiImplicitParams(
            {
                    @ApiImplicitParam(
                            name = "keyword"
                            , value = "검색 키워드"
                            , required = false
                            , dataType = "string"
                            , paramType = "query"
                            , defaultValue = "None"
                    )
                    ,
                    @ApiImplicitParam(
                            name = "roast"
                            , value = "로스팅 정도"
                            , required = false
                            , dataType = "string"
                            , paramType = "query"
                            , defaultValue = "LIGHT"
                    )
                    ,
                    @ApiImplicitParam(
                            name = "body"
                            , value = "바디감 정도"
                            , required = false
                            , dataType = "string"
                            , paramType = "query"
                            , defaultValue = "HEAVY"
                    ),
                    @ApiImplicitParam(
                            name = "acidity"
                            , value = "산미 정도"
                            , required = false
                            , dataType = "string"
                            , paramType = "query"
                            , defaultValue = "LOW"
                    )
                    ,
                    @ApiImplicitParam(
                            name = "flavorNote"
                            , value = "플레이버 노트"
                            , required = false
                            , dataType = "string"
                            , paramType = "query"
                            , defaultValue = "fruity, sweety"
                    ),
                    @ApiImplicitParam(
                            name = "page"
                            , value = "Integer"
                            , required = false
                            , dataType = "number"
                            , paramType = "query"
                            , defaultValue = "0"
                    ),
                    @ApiImplicitParam(
                            name = "size"
                            , value = "Integer"
                            , required = false
                            , dataType = "number"
                            , paramType = "query"
                            , defaultValue = "8"
                    )
            })
    @ApiOperation(value = "원두 검색")
    @GetMapping("/products/bean")
    public BaseResponse<SearchBeanResponse> getProductsBySearchBean(SearchProductRequest searchProductRequest) {
        return BaseResponse.<SearchBeanResponse>builder()
                .data(searchService.getProductsBySearchBean(searchProductRequest))
                .build();
    }
}
