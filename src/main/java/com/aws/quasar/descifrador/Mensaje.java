package com.aws.quasar.descifrador;

import java.util.List;

/**
 * DTO para procesar el mensaje de entrada que contiene los satelites
 */
public class Mensaje {

    private List<MensajeSatelite> satelites;

    public Mensaje(){
    }

    public List<MensajeSatelite> getSatelites() {
        return satelites;
    }

    public void setSatelites(List<MensajeSatelite> satelites) {
        this.satelites = satelites;
    }
}
