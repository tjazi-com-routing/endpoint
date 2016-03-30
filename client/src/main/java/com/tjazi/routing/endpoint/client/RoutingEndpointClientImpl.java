package com.tjazi.routing.endpoint.client;

import com.tjazi.routing.endpoint.messages.RouterToEndpointPayloadMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

/**
 * Created by Krzysztof Wasiak on 30/03/2016.
 */
public class RoutingEndpointClientImpl implements RoutingEndpointClient {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${endpoint.queue.name.prefix}")
    private String endpointQueueNamePrefix;

    private final static Logger log = LoggerFactory.getLogger(RoutingEndpointClientImpl.class);

    public void sendMessagePayloadToEndpointClients(String endpointId, List<String> clientAddresses, String messagePayload) {

        this.assertInputParams(endpointId, clientAddresses, messagePayload);

        // send the message
        RouterToEndpointPayloadMessage payloadMessage = new RouterToEndpointPayloadMessage();
        payloadMessage.setPayload(messagePayload);
        payloadMessage.setClientAddresses(clientAddresses);

        String targetQueueName = endpointQueueNamePrefix + endpointId;

        rabbitTemplate.convertAndSend(targetQueueName, payloadMessage);
    }

    private void assertInputParams(String endpointId, List<String> clientAddresses, String messagePayload) {

        String error = null;

        if (endpointId == null || endpointId.isEmpty()) {
            error = "endpointId is null or empty";
        }

        if (clientAddresses == null || clientAddresses.isEmpty()) {
            error = "clientAddresses is null or empty";
        }

        if (messagePayload == null || messagePayload.isEmpty()) {
            error = "messagePayload is null or empty";
        }

        if (error != null) {
            log.error(error);
            throw new IllegalArgumentException(error);
        }
    }
}
