package com.mgp.promo.user.exception;

import com.mgp.promo.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class UserNotPresentException extends BusinessException {

    private static final HttpStatus DEFAULT_HTTP_STATUS = HttpStatus.NOT_FOUND;

    public UserNotPresentException(int id) {
        super(String.format("User with given id not found in the database {userId=%d}", id), DEFAULT_HTTP_STATUS);
    }
}
