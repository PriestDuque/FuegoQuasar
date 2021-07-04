package com.aws.quasar.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import com.aws.quasar.GatewayResponse;
import com.aws.quasar.descifrador.DescifradorException;
import com.aws.quasar.descifrador.ProcesadorMensaje;
import com.aws.quasar.descifrador.ValidationException;
import org.json.JSONObject;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Handler for requests to Lambda function.
 */
public class FuegoQuasarHandler implements RequestHandler<Object, Object> {


    /**
     * Manejador que es llamado por la Lambda al momento de procesar la peticion,
     * este envia a procesar un mensaje de entrada para ubicar la posicion de la
     * nave y el mensaje.
     * @param input String con los mensajes y distancias de todos los satelites
     * @param context
     * @return respuesta con la ubicacion del a nave y el mensaje
     */
    public Object handleRequest(final Object input, final Context context) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        if(StringUtils.isEmpty(input)){
            return new GatewayResponse(new JSONObject().put("Output","invalid input").toString(), headers, 403);
        }
        try {
            String res=new ProcesadorMensaje().procesarMensaje(input.toString());
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
