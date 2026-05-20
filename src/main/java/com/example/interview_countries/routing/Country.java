package com.example.interview_countries.routing;

import java.util.Set;

/**
 * Domain record representing a country. {@code borders} is defensively copied to an immutable set
 * in the canonical constructor.
 */
public record Country(String code, Set<String> borders) {
    public Country {
        borders = Set.copyOf(borders);
    }
}
