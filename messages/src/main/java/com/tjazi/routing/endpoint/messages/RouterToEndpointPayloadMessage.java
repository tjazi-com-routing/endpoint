package com.tjazi.routing.endpoint.messages;

import java.util.List;

/**
 * Created by Krzysztof Wasiak on 29/03/2016.
 *
 * Message sent from router to endpoints
 */
public class RouterToEndpointPayloadMessage {

    /**
     * List of client addresses within endpoint, which should get the message
     */
    private List<String> clientAddresses;

    /**
     * Actual message to be sent to clients
     */
    private String payload;

    public List<String> getClientAddresses() {
        return clientAddresses;
    }

    public void setClientAddresses(List<String> clientAddresses) {
        this.clientAddresses = clientAddresses;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }
}
