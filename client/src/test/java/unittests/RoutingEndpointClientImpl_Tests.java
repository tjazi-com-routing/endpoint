package unittests;

import com.tjazi.routing.endpoint.client.RoutingEndpointClientImpl;
import com.tjazi.routing.endpoint.messages.RouterToEndpointPayloadMessage;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by Krzysztof Wasiak on 30/03/2016.
 */

@RunWith(MockitoJUnitRunner.class)
public class RoutingEndpointClientImpl_Tests {

    private final static String queuePrefixName = "QueuePrefix1";;

    @InjectMocks
    public RoutingEndpointClientImpl routingEndpointClient;

    @Mock
    public RabbitTemplate rabbitTemplate;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setup() {
        ReflectionTestUtils.setField(routingEndpointClient, "endpointQueueNamePrefix", queuePrefixName);
    }

    @Test
    public void nullOrEmpty_endpointId_Test() {

        thrown.expect(IllegalArgumentException.class);

        routingEndpointClient.sendMessagePayloadToEndpointClients(null, null, null);
    }

    @Test
    public void nullOrEmpty_clientAddresses_Test() {

        thrown.expect(IllegalArgumentException.class);

        routingEndpointClient.sendMessagePayloadToEndpointClients("sample endpoint ID", null, null);
    }

    @Test
    public void nullOrEmpty_messagePayload_Test() {

        thrown.expect(IllegalArgumentException.class);

        routingEndpointClient.sendMessagePayloadToEndpointClients(
                "sample endpoint ID", Collections.singletonList("sample client address"), null);
    }

    @Test
    public void sendMessageToEndpointQueue_Test() {

        // input parameters
        final String endpointName = "SampleEndpointName1";
        final String expectedQueueName = queuePrefixName + endpointName;
        final List<String> targetClientAddresses = Arrays.asList(new String[] { "client1", "client2"});
        final String samplePayload = "Sample message payload" + UUID.randomUUID().toString();

        routingEndpointClient.sendMessagePayloadToEndpointClients(endpointName, targetClientAddresses, samplePayload);

        ArgumentCaptor<RouterToEndpointPayloadMessage> sendToQueueMessageCaptore = ArgumentCaptor.forClass(RouterToEndpointPayloadMessage.class);

        verify(rabbitTemplate, times(1)).convertAndSend(eq(expectedQueueName), sendToQueueMessageCaptore.capture());

        // verify fields
        RouterToEndpointPayloadMessage capturedValue = sendToQueueMessageCaptore.getValue();

        assertEquals(samplePayload, capturedValue.getPayload());
        assertEquals(targetClientAddresses, capturedValue.getClientAddresses());
    }
}
