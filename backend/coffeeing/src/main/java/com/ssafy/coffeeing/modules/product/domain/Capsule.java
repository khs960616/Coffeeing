package com.ssafy.coffeeing.modules.product.domain;

import com.ssafy.coffeeing.modules.global.embedded.CoffeeCriteria;
import com.ssafy.coffeeing.modules.util.base.BaseEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;

@Getter
@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(name = "id", column = @Column(name = "capsule_id"))
@Entity
public class Capsule extends BaseEntity {

    @Column
    private String brandKr;

    @Column
    private String brandEng;

    @Column
    private String capsuleNameKr;

    @Column
    private String capsuleNameEng;

    @Embedded
    private CoffeeCriteria coffeeCriteria;

    @Column
    private String flavorNote;

    @Column
    private Integer machineType;

    @Column
    private String imageUrl;

    @Column
    private String productDescription;

    @Column
    @Builder.Default
    private Double totalScore = 0.0;

    @Column
    @Builder.Default
    private Integer totalReviewer = 0;

    @Column
    @Builder.Default
    private Integer popularity = 0;
}
