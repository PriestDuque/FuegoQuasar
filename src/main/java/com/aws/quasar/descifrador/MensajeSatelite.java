package com.aws.quasar.descifrador;

/**
 * DTO que maneja la informacion de cada satelite
 */
public class MensajeSatelite {

    private String name;
    private double distance;
    private String[] message;

    public MensajeSatelite(){

    }

    public MensajeSatelite(String name, double distance,String[] message){
        this.setName(name);
        this.setDistance(distance);
        this.setMessage(message);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String[] getMessage() {
        return message;
    }

    public void setMessage(String[] message) {
        this.message = message;
    }
}
