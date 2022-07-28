package com.mgp.promo.promotion.exception;

import com.mgp.promo.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class PromotionNotPresentException extends BusinessException {

    private static final HttpStatus DEFAULT_HTTP_STATUS = HttpStatus.NOT_FOUND;

    public PromotionNotPresentException(String promotionCode) {
        super(String.format("Promotion with given promotion code not found in the database {promotionCode=%s}",
                promotionCode), DEFAULT_HTTP_STATUS);
    }

}
