package com.aws.quasar.descifrador;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Descifrador {

    private String respuesta;

    public String descifrarCompleto(String input){
       /* ObjectMapper objectMapper = new ObjectMapper();
        Map<String, List> mapStr;

        //read json file and convert to customer object
        try {
            Map<String,List< Object>> data = objectMapper.readValue(input, new TypeReference<Map<String,List<Object>>>(){});
            //mapStr = objectMapper.readValue(input,data);

            validarSatelite((Map)data.get("satelites").get(0));
        } catch (IOException e) {
            e.printStackTrace();
        }

*/


        return "Hola";
    }

    public String descifrarIndividual(String input){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
        MensajeSatelite data = objectMapper.readValue(input, MensajeSatelite.class);
        //mapStr = objectMapper.readValue(input,data);

            validarSatelite((Map)data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return respuesta;
    }

    private void agregarSatelite(MensajeSatelite satelite) {

    }

    private void validarSatelite(Map object){
        MensajeSatelite satelite=new MensajeSatelite(object);
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
