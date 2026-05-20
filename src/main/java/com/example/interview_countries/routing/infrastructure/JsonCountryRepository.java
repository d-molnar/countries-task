package com.example.interview_countries.routing.infrastructure;

import com.example.interview_countries.routing.Country;
import com.example.interview_countries.routing.CountryRepository;
import com.fasterxml.jackson.annotation.JsonProperty;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Loads and caches country border data from {@code data/borders.json} on the classpath at startup.
 * A private {@code Entry} DTO isolates the JSON field name ({@code cca3}) from the domain model.
 */
@Repository
public class JsonCountryRepository implements CountryRepository {

    private static final String RESOURCE_PATH = "data/borders.json";

    private final ObjectMapper objectMapper;
    private Map<String, Country> countries;

    public JsonCountryRepository(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    void load() {
        ClassPathResource resource = new ClassPathResource(RESOURCE_PATH);
        try (InputStream in = resource.getInputStream()) {
            List<Entry> entries = objectMapper.readValue(in, new TypeReference<List<Entry>>() {});
            Map<String, Country> result = new HashMap<>(entries.size() * 2);
            for (Entry entry : entries) {
                Set<String> borders = entry.borders() == null ? Set.of() : Set.copyOf(entry.borders());
                result.put(entry.code(), new Country(entry.code(), borders));
            }
            countries = Collections.unmodifiableMap(result);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load " + RESOURCE_PATH, e);
        }
    }

    @Override
    public Map<String, Country> findAll() {
        return countries;
    }

    private record Entry(@JsonProperty("cca3") String code, @JsonProperty("borders") List<String> borders) {}
}
