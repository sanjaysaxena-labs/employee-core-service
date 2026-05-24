package com.sanjaysaxena_labs.employee_core_service.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter; // The modern non-deprecated import
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "employee.company.exchange";
    public static final String QUEUE_NAME="employee.company.queue";
    public static final String ROUTING_KEY="employee.company.onboard";


    //Declare an Exchange to receive incoming events from services

    @Bean
    public TopicExchange employeeExchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    //Declare a persistent Queue to hold events safely until processed
    @Bean
    public Queue onboardingQueue(){
        return new Queue(QUEUE_NAME, true) ;
    }

    //Bind the queue to the exchange using our explicit routing key pattern
    @Bean
    public Binding binding(Queue onboardingQueue, TopicExchange employeeExchange){
        return BindingBuilder.bind(onboardingQueue).to(employeeExchange).with(ROUTING_KEY);
    }

    // Modern Spring Boot 4 / Spring 7 pattern to enforce clean JSON string values
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new JacksonJsonMessageConverter();
    }
}
