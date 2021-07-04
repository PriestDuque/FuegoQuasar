package com.aws.quasar.descifrador;

import com.google.gson.Gson;

/**
 * Se encarga de procesar el mensaje de entrada y llamar al Descifrador para que nos de la posicion y mensaje que
 * se creara como respuesta
 */
public class ProcesadorMensaje {

    private String respuesta;

    public String procesarMensaje(String input) throws DescifradorException, ValidationException {
        Posicion posicion;
        String mensaje;
        try {
            Gson gson = new Gson();
            Mensaje data = gson.fromJson(input, Mensaje.class);
            Descifrador descifrador=new Descifrador();
            MensajeSatelite kenobi=data.getSatelites().get(0);
            MensajeSatelite skywalker=data.getSatelites().get(1);
            MensajeSatelite sato=data.getSatelites().get(2);
            String[][] mensajes=new String[][]{kenobi.getMessage(),skywalker.getMessage(),sato.getMessage()};
            posicion=descifrador.getLocation(kenobi.getDistance(),skywalker.getDistance(),sato.getDistance());
            mensaje=descifrador.getMessage(mensajes);
        }catch (Exception e){
            throw new ValidationException(e.getMessage(),e);
        }

        if(posicion!=null && mensaje!=null){
            respuesta=new Gson().toJson(new Respuesta(posicion,mensaje));
        }else{
            throw new DescifradorException("Mensaje indescifrable");
        }
        return respuesta;
    }

}
