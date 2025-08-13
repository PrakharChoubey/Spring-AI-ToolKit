package com.springai.spring_ai_functions.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record CityDetails(
        @JsonProperty("place_id") long placeId,
        String licence,
        @JsonProperty("osm_type") String osmType,
        @JsonProperty("osm_id") long osmId,
        String lat,
        String lon,
        @JsonProperty("class") String clazz,
        String type,
        @JsonProperty("place_rank") int placeRank,
        double importance,
        String addresstype,
        String name,
        @JsonProperty("display_name") String displayName,
        List<String> boundingbox
) {
}
