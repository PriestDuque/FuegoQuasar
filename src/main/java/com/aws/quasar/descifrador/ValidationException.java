package com.aws.quasar.descifrador;

/**
 * Excepcion relacionadas con validacion de datos
 */
public class ValidationException extends Exception {

    ValidationException(String message, Exception e){
        super(message, e);
    }
}
