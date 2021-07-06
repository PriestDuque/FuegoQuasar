package com.aws.quasar.handler;

import com.amazonaws.serverless.proxy.internal.testutils.MockLambdaContext;
import com.aws.quasar.descifrador.DescifradorException;
import com.aws.quasar.util.ArchivoUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for {@link FuegoQuasarSplitSatoHandler}.
 */
@DisplayName("Tests for FuegoQuasarSplitSatoHandler")
class FuegoQuasarSplitSatoHandlerTest {

    private final MockLambdaContext mockLambdaContext = new MockLambdaContext();

    @Test
    @DisplayName("El mensaje que ingresa se guarda en un archivo llamado sato")
    void testHandleRequestCorrecto()throws DescifradorException {
        String input="{\"distance\":632.46,\"message\":[\"este\", \"\", \"un\", \"\", \"\"]}";
        Object response = new FuegoQuasarSplitSatoHandler().handleRequest(input, mockLambdaContext);


        String sato=new ArchivoUtil().leer("sato");

        assertEquals("200 OK", response);
        assertEquals("{\"name\":\"sato\",\"distance\":632.46,\"message\":[\"este\",\"\",\"un\",\"\",\"\"]}", sato);
    }

}
