package com.mfokumus.exception;

public class BadRequest400Exception extends RuntimeException{
    public BadRequest400Exception(String message) {
        super(message);
    }
}// end BadRequest400Exception class
