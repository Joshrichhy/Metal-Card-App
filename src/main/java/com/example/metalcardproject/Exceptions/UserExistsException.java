package com.example.metalcardproject.Exceptions;

public class UserExistsException extends Throwable {
    public UserExistsException(String message){
    super(message);}
}