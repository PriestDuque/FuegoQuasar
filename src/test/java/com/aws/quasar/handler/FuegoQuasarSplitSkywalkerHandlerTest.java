package com.aws.quasar.handler;

import com.amazonaws.serverless.proxy.internal.testutils.MockLambdaContext;
import com.aws.quasar.descifrador.DescifradorException;
import com.aws.quasar.util.ArchivoUtil;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for {@link FuegoQuasarSplitSkywalkerHandler}.
 */
@DisplayName("Tests for FuegoQuasarSplitSkywalkerHandler")
class FuegoQuasarSplitSkywalkerHandlerTest {

    private static final String EXPECTED_CONTENT_TYPE = "application/json";
    private static final int EXPECTED_STATUS_CODE_SUCCESS = 200;

    private final MockLambdaContext mockLambdaContext = new MockLambdaContext();

    @Test
    @DisplayName("El mensaje que ingresa se guarda en un archivo llamado skywalker")
    void testHandleRequestCorrecto() throws DescifradorException {
        String input="{\"distance\":1000,\"message\":[\"\", \"es\", \"\", \"\", \"secreto\"]}";
        GatewayResponse response = (GatewayResponse) new FuegoQuasarSplitSkywalkerHandler().handleRequest(input, mockLambdaContext);

        // Verify the response obtained matches the values we expect.
        JSONObject jsonObjectFromResponse = new JSONObject(response.getBody());

        String skywalker=new ArchivoUtil().leer("skywalker");

        assertEquals("OK", jsonObjectFromResponse.get("Output"));
        assertEquals(EXPECTED_CONTENT_TYPE, response.getHeaders().get("Content-Type"));
        assertEquals(EXPECTED_STATUS_CODE_SUCCESS, response.getStatusCode());
        assertEquals("{\"name\":\"skywalker\",\"distance\":1000.0,\"message\":[\"\",\"es\",\"\",\"\",\"secreto\"]}", skywalker);
    }

}
