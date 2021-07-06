package com.aws.quasar.descifrador;

/**
 * Excepciones en el calculo del descifrador
 */
public class DescifradorException extends Exception {

    public DescifradorException(String message){
        super(message);
    }

    public DescifradorException(String message, Exception e){
        super(message, e);
    }
}
