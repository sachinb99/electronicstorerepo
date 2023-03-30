package com.infopeersoft.electronicstore.exceptions;

public class BadApiRequest extends RuntimeException{
    public BadApiRequest(String message){
        super(message);
    }
    public BadApiRequest(){
        super("Bad Request");
    }
}
