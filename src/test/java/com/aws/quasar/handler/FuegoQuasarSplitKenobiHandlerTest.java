package com.aws.quasar.handler;

import com.amazonaws.serverless.proxy.internal.testutils.MockLambdaContext;
import com.aws.quasar.descifrador.DescifradorException;
import com.aws.quasar.descifrador.MensajeSatelite;
import com.aws.quasar.util.ArchivoUtil;
import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for {@link FuegoQuasarSplitKenobiHandler}.
 */
@DisplayName("Tests for FuegoQuasarSplitKenobiHandler")
class FuegoQuasarSplitKenobiHandlerTest {

    private final MockLambdaContext mockLambdaContext = new MockLambdaContext();

    private Gson gson=new Gson();

    @Test
    @DisplayName("El mensaje que ingresa se guarda en un archivo llamado kenobi")
    void testHandleRequestCorrecto() throws DescifradorException {
        String input="{\"distance\":1500,\"message\":[\"este\",\"\",\"\", \"mensaje\",\"\"]}";
        Object response = new FuegoQuasarSplitKenobiHandler().handleRequest(gson.fromJson(input, MensajeSatelite.class), mockLambdaContext);

        String kenobi=new ArchivoUtil().leer("kenobi");

        assertEquals("200 OK", response);
        assertEquals("{\"name\":\"kenobi\",\"distance\":1500.0,\"message\":[\"este\",\"\",\"\",\"mensaje\",\"\"]}", kenobi);
    }

}
