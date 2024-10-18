package com.colak.springtutorial.config;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class RouteService extends RouteBuilder {
    @Override
    public void configure() {

        // first route
        from("timer:mytimer?period=5000")
                .transform(simple("Random number ${random(0,100)}"))
                .to("jms:DEV.QUEUE.1");

        // second route
        from("jms:queue:DEV.QUEUE.1")
                .log("Message Posted in RabbitMq")
                .to("spring-rabbitmq:librayQueue.exchange?queues=librayQueue");

        // third route
        from("spring-rabbitmq:librayQueue.exchange?queues=librayQueue")
                .log("Data from rabbitmq: ${body}");
    }
}
