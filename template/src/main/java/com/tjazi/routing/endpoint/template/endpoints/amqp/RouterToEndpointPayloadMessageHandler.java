package com.tjazi.routing.endpoint.template.endpoints.amqp;

import com.tjazi.routing.endpoint.messages.RouterToEndpointPayloadMessage;
import com.tjazi.routing.endpoint.template.coreinterfaces.ICoreImplementation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Krzysztof Wasiak on 29/03/2016.
 */
public class RouterToEndpointPayloadMessageHandler {

    @Autowired
    private ICoreImplementation coreImplementation;

    private static final Logger log = LoggerFactory.getLogger(RouterToEndpointPayloadMessageHandler.class);

    public void handleMessage(RouterToEndpointPayloadMessage message) {

        // if message is broken then don't do too much noise about it
        // that won't be an exception here, more like error log
        if (!this.assertInputMessage(message)) {
            return;
        }

        // pass the message to the actual implementation
        coreImplementation.passPayloadToClients(message.getClientAddresses(), message.getPayload());
    }

    private boolean assertInputMessage(RouterToEndpointPayloadMessage message) {

        String error = null;

        if (message == null) {
            error = "Message is null";
        }

        if (message.getClientAddresses() == null || message.getClientAddresses().size() == 0) {
            error = "List if client addresses is null or empty";
        }

        if (message.getPayload() == null) {
            error = "Payload is null";
        }

        if (error != null) {
            log.error(error);

            return false;
        }

        return true;
    }
}
