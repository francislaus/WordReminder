package com.example.franz.wordreminder.Model;

/**
 * Created by franz on 21.09.16.
 */

public class InvalidSourceFileException extends RuntimeException {

    public InvalidSourceFileException(){
        this("");
    }

    public InvalidSourceFileException(String exceptionString){
        super(exceptionString);
    }

}
