package com.aws.quasar.descifrador;

public class Mensaje {

    private MensajeSatelite[] satelites;

    public Mensaje(){

    }

    public MensajeSatelite[] getSatelites() {
        return satelites;
    }

    public void setSatelites(MensajeSatelite[] satelites) {
        this.satelites = satelites;
    }
}
