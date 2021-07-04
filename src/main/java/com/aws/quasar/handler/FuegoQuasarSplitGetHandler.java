package com.aws.quasar.handler;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.aws.quasar.descifrador.*;
import org.json.JSONObject;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FuegoQuasarSplitGetHandler implements RequestHandler<Object, Object> {

    static AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();


    public Object handleRequest(final Object input, final Context context) {
        DynamoDBMapper mapper = new DynamoDBMapper(client);
        Mensaje mensaje=new Mensaje();
        List<MensajeSatelite> satelites=new ArrayList<>();
        satelites.add(mapper.load(MensajeSatelite.class, "kenobi"));
        satelites.add(mapper.load(MensajeSatelite.class, "skywalker"));
        satelites.add(mapper.load(MensajeSatelite.class, "sato"));
        mensaje.setSatelites(satelites);
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        if(StringUtils.isEmpty(mensaje)){
            return new GatewayResponse(new JSONObject().put("Output","invalid input").toString(), headers, 403);
        }
        try {
            String res=new ProcesadorMensaje().procesarMensaje(mensaje.toString());
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
