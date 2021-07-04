package com.aws.quasar.descifrador;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

/**
 * DTO que maneja la informacion de cada satelite
 */
@DynamoDBTable(tableName = "Satelite")
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

    @DynamoDBHashKey(attributeName = "Id")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @DynamoDBAttribute(attributeName = "Distance")
    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    @DynamoDBAttribute(attributeName = "Distance")
    public String[] getMessage() {
        return message;
    }

    public void setMessage(String[] message) {
        this.message = message;
    }
}
