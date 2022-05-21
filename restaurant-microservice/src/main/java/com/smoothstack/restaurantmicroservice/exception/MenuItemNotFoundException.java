package com.smoothstack.restaurantmicroservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MenuItemNotFoundException extends RuntimeException {
    public MenuItemNotFoundException (String exception){
        super(exception);
    }
}
