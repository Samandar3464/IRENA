package com.example.irena.exception;

public class RecordAlreadyExistException extends RuntimeException{
    public RecordAlreadyExistException(String name){
        super(name);
    }
}
