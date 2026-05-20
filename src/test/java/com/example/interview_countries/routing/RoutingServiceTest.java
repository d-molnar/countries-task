package com.example.interview_countries.routing;

import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RoutingServiceTest {

    private static CountryRepository repositoryOf(Map<String, Country> countries) {
        return () -> countries;
    }

    private static Map<String, Country> sampleGraph() {
        Map<String, Country> map = new LinkedHashMap<>();
        map.put("CZE", new Country("CZE", Set.of("AUT", "POL")));
        map.put("AUT", new Country("AUT", Set.of("CZE", "ITA")));
        map.put("ITA", new Country("ITA", Set.of("AUT")));
        map.put("POL", new Country("POL", Set.of("CZE")));
        map.put("ISL", new Country("ISL", Set.of()));
        return map;
    }

    @Test
    void findsRouteBetweenLandConnectedCountries() {
        RoutingService service = new RoutingService(repositoryOf(sampleGraph()));

        List<String> route = service.findRoute("CZE", "ITA");

        assertTrue(route.contains("CZE"));
        assertTrue(route.contains("ITA"));
    }

    @Test
    void returnsNullWhenNoRouteExists() {
        RoutingService service = new RoutingService(repositoryOf(sampleGraph()));

        assertNull(service.findRoute("ISL", "ITA"));
    }
}
