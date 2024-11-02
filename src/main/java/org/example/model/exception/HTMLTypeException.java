package org.example.model.exception;

public class HTMLTypeException extends Exception{
    public HTMLTypeException(String tagName) {
        super("The tag <" + tagName + "> is a void element and cannot contain child elements.");
    }
}
