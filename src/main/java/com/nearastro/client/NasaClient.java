package com.nearastro.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.nearastro.model.DateRange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
public class NasaClient {

    public static final String CACHE_KEY = "asteroid";
    private final WebClient client;
    private final ReactiveHashOperations<String, String, JsonNode> cache;

    public NasaClient(
            @Value("${nasa.base.url}") String baseUrl,
            @Value("${nasa.api.key}") String apiKey,
            WebClient.Builder clientBuilder,
            ReactiveHashOperations<String, String, JsonNode> cache) {
        this.cache = cache;
        client = clientBuilder
                .baseUrl(baseUrl)
                .defaultUriVariables(Map.of("apiKey", apiKey))
                .build();
    }

    public Mono<JsonNode> getNearAsteroids(DateRange request) {
        System.out.println("Fetching for " + request);
        var requestHash = request.start().toString() + request.end().toString();
        return cache.get(CACHE_KEY, requestHash)
                .switchIfEmpty(requestNearAsteroids(request))
                .flatMap(node -> cache.put(CACHE_KEY, requestHash, node)
                        .thenReturn(node));
    }

    private Mono<JsonNode> requestNearAsteroids(DateRange request) {
        return client.get()
                .uri(uriBuilder ->
                        uriBuilder.path("/neo/rest/v1/feed")
                                .queryParam("start_date", request.start())
                                .queryParam("end_date", request.end())
                                .queryParam("api_key", "{apiKey}")
                                .build()
                ).retrieve()
                .bodyToMono(JsonNode.class)
                .doOnNext(a -> System.out.println("Made http call for " + request));
    }
}
