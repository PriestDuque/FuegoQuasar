package com.aws.quasar.handler;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.aws.quasar.descifrador.MensajeSatelite;
import com.google.gson.Gson;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FuegoQuasarSplitKenobiHandler implements RequestHandler<Object, Object> {

    static AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();

    public Object handleRequest(final Object input, final Context context) {
        DynamoDBMapper mapper = new DynamoDBMapper(client);
        Gson gson = new Gson();
        MensajeSatelite data = gson.fromJson(input.toString(), MensajeSatelite.class);
        data.setName("kenobi");
        mapper.save(data);
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        return new GatewayResponse(new JSONObject().put("Output", "OK").toString(), headers, 200);
    }
}
