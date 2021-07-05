package com.aws.quasar.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.aws.quasar.descifrador.DescifradorException;
import com.aws.quasar.descifrador.MensajeSatelite;
import com.aws.quasar.util.ArchivoUtil;
import com.google.gson.Gson;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FuegoQuasarSplitKenobiHandler implements RequestHandler<Object, Object> {


    public Object handleRequest(final Object input, final Context context) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        Gson gson = new Gson();
        MensajeSatelite data = gson.fromJson(input.toString(), MensajeSatelite.class);
        data.setName("kenobi");
        try {
            new ArchivoUtil().escribir("kenobi", gson.toJson(data));
        }catch (DescifradorException e){
            return new GatewayResponse(new JSONObject().put("Output", "Error").toString(), headers, 403);
        }
        return new GatewayResponse(new JSONObject().put("Output", "OK").toString(), headers, 200);
    }
}
