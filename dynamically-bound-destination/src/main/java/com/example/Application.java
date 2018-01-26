package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.binding.BinderAwareChannelResolver;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
@EnableBinding(Processor.class)
public class Application {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private final BinderAwareChannelResolver resolver;

    private final MessageChannel output;

    public Application(BinderAwareChannelResolver resolver,
                       MessageChannel output) {
        this.resolver = resolver;
        this.output = output;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class,
                              args);
    }

    @Scheduled(fixedRate = 15000, initialDelay = 5000)
    void sendMessageToDynamicDestination(){
        String messageContent = "dynamic destination";
        logger.info("Sending '" + messageContent + "' ...");
        Message<String> message = MessageBuilder.withPayload(messageContent).build();
        resolver.resolveDestination("notifications").send(message);
    }

    @Scheduled(fixedRate = 15000, initialDelay = 10000)
    void sendMessageToStaticDestination(){
        String messageContent = "static destination";
        logger.info("Sending '" + messageContent + "' ...");
        Message<String> message = MessageBuilder.withPayload(messageContent).build();
        output.send(message);
    }

}
