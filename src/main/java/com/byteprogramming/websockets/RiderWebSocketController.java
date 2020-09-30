package com.byteprogramming.websockets;

import com.byteprogramming.models.Rider;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
public class RiderWebSocketController {

    @MessageMapping("/riderUpdate")
    @SendTo("/topic/riders")
    public Rider send(Rider rider) throws Exception {

        return new Rider(UUID.randomUUID(), "test", 1.1, 1.1);
    }

}
