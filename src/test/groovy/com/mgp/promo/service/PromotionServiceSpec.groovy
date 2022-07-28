package com.mgp.promo.service

import com.mgp.promo.promotion.exception.PromotionNotApplicableForUserException
import com.mgp.promo.promotion.exception.PromotionNotPresentException
import com.mgp.promo.promotion.exception.PromotionValidityPeriodException
import com.mgp.promo.promotion.Promotion
import com.mgp.promo.user.User
import com.mgp.promo.promotion.PromotionRepository
import com.mgp.promo.promotion.PromotionService
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import java.time.LocalDateTime;

class PromotionServiceSpec extends Specification {

    def promotionRepository = Mock(PromotionRepository)

    @Subject
    def promotionService = new PromotionService(promotionRepository)

    def "Should correctly return info about the promotion"() {
        given: "promotion code and user id"
        def promotionCode = "LATO2022"
        def userId = 1

        and: "valid expected promotion"
        def expectedPromotion = buildPromotion()

        and: "repository is being mocked"
        promotionRepository.findByPromotionCode(promotionCode) >> Optional.of(expectedPromotion)

        when: "getPromotionCode method is called with given parameters"
        def result = promotionService.getPromotionCode(userId, promotionCode)

        then: "result is returned correctly"
        noExceptionThrown()
        result == expectedPromotion
    }

    @Unroll
    def "Should throw #expectedException.getSimpleName() when promotion is #scenario"() {
        given: "repository result is mocked"
        promotionRepository.findByPromotionCode(promotionCode) >> givenPromotion

        when: "getPromotionCode method is called with #promotionCode and"
        promotionService.getPromotionCode(userId, promotionCode)

        then: "expected exception is thrown"
        thrown(expectedException)

        where:
        scenario                       | promotionCode | userId | givenPromotion                                                                                   || expectedException
        "not found"                    | "LATO2022"    | 1      | Optional.empty()                                                                                 || PromotionNotPresentException
        "assigned to a different user" | "WIOSNA2022"  | 2      | Optional.of(buildPromotion())                                                                    || PromotionNotApplicableForUserException
        "outdated"                     | "WIOSNA2022"  | 1      | Optional.of(buildPromotion(LocalDateTime.now().minusDays(10), LocalDateTime.now().minusDays(5))) || PromotionValidityPeriodException
        "not valid yet"                | "WIOSNA2022"  | 1      | Optional.of(buildPromotion(LocalDateTime.now().plusDays(5), LocalDateTime.now().plusDays(10)))   || PromotionValidityPeriodException
    }

    def buildPromotion(
            startDate = LocalDateTime.now().minusDays(10),
            endDate = LocalDateTime.now().plusDays(10)
    ) {
        return Promotion.builder()
                .promotionCode("LATO2020")
                .user(User.builder()
                        .id(1)
                        .email("max@gmail.com")
                        .build())
                .startDate(startDate)
                .endDate(endDate)
                .build()
    }

}