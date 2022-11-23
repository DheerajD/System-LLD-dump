package com.paymentrecommendation.exception;


public class LOBNotFoundException extends Exception {
    public static final String LOB_NOT_FOUND_MSG = "Given LOB not found";
    public LOBNotFoundException() {
        super(LOB_NOT_FOUND_MSG);
    }
}