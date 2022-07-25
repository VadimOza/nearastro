package com.nearastro.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nearastro.client.NasaClient;
import com.nearastro.model.Asteroid;
import com.nearastro.model.DateRange;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.ParallelFlux;
import reactor.core.scheduler.Schedulers;

import java.time.LocalDate;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AsteroidService {

    private final NasaClient client;
    private final DateRangeCalculator dateRangeCalculator;
    private final ObjectMapper mapper;

    public ParallelFlux<Asteroid> findNearBetween(LocalDate start, LocalDate end) {
        return dateRangeCalculator.getSubrangesBetween(start, end)
                .parallel()
                .runOn(Schedulers.parallel())
                .flatMap(client::getNearAsteroids)
                .flatMap(this::parseAsteroids);
    }

    private Flux<Asteroid> parseAsteroids(JsonNode node) {
        List<Asteroid> result = new LinkedList<>();

        var dayIterator = node.get("near_earth_objects").fields();
        while (dayIterator.hasNext()) {
            var day = dayIterator.next();

            for (JsonNode asteroid : day.getValue()) {
                try {
                    result.add(mapper.readValue(asteroid.toString(), Asteroid.class));
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return Flux.fromIterable(result);
    }
}
