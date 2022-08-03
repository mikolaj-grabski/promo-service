package com.mgp.promo.it

import com.mgp.promo.promotion.Promotion
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.jdbc.Sql
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate
import spock.lang.Specification
import spock.lang.Unroll

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles(value = "test")
class PromotionITSpec extends Specification {

    RestTemplate restTemplate = new RestTemplate();

    @Sql(value = "classpath:sql/insert_test_users_and_promotions.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:sql/delete_test_users_and_promotions.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    def "Should return 200 OK for a correctly retrieved promotion"() {
        when: "endpoint is called to get promotion details"
        def response = restTemplate.getForEntity(buildPromotionUrl(), Promotion)

        then: "response entity has status 200 and expected promotion is returned"
        verifyAll(response) {
            statusCode == HttpStatus.OK
            verifyAll(body as Promotion) {
                promotionCode == "ALLMINUS30"
                user.id == 2
            }
        }
    }

    @Unroll
    @Sql(value = "classpath:sql/insert_test_users_and_promotions.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:sql/delete_test_users_and_promotions.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    def "Should return status code #expectedCode for a promotion code which #scenario"() {
        when: "endpoint is called to get promotion for code which does not exist in db"
        restTemplate.getForEntity(buildPromotionUrl(userId, promotionCode), Promotion)

        then: "HttpClientErrorException is thrown with status code #expectedCode"
        HttpClientErrorException response = thrown(HttpClientErrorException)
        verifyAll(response) {
            statusCode == expectedCode
            responseBodyAsString.contains(expectedMessage)
        }

        where:
        scenario                        | userId | promotionCode || expectedCode           | expectedMessage
        "is not found"                  | 1      | "LATO2022"    || HttpStatus.NOT_FOUND   | "Promotion with given promotion code not found in the database {promotionCode=" + promotionCode + "}"
        "is not assigned to given user" | 2      | "WIOSNA2022"  || HttpStatus.BAD_REQUEST | "Promotion code not applicable for given userId {promotionCode=" + promotionCode + ", userId=" + userId + "}"
        "is outdated"                   | 1      | "WIOSNA2022"  || HttpStatus.BAD_REQUEST | "Promotion with given promotion code is either expired or not valid yet {promotionCode=" + promotionCode
        "is not valid yet"              | 1      | "WIOSNA2022"  || HttpStatus.BAD_REQUEST | "Promotion with given promotion code is either expired or not valid yet {promotionCode=" + promotionCode
    }

    def buildPromotionUrl(userId = 2, promotionCode = "ALLMINUS30") {
        "http://localhost:8080/promotion?userId=" + userId + "&promotionCode=" + promotionCode
    }

}
