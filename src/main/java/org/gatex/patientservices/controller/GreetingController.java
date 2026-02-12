package org.gatex.patientservices.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class GreetingController {

    @GetMapping("/api/patient/greeting")
    public Map<String, String> greeting(
            @RequestHeader("X-User-Id") String userId,
            @RequestHeader("X-User-Role") String role
    ) {

        return Map.of(
                "message", "Hello User " + userId,
                "role", role
        );
    }
}
