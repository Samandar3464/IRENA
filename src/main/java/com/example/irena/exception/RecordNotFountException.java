package com.example.irena.exception;

public class RecordNotFountException extends RuntimeException{
    public RecordNotFountException(String name){
        super(name);
    }
}
