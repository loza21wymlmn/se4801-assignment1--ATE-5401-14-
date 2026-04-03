// Student Number: ATE/5401/14

package com.shopwave.shopwave_starter.exception;


public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String message) {
        super(message);
    }
}