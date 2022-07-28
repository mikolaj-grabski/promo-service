package com.mgp.promo.promotion.exception;

import com.mgp.promo.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class PromotionValidityPeriodException extends BusinessException {

    private static final HttpStatus DEFAULT_HTTP_STATUS = HttpStatus.BAD_REQUEST;

    public PromotionValidityPeriodException(String promotionCode, String startDate, String endDate) {
        super(String.format("Promotion with given promotion code is either expired or not valid yet " +
                "{promotionCode=%s, startDate=%s, endDate=%s", promotionCode, startDate, endDate), DEFAULT_HTTP_STATUS);
    }

}
