package com.aws.quasar.handler;

import com.amazonaws.serverless.proxy.internal.testutils.MockLambdaContext;
import com.aws.quasar.descifrador.DescifradorException;
import com.aws.quasar.util.ArchivoUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for {@link FuegoQuasarSplitSkywalkerHandler}.
 */
@DisplayName("Tests for FuegoQuasarSplitSkywalkerHandler")
class FuegoQuasarSplitSkywalkerHandlerTest {

    private final MockLambdaContext mockLambdaContext = new MockLambdaContext();

    @Test
    @DisplayName("El mensaje que ingresa se guarda en un archivo llamado skywalker")
    void testHandleRequestCorrecto() throws DescifradorException {
        String input="{\"distance\":1000,\"message\":[\"\", \"es\", \"\", \"\", \"secreto\"]}";
        Object response = new FuegoQuasarSplitSkywalkerHandler().handleRequest(input, mockLambdaContext);


        String skywalker=new ArchivoUtil().leer("skywalker");

        assertEquals("200 OK", response);
        assertEquals("{\"name\":\"skywalker\",\"distance\":1000.0,\"message\":[\"\",\"es\",\"\",\"\",\"secreto\"]}", skywalker);
    }

}
