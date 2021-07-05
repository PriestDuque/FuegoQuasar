package com.aws.quasar.handler;

import com.amazonaws.serverless.proxy.internal.testutils.MockLambdaContext;
import com.aws.quasar.descifrador.MensajeSatelite;
import com.google.gson.Gson;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for {@link FuegoQuasarSplitGetHandler}.
 */
@DisplayName("Tests for FuegoQuasarSplitGetHandler")
class FuegoQuasarSplitGetHandlerTest {

    private static final String EXPECTED_CONTENT_TYPE = "application/json";
    private static final int EXPECTED_STATUS_CODE_SUCCESS = 200;
    private static final int EXPECTED_STATUS_CODE_INDESCIFRABLE = 404;

    private final MockLambdaContext mockLambdaContext = new MockLambdaContext();

    private Gson gson=new Gson();

    @Test
    @DisplayName("Se obtiene el resultado de los tres satelites almacenado en los archivos")
    void testHandleRequestCorrecto() {
        String input="{\"distance\":1500,\"message\":[\"este\",\"\",\"\", \"mensaje\",\"\"]}";
        new FuegoQuasarSplitKenobiHandler().handleRequest(gson.fromJson(input, MensajeSatelite.class), mockLambdaContext);
        input="{\"distance\":632.46,\"message\":[\"este\", \"\", \"un\", \"\", \"\"]}";
        new FuegoQuasarSplitSatoHandler().handleRequest(input, mockLambdaContext);
        input="{\"distance\":1000,\"message\":[\"\", \"es\", \"\", \"\", \"secreto\"]}";
        new FuegoQuasarSplitSkywalkerHandler().handleRequest(input, mockLambdaContext);

        GatewayResponse response = (GatewayResponse) new FuegoQuasarSplitGetHandler().handleRequest(null, mockLambdaContext);

        // Verify the response obtained matches the values we expect.
        JSONObject jsonObjectFromResponse = new JSONObject(response.getBody());

        assertEquals("{\"position\":{\"x\":700.0,\"y\":700.0},\"message\":\"este es un mensaje secreto\"}", jsonObjectFromResponse.get("Output"));
        assertEquals(EXPECTED_CONTENT_TYPE, response.getHeaders().get("Content-Type"));
        assertEquals(EXPECTED_STATUS_CODE_SUCCESS, response.getStatusCode());
    }

    @Test
    @DisplayName("Se obtiene el resultado de los tres satelites almacenado en los archivos")
    void testHandleRequestIncorrecto() {
        String input="{\"distance\":100,\"message\":[\"este\",\"\",\"\", \"mensaje\",\"\"]}";
        new FuegoQuasarSplitKenobiHandler().handleRequest(gson.fromJson(input, MensajeSatelite.class), mockLambdaContext);
        input="{\"distance\":142.7,\"message\":[\"este\", \"\", \"un\", \"\", \"\"]}";
        new FuegoQuasarSplitSatoHandler().handleRequest(input, mockLambdaContext);
        input="{\"distance\":115.5,\"message\":[\"\", \"es\", \"\", \"\", \"secreto\"]}";
        new FuegoQuasarSplitSkywalkerHandler().handleRequest(input, mockLambdaContext);

        GatewayResponse response = (GatewayResponse) new FuegoQuasarSplitGetHandler().handleRequest(null, mockLambdaContext);

        // Verify the response obtained matches the values we expect.
        JSONObject jsonObjectFromResponse = new JSONObject(response.getBody());

        assertEquals("Mensaje indescifrable", jsonObjectFromResponse.get("Output"));
        assertEquals(EXPECTED_CONTENT_TYPE, response.getHeaders().get("Content-Type"));
        assertEquals(EXPECTED_STATUS_CODE_INDESCIFRABLE, response.getStatusCode());
    }

}
