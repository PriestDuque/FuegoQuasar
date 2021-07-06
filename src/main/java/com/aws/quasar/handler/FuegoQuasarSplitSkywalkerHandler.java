package com.aws.quasar.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.aws.quasar.descifrador.DescifradorException;
import com.aws.quasar.descifrador.MensajeSatelite;
import com.aws.quasar.util.ArchivoUtil;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class FuegoQuasarSplitSkywalkerHandler implements RequestHandler<Object, Object> {


    public Object handleRequest(final Object input, final Context context) {
        Gson gson = new Gson();
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        MensajeSatelite data = gson.fromJson(input.toString(), MensajeSatelite.class);
        data.setName("skywalker");
        try{
            new ArchivoUtil().escribir("skywalker", gson.toJson(data));
        }catch (DescifradorException e){
            throw new RuntimeException("404 "+e.getMessage(),e);
        }
        return "200 OK";
    }
}
