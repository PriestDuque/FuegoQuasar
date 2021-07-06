package com.aws.quasar.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import com.aws.quasar.descifrador.DescifradorException;
import com.aws.quasar.descifrador.Mensaje;
import com.aws.quasar.descifrador.ProcesadorMensaje;
import com.aws.quasar.descifrador.ValidationException;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Handler for requests to Lambda function.
 */
public class FuegoQuasarHandler implements RequestHandler<Mensaje, Object> {


    /**
     * Manejador que es llamado por la Lambda al momento de procesar la peticion,
     * este envia a procesar un mensaje de entrada para ubicar la posicion de la
     * nave y el mensaje.
     * @param input String con los mensajes y distancias de todos los satelites
     * @param context
     * @return respuesta con la ubicacion del a nave y el mensaje
     */
    public Object handleRequest(final Mensaje input, final Context context) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        if(StringUtils.isEmpty(input)){
            throw new RuntimeException("invalid input");
        }
        try {
            return new ProcesadorMensaje().procesarMensaje(input);
        }catch (ValidationException | DescifradorException e){
           throw new RuntimeException("404 "+e.getMessage(),e);
        }
    }
}
