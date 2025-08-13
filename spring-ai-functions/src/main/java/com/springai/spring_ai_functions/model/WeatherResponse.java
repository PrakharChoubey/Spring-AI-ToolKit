package com.springai.spring_ai_functions.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import java.math.BigDecimal;

public record WeatherResponse(
        @JsonPropertyDescription("Wind Speed in KMH") @JsonProperty("wind_speed") BigDecimal windSpeed,
        @JsonPropertyDescription("Direction of wind") @JsonProperty("wind_degrees") Integer windDegrees,
        @JsonPropertyDescription("Current Temperature in celsius") @JsonProperty("temp") Integer temp,
        @JsonPropertyDescription("Current Humidity") @JsonProperty("humidity") Integer humidity,
        @JsonPropertyDescription("Epoch time of sunset GMT") @JsonProperty("sunset") Integer sunset,
        @JsonPropertyDescription("Low Temperature in celsius") @JsonProperty("min_temp") Integer minTemp,
        @JsonPropertyDescription("Cloud coverage percentage") @JsonProperty("cloud_pct") Integer cloudPct,
        @JsonPropertyDescription("Temperature in celsius") @JsonProperty("feels_like") Integer feelsLike,
        @JsonPropertyDescription("Epoch time of sunrise GMT") @JsonProperty("sunrise") Integer sunrise,
        @JsonPropertyDescription("Maximum Temperature in celsius") @JsonProperty("max_temp") Integer maxTemp
) {
}
