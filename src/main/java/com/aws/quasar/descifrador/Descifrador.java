package com.aws.quasar.descifrador;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.json.JSONObject;

import java.io.IOException;


public class Descifrador {

    private String respuesta;

    public String descifrarCompleto(String input){
        Gson gson=new Gson();
        Mensaje data = gson.fromJson(input,Mensaje.class);
        validarSatelite(data.getSatelites()[0]);
        return "Hola";
    }

    public String descifrarIndividual(String input){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
        MensajeSatelite data = objectMapper.readValue(input, MensajeSatelite.class);
        //mapStr = objectMapper.readValue(input,data);

            validarSatelite(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return respuesta;
    }

    private void agregarSatelite(MensajeSatelite satelite) {

    }

    private void validarSatelite(MensajeSatelite satelite){
        String position=new Satelite().triangularPosicion(satelite);
        String message=new Satelite().completarMensaje(satelite);
        if(position!=null && message!=null){
            JSONObject respuestaObject=new JSONObject();
            respuestaObject.put("position",position);
            respuestaObject.put("message",message);
            respuesta=respuestaObject.toString();
        }
    }


}
