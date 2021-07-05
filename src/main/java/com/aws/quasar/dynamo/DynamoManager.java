package com.aws.quasar.dynamo;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.aws.quasar.descifrador.MensajeSatelite;

public class DynamoManager {

    private static final Regions REGION = Regions.US_EAST_2;
    private final DynamoDB dynamoDb;
    private static final String TABLA_SATELITE = "SATELITE";
    private static final String IDX_KENOBI = "IDX_KENOBI";

    public DynamoManager() {
        AmazonDynamoDBClient client = new AmazonDynamoDBClient();
        client.setRegion(Region.getRegion(REGION));
        this.dynamoDb = new DynamoDB(client);
    }

    public void insertSatelite(MensajeSatelite satelite) {
        Table tabla = this.dynamoDb.getTable(TABLA_SATELITE);
        Item itemSatelite = new Item();
        itemSatelite.with("name",satelite.getName());
        itemSatelite.with("distance",satelite.getDistance());
        itemSatelite.with("message",satelite.getMessage().toString());
        saveData(tabla, itemSatelite);
    }

    public void saveData(Table tabla, Item item) {
        tabla.putItem(item);
    }

}
