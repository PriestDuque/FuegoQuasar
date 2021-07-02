package com.aws.quasar.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.aws.quasar.GatewayResponse;
import com.aws.quasar.descifrador.Descifrador;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FuegoQuasarSplitHandler implements RequestHandler<Object, Object> {

    public Object handleRequest(final Object input, final Context context) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        return new GatewayResponse(new JSONObject().put("Output", new Descifrador().descifrarIndividual(input.toString())).toString(), headers, 200);
    }
}