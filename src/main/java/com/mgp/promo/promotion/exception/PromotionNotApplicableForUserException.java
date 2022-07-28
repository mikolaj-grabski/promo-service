package com.mgp.promo.promotion.exception;

import com.mgp.promo.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class PromotionNotApplicableForUserException extends BusinessException {

    private static final HttpStatus DEFAULT_HTTP_STATUS = HttpStatus.BAD_REQUEST;

    public PromotionNotApplicableForUserException(String promotionCode, int userId) {
        super(String.format("Promotion code not applicable for given userId {promotionCode=%s, userId=%d}",
                promotionCode, userId), DEFAULT_HTTP_STATUS);
    }

}
