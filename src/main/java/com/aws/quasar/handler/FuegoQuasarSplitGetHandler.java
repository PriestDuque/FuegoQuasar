package com.aws.quasar.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.aws.quasar.descifrador.*;
import com.aws.quasar.util.ArchivoUtil;
import com.google.gson.Gson;
import org.json.JSONObject;
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
            return new GatewayResponse(new JSONObject().put("Output",e.getMessage()).toString(), headers, 403);
        }
        Mensaje mensaje=new Mensaje();
        mensaje.setSatelites(satelites);
        if(StringUtils.isEmpty(mensaje)){
            return new GatewayResponse(new JSONObject().put("Output","invalid input").toString(), headers, 403);
        }
        try {
            String res=new ProcesadorMensaje().procesarMensaje(mensaje);
            if(res!=null) {
                return new GatewayResponse(new JSONObject().put("Output", res).toString(), headers, 200);
            }else {
                return new GatewayResponse("", headers, 404);
            }
        }catch (ValidationException e){
            return new GatewayResponse(new JSONObject().put("Output",e.getMessage()).toString(), headers, 403);
        }catch (DescifradorException e){
            return new GatewayResponse(new JSONObject().put("Output",e.getMessage()).toString(), headers, 404);
        }
    }
}
