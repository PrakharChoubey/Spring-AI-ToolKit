package com.springai.spring_ai_functions.functions;

import ch.qos.logback.core.util.StringUtil;
import com.springai.spring_ai_functions.model.CityDetails;
import com.springai.spring_ai_functions.model.WeatherRequest;
import com.springai.spring_ai_functions.model.WeatherResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.function.Function;

public class WeatherServiceFunction implements Function<WeatherRequest, WeatherResponse> {

    public static final String WEATHER_URL = "https://api.api-ninjas.com/v1/weather";
    public static final String CITY_DETAILS_URL = "https://nominatim.openstreetmap.org/search";
    private final String ninjaApiKey;

    public WeatherServiceFunction(String ninjaApiKey) {
        this.ninjaApiKey = ninjaApiKey;
    }

    @Override
    public WeatherResponse apply(WeatherRequest weatherRequest) {
        RestClient weatherRestClient = RestClient.builder()
                .baseUrl(WEATHER_URL)
                .defaultHeaders(httpHeaders -> {
                    httpHeaders.set("X-Api-Key", ninjaApiKey);
                    httpHeaders.set("Accept", "application/json");
                    httpHeaders.set("Content-Type", "application/json");
                })
                .build();

        return weatherRestClient.get().uri(uriBuilder -> {
            // returns 400 - Searching by city parameter requires a premium subscription.
//            uriBuilder.queryParam("city", weatherRequest.location());
//            if (!StringUtil.isNullOrEmpty(weatherRequest.state()))
//                uriBuilder.queryParam("state", weatherRequest.state());
//            if (!StringUtil.isNullOrEmpty(weatherRequest.country()))
//                uriBuilder.queryParam("country", weatherRequest.country());

            // fetching lat & lon from nominatim api
            CityDetails cityDetails = getDetailsByCity(weatherRequest.location()).get(0);
            WeatherRequest weatherRequestWithDetails = new WeatherRequest(cityDetails.name(), cityDetails.lat(), cityDetails.lon());

            if (weatherRequestWithDetails.lat() != null)
                uriBuilder.queryParam("lat", weatherRequestWithDetails.lat());
            if (weatherRequestWithDetails.lon() != null)
                uriBuilder.queryParam("lon", weatherRequestWithDetails.lon());

            System.out.println("URI for weather req == " + uriBuilder);
            return uriBuilder.build();
        }).retrieve().body(WeatherResponse.class);
    }

    //?q=London&format=json&limit=1
    private List<CityDetails> getDetailsByCity(String cityName) {
        RestClient cityDetailsRestClient = RestClient.builder()
                .baseUrl(CITY_DETAILS_URL)
                .defaultHeaders(httpHeaders -> {
                    httpHeaders.set("Accept", "application/json");
                    httpHeaders.set("Content-Type", "application/json");
                    httpHeaders.set("User-Agent", "WeatherApp 1.0");
                })
                .build();

        return cityDetailsRestClient.get().uri(uriBuilder -> {
            if (!StringUtil.isNullOrEmpty(cityName))
                uriBuilder.queryParam("q", cityName);
            uriBuilder.queryParam("format", "json");
            uriBuilder.queryParam("limit", 1);

            System.out.println("URI for city_details req == " + uriBuilder);
            return uriBuilder.build();
        }).retrieve().body(new ParameterizedTypeReference<List<CityDetails>>() {
        });
    }
}
