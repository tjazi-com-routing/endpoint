package com.tjazi.routing.endpoint.client;

import java.util.List;

/**
 * Created by Krzysztof Wasiak on 30/03/2016.
 */
public interface RoutingEndpointClient {

    void sendMessagePayloadToEndpointClients(String endpointId, List<String> clientAddresses, String messagePayload);
}
