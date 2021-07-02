package com.aws.quasar.descifrador;

import java.util.Map;

public class MensajeSatelite {

    private String name;
    private double distance;
    private String[] message;

    public MensajeSatelite(){

    }

    public MensajeSatelite(Map input){
        name=input.get("name").toString();
        distance=Double.parseDouble(input.get("distance").toString());
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
