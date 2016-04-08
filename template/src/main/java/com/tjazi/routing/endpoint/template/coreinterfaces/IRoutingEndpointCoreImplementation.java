package com.tjazi.routing.endpoint.template.coreinterfaces;

import java.util.List;

/**
 * Created by Krzysztof Wasiak on 29/03/2016.
 *
 * This interface will have to be implemented by the core service
 */
public interface IRoutingEndpointCoreImplementation {

    void passPayloadToClients(List<String> clientAddresses, String payload);
}
