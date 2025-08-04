package com.springai.spring_ai_intro.model;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public record GetCaptialAndInfoResponse(@JsonPropertyDescription("This is the capital city") String capital,
                                        @JsonPropertyDescription("This is the primary language") String language,
                                        @JsonPropertyDescription("This is the major currency") String currency) {
}
