package com.aws.quasar.descifrador;

/**
 * Se encarga de procesar el mensaje de entrada y llamar al Descifrador para que nos de la posicion y mensaje que
 * se creara como respuesta
 */
public class ProcesadorMensaje {

    public Respuesta procesarMensaje(Mensaje input) throws DescifradorException, ValidationException {
        Posicion posicion;
        String mensaje;
        try {
            Descifrador descifrador=new Descifrador();
            MensajeSatelite kenobi=input.getSatelites().get(0);
            MensajeSatelite skywalker=input.getSatelites().get(1);
            MensajeSatelite sato=input.getSatelites().get(2);
            String[][] mensajes=new String[][]{kenobi.getMessage(),skywalker.getMessage(),sato.getMessage()};
            posicion=descifrador.getLocation(kenobi.getDistance(),skywalker.getDistance(),sato.getDistance());
            mensaje=descifrador.getMessage(mensajes);
        }catch (Exception e){
            throw new ValidationException(e.getMessage(),e);
        }

        if(posicion!=null && mensaje!=null){
           return new Respuesta(posicion,mensaje);
        }else{
            throw new DescifradorException("Mensaje indescifrable");
        }
    }

}
