package com.aws.quasar.descifrador;

import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Se encarga de descifrar el mensaje y trilaterar la ubicacion de la nave
 */
public class Descifrador {

    /**
     * Recorre los arreglos con palabras para unificar un solo mensaje
     * @param mensajesSatelites arreglo con parte o el mensaje a unificar
     */
    public String getMessage(String[][] mensajesSatelites){
        if(mensajesSatelites.length!=3){
            return null;
        }
        String[] mensajeRespuesta=new String[mensajesSatelites[0].length];
        for(int i=0;i<mensajesSatelites.length;i++){
            if(mensajesSatelites[i]==null || mensajesSatelites[i].length<1){
                return null;
            }
            for(int j=0;j<mensajesSatelites[i].length;j++){
                if (!StringUtils.isEmpty(mensajesSatelites[i][j])) {
                    mensajeRespuesta[j] = mensajesSatelites[i][j];
                }
            }
        }
        boolean mensajeIncompleto = Arrays.stream(mensajeRespuesta).anyMatch(element -> StringUtils.isEmpty(element));
        if(mensajeIncompleto){
            return null;
        }
        return Arrays.stream(mensajeRespuesta).collect(Collectors.joining(" "));
    }

    /**
     * Obtiene la ubicacion de la nave
     * @return Posicion donde se encuentra la nave
     */
    public Posicion getLocation(double distanciaKenobi,double distanciaSkywalker, double distanciaSato) {
        Posicion posicion=calcularTrilateracion(distanciaKenobi,distanciaSkywalker,distanciaSato);

        double x=Math.round(posicion.getX()*100)/100;
        double y=Math.round(posicion.getY()*100)/100;

        boolean res=verificarResultado(x,y,distanciaKenobi,distanciaSkywalker,distanciaSato);
        if(!res){
            return null;
        }
        return new Posicion(Math.round(x*100),y*100);
    }

    /**
     * Calcula la distancia entre los satelites y el punto X,Y que ingresa como parametros
     * Se deja un delta de diferencia por la aproximaciones de decimales
     * La formula para calcular la distancia entre 2 puntos es
     * d= √((X2-X1)¹ + (Y2-Y1)¹)
     * @param x posicion X
     * @param y posicion Y
     * @return si la distancia concuerda
     */
    private boolean verificarResultado(double x,double y,double distancia1, double distancia2, double distancia3){
        double radio1=distancia1/100;
        double radio2=distancia2/100;
        double radio3=distancia3/100;

        double distanciaPA= Math.sqrt(Math.pow(-5-x,2)+Math.pow(-2-y,2));
        double distanciaPB= Math.sqrt(Math.pow(1-x,2)+Math.pow(-1-y,2));
        double distanciaPC= Math.sqrt(Math.pow(5-x,2)+Math.pow(1-y,2));

        return distanciaPA-radio1<1 && distanciaPB-radio2<1 && distanciaPC-radio3<1;
    }

    /**
     * Ejecuta la formula para calcular la trilateracion de la nave
     * Con los satelites kenobi(-5-2) skywalker(1,-1) y sato (5,1)
     * Se conocen tambien las distacion de cada satelita al emisor,
     * estas distancias se renombran como radio1, radio2 y radio3
     * Para estos puntos(satelites) se obtuvo una formula para calcular X
     * y otra para calcular Y las cuales son
     * X= (3a-b)/16
     * Y= (3b -5a)/8
     * Notese que los puntos de los satelites fueron divididos en 100 para
     * facilidad de la formula, por lo cual cuando ingresan las distancias o radios
     * estos se dividen 100 para no afectar el sistema. Y al salir se vuelve a multiplicar por 100
     * para dar la respuesta en la misma notacion.
     * Las variables a y b son datos conocidos pero que solo se obtienen al momento de ingresar las distacias
     * por lo cual se crean tambien 2 formulas para obtener a y b con base en las distacias, las cuales son
     * a= radio1^2 - radio2^2 -27
     * b= radio1^2 - radio3^2 -3
     * @return Posicion X,Y del emisor
     */
    private Posicion calcularTrilateracion(double distancia1, double distancia2, double distancia3){
        double radio1=distancia1/100;
        double radio2=distancia2/100;
        double radio3=distancia3/100;

        double constanteA=(Math.pow(radio1,2)-Math.pow(radio2,2))-27;
        double constanteB=(Math.pow(radio1,2)-Math.pow(radio3,2))-3;

        double posicionX=((3*constanteA)-constanteB)/16;
        double posicionY=((3*constanteB)-(5*constanteA))/8;

        posicionX=Math.round(posicionX*100)/100;
        posicionY=Math.round(posicionY*100)/100;

        return new Posicion(posicionX,posicionY);
    }

}
