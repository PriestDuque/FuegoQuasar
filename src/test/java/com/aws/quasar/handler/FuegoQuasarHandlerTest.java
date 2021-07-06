package com.aws.quasar.handler;

import com.amazonaws.serverless.proxy.internal.testutils.MockLambdaContext;

import com.aws.quasar.descifrador.DescifradorException;
import com.aws.quasar.descifrador.Mensaje;
import com.google.gson.Gson;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for {@link FuegoQuasarHandler}.
 */
@DisplayName("Tests for FuegoQuasarHandler")
class FuegoQuasarHandlerTest {

    private static final String EXPECTED_CONTENT_TYPE = "application/json";
    private static final String EXPECTED_RESPONSE_VALUE = "{\"position\":{\"x\":700.0,\"y\":700.0},\"message\":\"este es un mensaje secreto\"}";
    private static final int EXPECTED_STATUS_CODE_SUCCESS = 200;
    private static final String EXPECTED_ERROR_RESPONSE_VALUE = "invalid input";
    private static final String EXPECTED_INDESCIFRABLE_RESPONSE_VALUE = "Mensaje indescifrable";
    private static final int EXPECTED_STATUS_CODE_ERROR = 403;
    private static final int EXPECTED_STATUS_CODE_INDESCIFRABLE = 404;

    private final MockLambdaContext mockLambdaContext = new MockLambdaContext();

    private Gson gson=new Gson();

    /**
     * Basic test to verify the result obtained when calling {@link FuegoQuasarHandler} successfully.
     */
    @Test
    @DisplayName("El mensaje que ingresa es correcto y tiene solucion")
    void testHandleRequestCorrecto() {
        String input="{\"satelites\":[{\"name\":\"kenobi\",\"distance\":1500,\"message\":[\"este\", \"\", \"\", \"mensaje\", \"\"]},{\"name\":\"skywalker\",\"distance\":1000,\"message\":[\"\", \"es\", \"\", \"\", \"secreto\"]},{\"name\":\"sato\",\"distance\":632.46,\"message\":[\"este\", \"\", \"un\", \"\", \"\"]}]}";
        Object res= new FuegoQuasarHandler().handleRequest(gson.fromJson(input,Mensaje.class), mockLambdaContext);

        assertEquals(EXPECTED_RESPONSE_VALUE, res);
    }


    /**
     * Basic test to verify the result obtained when calling {@link FuegoQuasarHandler} successfully.
     */
    @Test
    @DisplayName("El mensaje que ingresa es correcto pero no tiene solucion")
    void testHandleRequestFormatoCorrectoSinSolucion() {
        String input="{\"satelites\":[{\"name\":\"kenobi\",\"distance\":100.0,\"message\":[\"este\", \"\", \"\", \"mensaje\", \"\"]},{\"name\":\"skywalker\",\"distance\":115.5,\"message\":[\"\", \"es\", \"\", \"\", \"secreto\"]},{\"name\":\"sato\",\"distance\":142.7,\"message\":[\"este\", \"\", \"un\", \"\", \"\"]}]}";

        Exception exception = Assertions.assertThrows(RuntimeException.class,() -> new FuegoQuasarHandler().handleRequest(gson.fromJson(input,Mensaje.class), mockLambdaContext));
        // Verify the response obtained matches the values we expect.
        String expectedMessage = "404 Mensaje indescifrable";
        String actualMessage = exception.getMessage();

        Assert.assertEquals(expectedMessage,actualMessage);
    }

    /**
     * Basic test to verify the result obtained when calling {@link FuegoQuasarHandler} successfully.
     */
    @Test
    @DisplayName("El mensaje que ingresa es nulo")
    void testHandleRequestNull() {
        String input=null;
        Exception exception = Assertions.assertThrows(RuntimeException.class,() -> new FuegoQuasarHandler().handleRequest(gson.fromJson(input,Mensaje.class), mockLambdaContext));

        // Verify the response obtained matches the values we expect.
        String expectedMessage = "invalid input";
        String actualMessage = exception.getMessage();

        Assert.assertEquals(expectedMessage,actualMessage);
    }

    @Test
    @DisplayName("El mensaje que ingresa esta vacio")
    void testHandleRequestVacio() {
        String input="";
        Exception exception = Assertions.assertThrows(RuntimeException.class,() ->  new FuegoQuasarHandler().handleRequest(gson.fromJson(input,Mensaje.class), mockLambdaContext));

        // Verify the response obtained matches the values we expect.
        String expectedMessage = "invalid input";
        String actualMessage = exception.getMessage();

        Assert.assertEquals(expectedMessage,actualMessage);
    }

    //@Test
    @DisplayName("El mensaje que ingresa esta incompleto")
    void testHandleRequestFormatoInvalido() {
       String input="{\"satelites\":[{\"name\":\"kenobi\",\"distance\":100.0,\"message\":[\"este\", \"\", \"\", \"mensaje\", \"\"]},{";
       Object res =  new FuegoQuasarHandler().handleRequest(gson.fromJson(input, Mensaje.class), mockLambdaContext);

       assertEquals("java.io.EOFException: End of input at line 1 column 93 path $.satelites[1].", res);
    }
}
