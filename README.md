# Trace Id issue reproducer 

This is a reproducer for the trace Id issue when using dynamically bound destination for Spring Cloud Streams.

The application has the following characteristics :
- there is one message producer sending messages to a statically bound destination. See method `Application#sendMessageToStaticDestination()`
- there is one message producer sending messages to a dynamically bound destination. See method  `Application#sendMessageToDynamicDestination()`
- there is one message consumer (which is consuming messages from both producers). See method `ConsumerHandler#receiveMessage(...)`
- each producer is logging the sent message
- the consumer is logging the received message


## Issue
We can observe the following:
- Consumer's logs have the same trace id as the producer's ones when using statically bound destination:
```
2018-01-26 15:42:17.407  INFO [TraceId reproducer,c0245aff03f0c6f7,c0245aff03f0c6f7,false] 10164 --- [ask-scheduler-2] ication$$EnhancerBySpringCGLIB$$40615be4 : Sending 'static destination' ...
2018-01-26 15:42:17.412  INFO [TraceId reproducer,c0245aff03f0c6f7,016a528095dbc8b4,false] 10164 --- [CyBIOM3iE2FOw-1] com.example.ConsumerHandler              : Received 'static destination'!
```
- Consumer's logs have different trace id when using dynamically bound destination:
```
2018-01-26 15:42:27.400  INFO [TraceId reproducer,8f1896c9706621f6,8f1896c9706621f6,false] 10164 --- [ask-scheduler-1] ication$$EnhancerBySpringCGLIB$$40615be4 : Sending 'dynamic destination' ...
2018-01-26 15:42:27.403  INFO [TraceId reproducer,277b83502001a412,277b83502001a412,false] 10164 --- [CyBIOM3iE2FOw-1] com.example.ConsumerHandler              : Received 'dynamic destination'!
```

## How to execute it
### Launch the Rabbit MQ
```
cd docker
docker-compose up -d
```

### Launch the Spring Boot Application
- run the class `com.example.Application` from your IDE

*Or* 

- run `mvn spring-boot:run` from `dynamically-bound-destination`


