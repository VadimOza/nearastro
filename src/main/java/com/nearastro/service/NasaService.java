package com.nearastro.service;

import com.nearastro.model.Asteroid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NasaService {

    private final AsteroidService asteroidService;

    public Collection<Asteroid> fetch10Neares(LocalDate start, LocalDate end) {
        return asteroidService.findNearBetween(start, end)
                .sorted(Comparator.comparing(Asteroid::getMinDistance))
                .take(10)
                .collect(Collectors.toList())
                .block();
    }


    public Asteroid largest(Integer year) {
        var firstDayOfTheYear = LocalDate.of(year, 1, 1);
        var lastDayOfTheYear = LocalDate.of(year, 12, 31);

        return asteroidService.findNearBetween(firstDayOfTheYear, lastDayOfTheYear)
                .reduce(this::max)
                .block();
    }

    private Asteroid max(Asteroid a1, Asteroid a2) {
        return a1.getMaxDiameter() >= a2.getMaxDiameter() ? a1 : a2;
    }
}

