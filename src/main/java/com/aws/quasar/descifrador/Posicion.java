package com.aws.quasar.descifrador;

/**
 * DTO para manejar un punto con cordenadas X,Y
 */
public class Posicion {

    /**Coordena X del punto*/
    private double x;
    /**Coordena Y del punto*/
    private double y;

    public Posicion(double x, double y){
        this.setX(x);
        this.setY(y);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
