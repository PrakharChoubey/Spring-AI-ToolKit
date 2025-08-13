package com.springai.spring_ai_functions.model;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonClassDescription("Weather API Request")
public record WeatherRequest(
        @JsonProperty(required = true, value = "location") @JsonPropertyDescription("The city name e.g. London") String location,
        @JsonProperty(required = false) @JsonPropertyDescription("latitude of the city") String lat,
        @JsonProperty(required = false) @JsonPropertyDescription("longitude of the city") String lon) {
}

// api by city - paywall error - 400
//public record WeatherRequest(
//        @JsonProperty(required = true, value = "location") @JsonPropertyDescription("The city and two-character-state e.g. San Francisco, CA") String location,
//        @JsonProperty(required = false) @JsonPropertyDescription("Optional State for US Cities Only. Use full names of state") String state,
//        @JsonProperty(required = false) @JsonPropertyDescription("Optional country name") String country) {
//}
