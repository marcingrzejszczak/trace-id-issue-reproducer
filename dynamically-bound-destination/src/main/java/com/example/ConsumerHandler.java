package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Component;

@Component
public class ConsumerHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @StreamListener(Sink.INPUT)
    public void receiveMessage(String messageContent) {
        logger.info("Received '" + messageContent + "'!\n");
    }

}
