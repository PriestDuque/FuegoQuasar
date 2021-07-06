package com.aws.quasar.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.aws.quasar.descifrador.DescifradorException;
import com.aws.quasar.descifrador.MensajeSatelite;
import com.aws.quasar.util.ArchivoUtil;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class FuegoQuasarSplitKenobiHandler implements RequestHandler<MensajeSatelite, Object> {


    public Object handleRequest(final MensajeSatelite input, final Context context) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        Gson gson = new Gson();
        input.setName("kenobi");
        try {
            new ArchivoUtil().escribir("kenobi", gson.toJson(input));
        }catch (DescifradorException e){
            throw new RuntimeException("404 "+e.getMessage(),e);
        }
        return "200 OK";
    }
}
