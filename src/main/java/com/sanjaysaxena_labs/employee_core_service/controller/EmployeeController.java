package com.sanjaysaxena_labs.employee_core_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {
    @GetMapping("/smoke-test")
    public Map<String, String> smokeTest() {
        return Map.of(
                "status", "UP",
                "message", "Greetings from the Employee Core Microservice Domain Engine!"
        );
    }
}
