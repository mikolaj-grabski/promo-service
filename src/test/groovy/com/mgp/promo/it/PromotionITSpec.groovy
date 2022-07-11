package com.mgp.promo.it

import com.mgp.promo.exception.ErrorResponse
import com.mgp.promo.model.Promotion
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.web.client.RestTemplate
import spock.lang.Specification
import spock.lang.Unroll

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class PromotionITSpec extends Specification {

    RestTemplate restTemplate = new RestTemplate();

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

    def buildPromotionUrl(userId = 2, promotionCode = "ALLMINUS30") {
        "http://localhost:8080/promotion?userId=" + userId + "&promotionCode=" + promotionCode
    }

}
