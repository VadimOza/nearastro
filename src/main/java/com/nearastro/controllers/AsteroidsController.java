package com.nearastro.controllers;

import com.nearastro.model.Asteroid;
import com.nearastro.model.DateRange;
import com.nearastro.service.NasaService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/asteroids")
public class AsteroidsController {

    private final NasaService service;

    @GetMapping
    public Collection<Asteroid> nearest(
            @RequestParam("start")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate start,

            @RequestParam("end")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate end
    ) {
        return service.fetch10Neares(start, end);
    }

    @GetMapping("/largest/{year}")
    public Asteroid largest(
            @PathVariable Integer year
    ) {
        return service.largest(year);
    }
}
