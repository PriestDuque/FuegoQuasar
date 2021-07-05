package com.aws.quasar.handler;

import com.amazonaws.serverless.proxy.internal.testutils.MockLambdaContext;
import com.aws.quasar.descifrador.DescifradorException;
import com.aws.quasar.descifrador.MensajeSatelite;
import com.aws.quasar.util.ArchivoUtil;
import com.google.gson.Gson;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for {@link FuegoQuasarSplitKenobiHandler}.
 */
@DisplayName("Tests for FuegoQuasarSplitKenobiHandler")
class FuegoQuasarSplitKenobiHandlerTest {

    private static final String EXPECTED_CONTENT_TYPE = "application/json";
    private static final int EXPECTED_STATUS_CODE_SUCCESS = 200;

    private final MockLambdaContext mockLambdaContext = new MockLambdaContext();

    private Gson gson=new Gson();

    @Test
    @DisplayName("El mensaje que ingresa se guarda en un archivo llamado kenobi")
    void testHandleRequestCorrecto() throws DescifradorException {
        String input="{\"distance\":1500,\"message\":[\"este\",\"\",\"\", \"mensaje\",\"\"]}";
        GatewayResponse response = (GatewayResponse) new FuegoQuasarSplitKenobiHandler().handleRequest(gson.fromJson(input, MensajeSatelite.class), mockLambdaContext);

        // Verify the response obtained matches the values we expect.
        JSONObject jsonObjectFromResponse = new JSONObject(response.getBody());

        String kenobi=new ArchivoUtil().leer("kenobi");

        assertEquals("OK", jsonObjectFromResponse.get("Output"));
        assertEquals(EXPECTED_CONTENT_TYPE, response.getHeaders().get("Content-Type"));
        assertEquals(EXPECTED_STATUS_CODE_SUCCESS, response.getStatusCode());

        assertEquals("{\"name\":\"kenobi\",\"distance\":1500.0,\"message\":[\"este\",\"\",\"\",\"mensaje\",\"\"]}", kenobi);
    }

}
