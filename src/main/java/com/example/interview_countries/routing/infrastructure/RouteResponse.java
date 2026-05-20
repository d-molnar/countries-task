package com.example.interview_countries.routing.infrastructure;

import java.util.List;

/** JSON response DTO. Serializes to {@code {"route": [...]}}. */
record RouteResponse(List<String> route) {}
