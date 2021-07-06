package com.aws.quasar.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.aws.quasar.descifrador.*;
import com.aws.quasar.util.ArchivoUtil;
import com.google.gson.Gson;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FuegoQuasarSplitGetHandler implements RequestHandler<Object, Object> {

    public Object handleRequest(final Object input, final Context context) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        Gson gson = new Gson();
        List<MensajeSatelite> satelites=new ArrayList<>();
        ArchivoUtil archivoUtil=new ArchivoUtil();
        try {
            satelites.add(gson.fromJson(archivoUtil.leer("kenobi"),MensajeSatelite.class));
            satelites.add(gson.fromJson(archivoUtil.leer("skywalker"),MensajeSatelite.class));
            satelites.add(gson.fromJson(archivoUtil.leer("sato"),MensajeSatelite.class));
        }catch (DescifradorException e){
            throw new RuntimeException("404 "+e.getMessage(),e);
        }
        Mensaje mensaje=new Mensaje();
        mensaje.setSatelites(satelites);
        if(StringUtils.isEmpty(mensaje)){
            throw new RuntimeException("404 invalid input");
        }
        try {
            return new ProcesadorMensaje().procesarMensaje(mensaje);
        }catch (ValidationException | DescifradorException e){
            throw new RuntimeException("404 "+e.getMessage(),e);
        }
    }
}
