package com.example.metalcardproject.Exceptions;

public class InvalidPinException extends Throwable{
    public InvalidPinException(String message){
        super(message);
    }
}
