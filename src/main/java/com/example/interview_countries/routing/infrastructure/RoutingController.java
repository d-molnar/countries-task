package com.example.interview_countries.routing.infrastructure;

import com.example.interview_countries.routing.RoutingService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * HTTP entry point for {@code GET /routing/{origin}/{destination}}.
 * Returns {@code 400} if no land route exists.
 */
@RestController
class RoutingController {

    private final RoutingService routingService;

    RoutingController(RoutingService routingService) {
        this.routingService = routingService;
    }

    @GetMapping("/routing/{origin}/{destination}")
    ResponseEntity<?> route(@PathVariable String origin, @PathVariable String destination) {
        List<String> route = routingService.findRoute(origin.toUpperCase(), destination.toUpperCase());
        if (route == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(new RouteResponse(route));
    }
}
