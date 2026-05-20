package com.example.interview_countries.routing;

import org.springframework.stereotype.Service;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Domain service that finds a land route between two countries using BFS over the country border
 * graph. Throws {@code 400} if either country is unknown or no land route exists.
 */
@Service
public class RoutingService {

    private final CountryRepository countryRepository;

    public RoutingService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public List<String> findRoute(String origin, String destination) {
        return bfs(countryRepository.findAll(), origin, destination);
    }

    private static List<String> bfs(Map<String, Country> countries, String origin, String destination) {
        Country start = countries.get(origin);
        Country end = countries.get(destination);
        if (start == null || end == null) {
            return null;
        }
        if (origin.equals(destination)) {
            return List.of(origin);
        }

        Set<String> visited = new HashSet<>();
        Map<String, String> parents = new HashMap<>();
        Deque<Country> queue = new ArrayDeque<>();

        visited.add(start.code());
        queue.add(start);

        while (!queue.isEmpty()) {
            Country current = queue.poll();
            for (String neighborCode : current.borders()) {
                if (!visited.add(neighborCode)) {
                    continue;
                }
                parents.put(neighborCode, current.code());
                if (neighborCode.equals(end.code())) {
                    return reconstruct(parents, origin, destination);
                }
                Country neighbor = countries.get(neighborCode);
                if (neighbor != null) {
                    queue.add(neighbor);
                }
            }
        }
        return null;
    }

    private static List<String> reconstruct(Map<String, String> parents, String origin, String destination) {
        List<String> path = new ArrayList<>();
        String cursor = destination;
        while (cursor != null) {
            path.add(cursor);
            if (cursor.equals(origin)) {
                break;
            }
            cursor = parents.get(cursor);
        }
        Collections.reverse(path);
        return List.copyOf(path);
    }
}
