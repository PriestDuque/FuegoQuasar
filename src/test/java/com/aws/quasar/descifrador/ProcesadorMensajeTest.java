package com.aws.quasar.descifrador;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for ProcesadorMensaje")
public class ProcesadorMensajeTest {

    @Test
    @DisplayName("Posicion y mensaje correcto")
    void testProcesarMensaje_messageylocationOK() throws DescifradorException, ValidationException {
        String input="{\"satelites\":[{\"name\":\"kenobi\",\"distance\":1500,\"message\":[\"este\", \"\", \"\", \"mensaje\", \"\"]},{\"name\":\"skywalker\",\"distance\":1000,\"message\":[\"\", \"es\", \"\", \"\", \"secreto\"]},{\"name\":\"sato\",\"distance\":632.46,\"message\":[\"este\", \"\", \"un\", \"\", \"\"]}]}";

        ProcesadorMensaje descifrador=new ProcesadorMensaje();
        String res=descifrador.procesarMensaje(input);

        Assert.assertEquals("{\"position\":{\"x\":700.0,\"y\":700.0},\"message\":\"este es un mensaje secreto\"}",res);
    }

    @Test
    @DisplayName("Posicion incorrecta loa distancias no dan resultado y mensaje correcto")
    void testProcesarMensaje_messageOKylocationNoEncontrada(){
        String input="{\"satelites\":[{\"name\":\"kenobi\",\"distance\":100,\"message\":[\"este\", \"\", \"\", \"mensaje\", \"\"]},{\"name\":\"skywalker\",\"distance\":115.5,\"message\":[\"\", \"es\", \"\", \"\", \"secreto\"]},{\"name\":\"sato\",\"distance\":142.7,\"message\":[\"este\", \"\", \"un\", \"\", \"\"]}]}";

        ProcesadorMensaje descifrador=new ProcesadorMensaje();

        Exception exception = Assertions.assertThrows(DescifradorException.class,() -> descifrador.procesarMensaje(input));


        String expectedMessage = "Mensaje indescifrable";
        String actualMessage = exception.getMessage();

        Assert.assertTrue(actualMessage.contains(expectedMessage));
    }


    @Test
    @DisplayName("Mensaje incompleto, falta un satelite")
    void testProcesarMensaje_messageIncomplete_sateliteFaltante(){
        String input="{\"satelites\":[{\"name\":\"kenobi\",\"distance\":100.0,\"message\":[\"este\", \"\", \"\", \"mensaje\", \"\"]},{\"name\":\"skywalker\",\"distance\":115.5,\"message\":[\"\", \"es\", \"\", \"\", \"secreto\"]}]}";

        ProcesadorMensaje descifrador=new ProcesadorMensaje();

        Assertions.assertThrows(ValidationException.class,() -> descifrador.procesarMensaje(input));
    }

    @Test
    @DisplayName("Mensaje incompleto aun con tres satelites")
    void testProcesarMensaje_messageIncompleto(){
        String input="{\"satelites\":[{\"name\":\"kenobi\",\"distance\":100.0,\"message\":[\"este\", \"\", \"\", \"mensaje\", \"\"]},{\"name\":\"skywalker\",\"distance\":115.5,\"message\":[\"\", \"es\", \"\", \"\", \"\"]},{\"name\":\"sato\",\"distance\":142.7,\"message\":[\"este\", \"\", \"un\", \"\", \"\"]}]}";

        ProcesadorMensaje descifrador=new ProcesadorMensaje();

        Exception exception = Assertions.assertThrows(DescifradorException.class,() -> descifrador.procesarMensaje(input));

        String expectedMessage = "Mensaje indescifrable";
        String actualMessage = exception.getMessage();

        Assert.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @DisplayName("Mensjae con formato erroneo un satelite con una posicion de mas")
    void testProcesarMensaje_messageFormatoIncorrecto(){
        String input="{\"satelites\":[{\"name\":\"kenobi\",\"distance\":100.0,\"message\":[\"este\", \"\", \"\", \"mensaje\", \"\"]},{\"name\":\"skywalker\",\"distance\":115.5,\"message\":[\"\", \"es\", \"\", \"\", \"\",\"secreto\"]},{\"name\":\"sato\",\"distance\":142.7,\"message\":[\"este\", \"\", \"un\", \"\", \"\"]}]}";

        ProcesadorMensaje descifrador=new ProcesadorMensaje();

        Exception exception = Assertions.assertThrows(ValidationException.class,() -> descifrador.procesarMensaje(input));

        String expectedMessage = "Index";
        String actualMessage = exception.getMessage();

        Assert.assertTrue(actualMessage.contains(expectedMessage));
    }


}
