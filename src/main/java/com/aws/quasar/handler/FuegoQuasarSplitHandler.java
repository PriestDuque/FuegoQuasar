package com.aws.quasar.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.aws.quasar.GatewayResponse;
import com.aws.quasar.descifrador.MensajeSatelite;
import com.aws.quasar.dynamo.DynamoManager;
import com.google.gson.Gson;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FuegoQuasarSplitHandler implements RequestHandler<Object, Object> {

    private DynamoManager dbManager;

    public FuegoQuasarSplitHandler(DynamoManager dbManager) {
        this.dbManager = dbManager;
    }

    public Object handleRequest(final Object input, final Context context) {
        if(this.dbManager == null) {
            this.dbManager = new DynamoManager();
        }
        Gson gson = new Gson();
        MensajeSatelite data = gson.fromJson(input.toString(), MensajeSatelite.class);
        dbManager.insertSatelite(data);
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        return new GatewayResponse(new JSONObject().put("Output", "OK").toString(), headers, 200);
    }
}
