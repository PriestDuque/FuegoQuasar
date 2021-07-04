package com.aws.quasar.descifrador;

/**
 * DTO para manejar la respuesta que se enviara al usuario
 */
public class Respuesta {

    private Posicion position;
    private String message;

    public Respuesta(Posicion position, String message){
        this.setPosition(position);
        this.setMessage(message);
    }

    public Posicion getPosition() {
        return position;
    }

    public void setPosition(Posicion position) {
        this.position = position;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
