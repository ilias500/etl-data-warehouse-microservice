package com.syrros.etl.exception;

public class DataWarehouseException extends RuntimeException {

    public DataWarehouseException(String message, Exception ex) {
        super(message, ex);
    }
}