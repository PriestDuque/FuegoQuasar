package com.aws.quasar.handler;

import com.amazonaws.serverless.proxy.internal.testutils.MockLambdaContext;

import com.aws.quasar.GatewayResponse;

import com.aws.quasar.descifrador.Mensaje;
import com.aws.quasar.descifrador.MensajeSatelite;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for {@link FuegoQuasarHandler}. Modify the tests in order to support your use case as you build your project.
 */
@DisplayName("Tests for FuegoQuasarHandler")
public class FuegoQuasarHandlerTest {

    private static final String EXPECTED_CONTENT_TYPE = "application/json";
    private static final String EXPECTED_RESPONSE_VALUE = "Hola";
    private static final int EXPECTED_STATUS_CODE_SUCCESS = 200;

    // A mock class for com.amazonaws.services.lambda.runtime.Context
    private final MockLambdaContext mockLambdaContext = new MockLambdaContext();
    private final Object input = new Object();

    /**
     * Initializing variables before we run the tests.
     * Use @BeforeAll for initializing static variables at the start of the test class execution.
     * Use @BeforeEach for initializing variables before each test is run.
     */
    @BeforeAll
    static void setup() {
        // Use as needed.
    }

    /**
     * De-initializing variables after we run the tests.
     * Use @AfterAll for de-initializing static variables at the end of the test class execution.
     * Use @AfterEach for de-initializing variables at the end of each test.
     */
    @AfterAll
    static void tearDown() {
        // Use as needed.
    }

    /**
     * Basic test to verify the result obtained when calling {@link FuegoQuasarHandler} successfully.
     */
    @Test
    @DisplayName("Basic test for request handler")
    void testHandleRequest() {
        String input="{\"satelites\":[{\"name\":\"kenobi\",\"distance\":100.0,\"message\":[\"este\", \"\", \"\", \"mensaje\", \"\"]},{\"name\":\"skywalker\",\"distance\":115.5,\"message\":[\"\", \"es\", \"\", \"\", \"secreto\"]},{\"name\":\"sato\",\"distance\":142.7,\"message\":[\"este\", \"\", \"un\", \"\", \"\"]}]}";
        GatewayResponse response = (GatewayResponse) new FuegoQuasarHandler().handleRequest(input, mockLambdaContext);

        // Verify the response obtained matches the values we expect.
        JSONObject jsonObjectFromResponse = new JSONObject(response.getBody());
        assertEquals(EXPECTED_RESPONSE_VALUE, jsonObjectFromResponse.get("Output"));
        assertEquals(EXPECTED_CONTENT_TYPE, response.getHeaders().get("Content-Type"));
        assertEquals(EXPECTED_STATUS_CODE_SUCCESS, response.getStatusCode());
    }
}