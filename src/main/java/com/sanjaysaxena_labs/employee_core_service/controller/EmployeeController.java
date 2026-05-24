package com.sanjaysaxena_labs.employee_core_service.controller;

import com.sanjaysaxena_labs.employee_core_service.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/smoke-test")
    public Map<String, String> smokeTest() {
        return Map.of(
                "status", "UP",
                "message", "Greetings from the Employee Core Microservice Domain Engine!"
        );
    }
    // New API test endpoint to broadcast data packets live into Docker Broker
    @GetMapping("/onboard")
    public Map<String, Object> testOnboardingMessage(
            @RequestParam(defaultValue = "101") String id,
            @RequestParam(defaultValue = "Sanjay Saxena") String name) {

        Map<String, String> employeePayload = Map.of(
                "employeeId", id,
                "employeeName", name,
                "action", "NEW_ONBOARD_PROFILE_CREATED"
        );

        // Broadcast the JSON message packet live into your RabbitMQ container
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE_NAME,
                RabbitMQConfig.ROUTING_KEY,
                employeePayload
        );

        return Map.of(
                "success", true,
                "dispatchedMessage", employeePayload,
                "info", "Event sent to RabbitMQ exchange!"
        );
    }
}
