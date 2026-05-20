package com.example.interview_countries.routing;

import java.util.Map;

/**
 * Domain interface for country data access. Returns countries keyed by cca3 code for O(1) lookup
 * during graph traversal.
 */
public interface CountryRepository {
    Map<String, Country> findAll();
}
